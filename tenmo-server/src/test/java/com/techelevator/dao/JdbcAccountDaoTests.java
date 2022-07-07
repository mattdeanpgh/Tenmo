package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.UserNotFoundException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcAccountDaoTests extends BaseDaoTests {

    private static final Account ACCOUNT_1 = new Account(2001, 1001, new BigDecimal(1000));
    private static final Account ACCOUNT_2 = new Account(2002, 1002, new BigDecimal(500));
    private static final Account ACCOUNT_3 = new Account(2003, 1003, new BigDecimal(250));

    private JdbcAccountDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcAccountDao(jdbcTemplate);
    }

    @Test
    public void getAllAccounts_returns_all_accounts() {
        List<Account> accounts = sut.getAllAccounts();
        Assert.assertEquals(3, accounts.size());

        assertAccountListsMatch(ACCOUNT_1, accounts.get(0));
        assertAccountListsMatch(ACCOUNT_2, accounts.get(1));
        assertAccountListsMatch(ACCOUNT_3, accounts.get(2));

    }


    private void assertAccountListsMatch(Account expected, Account actual) {
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertTrue(expected.getBalance().compareTo(actual.getBalance()) == 0);
    }
}


