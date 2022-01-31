package com.uraditi.backend.client;

import com.uraditi.backend.configuration.ClientConfiguration;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@FeignClient(name = "${keycloak.resource}",
//        url = "${com.uraditi.backend.keycloak.user.management}/users",
        url = "http://ptsv2.com/t/wv16g-1643578966/post",
        configuration = ClientConfiguration.class)
public interface KeycloakClient {

    @RequestMapping(method = GET, value = "")
    Object getAllUsers();

    @RequestMapping(method = POST, value = "", consumes = "application/json", headers = "\"Content-Type: application/json\"")
    String save(@RequestBody UserRepresentation user);

    @RequestMapping(method = GET, value = "")
    List<UserRepresentation> findByUsername(@RequestHeader("Authorization") String bearerToken, @RequestParam String username, @RequestParam boolean exact);

    @RequestMapping(method = DELETE, value = "/{id}")
    Object delete(@RequestHeader String bearerToken, @PathVariable String id);

}
