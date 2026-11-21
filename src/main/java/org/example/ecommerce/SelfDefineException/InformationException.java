package org.example.ecommerce.SelfDefineException;

public class InformationException extends RuntimeException{
    public InformationException(String message){
        super(message);
    }
    public InformationException(String message, Throwable cause){
        super(message, cause);
    }
}
