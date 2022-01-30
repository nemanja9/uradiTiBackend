package com.uraditi.backend.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uraditi.backend.exception.ApiException;
import com.uraditi.backend.exception.ConflictApiException;
import com.uraditi.backend.exception.ErrorCode;
import com.uraditi.backend.exception.dto.ConflictErrorMessageDto;
import com.uraditi.backend.exception.dto.ErrorMessageDto;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

@Slf4j
public class InternalHttpErrorDecoder implements ErrorDecoder {

    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String MESSAGE = "message";
    public static final String CONTENT = "content";
    public static final String ORIGINAL_MESSAGE = "originalErrorMessage";

    @Override
    public Exception decode(String methodKey, Response response) {

        Reader reader;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            HttpStatus responseHttpStatus = HttpStatus.valueOf(response.status());

            if (response.body() == null) {
                return new ApiException(null, responseHttpStatus, ErrorCode.INTERNAL_ERROR, "No response body from upstream service: " + response.request().url(), null);
            }
            reader = response.body().asReader();
            String result = IOUtils.toString(reader);
            try {

                var hashMap = objectMapper.readValue(result, HashMap.class);

                try {
                    if (responseHttpStatus == HttpStatus.CONFLICT) {
                        ConflictErrorMessageDto conflictErrorMessageDto = decodeConflict(hashMap);
                        return new ConflictApiException(conflictErrorMessageDto.getProperty(), conflictErrorMessageDto.getSuggestedValue(), conflictErrorMessageDto.getMessage(), conflictErrorMessageDto);
                    }
                } catch (NullPointerException e) {
                    log.warn("ConflictApiException expected");
                }
                ErrorMessageDto errorMessageDto = decode(hashMap);

                if (responseHttpStatus.is4xxClientError()) {
                    return new ApiException(null, responseHttpStatus, errorMessageDto.getCode(), "Failed to get resource from upstream service.", errorMessageDto);
                } else {
                    return new ApiException(null, HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR, errorMessageDto.getMessage(), errorMessageDto);
                }
                //NPE for json or runtime exception, JPE for text or xml errors
            } catch (NullPointerException | JsonProcessingException e) {
                return new ApiException(null, responseHttpStatus, ErrorCode.INTERNAL_ERROR, result.replaceAll("\"", ""), null);
            }

        } catch (IOException e) {
            return new RuntimeException("This should not happen");
        }
    }

    private ErrorMessageDto decode(HashMap hashMap) {
        ErrorCode responseErrorCode = ErrorCode.valueOf(hashMap.get(CODE).toString());
        ErrorMessageDto errorMessageDto = new ErrorMessageDto();
        errorMessageDto.setId(UUID.fromString(hashMap.get(ID).toString()));
        errorMessageDto.setMessage(hashMap.get(MESSAGE).toString());
        if (hashMap.get(ORIGINAL_MESSAGE) != null && hashMap.get(ORIGINAL_MESSAGE).getClass().isAssignableFrom(LinkedHashMap.class)) {
            errorMessageDto.setOriginalErrorMessage(decode((LinkedHashMap) hashMap.get(ORIGINAL_MESSAGE)));
        }
        if (hashMap.get(CONTENT) != null) {
            errorMessageDto.setContent(hashMap.get(CONTENT).toString());
        }
        errorMessageDto.setCode(responseErrorCode);
        return errorMessageDto;
    }

    private ConflictErrorMessageDto decodeConflict(HashMap hashMap) {
        ConflictErrorMessageDto conflictErrorMessageDto = new ConflictErrorMessageDto();
        ErrorCode responseErrorCode = ErrorCode.valueOf(hashMap.get(CODE).toString());
        conflictErrorMessageDto.setId(UUID.fromString(hashMap.get(ID).toString()));
        conflictErrorMessageDto.setProperty(hashMap.get("property").toString());
        conflictErrorMessageDto.setSuggestedValue(hashMap.get("suggestedValue").toString());
        conflictErrorMessageDto.setMessage(hashMap.get(MESSAGE).toString());
        if (hashMap.get(ORIGINAL_MESSAGE) != null && hashMap.get(ORIGINAL_MESSAGE).getClass().isAssignableFrom(LinkedHashMap.class)) {
            conflictErrorMessageDto.setOriginalErrorMessage(decode((LinkedHashMap) hashMap.get(ORIGINAL_MESSAGE)));
        }
        if (hashMap.get(CONTENT) != null) {
            conflictErrorMessageDto.setContent(hashMap.get(CONTENT).toString());
        }
        conflictErrorMessageDto.setCode(responseErrorCode);
        return conflictErrorMessageDto;
    }
}
