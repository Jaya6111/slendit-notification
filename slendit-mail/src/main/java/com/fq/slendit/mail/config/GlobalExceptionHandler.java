package com.fq.slendit.mail.config;

import java.nio.file.AccessDeniedException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ExceptionResponse validationExceptionHandler(MethodArgumentNotValidException ex) {

		Set<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.toSet());

		return buildExceptionResponse(HttpStatus.BAD_REQUEST, "400", "Validation failed", errorMessages);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ExceptionResponse illegalArgumentHandler(IllegalArgumentException ex) {
		return buildExceptionResponse(HttpStatus.BAD_REQUEST, "400", ex.getMessage(), null);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
    public ExceptionResponse accessDeniedHandler(AccessDeniedException ex) {
        return buildExceptionResponse(HttpStatus.FORBIDDEN, "403", "Access denied", null);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ExceptionResponse authenticationHandler(AuthenticationException ex) {
        return buildExceptionResponse(HttpStatus.UNAUTHORIZED, "401", "Authentication failed", null);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ExceptionResponse noHandlerFoundHandler(NoHandlerFoundException ex) {
        return buildExceptionResponse(HttpStatus.NOT_FOUND, "404", "Endpoint not found", null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ExceptionResponse constraintViolationHandler(ConstraintViolationException ex) {
        Set<String> errorMessages = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toSet());

        return buildExceptionResponse(HttpStatus.BAD_REQUEST, "400", "Validation failed", errorMessages);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionResponse methodNotSupportedHandler(HttpRequestMethodNotSupportedException ex) {
        return buildExceptionResponse(HttpStatus.METHOD_NOT_ALLOWED, "405", "HTTP method not supported", null);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ExceptionResponse mediaTypeNotSupportedHandler(HttpMediaTypeNotSupportedException ex) {
        return buildExceptionResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "415", "Unsupported media type", null);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ExceptionResponse missingRequestParamHandler(MissingServletRequestParameterException ex) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, "400", "Missing request parameter: " + ex.getParameterName(), null);
    }

    @ExceptionHandler(MultipartException.class)
    public ExceptionResponse multipartHandler(MultipartException ex) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, "400", "Error processing file upload", null);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ExceptionResponse httpClientErrorHandler(HttpClientErrorException ex) {
        return buildExceptionResponse(HttpStatus.valueOf(ex.getRawStatusCode()),
                                       String.valueOf(ex.getRawStatusCode()),
                                       ex.getStatusText(), null);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ExceptionResponse httpServerErrorHandler(HttpServerErrorException ex) {
        return buildExceptionResponse(HttpStatus.valueOf(ex.getRawStatusCode()),
                                       String.valueOf(ex.getRawStatusCode()),
                                       "Server error from downstream service", null);
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ExceptionResponse resourceAccessHandler(ResourceAccessException ex) {
        return buildExceptionResponse(HttpStatus.SERVICE_UNAVAILABLE, "503", "Service unavailable or timeout", null);
    }

    @ExceptionHandler(RestClientException.class)
    public ExceptionResponse restClientHandler(RestClientException ex) {
        return buildExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "500", "Error while communicating with downstream service", null);
    }


	@ExceptionHandler(Exception.class)
	public ExceptionResponse generalExceptionHandler(Exception ex) {
		return buildExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "500", "An unexpected error occured", null);
	}

	private ExceptionResponse buildExceptionResponse(HttpStatus status, String statusCode, String message,
			Set<String> errorMessages) {
		return new ExceptionResponse(status, statusCode, message, errorMessages);
	}

}
