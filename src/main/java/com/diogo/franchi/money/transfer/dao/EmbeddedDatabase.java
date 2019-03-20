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
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ACCOUNT(id varchar(255) not null, number varchar(255), amount decimal(19,2), PRIMARY KEY(id))";
	
	public static void openServerDataBase(){
		try {
			Server.createTcpServer().start();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public static void closeDataBase(){
		try {
			Server.createTcpServer().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public static Connection getDBConnection(){
        Connection dbConnection = null;
        try {
			Class.forName(DB_DRIVER);
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return dbConnection;
    }
	
	public static void createTable(){
        Connection connection = getDBConnection();
        PreparedStatement createPreparedStatement = null;
        try {
            try {
				connection.setAutoCommit(false);
				createPreparedStatement = connection.prepareStatement(CREATE_TABLE);
				createPreparedStatement.executeUpdate();
				createPreparedStatement.close();
				connection.commit();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        } finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
     }
	
}
