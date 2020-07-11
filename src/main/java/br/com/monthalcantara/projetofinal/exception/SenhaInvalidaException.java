package br.com.monthalcantara.projetofinal.exception;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException(){
        super("Senha Inválida");
    }
}
