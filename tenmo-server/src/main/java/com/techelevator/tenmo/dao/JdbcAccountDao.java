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
        String sql = "SELECT * FROM tenmo_account " +
                "ORDER BY account_id";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }
    public Account getAccount(int accountId){
        Account account = null;
        String sql = "SELECT * FROM tenmo_account "
                + "WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            if (results.next()) {
                account = mapRowToAccount((results));
            }
        return account;
    }


    @Override
    public BigDecimal getBalanceByUserId(Long userId) {
        Account account = null;
        String sql = "SELECT * FROM tenmo_account WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            account = mapRowToAccount(results);
        }
        return account.getBalance();
    }

    @Override
    public BigDecimal getBalanceByAcctId(int acctId) {
        Account account = null;
        String sql = "SELECT * FROM tenmo_account WHERE account_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, acctId);
        if (results.next()) {
            account = mapRowToAccount(results);
        }
        return account.getBalance();
    }




    @Override
    public Account addAccount(int userId) {

        String sql = "INSERT INTO tenmo_account (user_id, balance) " +
                "VALUES (?, " + STARTING_BALANCE + ") RETURNING account_id;";
        int accountId = jdbcTemplate.queryForObject(sql, Integer.class, userId);

        return getAccount(accountId);
    }


    @Override
    public void deleteAccount(int acctId) {
        String sql = "DELETE FROM tenmo_account WHERE account_id = ?;";
        jdbcTemplate.update(sql, acctId);
    }



    private Account mapRowToAccount(SqlRowSet results) {
        Account account = new Account();
        account.setAccountId(results.getInt("account_id"));
        account.setUserId(results.getInt("user_id"));
        account.setBalance(results.getBigDecimal("balance"));
        return account;
    }

}
