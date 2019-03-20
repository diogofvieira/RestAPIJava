package com.diogo.franchi.money.transfer.account;

import com.diogo.franchi.money.transfer.dao.EmbeddedDatabase;
import com.diogo.franchi.money.transfer.model.Account;
import com.diogo.franchi.money.transfer.transfer.TransferRequest;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

	private static final String SELECT_ALL = "select * from ACCOUNT where number = ?";
	private static final String INSERT = "insert into ACCOUNT" + "(id, number, amount) values" + "(?,?,?)";
	private static final String SELECT_ONE = "select * from ACCOUNT";
	private static final String DELETE = "delete from ACCOUNT";
	private static final String UPDATE = "update ACCOUNT set " + "amount = ? " + "where number = ?";
	
	private Connection connection;
	private PreparedStatement preparedStatement;
	
	public AccountDAO() {
		this.connection = null;
		this.preparedStatement = null;
	}

    public Account find(String accountNumber) {
        Account account = new Account();
        try {
        	connection = EmbeddedDatabase.getDBConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SELECT_ALL);
            preparedStatement.setString(1, accountNumber);
            ResultSet rs = preparedStatement.executeQuery();
            getAccount(account, rs);
            preparedStatement.close();
            connection.commit();
            return account;
        } catch (SQLException e) {
        	e.printStackTrace();
        	return null;
		} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        }
    }

    public Account save(Account account) {
        try {
        	connection = EmbeddedDatabase.getDBConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, account.getId());
            preparedStatement.setString(2, account.getAccountNumber());
            preparedStatement.setBigDecimal(3, account.getAmount());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
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
        List<Account> accounts = new ArrayList<>();
        try {
        	connection = EmbeddedDatabase.getDBConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(SELECT_ONE);
            ResultSet rs = preparedStatement.executeQuery();
            getAccounts(accounts, rs);
            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
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

	public int clear(){
        try {
        	connection = EmbeddedDatabase.getDBConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.execute();
            int count = preparedStatement.getUpdateCount();
            preparedStatement.close();
            connection.commit();
            return count;
        } catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        }
	}
	
	public Account update(TransferRequest transferRequest){
		
		Account accountDebit = find(transferRequest.getAccountDebit());;
		Account accountCredit = find(transferRequest.getAccountCredit());

		BigDecimal debitAmount = accountDebit.getAmount();
		BigDecimal creditAmount = accountCredit.getAmount();

		accountDebit.setAmount(debitAmount.subtract(transferRequest.getValue()));
		accountCredit.setAmount(creditAmount.add(transferRequest.getValue()));

		try {
            connection = EmbeddedDatabase.getDBConnection();
            connection.setAutoCommit(false);
            updateAccount(accountDebit);
            updateAccount(accountCredit);
            connection.commit();
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        }
		return accountDebit;
    }

	private void updateAccount(Account accountDebit) throws SQLException {
		preparedStatement = connection.prepareStatement(UPDATE);
		preparedStatement.setBigDecimal(1, accountDebit.getAmount());
		preparedStatement.setString(2, accountDebit.getAccountNumber());
		preparedStatement.executeUpdate();
		preparedStatement.close();
	}
	
	private void getAccount(Account account, ResultSet rs) throws SQLException {
		while (rs.next()) {
			account.setId(rs.getString("id"));
			account.setAccountNumber(rs.getString("number"));
			account.setAmount(rs.getBigDecimal("amount"));
		}
	}
	
	private void getAccounts(List<Account> accounts, ResultSet rs) throws SQLException {
		while (rs.next()) {
			Account account = new Account();
			account.setId(rs.getString("id"));
			account.setAccountNumber(rs.getString("number"));
			account.setAmount(rs.getBigDecimal("amount"));
			accounts.add(account);
		}
	}

}

