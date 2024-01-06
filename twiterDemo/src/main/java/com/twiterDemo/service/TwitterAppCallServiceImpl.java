package com.twiterDemo.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TwitterAppCallServiceImpl implements TwitterAppCallService{

    private static final String API_BASE_URL = "https://api.twitter.com/2/users/by";
    @Value("${bearer_token}")
    private String bearer_token;

    @Override
    public String getUser(String username) throws URISyntaxException, IOException {
        String userResponse = null;
        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/users/by");
        ArrayList<NameValuePair> queryParameters;
        queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("usernames", username));
        queryParameters.add(new BasicNameValuePair("user.fields", "created_at,description,pinned_tweet_id"));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", String.format("Bearer %s", bearer_token));
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (null != entity) {
            userResponse = EntityUtils.toString(entity, "UTF-8");
        }
        return userResponse;
    }

    @Override
    public String getTweetByUser(String userId, String authorizationHeader) throws URISyntaxException, IOException {
        String tweetsResponse = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().build())
                .build();

        URIBuilder uriBuilder = new URIBuilder(API_BASE_URL + "/tweets");
        List<NameValuePair> queryParameters = new ArrayList<>();
        queryParameters.add(new BasicNameValuePair("user_id", userId));
        uriBuilder.addParameters(queryParameters);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Authorization", authorizationHeader);
        httpGet.setHeader("Content-Type", "application/json");

        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            tweetsResponse = EntityUtils.toString(entity, "UTF-8");
        }

        return tweetsResponse;
    }
}
