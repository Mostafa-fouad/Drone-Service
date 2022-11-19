package com.task.core.controller.advice;

import com.task.core.exception.NotFoundException;
import com.task.core.exception.ValidationException;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiErrorResponse handleException(@NotNull MethodArgumentNotValidException ex) {

    if (ex.getBindingResult().getFieldErrors().stream()
        .anyMatch(
            fieldError ->
                Objects.requireNonNull(fieldError.getDefaultMessage()).contains("^[A-Z0-9_]*$"))) {

      return logAndRespond(
          "Medication item 'CODE' can contains ONLY Capital letters, numbers or '_'", ex);
    }

    if (ex.getBindingResult().getFieldErrors().stream()
        .anyMatch(
            fieldError ->
                Objects.requireNonNull(fieldError.getDefaultMessage())
                    .contains("^[A-Za-z0-9_-]*$"))) {

      return logAndRespond(
          "Medication item 'NAME' can contains ONLY Capital/Small letters, numbers, '-' or '_'",
          ex);
    }

    String message =
        ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(". "));
    return logAndRespond(message, ex);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BindException.class)
  public ApiErrorResponse handleException(@NotNull BindException ex) {
    String message =
        ex.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));
    return logAndRespond(message, ex);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public ApiErrorResponse handleValidationException(ValidationException ex) {
    return logAndRespond(ex.getMessage(), ex);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ApiErrorResponse handleException(@NotNull MethodArgumentTypeMismatchException ex) {
    final String message = "Method argument type mismatch. " + ex.getMessage();
    return logAndRespond(message, ex);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public ApiErrorResponse handleException(IllegalArgumentException ex) {
    return logAndRespond(ex.getMessage(), ex);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({MissingServletRequestParameterException.class})
  public ApiErrorResponse handleException(MissingServletRequestParameterException ex) {
    return logAndRespond(ex.getMessage(), ex);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public ApiErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {
    return logAndRespond(ex.getMessage(), ex);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
  public ApiErrorResponse handleHibernateConstraintViolationException(
      org.hibernate.exception.@NotNull ConstraintViolationException ex) {
    String message = ex.getCause().getMessage();
    message =
        message.indexOf("Detail:") > 0 ? message.substring(message.indexOf("Detail:")) : message;
    return logAndRespond(message, ex);
  }

  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
  public ApiErrorResponse handleException(HttpRequestMethodNotSupportedException ex) {
    return logAndRespond(ex.getMessage(), ex);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ApiErrorResponse handleIdentityUserNotFoundException(NotFoundException ex) {
    return logAndRespond(ex.getMessage(), ex);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(NullPointerException.class)
  public ApiErrorResponse handleNullPointerException(NullPointerException ex) {
    return logAndRespond(ex.getMessage(), ex);
  }

  @Contract("_, _ -> new")
  private @NotNull ApiErrorResponse logAndRespond(final String customMessage, final Exception ex) {
    log.error("Caught Error with message = {}", customMessage, ex);
    return new ApiErrorResponse(customMessage);
  }
}
