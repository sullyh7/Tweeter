package com.sulay.tweeter.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {TweeterException.class})
    public ResponseEntity<Object> handleTweeterException(TweeterException e, WebRequest request) {
        ExceptionResponse body = new ExceptionResponse(HttpStatus.CONFLICT.toString(), e.getMessage(), Instant.now());
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {TweetNotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleTweetNotFoundException(TweetNotFoundException e, WebRequest request) {
        ExceptionResponse body = new ExceptionResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage(), Instant.now());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(value = {UserExistsException.class})
    public ResponseEntity<Object> handleUserExistsException(UserExistsException e, WebRequest request) {
        ExceptionResponse body = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), Instant.now());
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(JWTDecodeException.class)
    public ResponseEntity<Object> handleJwtVerificationException(JWTDecodeException e, WebRequest request) {
        ExceptionResponse body = new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), e.getMessage(),
                Instant.now());
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
