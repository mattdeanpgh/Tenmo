package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/transfer")
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @RequestMapping(path = "/history/{acctId}", method = RequestMethod.GET)
    public List<Transfer> transfersForUser(@PathVariable int acctId) {
        return transferDao.getTransfersForUser(acctId, acctId);
    }

    @RequestMapping(path = "/{transferId}", method = RequestMethod.GET)
    public Transfer transferById(@PathVariable int transferId) {
        return transferDao.getTransfer(transferId);
    }


    @RequestMapping(path = "/{transferId}", method = RequestMethod.POST)
    public Transfer transferById(@Valid @RequestBody Transfer transfer) {

       if ((transfer.getTransferAmount().compareTo(new BigDecimal(0.00)) == 1) &&
                (transfer.getTransferAmount() >= transfer.getAccountFrom().//account balance)))
        {

        accountDao.updateBalanceFrom(transfer.getTransferAmount(), transfer.getAccountFrom());
        accountDao.updateBalanceTo(transfer.getTransferAmount(), transfer.getAccountTo());
        return transferDao.createTransfer(transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(),
                transfer.getAccountTo(), transfer.getTransferAmount());


    }
}
}

