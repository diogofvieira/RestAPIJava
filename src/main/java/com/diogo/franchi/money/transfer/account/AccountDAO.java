package com.diogo.franchi.money.transfer.account;

import com.diogo.franchi.money.transfer.dao.EmbeddedDatabase;
import com.diogo.franchi.money.transfer.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public Account find(String accountNumber) {
    	Connection connection = EmbeddedDatabase.getDBConnection();
        PreparedStatement selectPreparedStatement = null;
        Account account = new Account();
        String SelectQuery = "select * from ACCOUNT where number = ?";
        try {
            connection.setAutoCommit(false);
            selectPreparedStatement = connection.prepareStatement(SelectQuery);
            selectPreparedStatement.setString(1, accountNumber);
            ResultSet rs = selectPreparedStatement.executeQuery();
            while (rs.next()) {
            	account.setId(rs.getString("id"));
            	account.setAccountNumber(rs.getString("number"));
            	account.setAmount(rs.getBigDecimal("amount"));
            }
            selectPreparedStatement.close();
            connection.commit();
            System.out.println(account);
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
        return account;
    }

    public Account save(Account account) {
		Connection connection = EmbeddedDatabase.getDBConnection();
        PreparedStatement insertPreparedStatement = null;
        String insertQuery = "insert into ACCOUNT" + "(id, number, amount) values" + "(?,?,?)";
        try {
            connection.setAutoCommit(false);
            insertPreparedStatement = connection.prepareStatement(insertQuery);
            insertPreparedStatement.setString(1, account.getId());
            insertPreparedStatement.setString(2, account.getAccountNumber());
            insertPreparedStatement.setBigDecimal(3, account.getAmount());
            insertPreparedStatement.executeUpdate();
            insertPreparedStatement.close();
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
		return account;
    }
    
    public List<Account> findAll() {
		Connection connection = EmbeddedDatabase.getDBConnection();
        PreparedStatement selectPreparedStatement = null;
        List<Account> accounts = new ArrayList<>();
        String selectQuery = "select * from ACCOUNT";
        try {
            connection.setAutoCommit(false);
            selectPreparedStatement = connection.prepareStatement(selectQuery);
            ResultSet rs = selectPreparedStatement.executeQuery();
            while (rs.next()) {
            	Account account = new Account();
            	account.setId(rs.getString("id"));
            	account.setAccountNumber(rs.getString("number"));
            	account.setAmount(rs.getBigDecimal("amount"));
            	accounts.add(account);
            }
            selectPreparedStatement.close();
            connection.commit();
            System.out.println(accounts);
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
		return accounts;
    }


	public void clear() {
		Connection connection = EmbeddedDatabase.getDBConnection();
        PreparedStatement deletePreparedStatement = null;
        String deleteQuery = "delete from ACCOUNT";
        try {
            connection.setAutoCommit(false);
            deletePreparedStatement = connection.prepareStatement(deleteQuery);
            deletePreparedStatement.executeUpdate();
            deletePreparedStatement.close();
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
	
	public void update(Account accountDebit, Account accountCredit) {
		Connection connection = EmbeddedDatabase.getDBConnection();
        PreparedStatement updatePreparedStatementDebit = null;
        PreparedStatement updatePreparedStatementCredit = null;
        String updateQueryDebit = "update ACCOUNT set " + "amount = ? " + "where number = ?";
        String updateQueryCredit = "update ACCOUNT set " + "amount = ? " + "where id = ?";
        try {
            connection.setAutoCommit(false);
            
            updatePreparedStatementDebit = connection.prepareStatement(updateQueryDebit);
            updatePreparedStatementDebit.setBigDecimal(1, accountDebit.getAmount());
            updatePreparedStatementDebit.setString(2, accountDebit.getAccountNumber());
            updatePreparedStatementDebit.executeUpdate();
            updatePreparedStatementDebit.close();

            updatePreparedStatementCredit = connection.prepareStatement(updateQueryCredit);
            updatePreparedStatementCredit.setBigDecimal(1, accountCredit.getAmount());
            updatePreparedStatementCredit.setString(2, accountCredit.getAccountNumber());
            updatePreparedStatementCredit.executeUpdate();
            updatePreparedStatementCredit.close();
            
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
