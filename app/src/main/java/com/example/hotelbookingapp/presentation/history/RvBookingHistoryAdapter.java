package com.example.hotelbookingapp.presentation.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbookingapp.data.dto.hotel_booking.BookingDetails;
import com.example.hotelbookingapp.databinding.HistoryItemViewHolderBinding;
import com.example.hotelbookingapp.domain.model.UserRole;

import java.time.LocalDate;
import java.util.List;

public class RvBookingHistoryAdapter extends RecyclerView.Adapter<RvBookingHistoryAdapter.BookedHotelViewHolder> {

    private static OnBookingClickListener listener;
    private static OnApproveClickListener approveListener;
    private final List<BookingDetails> bookingList;
    private final String userRole;

    public RvBookingHistoryAdapter(String userRole, List<BookingDetails> bookingList, OnBookingClickListener listener,
                                   OnApproveClickListener onApproveClickListener) {
        this.bookingList = bookingList;
        this.userRole = userRole;
        RvBookingHistoryAdapter.listener = listener;
        RvBookingHistoryAdapter.approveListener = onApproveClickListener;
    }

    @Override
    public BookedHotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookedHotelViewHolder(
                HistoryItemViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                userRole
        );
    }

    @Override
    public void onBindViewHolder(BookedHotelViewHolder holder, int position) {
        holder.bind(bookingList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public interface OnBookingClickListener {
        void onBookingClick(BookingDetails bookingDetails);
    }

    public interface OnApproveClickListener {
        void onApproveClick(int bookingId);
    }

    public static class BookedHotelViewHolder extends RecyclerView.ViewHolder {

        private final HistoryItemViewHolderBinding binding;
        private BookingDetails bookedHotel;
        private String userRole;

        public BookedHotelViewHolder(HistoryItemViewHolderBinding binding, String userRole) {
            super(binding.getRoot());
            this.binding = binding;

            if (userRole.equals(UserRole.ROLE_ADMIN.name()))
                binding.buttonApproveBooking.setVisibility(View.VISIBLE);

            binding.buttonCancelBooking.setOnClickListener(v -> {
                if (bookedHotel != null) {
                    listener.onBookingClick(bookedHotel);
                }
            });
        }

        public void bind(BookingDetails curBookedHotel) {
            binding.textViewBookedHotelName.setText(curBookedHotel.getHotelName());
            binding.textViewBookedCheckIn.setText(curBookedHotel.getCheckInDate());
            binding.textViewBookedCheckOut.setText(curBookedHotel.getCheckOutDate());
            if (curBookedHotel.isApproved()) {
                binding.textViewBookingStat.setText("Approved");
                binding.buttonApproveBooking.setVisibility(View.GONE);
            } else {
                binding.textViewBookingStat.setText("Pending");
                binding.buttonApproveBooking.setVisibility(View.VISIBLE);
            }
            if (datePassed(curBookedHotel.getCheckInDate())) {
                binding.buttonCancelBooking.setVisibility(View.GONE);
            }

            bookedHotel = curBookedHotel;
        }

        private boolean datePassed(String dateString) {
            try {
                LocalDate date = LocalDate.parse(dateString);
                return LocalDate.now().isAfter(date);
            } catch (Exception e) {
                return false;
            }
        }
    }
}
