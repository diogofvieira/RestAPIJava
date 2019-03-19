package com.diogo.franchi.money.transfer.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.h2.tools.Server;

public class EmbeddedDatabase {
	
	private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";
	
	public static void openServerDataBase() {
        Server server;
		try {
			server = Server.createTcpServer().start();
			System.out.println("Server started and connection is open.");
			System.out.println("URL: jdbc:h2:" + server.getURL() + "/mem:test");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
	public static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
	
	public static void createTable() {
        Connection connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;

        String createQuery = "CREATE TABLE ACCOUNT(id varchar(255) not null, number varchar(255), amount decimal(19,2), PRIMARY KEY(id))";
        
        try {
            connection.setAutoCommit(false);

            createPreparedStatement = connection.prepareStatement(createQuery);
            createPreparedStatement.executeUpdate();
            createPreparedStatement.close();
            System.out.println(createQuery);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }
	
	
	
	
	
    
    
	
}
