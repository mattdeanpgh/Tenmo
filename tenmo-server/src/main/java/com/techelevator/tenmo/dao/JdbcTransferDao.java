package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public Transfer createTransfer(int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal transferAmount) {
        String sql = "INSERT INTO tenmo_transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) " +
                     "VALUES (2, 2, ?, ?, ?) " +
                     "RETURNING transfer_id;";
        int transferId = jdbcTemplate.queryForObject(sql, Integer.class, accountFrom, accountTo, transferAmount);


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
    public List<Transfer> getTransfersForUser(int accountFrom, int accountTo) {
        List<Transfer> transfersForUser = new ArrayList<>();
        String sql = "SELECT * FROM tenmo_transfer WHERE account_from = ? OR account_to = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountFrom, accountTo);
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
    public Transfer getTransferAmount(int transferId) {
        return null;
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
}
