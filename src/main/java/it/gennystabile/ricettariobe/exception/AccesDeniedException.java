package it.gennystabile.ricettariobe.exception;

public class AccesDeniedException extends RuntimeException {
    public AccesDeniedException(String message) {
        super(message);
    }
}
