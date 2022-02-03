package com.uraditi.backend.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestClient extends GenericRestClient {

    @Autowired
    public RestClient(RestTemplate restTemplate) {
        super(restTemplate);
    }

}
