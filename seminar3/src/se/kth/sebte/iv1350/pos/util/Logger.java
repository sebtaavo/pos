package se.kth.sebte.iv1350.pos.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
	public static void log(String message, String path, boolean append) {
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = timestamp.format(formatter);
        String logEntry = "[" + formattedTime + "] " + message;

        // Write to the log file
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path, append)))) {
            writer.println(logEntry);
        } catch (IOException e) {
        	e.printStackTrace();
            System.err.println("Error writing to log file.");
        }
	}
	
}
