package com.ideal.assets.services.exceptions;

public class EntityNotFundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public EntityNotFundException(String msg) {
		super(msg);
	}

}
