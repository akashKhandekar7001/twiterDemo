package com.twiterDemo.service;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;

@Component
public interface TwitterAppCallService {

    String getUser(String username) throws URISyntaxException, IOException;

    String getTweetByUser(String userId, String authorizationHeader) throws URISyntaxException, IOException;
}
