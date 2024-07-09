package com.tech.challenge.zonaAzul.util.exception;

public class ConductorAlreadyExistsException extends Exception {

    public ConductorAlreadyExistsException(){super("Condutor já existente!");}

    public ConductorAlreadyExistsException(String message){super(message);}

}
