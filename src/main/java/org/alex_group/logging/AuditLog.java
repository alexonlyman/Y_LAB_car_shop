package org.alex_group.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * The AuditLog class provides a static method for logging audit messages.
 * It logs messages using a Logger and prints additional information to the
 * standard output.
 *
 * This class contains one method:
 * - log(String message)
 */

public class AuditLog {
    private static final Logger logger = LogManager.getLogger(AuditLog.class);

    /**
     * Logs the given message.
     *
     * The message is logged at the INFO level using a Logger. Additionally,
     * information about the logging attempt, the logger's name, and the
     * logger's level is printed to the standard output.
     *
     * @param message The message to log. Cannot be null.
     *
     * @throws IllegalArgumentException If message is null.
     */
    public static void log(String message) {
        logger.info(message);
        System.out.println("Attempting to log: " + message);
        System.out.println("Logger name: " + logger.getName());
        System.out.println("Logger level: " + logger.getLevel());
    }
}
