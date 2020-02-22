package dev.leroyjv;

import dev.leroyjv.Models.Reservation;
import dev.leroyjv.Utils.FileUtil;
import test.FileHandlerTest;
import test.SeatAssignTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

/**
 * Main class of app
 */
public class MovieSeatingApp {
    static final String outputFileName = "assigned_seats.txt";

    /**
     * Main function
     * @param args commandline arguments
     * @throws IOException on file read/write exception
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 0) {
            final String mode = args[0];

            if ("assign".equals(mode)) {
                final String filePath = args[1];

                final FileUtil fileHandler = new FileUtil();
                final ArrayList<Reservation> reservationData = fileHandler.readReservationData(filePath);
                final MovieSeating movieSeating = MovieSeating.newInstance(reservationData);

                final Map<String, ArrayList<String>> seatReservations = movieSeating.getReservationSeatingArrangement();
                final String outputFilepath = constructOutputFilePath(filePath, outputFileName);
                fileHandler.writeReservationData(outputFilepath, seatReservations);
                System.out.println("Saved output file to: " + outputFilepath);
            } else if ("test".equals(mode)) {
                testAll();
            } else {
                System.out.println("Invalid mode.");
            }

            System.out.println("Done!");
        } else {
            System.out.println("Please include mode (assign / test), and input filepath if assign.");
        }
    }

    /**
     * Constructs output file path
     * The directory of inputFile is used for output file as well.
     * @param inputFilePath the input filepath
     * @param outputFileName the output file name
     * @return outputFilePath
     */
    public static String constructOutputFilePath(String inputFilePath, String outputFileName) {
        final Path pathStr = Paths.get(inputFilePath);
        return pathStr.getParent().toString() + File.separator + outputFileName;
    }

    /**
     * Run all test cases
     */
    public static void testAll() {
        final FileHandlerTest fileHandlerTest = new FileHandlerTest();
        fileHandlerTest.testFileRead();
        fileHandlerTest.testEmptyFileRead();
        fileHandlerTest.testEmptyLineFileRead();
        fileHandlerTest.testNegativeSeatsFileRead();
        fileHandlerTest.testMissingSeats();
        fileHandlerTest.testMissingReservationId();

        final SeatAssignTest seatAssignTest = new SeatAssignTest();
        seatAssignTest.testAllReservationsAssign();
        seatAssignTest.testReservationAssign();
        seatAssignTest.testSeatingArrangement();
        seatAssignTest.testReservationAdd();

        System.out.println("All test cases pass!");
    }
}
