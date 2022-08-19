package br.com.estoqueag.controllers.exceptions;

import java.time.Instant;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ExceptionPadrao> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
		ExceptionPadrao err = new ExceptionPadrao();
		err.setTimestamp(Instant.now());
		err.setStauts(HttpStatus.NOT_FOUND.value());
		err.setError("Recurso não encontrado");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ExceptionPadrao> illegalArgument(IllegalArgumentException e, HttpServletRequest request){
		ExceptionPadrao err = new ExceptionPadrao();
		err.setTimestamp(Instant.now());
		err.setStauts(HttpStatus.BAD_REQUEST.value());
		err.setError("Erro nos dados da operação");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionPadrao> methodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request){
		ExceptionPadrao err = new ExceptionPadrao();
		err.setTimestamp(Instant.now());
		err.setStauts(HttpStatus.BAD_REQUEST.value());
		err.setError("Erro nos dados da operação");
		err.setPath(request.getRequestURI());
		StringBuilder errorMessage = new StringBuilder();
		e.getAllErrors().forEach(erro->{
			errorMessage.append(erro.getDefaultMessage()).append("; ");
		});
		err.setMessage(errorMessage.toString());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
}
