package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {


        List<Account> getAllAccounts();

        BigDecimal getBalanceByUserId(Long id);

        BigDecimal getBalanceByAcctId(int acctId);


        Account addAccount(int userId);

        void deleteAccount(int acctId);



    }

