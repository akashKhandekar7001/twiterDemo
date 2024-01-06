package com.twiterDemo.controller;

import com.twiterDemo.service.TwitterAppCallService;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/twitter")
class TwitterController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TwitterAppCallService twitterAppCallService;

    //search user
    @GetMapping("/search-user")
    public String searchTweetUser(@RequestParam String username) throws URISyntaxException, IOException {
       return twitterAppCallService.getUser(username);
    }
    //get tweets of selected user
    @GetMapping("/get-tweets")
    public String getTweetsOfUser(@RequestParam String userId, @RequestHeader("Authorization") String authorizationHeader) throws IOException, URISyntaxException {
        return  twitterAppCallService.getTweetByUser(userId,authorizationHeader);
    }
}