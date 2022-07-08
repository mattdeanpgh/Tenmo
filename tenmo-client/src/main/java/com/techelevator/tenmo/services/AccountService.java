package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {
    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public void setCurrentUser(AuthenticatedUser currentUser) {
        this.currentUser = currentUser;
    }


    public Transfer[] listTransfers() {


        String authToken = currentUser.getToken();
        Transfer[] transfers = new Transfer[0];
        if (authToken != null) {
            transfers = null;
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            try {
                ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "/transfer/history/2001", HttpMethod.GET,
                        entity, Transfer[].class);
                transfers = response.getBody();
            } catch (RestClientResponseException | ResourceAccessException e) {
                BasicLogger.log(e.getMessage());
            }
        }
        return transfers;

    }

    public Transfer viewTransfer() {
        String authToken = currentUser.getToken();
        Transfer transfer = new Transfer();
        if (authToken != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(authToken);
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            try {
                ResponseEntity<Transfer> response = restTemplate.exchange(API_BASE_URL + "/transfer/3003", HttpMethod.GET,
                        entity, Transfer.class);
                transfer = response.getBody();
            } catch (RestClientResponseException | ResourceAccessException e) {
                System.out.println(e.getMessage());
                BasicLogger.log(e.getMessage());
            }
        }
        return transfer;
    }


}

