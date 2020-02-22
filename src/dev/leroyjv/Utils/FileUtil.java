package dev.leroyjv.Utils;

import dev.leroyjv.Models.Reservation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FileUtil {
    /**
     * Reads the files, parses data and converts into Reservation data structure
     * @param filePath the input filepath
     * @return list of Reservations
     */
    public ArrayList<Reservation> readReservationData(String filePath) {
        ArrayList<Reservation> reservationsData = new ArrayList<Reservation>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                Reservation currentReservationData = new Reservation();

                // check edge cases - line contains space and not just space
                if (line.contains(" ") && line.length() > 1) {
                    try {
                        String[] split_line = line.split(" ");
                        int seatsRequested = Integer.parseInt(split_line[1]);

                        // check edge case - seatsRequested is non-zero and non-negative
                        if (seatsRequested > 0) {
                            currentReservationData.setReservationId(split_line[0]);
                            currentReservationData.setNumberOfSeats(seatsRequested);
                            reservationsData.add(currentReservationData);
                        }
                    } catch (Exception e) {
                        // skip current line if it is in a non-expected format
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return reservationsData;
    }

    /**
     * Writes human readable reservation seating chart to file
     * @param outputFilePath the output filepath
     * @param reservationSeating the constructed reservation chart
     * @throws IOException on writing file
     */
    public void writeReservationData(String outputFilePath, Map<String, ArrayList<String>> reservationSeating) throws IOException {
        FileWriter writer = new FileWriter(outputFilePath);
        for (var reservation: reservationSeating.entrySet()) {
            String outputLine = reservation.getKey() + " " + String.join(",", reservation.getValue()) + System.lineSeparator();
//            System.out.println(outputLine);
            writer.write(outputLine);
        }
        writer.close();
    }
}
