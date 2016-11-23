package br.com.pucrs.collections;

public class EmptyQueueException extends RuntimeException {

    public EmptyQueueException() {
        super("EmptyQueueException");
    }
    
    public EmptyQueueException(String message) {
        super(message);
    }
    
}
