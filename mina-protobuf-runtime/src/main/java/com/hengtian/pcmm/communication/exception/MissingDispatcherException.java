package com.hengtian.pcmm.communication.exception;

public class MissingDispatcherException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public MissingDispatcherException(String message) {
		super(message);
	}
}
