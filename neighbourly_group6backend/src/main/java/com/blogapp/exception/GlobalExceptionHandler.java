package com.blogapp.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(UserNotFoundException exception,
			WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ErrorDetails> handleUserAlreadyExistException(UserAlreadyExistException exception,
			WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(PostNotFoundException exception,
			WebRequest webRequest) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
				webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}



	@ResponseStatus
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> validationHandler(MethodArgumentNotValidException ex) {
		Map<String, String> validatonErrors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			validatonErrors.put(error.getField(), error.getDefaultMessage());
		});
		return validatonErrors;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resousceNotFoundException(ResourceNotFoundException ex) {
		String mesg = ex.getMessage();
		ApiResponse s = new ApiResponse(mesg, false);
		return new ResponseEntity<ApiResponse>(s, HttpStatus.NOT_FOUND);

	}

}