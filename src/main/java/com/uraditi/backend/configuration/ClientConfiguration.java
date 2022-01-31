package com.uraditi.backend.configuration;

import feign.Client;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClientConfiguration {


    @Bean
    public ErrorDecoder errorDecoderGeneric() {
        return new InternalHttpErrorDecoder();
    }

    @Bean
    RequestInterceptor requestInterceptorGeneric() {
        return requestTemplate -> requestTemplate.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).header(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    @Bean
    public Client clientGeneric() {
        return new Client.Default(null, null);
    }

}
