package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("/account")
public class AccountController {


    private UserDao userDao;
    private AccountDao dao;

    public AccountController(AccountDao dao, UserDao userDao){
        this.dao = dao;
        this.userDao = userDao;
    }


    //Return Account Balance for User
    @RequestMapping(path = "/balance/{acctId}", method = RequestMethod.GET)
    public BigDecimal accountBalance (@PathVariable int acctId) {
        return dao.getBalanceByAcctId(acctId);
    }



}
