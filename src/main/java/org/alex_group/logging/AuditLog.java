package org.alex_group.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuditLog {
    private static final Logger logger = LogManager.getLogger(AuditLog.class);

    public static void log(String message) {
        logger.info(message);
        System.out.println("Attempting to log: " + message);
        System.out.println("Logger name: " + logger.getName());
        System.out.println("Logger level: " + logger.getLevel());
    }
}
