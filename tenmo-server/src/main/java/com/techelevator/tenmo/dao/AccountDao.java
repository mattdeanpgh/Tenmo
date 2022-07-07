package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {


        List<Account> getAllAccounts();

        BigDecimal getBalanceByUserId(int id);

        Account getBalanceByAcctId(int acctId);

        void updateBalance(BigDecimal balance, int acctId);

        void addAccount(int userId);

        void deleteAccount(int acctId);

    }

