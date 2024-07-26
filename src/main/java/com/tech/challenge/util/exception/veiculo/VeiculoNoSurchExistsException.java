package com.tech.challenge.util.exception.veiculo;

public class VeiculoNoSurchExistsException extends Exception{

    public VeiculoNoSurchExistsException(){super("Nenhum veículo encontrado!");}

    public VeiculoNoSurchExistsException(String message){super(message);}
}
