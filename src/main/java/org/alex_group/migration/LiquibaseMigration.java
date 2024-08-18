package org.alex_group.migration;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * The LiquibaseMigration class provides a static method for applying Liquibase database migrations.
 * It connects to a PostgreSQL database and performs database schema updates based on the Liquibase changelog.
 *
 * <p>This class contains one method:</p>
 * <ul>
 *     <li>{@link #migrate()}</li>
 * </ul>
 */

public class LiquibaseMigration {

    /**
     * Applies database migrations using Liquibase.
     *
     * <p>This method establishes a connection to a PostgreSQL database using JDBC, then initializes Liquibase
     * with the specified changelog file. It applies any pending migrations to the database schema.</p>
     *
     * <p>If an error occurs during the migration process, the exception is printed to the standard error stream.</p>
     *
     * @throws RuntimeException If there is an error connecting to the database or applying the migrations.
     */
    public static void migrate() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/car_shop",
                "alex",
                "password"
        )) {
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase("db/changelog/db.changelog-master.yml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
