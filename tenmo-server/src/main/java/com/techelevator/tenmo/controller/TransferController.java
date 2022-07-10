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
//@PreAuthorize("isAuthenticated()")
@RequestMapping("/transfer")
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;
    private UserDao userDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/history", method = RequestMethod.GET)
    public List<Transfer> allTransfers(){
        return transferDao.getAllTransfers();
    }

    @RequestMapping(path = "/history/{userId}", method = RequestMethod.GET)
    public List<Transfer> transfersForUser(@PathVariable Long userId) {
        return transferDao.getTransfersForUser(userId);
    }

    @RequestMapping(path = "/{transferId}", method = RequestMethod.GET)
    public Transfer transferById(@PathVariable int transferId) {
        return transferDao.getTransfer(transferId);
    }


    //    @RequestMapping(path = "/{transferId}", method = RequestMethod.POST)
//    public Transfer transferById(@Valid @RequestBody Transfer transfer) {
//        int accountFrom = transfer.getAccountFrom();
//
//       if ((transfer.getTransferAmount().compareTo(new BigDecimal(0.00)) == 1) &&
//                (transfer.getTransferAmount().compareTo(accountDao.getBalanceByAcctId(accountFrom)) == 1))
//        {
//
//        accountDao.updateBalanceFrom(transfer.getTransferAmount(), transfer.getAccountFrom());
//        accountDao.updateBalanceTo(transfer.getTransferAmount(), transfer.getAccountTo());
//        return transferDao.createTransfer(transfer, transfer.getAccountTo());
//
//
//    } else return null;
//}

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping( path = "", method = RequestMethod.POST)
    public Transfer transferById(@Valid @RequestBody Transfer transfer) {
            accountDao.updateBalanceFrom(transfer.getTransferAmount(), transfer.getAccountFrom());
            accountDao.updateBalanceTo(transfer.getTransferAmount(), transfer.getAccountTo());
        return transferDao.createTransfer(transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(),
                transfer.getAccountTo(), transfer.getTransferAmount());
    }
//    @RequestMapping(path = "/{transferEnteredByUser}", method = RequestMethod.POST)
//    public Transfer transferById(@Valid @RequestBody Transfer transfer) {
//        int accountFrom = transfer.getAccountFrom();
//
//        if ((transfer.getTransferAmount().compareTo(new BigDecimal(0.00)) == 1) &&
//                (transfer.getTransferAmount().compareTo(accountDao.getBalanceByAcctId(accountFrom)) == 1)) {
//
//            accountDao.updateBalanceFrom(transfer.getTransferAmount(), transfer.getAccountFrom());
//            accountDao.updateBalanceTo(transfer.getTransferAmount(), transfer.getAccountTo());
//            return transferDao.createTransfer(transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFrom(),
//                    transfer.getAccountTo(), transfer.getTransferAmount());
//        }
//        return null;
//    }
}

