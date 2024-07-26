package com.tech.challenge.util.exception.condutor;

public class ConductorAlreadyExistsException extends Exception {

    public ConductorAlreadyExistsException(){super("Condutor já existente!");}

    public ConductorAlreadyExistsException(String message){super(message);}

}
