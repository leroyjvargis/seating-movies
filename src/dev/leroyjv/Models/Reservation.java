package dev.leroyjv.Models;

/**
 * Reservation Model - with reservationId and number of seats requested
 */
public class Reservation {
    private String reservationId;
    private int numberOfSeats;

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
