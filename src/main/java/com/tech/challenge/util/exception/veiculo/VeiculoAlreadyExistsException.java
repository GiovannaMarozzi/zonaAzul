package com.tech.challenge.util.exception.veiculo;

public class VeiculoAlreadyExistsException extends Exception{

    public VeiculoAlreadyExistsException(){super("Veículo já cadastrado");}

    public VeiculoAlreadyExistsException(String message){super(message);}
}
