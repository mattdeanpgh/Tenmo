package com.techelevator.tenmo.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;

public class AccountService {
    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public void setCurrentUser(AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
    }


    public Transfer[] listTransfers() {
       Long userId = currentUser.getUser().getUserId();


        String authToken = currentUser.getToken();
        Transfer[] transfers = new Transfer[0];
        if (authToken != null) {
            transfers = null;
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            try {
                ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "/transfer/history/" + userId, HttpMethod.GET,
                        entity, Transfer[].class);
                transfers = response.getBody();
            } catch (RestClientResponseException | ResourceAccessException e) {
                BasicLogger.log(e.getMessage());
            }
        }
        return transfers;

    }

    public Transfer viewTransfer(int transferId) {

        String authToken = currentUser.getToken();
        Transfer transfer = new Transfer();
        if (authToken != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            try {
                ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "/transfer/" + transferId, HttpMethod.GET,
                        entity, Transfer.class);
                transfer = response.getBody();
            } catch (RestClientResponseException | ResourceAccessException e) {
                System.out.println(e.getMessage());
                BasicLogger.log(e.getMessage());
            }
        }
        return transfer;
    }


    public Transfer createTransfer(Transfer newTransfer){
        String authToken = currentUser.getToken();
        Transfer returnedTransfer = null;
        if (authToken != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Transfer> entity = new HttpEntity<>(newTransfer, headers);


            try {
                restTemplate.postForObject(API_BASE_URL + "/transfer" + HttpMethod.POST,
                        entity, Transfer.class);
            } catch (RestClientResponseException | ResourceAccessException e) {
                System.out.println(e.getMessage());
                BasicLogger.log(e.getMessage());
            }
        } return returnedTransfer;

    }
    User[] listOfUsers() {
        User[] users = null;
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(API_BASE_URL + "account/user", HttpMethod.GET, entity, User[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return users;
    }


}

