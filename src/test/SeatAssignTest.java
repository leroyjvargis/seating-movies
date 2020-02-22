package test;

import dev.leroyjv.Models.Reservation;
import dev.leroyjv.MovieSeating;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class SeatAssignTest {

    @Test
    public void testAllReservationsAssign() {
        ArrayList<Reservation> dummyReservations = createDummyReservations(5);
        final MovieSeating movieSeating = MovieSeating.newInstance(dummyReservations);

        var seatMatrix = movieSeating.getSeatMatrix();
        int totalSeatsAssigned = seatMatrix.get(0).size();
        assertEquals(15, totalSeatsAssigned);
    }

    @Test
    public void testReservationAssign() {
        ArrayList<Reservation> dummyReservations = createDummyReservations(5);
        final MovieSeating movieSeating = MovieSeating.newInstance(dummyReservations);

        var seatMatrix = movieSeating.getSeatMatrix();

        int counter = 0;
        for(Reservation reservation : seatMatrix.get(0)) {
            if (reservation.getReservationId().equals("R005")) {
                counter++;
            }
        }

        assertEquals(5, counter);
    }

    @Test
    public void testSeatingArrangement() {
        ArrayList<Reservation> dummyReservations = createDummyReservations(5);
        final MovieSeating movieSeating = MovieSeating.newInstance(dummyReservations);

        var seatingArrangement = movieSeating.getReservationSeatingArrangement();

        assertEquals(5, seatingArrangement.size());
    }

    /**
     * Assign reservations totalling 15 seats first
     * Then assign new reservation with 10 more seats
     * The first 15 seats should be in the first row
     * The next 10 seats should be in the second row
     */
    @Test
    public void testReservationAdd() {
        ArrayList<Reservation> dummyReservations = createDummyReservations(5);
        final MovieSeating movieSeating = MovieSeating.newInstance(dummyReservations);

        int totalSeatsAssigned = movieSeating.getSeatMatrix().get(0).size();

        assertEquals(15, totalSeatsAssigned);

        var newReservation = new Reservation();
        newReservation.setReservationId("R006");
        newReservation.setNumberOfSeats(10);

        movieSeating.assignSeatsToReservation(newReservation);

        totalSeatsAssigned = movieSeating.getSeatMatrix().get(1).size();
        assertEquals(10, totalSeatsAssigned);
    }

    /**
     * Dummy input generator
     * @param count the count of dummy data to generate
     * @return dummy data as Reservation list
     */
    private ArrayList<Reservation> createDummyReservations(int count) {
        ArrayList<Reservation> dummyReservations = new ArrayList<>();
        Reservation reservation;
        for (int i=1; i <= count; i++) {
            reservation = new Reservation();
            reservation.setReservationId("R00" + Integer.toString(i));
            reservation.setNumberOfSeats(i);
            dummyReservations.add(reservation);
        }
        return dummyReservations;
    }
}
