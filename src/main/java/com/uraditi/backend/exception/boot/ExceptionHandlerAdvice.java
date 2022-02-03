package com.uraditi.backend.exception.boot;

import com.uraditi.backend.exception.ApiException;
import com.uraditi.backend.exception.ConflictApiException;
import com.uraditi.backend.exception.ErrorCode;
import com.uraditi.backend.exception.ErrorMessageFactory;
import com.uraditi.backend.exception.dto.ErrorMessageDto;
import com.uraditi.backend.exception.dto.ValidationErrorMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/**
 * Has methods that deal with various types of exceptions
 */
@Slf4j
@ControllerAdvice
@ComponentScan
@Component
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExceptionHandlerAdvice {

    @Value("${include.debug.dto}")
    private boolean includeException;

    /**
     * Creates random number for error id
     *
     * @return {@link String} Error id
     */
    private static UUID createErrorId() {
        return UUID.randomUUID();
    }

    /**
     * Handles Api exceptions
     *
     * @param ex ApiException
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorMessageDto> handleApiException(ApiException ex) {
        UUID errorId = createErrorId();
        log.warn("Api exception id {} ", errorId, ex);
        return ResponseEntity.status(ex.getHttpStatus()).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessageFactory.createErrorDto(includeException, errorId, ex));

    }

    /**
     * Handles Conflict Api exceptions
     *
     * @param ex ConflictApiException
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(ConflictApiException.class)
    public ResponseEntity<ErrorMessageDto> handleApiException(ConflictApiException ex) {
        UUID errorId = createErrorId();
        log.warn("Conflict Api exception id {} ", errorId, ex);
        return ResponseEntity.status(ex.getHttpStatus()).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessageFactory.createErrorDto(errorId, ex));

    }

    /**
     * Handles exceptions that are thrown by the server when the size of an
     * uploaded file exceeded the set limit
     *
     * @param ex incoming exception
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorMessageDto> handleApiException(MaxUploadSizeExceededException ex) {
        UUID errorId = createErrorId();
        log.warn("File upload size exceeded {}", errorId, ex);
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessageFactory.createErrorDto(includeException, errorId, ErrorCode.BAD_REQUEST, ex));
    }

    /**
     * Handles exceptions which are thrown when multipart requests fail
     *
     * @param ex incoming exception
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<ErrorMessageDto> handleApiException(MultipartException ex) {
        UUID errorId = createErrorId();
        log.warn("Multipart request failed {}", errorId, ex);
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessageFactory.createErrorDto(includeException, errorId, ErrorCode.BAD_REQUEST, ex));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessageDto> handleConstraintException(ConstraintViolationException ex) {
        UUID errorId = createErrorId();
        log.warn("Validation failed {}", errorId);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessageFactory.createErrorDto(includeException, errorId, ErrorCode.BAD_REQUEST, ex));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessageDto> handleAccessDeniedException(AccessDeniedException ex) {
        UUID errorId = createErrorId();
        log.warn("Access denied {}", errorId);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessageFactory.createErrorDto(includeException, errorId, ErrorCode.FORBIDDEN, ex));
    }

    /**
     * Converts missing header into a Bad request
     *
     * @param ex exception thrown for missing headers
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<ErrorMessageDto> handleRequestHeaderException(Exception ex) {
        UUID errorId = createErrorId();
        log.warn("Header exception id {}", errorId);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessageFactory.createErrorDto(includeException, errorId, ErrorCode.BAD_REQUEST, ex));
    }

    /**
     * Handles JSON deserialization exceptions and other parameter validations
     *
     * @param request HttpServletRequest
     * @param ex      {@link HttpMessageNotReadableException }
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorMessageDto> handleValidationException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        UUID errorId = createErrorId();
        log.warn("Bad Request to {} id {}", request.getRequestURL(), errorId, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(new ValidationErrorMessageDto(errorId, ex));
    }

    /**
     * Handles Runtime Exception exceptions
     *
     * @param ex RuntimeException
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorMessageDto> handleException(HttpServletRequest request, Exception ex) {
        UUID errorId = createErrorId();
        log.error("Generic error to {} id {}", request.getRequestURL(), errorId, ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessageFactory.createErrorDto(includeException, errorId, ErrorCode.GENERIC_ERROR, ex));

    }

    /**
     * Handles JSON deserialization exceptions and other parameter validations
     *
     * @param request HttpServletRequest
     * @param ex      {@link HttpMessageNotReadableException }
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ErrorMessageDto> handleJsonException(HttpServletRequest request, Exception ex) {
        UUID errorId = createErrorId();
        log.warn("Error parsing request : {}, {}", request.getRequestURL(), errorId, ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessageFactory.createErrorDto(includeException, errorId, ErrorCode.BAD_REQUEST, ex));
    }

    /**
     * Handles wrong content-type exceptions
     *
     * @param request {@link HttpServletRequest}
     * @param ex      {@link HttpMediaTypeNotSupportedException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorMessageDto> handleMediaTypeException(HttpServletRequest request, Exception ex) {
        UUID errorId = createErrorId();
        log.warn("Media type not supported : {}, {}", request.getRequestURL(), errorId, ex);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessageFactory.createErrorDto(includeException, errorId, ErrorCode.BAD_REQUEST, ex));

    }

    /**
     * Handles missing json properties
     *
     * @param ex {@link MissingServletRequestPartException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorMessageDto> handleApiException(MissingServletRequestPartException ex) {
        UUID errorId = createErrorId();
        log.warn("Api exception id {} ", errorId, ex);
        return ResponseEntity.status(400).contentType(MediaType.APPLICATION_JSON)
                .body(ErrorMessageFactory.createErrorDto(true, errorId, ErrorCode.BAD_REQUEST, ex));

    }

    /**
     * Handles ExecutingException's throw from CompletableFuture's.
     *
     * @param httpServletRequest {@link HttpServletRequest}
     * @param ex                 {@link ExecutionException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(ExecutionException.class)
    public ResponseEntity<ErrorMessageDto> handleExecutionException(HttpServletRequest httpServletRequest, ExecutionException ex) {

        if (ex.getCause().getClass().equals(ApiException.class)) {
            ApiException apiException = (ApiException) (ex.getCause());
            return this.handleApiException(apiException);
        }

        return this.handleException(httpServletRequest, ex);
    }
}
