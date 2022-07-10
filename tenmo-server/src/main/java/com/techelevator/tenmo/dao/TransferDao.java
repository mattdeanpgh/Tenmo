package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDao {

    Transfer getTransfer (int transferId);

    Transfer createTransfer(Transfer transfer);

    List<Transfer> getAllTransfers();

    List<Transfer> getTransfersForUser(Long userId);


    Transfer getTransferStatus (int transferId);

    Transfer getTransferAmount (int transferId);

    void deleteTransfer(int transferId);




}
