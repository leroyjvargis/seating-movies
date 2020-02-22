package test;

import dev.leroyjv.Models.Reservation;
import dev.leroyjv.Utils.FileUtil;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    final FileUtil fileHandler = new FileUtil();
    String testFilePath;

    @Test
    public void testFileRead() {
        testFilePath = "resources/tests/test_file.txt";
        ArrayList<Reservation> reservations = fileHandler.readReservationData(testFilePath);

        assertEquals(4, reservations.size());
    }

    @Test
    public void testEmptyFileRead() {
        testFilePath = "resources/tests/empty_file.txt";
        ArrayList<Reservation> reservations = fileHandler.readReservationData(testFilePath);

        assertEquals(0, reservations.size());
    }

    @Test
    public void testEmptyLineFileRead() {
        testFilePath = "resources/tests/test_file_space.txt";
        ArrayList<Reservation> reservations = fileHandler.readReservationData(testFilePath);

        assertEquals(3, reservations.size());
    }

    @Test
    public void testNegativeSeatsFileRead() {
        testFilePath = "resources/tests/test_file_negative.txt";
        ArrayList<Reservation> reservations = fileHandler.readReservationData(testFilePath);

        assertEquals(3, reservations.size());
    }

    @Test
    public void testMissingSeats() {
        testFilePath = "resources/tests/test_file_missingdata.txt";
        ArrayList<Reservation> reservations = fileHandler.readReservationData(testFilePath);

        assertEquals(3, reservations.size());
    }

    @Test
    public void testMissingReservationId() {
        testFilePath = "resources/tests/test_file_missingdata2.txt";
        ArrayList<Reservation> reservations = fileHandler.readReservationData(testFilePath);

        assertEquals(3, reservations.size());
    }
}
