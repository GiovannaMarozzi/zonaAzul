package com.tech.challenge.zonaAzul.util.exception.veiculo;

public class VeiculoAlreadyExistsException extends Exception{

    public VeiculoAlreadyExistsException(){super("Veículo já cadastrado");}

    public VeiculoAlreadyExistsException(String message){super(message);}
}
