package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class JdbcAccountDao implements AccountDao {


    private static final BigDecimal STARTING_BALANCE = new BigDecimal("1000.00");
    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "select * from tenmo_account";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }


    @Override
    public BigDecimal getBalanceByUserId(int id) {
        BigDecimal balance = jdbcTemplate.queryForObject("SELECT balance FROM tenmo_account WHERE user_id = ?;", BigDecimal.class);
        return balance;
    }

    @Override
    public BigDecimal getBalanceByAcctId(int acctId) {
        BigDecimal balance = jdbcTemplate.queryForObject("SELECT balance FROM tenmo_account WHERE acct_id = ?;", BigDecimal.class);
        return balance;
    }

    @Override
    public void updateBalance(BigDecimal changeBalance, int acctId) {
        String sql = "UPDATE tenmo_account" +
                "SET balance = balance + ?" +
                "WHERE account_id = ?;";
        jdbcTemplate.update(sql, Account.class, changeBalance, acctId);
    }


    @Override
    public void addAccount(int userId) {
        String sql = "UPDATE tenmo_account" +
                "SET balance =" + STARTING_BALANCE +
                "WHERE user_id = ?;";
        Integer newId = jdbcTemplate.update(sql, Account.class, userId);

    }


    @Override
    public void deleteAccount(int acctId) {
        String sql = "DELETE FROM tenmo_account WHERE acct_id = ?;";
        jdbcTemplate.update(sql, acctId);
    }

    private Account mapRowToAccount(SqlRowSet results) {
        Account account = new Account();
        account.setAccountId(results.getInt("account_id"));
        account.setUserId(results.getInt("user_id"));
        account.getBalance(results.getBigDecimal("balance"));
        return account;
    }

}