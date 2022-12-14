package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final RestTemplate restTemplate = new RestTemplate();
    private final Scanner scanner = new Scanner(System.in);


    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else accountService.setCurrentUser(currentUser);
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        Long userId = currentUser.getUser().getUserId();


        String token = currentUser.getToken();
        if (token != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<BigDecimal> account = restTemplate.exchange(API_BASE_URL + "account/balance/user/" + userId, HttpMethod.GET, entity, BigDecimal.class);
            System.out.println();
            System.out.println("Your current account balance is $" + account.getBody());
        }
    }

	private void viewTransferHistory() {
        Transfer[] transfers = accountService.listTransfers();

        int transferSpecifics;

        if (transfers != null) {
            consoleService.printTransfers(transfers);
            System.out.println();
            transferSpecifics = consoleService.promptForMenuSelection("Please enter transfer ID to view details (0 to cancel): ");
            if (transferSpecifics > 0) {
                Transfer transfer = accountService.viewTransfer(transferSpecifics);
                consoleService.printTransfer(transfer);
            }
        }
    }



	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        Transfer newTransfer = null;

        System.out.println("--------------------------------------------");
        System.out.println("Enter transfer data as a comma separated list containing:");
        System.out.println("account_to, transfer_amount");
        System.out.println("2, 100");

        System.out.println("--------------------------------------------");
        System.out.println();
        newTransfer = makeTransfer(scanner.nextLine());
        if (newTransfer == null) {
            System.out.println("Invalid entry. Please try again.");
        }
        if (newTransfer != null) {
            int accountFrom =  currentUser.getUser().getUserId().intValue() + 1000;
            newTransfer.setTransferId(1);
            newTransfer.setTransferTypeId(1);
            newTransfer.setTransferStatusId(2);
            newTransfer.setAccountFrom(accountFrom);
            newTransfer.setAccountTo(newTransfer.getAccountTo());
            newTransfer.setTransferAmount(newTransfer.getTransferAmount());

            accountService.createTransfer(newTransfer);


        }
    }

    private Transfer makeTransfer (String csv) {
        Transfer transfer = new Transfer();
        String[] parsed = csv.split(", ");
        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(parsed[1].trim()));
        if (parsed.length == 2) {
            try {
                transfer = new Transfer();
                transfer.setTransferId(1);
                transfer.setTransferTypeId(1);
                transfer.setTransferStatusId(2);
                transfer.setAccountFrom(2001);
                transfer.setAccountTo(Integer.parseInt(parsed[0].trim()));
                transfer.setTransferAmount(amount);

            } catch (NumberFormatException e) {
                transfer = null;
            }
        }
        return transfer;
    }

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
