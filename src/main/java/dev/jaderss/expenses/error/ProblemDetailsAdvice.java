package dev.jaderss.expenses.error;

import static java.util.stream.Collectors.toMap;

import dev.jaderss.expenses.transactions.TransactionNotFoundException;
import java.util.TreeMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ProblemDetailsAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problem = e.getBody();
        problem.setDetail("The request is invalid.");
        problem.setProperty(
                "errors",
                e.getBindingResult().getFieldErrors().stream()
                        .collect(toMap(
                                FieldError::getField,
                                error -> String.valueOf(error.getDefaultMessage()),
                                (first, second) -> first,
                                TreeMap::new)));
        return handleExceptionInternal(e, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail problem = e.getBody();
        problem.setDetail("The request is invalid.");
        problem.setProperty(
                "errors",
                e.getParameterValidationResults().stream()
                        .collect(toMap(
                                result -> result.getMethodParameter().getParameterName(),
                                result -> String.valueOf(
                                        result.getResolvableErrors().getFirst().getDefaultMessage()),
                                (first, second) -> first,
                                TreeMap::new)));
        return handleExceptionInternal(e, problem, headers, status, request);
    }

    @ExceptionHandler
    ProblemDetail notFound(TransactionNotFoundException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
