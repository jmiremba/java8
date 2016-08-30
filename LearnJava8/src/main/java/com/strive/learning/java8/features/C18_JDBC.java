package com.strive.learning.java8.features;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;

@SuppressWarnings("unused")
public class C18_JDBC {
	
	public static void main(String[] args) {
		updateDatabase();
	}

	private static void updateDatabase() {
		// Using statement
		try (Connection connection = getConnection();
				Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ResultSet resultset = statement.executeQuery("select * from accounts")) {
			String fmt = "%5s %10s %12s %12s %15s %25s %7s %15s %10s";
			System.out.println(String.format(fmt, "ID", "Status", "Created", "Deletion", "Emergency", "Modified",
					"Type", "Undistributed", "Version"));

			System.out.println("\nOriginal records ...");
			while(resultset.next()) {
				System.out.println(String.format(fmt, 
						resultset.getLong("id"),
						resultset.getString("account_status"),
						resultset.getDate("date_created"),
						resultset.getDate("deletion_date"),
						resultset.getBigDecimal("emergency_amount"),
						resultset.getTimestamp("last_modified"),
						resultset.getString("subscription_type"),
						resultset.getBigDecimal("undistributed_amount"),
						resultset.getLong("version")
					));
			}
			
			// Update the first row's version
			System.out.println("\nUpdating the version of the first record ...");
			resultset.absolute(1);
			resultset.updateLong("version", resultset.getLong("version") + 1);
			resultset.updateRow();
			resultset.beforeFirst();
			while(resultset.next()) {
				System.out.println(String.format(fmt, 
						resultset.getLong("id"),
						resultset.getString("account_status"),
						resultset.getDate("date_created"),
						resultset.getDate("deletion_date"),
						resultset.getBigDecimal("emergency_amount"),
						resultset.getTimestamp("last_modified"),
						resultset.getString("subscription_type"),
						resultset.getBigDecimal("undistributed_amount"),
						resultset.getLong("version")
					));
			}
			
			// Insert a row
			System.out.println("\nInserting a row ...");
			resultset.moveToInsertRow();
			resultset.updateString("account_status", "DISABLED");
			resultset.updateDate("date_created", new Date(Instant.now().toEpochMilli()));
			resultset.updateDate("deletion_date", new Date(Instant.now().toEpochMilli()));
			resultset.updateBigDecimal("emergency_amount", new BigDecimal(123.45));
			resultset.updateTimestamp("last_modified", new Timestamp(Instant.now().toEpochMilli()));
			resultset.updateString("subscription_type", "FREE");
			resultset.updateBigDecimal("undistributed_amount", new BigDecimal(6789.01));
			resultset.updateLong("version", 1);
			resultset.insertRow();
			resultset.beforeFirst();
			while(resultset.next()) {
				System.out.println(String.format(fmt, 
						resultset.getLong("id"),
						resultset.getString("account_status"),
						resultset.getDate("date_created"),
						resultset.getDate("deletion_date"),
						resultset.getBigDecimal("emergency_amount"),
						resultset.getTimestamp("last_modified"),
						resultset.getString("subscription_type"),
						resultset.getBigDecimal("undistributed_amount"),
						resultset.getLong("version")
					));
			}
			
			// Delete a row
			System.out.println("\nDeleting the last row ...");
			resultset.absolute(3);
			resultset.deleteRow();
			resultset.close();
			ResultSet UpdatedResultset = statement.executeQuery("select * from accounts");
			while(UpdatedResultset.next()) {
				System.out.println(String.format(fmt, 
						UpdatedResultset.getLong("id"),
						UpdatedResultset.getString("account_status"),
						UpdatedResultset.getDate("date_created"),
						UpdatedResultset.getDate("deletion_date"),
						UpdatedResultset.getBigDecimal("emergency_amount"),
						UpdatedResultset.getTimestamp("last_modified"),
						UpdatedResultset.getString("subscription_type"),
						UpdatedResultset.getBigDecimal("undistributed_amount"),
						UpdatedResultset.getLong("version")
					));
			}
		} catch (Exception e) {
			System.err.println("Database connection failed: "+e.getMessage());
			e.printStackTrace();
		}
	}

	private static void queryDatabase() {
		// Using statement
		try (Connection connection = getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultset = statement.executeQuery("select * from accounts")) {
			String fmt = "%5s %10s %12s %12s %15s %25s %7s %15s %10s";
			System.out.println(String.format(fmt, "ID", "Status", "Created", "Deletion", "Emergency", "Modified",
					"Type", "Undistributed", "Version"));
			while(resultset.next()) {
				System.out.println(String.format(fmt, 
						resultset.getLong("id"),
						resultset.getString("account_status"),
						resultset.getDate("date_created"),
						resultset.getDate("deletion_date"),
						resultset.getBigDecimal("emergency_amount"),
						resultset.getTimestamp("last_modified"),
						resultset.getString("subscription_type"),
						resultset.getBigDecimal("undistributed_amount"),
						resultset.getLong("version")
						));
			}
		} catch (Exception e) {
			System.err.println("Database connection: Failed");
			e.printStackTrace();
		}
	}

	private static Connection getConnection() throws SQLException {
		// Connection settings
		String databaseUrl = "jdbc:mysql://localhost:3306/geldzin4";
		String username = "root";
		String password = "jubilee";
		
		/**
		 * Prevent java.sql.SQLException: The server time zone value 'MDT' is unrecognized or represents more than one time zone.
		 */
		databaseUrl += "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=America/Denver";
		
		/**
		 * Prevent WARN: Establishing SSL connection without server's identity verification is not recommended. 
		 * According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit 
		 * option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set 
		 * to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore 
		 * for server certificate verification.
		 */
		databaseUrl += "&useSSL=false";

		// Connection
		return DriverManager.getConnection(databaseUrl, username, password);
	}
}
