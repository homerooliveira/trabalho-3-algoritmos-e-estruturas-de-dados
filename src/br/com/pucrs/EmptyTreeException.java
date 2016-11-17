package br.com.pucrs;

public class EmptyTreeException extends RuntimeException {

    public EmptyTreeException() {
        super("EmptyTreeException");
    }
    
    public EmptyTreeException(String message) {
        super(message);
    }    
    
}
