package com.uraditi.backend.client;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@FeignClient(name = "${keycloak.resource}",
        url = "${com.uraditi.backend.keycloak.user.management}/users")
//        url = "http://ptsv2.com/t/wv16g-1643578966/post")
public interface KeycloakClient {

    @RequestMapping(method = GET, value = "")
    Object getAllUsers();

    //    @Headers({"Content-Type: application/json"})
    @RequestMapping(method = POST, value = "")
    Object save(@RequestHeader("Authorization") String bearerToken, @RequestBody UserRepresentation user);

    @RequestMapping(method = GET, value = "")
    List<UserRepresentation> findByUsername(@RequestHeader("Authorization") String bearerToken, @RequestParam String username, @RequestParam boolean exact);

    @RequestMapping(method = DELETE, value = "/{id}")
    Object delete(@RequestHeader String bearerToken, @PathVariable String id);

}
