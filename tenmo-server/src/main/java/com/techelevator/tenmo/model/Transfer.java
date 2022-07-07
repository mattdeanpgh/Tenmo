package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class Transfer {
    private int transferId;
    private int transferTypeId;
    private int transferStatusId;
    private int accountFrom;
    private int accountTo;
    private BigDecimal transferAmount;

    public Transfer() { }

    public Transfer(int transferId, int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal transferAmount) {
        this.transferId=transferId;
        this.transferTypeId=transferTypeId;
        this.transferStatusId=transferStatusId;
        this.accountFrom=accountFrom;
        this.accountTo=accountTo;
        this.transferAmount=transferAmount;
    }

    public Transfer( int transferTypeId, int transferStatusId, int accountFrom, int accountTo, BigDecimal transferAmount) {
        this.transferTypeId=transferTypeId;
        this.transferStatusId=transferStatusId;
        this.accountFrom=accountFrom;
        this.accountTo=accountTo;
        this.transferAmount=transferAmount;
    }




    public int getTransferId() {return transferId;}
    public void setTransferId(int transferId) {this.transferId = transferId;}
    public int getTransferTypeId() {return transferTypeId;}
    public void setTransferTypeId(int transferTypeId) {this.transferTypeId = transferTypeId;}
    public int getTransferStatusId() {return transferStatusId;}
    public void setTransferStatusId(int transferStatusId) {this.transferStatusId = transferStatusId;}
    public int getAccountFrom() {return accountFrom;}
    public void setAccountFrom(int accountFrom) {this.accountFrom = accountFrom;}
    public int getAccountTo() {return accountTo;}
    public void setAccountTo(int accountTo) {this.accountTo = accountTo;}
    public BigDecimal getTransferAmount() {return transferAmount;}
    public void setTransferAmount(BigDecimal transferAmount) {this.transferAmount = transferAmount;}


}
