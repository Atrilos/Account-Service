package account.exception;

import account.exception.model.CustomError;
import account.model.constant.Role;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static account.configuration.messages.AdminMessages.ROLE_NOT_FOUND_ERRORMSG;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler({UserExistException.class,
            RemoveUserException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    public void handleBadRequestExceptions(RuntimeException ex,
                                           HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public void handleBindException(BindException ex,
                                    HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(),
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public void handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
                                                      HttpServletResponse response) throws IOException {
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException
            && invalidFormatException.getTargetType().equals(Role.class))
            response.sendError(HttpStatus.NOT_FOUND.value(), ROLE_NOT_FOUND_ERRORMSG);
        else
            response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler({PaymentNotFoundException.class,
            UserNotFoundException.class})
    public void handlePaymentNotFoundException(RuntimeException ex,
                                               HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                             WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity
                .status(status)
                .body(createCustomError(ex, request, status));
    }

    private CustomError createCustomError(BindException ex,
                                          WebRequest request, HttpStatus status) {
        CustomError error = new CustomError();
        error.setStatus(status.value());
        error.setErrorMessage(status.getReasonPhrase());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String message = fieldErrors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(", "));
        error.setMessage(message);

        error.setPath(((ServletWebRequest) request).getRequest().getServletPath());

        return error;
    }

}
