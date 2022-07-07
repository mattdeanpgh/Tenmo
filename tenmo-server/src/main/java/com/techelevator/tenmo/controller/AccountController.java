package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private UserDao userDao;
    private AccountDao dao;

    public AccountController(AccountDao dao, UserDao userDao){
        this.dao = dao;
        this.userDao = userDao;
    }

    //Return Account Balance for User
    @RequestMapping(path = "/balance/{accountId}", method = RequestMethod.GET)
    public BigDecimal accountBalance (@PathVariable int accountId) {
        return dao.getBalanceByAcctId(accountId);
    }



}
