package se.kth.sebte.iv1350.pos.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author sebastian taavo ek
 * A utility class used to write any given string to a specific path. Used when logging an exception in <code>View</code>, and when
 * registering a new total sum of revenue in <code>TotalRevenueFileOutput</code>.
 *
 */
public class Logger {
	/**
	 * Logs a String to a given path. Can be accessed statically anywhere in the program.
	 * If the destination could not be found, throws and handles an IOException. This exception is not logged in the ExceptionLog.
	 * @param message The string to be written to a document.
	 * @param path The destination document to write to. Generates a new one if none can be found.
	 * @param append Boolean deciding if we erase the previous content of the document when writing to it, or if we simply append a line.
	 */
	public static void log(String message, String path, boolean append) {
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = timestamp.format(formatter);
        String logEntry = "[" + formattedTime + "] " + message;

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(path, append)))) {
            writer.println(logEntry);
        } catch (IOException e) {
        	e.printStackTrace();
            System.err.println("Error writing to log file.");
        }
	}
	
}
