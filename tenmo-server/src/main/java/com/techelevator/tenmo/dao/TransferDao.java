package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer getTransfer (int transferId);

    Transfer createTransfer(Transfer transfer);

    List<Transfer> getAllTransfers();

    List<Transfer> getTransfersForUser(int accountFrom, int accountTo);

    Transfer getTransferStatus (int transferId);

    Transfer getTransferAmount (int transferId);

    void deleteTransfer(int transferId);




}
