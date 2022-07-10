package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;

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

import java.math.BigDecimal;
//
//public class JdbcTransferDaoTests extends BaseDaoTests{
//    private static final Transfer TRANSFER_1 = new Transfer(2, 2, 2001, 2002, new BigDecimal(50));
//    private static final Transfer TRANSFER_2 = new Transfer(2, 2, 2002, 2003, new BigDecimal(100));
//    private static final Transfer TRANSFER_3 = new Transfer(2, 2, 2003, 2001, new BigDecimal(200));
//
//    private JdbcTransferDao sut;
//
//    @Before public void setup() {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//        sut = new JdbcTransferDao(jdbcTemplate);
//    }
//
//    @Test
//    public void createTransfer_creates_transfer() {
//        Transfer newTransfer = new Transfer(2, 2, 2003, 2002, new BigDecimal(10.00));
//        Transfer actualTransfer = sut.createTransfer(2, 2, 2003, 2002, new BigDecimal(10.00));
//        newTransfer.setTransferId(actualTransfer.getTransferId());
//        assertTransfersMatch(newTransfer, actualTransfer);
//    }







//    private void assertTransfersMatch(Transfer expected, Transfer actual) {
//        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
//        Assert.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
//        Assert.assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
//        Assert.assertEquals(expected.getAccountFrom(), actual.getAccountFrom());
//        Assert.assertEquals(expected.getAccountTo(), actual.getAccountTo());
//        Assert.assertTrue(expected.getTransferAmount().compareTo(actual.getTransferAmount()) == 0);
//    }
//
//
//}
