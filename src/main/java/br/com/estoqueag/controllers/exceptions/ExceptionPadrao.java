package br.com.estoqueag.controllers.exceptions;

import java.io.Serializable;
import java.time.Instant;

public class ExceptionPadrao implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Instant timestamp;
	private Integer stauts;
	private String error;
	private String message;
	private String path;
	
	public ExceptionPadrao() {}

	public ExceptionPadrao(Instant timestamp, Integer stauts, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.stauts = stauts;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getStauts() {
		return stauts;
	}

	public void setStauts(Integer stauts) {
		this.stauts = stauts;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	
}
