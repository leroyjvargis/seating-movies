package dev.leroyjv;

import dev.leroyjv.Models.Reservation;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 *
 */
public class MovieSeating {
    private final int NUMBER_OF_ROWS = 10;
    private final int NUMBER_OF_SEATS_IN_ROW = 20;
    private int remainingSeats = this.NUMBER_OF_ROWS * this.NUMBER_OF_SEATS_IN_ROW;

    private final ArrayList<Reservation> reservationRequests; // the input file will be parsed into this datastructure
    private final ArrayList<ArrayList<Reservation>> seatMatrix; // stores assigned seats

    /**
     * Constructor that takes in input reservations and assigns seats
     */
    private MovieSeating(ArrayList<Reservation> reservations) {
        this.reservationRequests = reservations;
        this.seatMatrix = new ArrayList<ArrayList<Reservation>>();

        for (int i=0; i < NUMBER_OF_ROWS; i++) {
            this.seatMatrix.add(new ArrayList<Reservation>());
        }

        this.assignSeatsToReservationList();
    }

    /**
     * Creates a new instance of the class
     * @param reservations input reservation requests
     * @return new instance with assigned seats
     */
    public static MovieSeating newInstance(ArrayList<Reservation> reservations) {
        return new MovieSeating(reservations);
    }

    /**
     * Gets the constructed seat arrangement matrix
     * @return seat matrix
     */
    public ArrayList<ArrayList<Reservation>> getSeatMatrix() {
        return this.seatMatrix;
    }

    /**
     * Business logic:
     * Seats of the same reservation are tried to be put together in the same row
     * If the above cannot be done, priority is given to assign maximum seats together in different rows.
     */
    public void assignSeatsToReservationList() {
        for (Reservation reservation : reservationRequests) {
            this.assignSeatsToReservation(reservation);
        }
    }

    /**
     * Assigns seats in a single reservation
     * @param reservation the reservation request being fulfilled
     */
    public void assignSeatsToReservation(Reservation reservation) {
        final int numberOfSeatsToReserve = reservation.getNumberOfSeats();

        // if remaining seats is not enough to fulfill all requested number of seats of the reservation, the reservation is skipped
        if (this.remainingSeats - numberOfSeatsToReserve >= 0) {
            final int bestRowIdx = this.findRowToFitSeats(numberOfSeatsToReserve);
            if (bestRowIdx >= 0) {
                this.assignSeatsToRow(numberOfSeatsToReserve, bestRowIdx, reservation);
            } else {
                this.assignSeatsToMultipleRows(numberOfSeatsToReserve, reservation);
            }

            remainingSeats -= numberOfSeatsToReserve;
        } else {
            System.out.println("Not enough seats left to fulfill reservation: " + reservation.getReservationId());
        }
    }

    /**
     * Assigns the requested seats on to the specified row, and updates seatMatrix with reservation info
     * @param seatCount count of seats requested
     * @param currentRowIdx the row to assign those seats
     * @param reservation the reservation info being fulfilled
     */
    private void assignSeatsToRow(int seatCount, int currentRowIdx, Reservation reservation){
        for (int i =0; i <seatCount; i++) {
            this.seatMatrix.get(currentRowIdx).add(reservation);
        }
    }

    /**
     * Assigns requested seats to multiple rows, fulfilling all seat requests
     * Priority to seat maximum tickets together in same rows
     * @param seatsToAssign the seats requested
     * @param reservation the current reservation
     */
    private void assignSeatsToMultipleRows(int seatsToAssign, Reservation reservation) {
        while (seatsToAssign > 0) {
            final int bestRowIdx = this.findRowWithMaxEmptySeats();
            final int seatsAssigned = Math.min(this.NUMBER_OF_SEATS_IN_ROW - this.seatMatrix.get(bestRowIdx).size(), seatsToAssign);
            this.assignSeatsToRow(seatsAssigned, bestRowIdx, reservation);
            seatsToAssign -= seatsAssigned;
        }
    }

    /**
     * Finds best row to assign the maximum seats.
     * Best row here is the one that has the most empty seats.
     * @return best row idx
     */
    private int findRowWithMaxEmptySeats() {
        int bestIdx = 0;
        for (int i=1; i < this.seatMatrix.size(); i++) {
            if (this.seatMatrix.get(bestIdx).size() > this.seatMatrix.get(i).size()) {
                bestIdx = i;
            }
        }
        return bestIdx;
    }

    /**
     * Finds best row to assign all the requested seats together
     * @param seatsToAssign the count of seats to assign together
     * @return the index of row assigned, else -1 to denote not possible to assign all together
     */
    private int findRowToFitSeats(int seatsToAssign) {
        for (int i = 0; i < this.seatMatrix.size(); i++) {
            if (this.NUMBER_OF_SEATS_IN_ROW - this.seatMatrix.get(i).size() > seatsToAssign) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Parses seat matrix and converts to human readable format
     * @return human readable reservation seating chart.
     */
    public LinkedHashMap<String, ArrayList<String>> getReservationSeatingArrangement() {
        // LinkedHashMap used here to maintain order 
        // input reservation order must be same as output seating arrangement order 
        final LinkedHashMap<String, ArrayList<String>> reservedSeats = new LinkedHashMap<>();
        
        for (Reservation reservation: this.reservationRequests) {
            for (int i=0; i< this.seatMatrix.size(); i++) {
                for (int j=0; j < this.seatMatrix.get(i).size(); j++) {
                    Reservation currentReservation = this.seatMatrix.get(i).get(j);
                    
                    if (currentReservation.getReservationId().equals(reservation.getReservationId())) {
                        char row = (char) ('A' + i);
                        
                        if (reservedSeats.containsKey(currentReservation.getReservationId())) {
                            var addedValues = reservedSeats.get(currentReservation.getReservationId());
                            addedValues.add(row + Integer.toString(j+1));
                            reservedSeats.replace(currentReservation.getReservationId(), addedValues);
                        } else {
                            var value = new ArrayList<String>();
                            value.add(row + Integer.toString(j+1));
                            reservedSeats.put(currentReservation.getReservationId(), value);
                        }
                    }
                }
            }
        }
        return  reservedSeats;
    }
}
