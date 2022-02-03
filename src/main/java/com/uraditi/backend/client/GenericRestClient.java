package com.uraditi.backend.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uraditi.backend.exception.ApiException;
import com.uraditi.backend.exception.ApiExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class GenericRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericRestClient.class);
    protected RestTemplate restTemplate;
    @Autowired
    private ObjectMapper mapper;

    public GenericRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> T get(Class<T> type, String url) throws ApiException {
        return get(type, url, null);
    }

    public <T> T get(Class<T> type, String url, Map<String, List<String>> headers) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders(headers);
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), type);
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            LOGGER.info(String.format("GET request failed. URL: %s, Response code: %s", url, e.getStatusCode()));
//            throw new RestClientException("Failed to execute GET request!", url, e.getStatusCode().value(), e);
            throw ApiExceptionFactory.genericError(e.getStatusCode(), e.getLocalizedMessage());
        } catch (Exception e) {
            LOGGER.error("Internal error while executing get", e);
//            throw new RestClientException("Internal error while executing request! ", url, e);
            throw ApiExceptionFactory.genericError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    public <T> List<T> getList(Class<T[]> type, String url) throws ApiException {
        return getList(type, url, null);
    }

    public <T> List<T> getList(Class<T[]> type, String url, Map<String, List<String>> headers) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders(headers);
            ResponseEntity<T[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), type);
            return List.of(response.getBody());
        } catch (HttpStatusCodeException e) {
            LOGGER.info(String.format("GET request failed. URL: %s, Response code: %s", url, e.getStatusCode()));
//            throw new RestClientException("Failed to execute GET request!", url, e.getStatusCode().value(), e);
            throw ApiExceptionFactory.genericError(e.getStatusCode(), e.getLocalizedMessage());

        } catch (Exception e) {
            LOGGER.error("Internal error while executing get", e);
            throw ApiExceptionFactory.genericError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    public <T> ResponseEntity<T> getWithHeaders(Class<T> type, String url, Map<String, List<String>> headers) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders(headers);
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), type);
        } catch (HttpStatusCodeException e) {
            LOGGER.info(String.format("GET request failed. URL: %s, Response code: %s", url, e.getStatusCode()));
//            throw new RestClientException("Failed to execute GET request!", url, e.getStatusCode().value(), e);
            throw ApiExceptionFactory.genericError(e.getStatusCode(), e.getLocalizedMessage());

        } catch (Exception e) {
            LOGGER.error("Internal error while executing get", e);
            throw ApiExceptionFactory.genericError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    public <T> T post(Class<T> type, String url, Object requestDto) throws ApiException {
        return post(type, url, requestDto, null);
    }

    public <T> T post(Class<T> type, String url, Object requestDto, Map<String, List<String>> headers) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders(headers);
            String json = mapper.writeValueAsString(requestDto);
            HttpEntity<String> entity = new HttpEntity<>(json, httpHeaders);
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, type);
            return responseEntity.getBody();

        } catch (Exception e) {
            LOGGER.error("Error while executing post", e);
            throw ApiExceptionFactory.genericError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    public <T> T delete(Class<T> type, String url, Object requestDto) throws ApiException {
        return delete(type, url, requestDto, null);
    }

    public <T> T delete(Class<T> type, String url, Object requestDto, Map<String, List<String>> headers) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders(headers);
            String json = mapper.writeValueAsString(requestDto);
            HttpEntity<String> entity = new HttpEntity<>(json, httpHeaders);
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, entity, type);
            return responseEntity.getBody();

        } catch (Exception e) {
            LOGGER.error("Error while executing post", e);
            throw ApiExceptionFactory.genericError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    public <T> List<T> postParameterizedType(Class<T> type, String url, Object requestDto, Map<String, List<String>> headers) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders(headers);
            String json = mapper.writeValueAsString(requestDto);
            HttpEntity<String> entity = new HttpEntity<>(json, httpHeaders);
            ResponseEntity<List<T>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<T>>() {
            });
            return responseEntity.getBody();

        } catch (Exception e) {
            LOGGER.error("Error while executing post", e);
            throw ApiExceptionFactory.genericError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

//    public <T> ResponseEntity<T> postWithHeaders(Class<T> type, String url, Object requestDto) throws ApiException {
//        return postWithHeaders(type, url, requestDto, null);
//    }

    public <T> ResponseEntity<T> postWithHeaders(Class<T> type, String url, Map<String, String> requestDto, Map<String, List<String>> headers) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders(headers);

            HttpEntity entity = null;
            ResponseEntity responseEntity = null;
            if (MediaType.APPLICATION_FORM_URLENCODED.equals(httpHeaders.getContentType())) {
                MultiValueMap parameters = new LinkedMultiValueMap<String, String>();
                parameters.setAll(requestDto);
                entity = new HttpEntity<>(parameters, httpHeaders);
            } else {
                String json = mapper.writeValueAsString(requestDto);
                entity = new HttpEntity<>(json, httpHeaders);
            }

            return restTemplate.postForEntity(url, entity, type, new HashMap<>());

        } catch (Exception e) {
            LOGGER.error("Error while executing post", e);
            throw ApiExceptionFactory.genericError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    public <T> List<T> postWithListResponse(Class<T[]> type, String url, Object requestDto) throws ApiException {
        return postWithListResponse(type, url, requestDto, null);
    }

    public <T> List<T> postWithListResponse(Class<T[]> type, String url, Object requestDto, Map<String, List<String>> headers) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders(headers);
            String json = mapper.writeValueAsString(requestDto);
            HttpEntity<String> entity = new HttpEntity<>(json, httpHeaders);
            ResponseEntity<T[]> response = restTemplate.exchange(url, HttpMethod.POST, entity, type);
            return List.of(response.getBody());

        } catch (Exception e) {
            LOGGER.error("Error while executing post", e);
            throw ApiExceptionFactory.genericError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    public <T> T put(Class<T> type, String url) throws ApiException {
        return put(type, url, null);
    }

    public <T> T put(Class<T> type, String url, Map<String, List<String>> headers) throws ApiException {
        try {
            HttpHeaders httpHeaders = createHeaders(headers);
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(httpHeaders), type);
            return responseEntity.getBody();
        } catch (Exception e) {
            LOGGER.error("Error while executing put", e);
            throw ApiExceptionFactory.genericError(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }


    private HttpHeaders createHeaders(Map<String, List<String>> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, List<String>> header : headers.entrySet()) {
                httpHeaders.addAll(header.getKey(), header.getValue());
            }
        } else {
            httpHeaders.add("Content-Type", "application/json");
        }
        return httpHeaders;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}