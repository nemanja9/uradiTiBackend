package com.uraditi.backend.client;

import com.uraditi.backend.configuration.KeycloakTokenCheckerClientConfiguration;
import com.uraditi.backend.dto.KeycloakTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@FeignClient(name = "keycloak-token-getter",
        url = "${com.uraditi.backend.keycloak.token.management}",
//        url = "http://ptsv2.com/t/wv16g-1643578966/post",
        configuration = KeycloakTokenCheckerClientConfiguration.class)
public interface KeycloakTokenChecker {

    @RequestMapping(method = POST, value = "")
    KeycloakTokenResponseDto getNewToken(@RequestBody String params);
}
