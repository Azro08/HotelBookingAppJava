package com.example.hotelbookingapp.presentation.hotels;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.hotelbookingapp.R;
import com.example.hotelbookingapp.data.dto.hotel.HotelResponse;
import com.example.hotelbookingapp.data.dto.hotel.Property;
import com.example.hotelbookingapp.data.dto.hotel.SingleHotelItem;
import com.example.hotelbookingapp.databinding.FragmentHotelsBinding;
import com.example.hotelbookingapp.helper.AuthManager;
import com.example.hotelbookingapp.helper.Constants;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.disposables.Disposable;

@AndroidEntryPoint
public class HotelsFragment extends Fragment {
    @Inject
    AuthManager authManager;
    private FragmentHotelsBinding binding;
    private HotelsRvAdapter rvAdapter;

    private HotelsListViewModel viewModel;
    private String currentLocation;
    private String checkInDate;
    private String checkOutDate;
    private Disposable subscription;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHotelsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HotelsListViewModel.class);
        binding.swipeRefreshLayout.setOnRefreshListener(this::refreshFragment);
        assert getArguments() != null;
        currentLocation = getArguments().getString(Constants.LOCATION_KEY);
        Log.d("currentLocation", currentLocation);
        if (currentLocation != null) {
            binding.textViewCurLocation.setVisibility(View.VISIBLE);
            binding.textViewCurLocation.setText(currentLocation);
        }
        checkInDate = getArguments().getString(Constants.CHECK_IN_DATE_KEY);
        checkOutDate = getArguments().getString(Constants.CHECK_OUT_DATE_KEY);
        viewModelOutputs(currentLocation, checkInDate, checkOutDate);
    }

    private void refreshFragment() {
        binding.loadingGif.setVisibility(View.VISIBLE);
        binding.recyclerViewHotels.setVisibility(View.GONE);
        binding.textViewError.setVisibility(View.GONE);

        new Handler().postDelayed(() -> {
            // This code will run after a 3-second delay
            viewModelOutputs(currentLocation, checkInDate, checkOutDate);
            binding.swipeRefreshLayout.setRefreshing(false);
            binding.loadingGif.setVisibility(View.GONE);
            binding.recyclerViewHotels.setVisibility(View.VISIBLE);
        }, 3000); // 3000 milliseconds (3 seconds)
    }


    private void viewModelOutputs(String currentLocation, String checkInDate, String checkOutDate) {
        viewModel.getHotelsList(currentLocation, checkInDate, checkOutDate);
        viewModel.getHotelsList().observe(getViewLifecycleOwner(), hotelResponseObservable -> {
            if (hotelResponseObservable != null) {
                subscription = hotelResponseObservable.subscribe(
                        response -> {
                            if (response != null && !response.getProperties().isEmpty()) {
                                displayHotels(response);
                            } else {
                                viewModel.getResponseError().observe(getViewLifecycleOwner(), this::handleError);
                            }
                        },
                        error -> handleError(error.getMessage())
                );
            }
        });
        viewModel.getResponseError().observe(getViewLifecycleOwner(), this::handleError);
    }

    private void handleError(String error) {
        if (error != null) {
            binding.loadingGif.setVisibility(View.GONE);
            binding.recyclerViewHotels.setVisibility(View.GONE);
            binding.textViewError.setVisibility(View.VISIBLE);
            binding.textViewError.setText(error);
        }
    }


    private void displayHotels(HotelResponse hotelData) {
        binding.textViewCurLocation.setText(currentLocation);
        binding.loadingGif.setVisibility(View.GONE);
        binding.recyclerViewHotels.setVisibility(View.VISIBLE);
        binding.textViewError.setVisibility(View.GONE);
        rvAdapter = new HotelsRvAdapter(hotelData.getProperties(), this::navToDetails, this::addHotelToFav);
        binding.recyclerViewHotels.setHasFixedSize(false);
        binding.recyclerViewHotels.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerViewHotels.setAdapter(rvAdapter);
    }

    private void addHotelToFav(Property property) {
        String hotelId = property.getId();
        int userId = authManager.getUserId();
        String imageUrl = property.getPropertyImage().getImage().getUrl();
        double reviews = property.getReviews().getScore();
        String name = property.getName();
        String price = property.getPrice().getDisplayMessages().get(0).getLineItems().get(0).getPrice().getPriceTag();
        String neighborhood = property.getNeighborhood() != null ? property.getNeighborhood().getName() : null;

        SingleHotelItem hotel = new SingleHotelItem();
        hotel.setHotelId(hotelId);
        hotel.setUserId(userId);
        hotel.setImageUrl(imageUrl);
        hotel.setReview(reviews);
        hotel.setName(name);
        hotel.setPrice(price);
        hotel.setNeighborhood(neighborhood);
        viewModel.addToFavorites(hotel);
        if (viewModel.getAddToFavError().isEmpty()) Toast.makeText(getContext(), "Failed " + viewModel.getAddToFavError(), Toast.LENGTH_SHORT).show();
        else Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

    }

    private void navToDetails(Property data) {
        Bundle bundle = new Bundle();
        bundle.putDouble(Constants.SCORE_KEY, data.getReviews().getScore());
        bundle.putString(Constants.HOTEL_ID_KEY, data.getId());
        double price = extractDoubleFromPriceString(data.getPrice().getDisplayMessages().get(0).getLineItems().get(0).getPrice().getPriceTag());
        bundle.putDouble(Constants.HOTEL_PRICE_KEY, price);
        NavHostFragment.findNavController(this).navigate(
                R.id.nav_hotel_to_details,
                bundle
        );
    }

    public static double extractDoubleFromPriceString(String priceString) {
        // Remove any non-numeric characters except for '.' (decimal point)
        String numericString = priceString.replaceAll("[^0-9.]", "");

        try {
            // Parse the numeric string to double
            double extractedPrice = Double.parseDouble(numericString);
            return extractedPrice;
        } catch (NumberFormatException e) {
            // Handle parsing error (e.g., if string is not a valid number)
            System.out.println("Error parsing price string: " + e.getMessage());
            return 0.0; // Return default value or handle the error as needed
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.loadingGif.setVisibility(View.VISIBLE);
        binding.recyclerViewHotels.setVisibility(View.GONE);
        binding.textViewError.setVisibility(View.GONE);
        viewModelOutputs(currentLocation, checkInDate, checkOutDate);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        rvAdapter = null;
        if (subscription != null && !subscription.isDisposed()) {
            subscription.dispose();
        }
    }
}
