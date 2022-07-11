package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transfer getTransfer(int transferId) {
        Transfer transfer = null;
        String sql = "SELECT * FROM tenmo_transfer WHERE transfer_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);
        if (rowSet.next()) {
            transfer = mapRowToTransfer(rowSet);
        }
        return transfer;
    }


    @Override
    public Transfer createTransfer(Transfer transfer) {
        Account fromAccount = new Account();
        String sqlAccount = "SELECT account_id, user_id, balance " +
                "FROM tenmo_account WHERE account_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sqlAccount, transfer.getAccountFrom());
        while (result.next()) {
            fromAccount = mapRowToAccount(result);
        }
        if (fromAccount.getBalance().compareTo(transfer.getTransferAmount()) < 0) {
            System.out.println("Sorry, there's not enough in your account to transfer that amount");
            return null;
        } else if (transfer.getTransferAmount().compareTo(transfer.getTransferAmount()) < 0) {
            System.out.println("Sorry, you can't transfer a negative amount");
            return null;
        } else if (fromAccount.getAccountId() == transfer.getAccountTo()) {
            System.out.println("Error - receiver and sender are the same ");
            return null;
        }
        String sql = "INSERT INTO tenmo_transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) " +
                "RETURNING transfer_id;";

        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getTransferAmount());


        String sqlBalanceTo = "UPDATE tenmo_account " +
                    "SET balance = balance + ? " +
                    "WHERE account_id = ?;";
            jdbcTemplate.update(sqlBalanceTo, transfer.getTransferAmount(), transfer.getAccountTo());

    String sqlBalanceFrom = "UPDATE tenmo_account " +
                    "SET balance = balance - ? " +
                    "WHERE account_id = ?;";
            jdbcTemplate.update(sqlBalanceFrom, transfer.getTransferAmount(), transfer.getAccountFrom());

        return getTransfer(transferId);
    }



    @Override
    public List<Transfer> getAllTransfers() {
        List<Transfer> allTransfers = new ArrayList<>();
        SqlRowSet results = jdbcTemplate.queryForRowSet("SELECT * FROM tenmo_transfer");
        while (results.next()) {
            allTransfers.add(mapRowToTransfer(results));
        }
        return allTransfers;
    }

    @Override
    public List<Transfer> getTransfersForUser(Long userId) {
        List<Transfer> transfersForUser = new ArrayList<>();
        String sql = "SELECT * " +
                "FROM tenmo_transfer " +
                "INNER JOIN tenmo_account ON account_to = account_id OR account_from = account_id " +
                "WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfersForUser.add(transfer);
        }
        return transfersForUser;
    }


    @Override
    public Transfer getTransferStatus(int transferId) {
        return null;
    }

    @Override
    public Transfer getTransferAmount(int transferId) { return null;


    }

    @Override
    public void deleteTransfer(int transferId) {
        String sql = "DELETE FROM tenmo_transfer WHERE transfer_id = ?";
        jdbcTemplate.update(sql, transferId);
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        int transferId = rowSet.getInt("transfer_id");
        transfer.setTransferId(transferId);
        int transferTypeId = rowSet.getInt("transfer_type_id");
        transfer.setTransferTypeId(transferTypeId);
        int transferStatusId =rowSet.getInt("transfer_status_id");
        transfer.setTransferStatusId(transferStatusId);
        int accountFrom = rowSet.getInt("account_from");
        transfer.setAccountFrom(accountFrom);
        int accountTo = rowSet.getInt("account_to");
        transfer.setAccountTo(accountTo);
        BigDecimal transferAmount = rowSet.getBigDecimal("amount");
        transfer.setTransferAmount(transferAmount);

        return transfer;


    }

    private Account mapRowToAccount(SqlRowSet results) {
        Account account = new Account();
        account.setAccountId(results.getInt("account_id"));
        account.setUserId(results.getInt("user_id"));
        account.setBalance(results.getBigDecimal("balance"));
        return account;
    }
}
