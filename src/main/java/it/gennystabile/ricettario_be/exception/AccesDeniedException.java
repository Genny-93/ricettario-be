package it.gennystabile.ricettario_be.exception;

public class AccesDeniedException extends RuntimeException {
    public AccesDeniedException(String message) {
        super(message);
    }
}
