package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);
    private final AccountService accountService = new AccountService();
    public Transfer promptForTransferData;

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

    public void printTransfers(Transfer[] transfers) {
        System.out.println("--------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID           FROM : TO             AMOUNT");
        System.out.println("--------------------------------------------");
        for (Transfer transfer : transfers) {
            System.out.println(transfer.getTransferId() + "         " + transfer.getAccountTo() + " : " + transfer.getAccountFrom() + "        " + transfer.getTransferAmount());
            System.out.println("--------------------------------------------");

        }
    }

    public void printTransfer(Transfer transfer) {

        System.out.println("--------------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("--------------------------------------------");
        System.out.println("ID: " + transfer.getTransferId());
        System.out.println("From: " + transfer.getAccountFrom());
        System.out.println("To: " + transfer.getAccountTo());
        System.out.println("Status: " + transfer.getTransferStatusId());
        System.out.println("Type: " + transfer.getTransferTypeId());
        System.out.println("Amount: $" + transfer.getTransferAmount());

    }

}


