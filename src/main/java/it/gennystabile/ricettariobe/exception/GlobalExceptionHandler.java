package it.gennystabile.ricettariobe.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    //per usare i logger
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    // Gestione 404 - Not Found
    // Dichiara che questo metodo deve scattare SOLO quando viene lanciata una ResourceNotFoundException.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        GenericErrorResponse error = new GenericErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Risorsa non trovata",
                ex.getMessage()
        );
        log.warn("Errore intercettato: {}", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Gestione 400 Bad Request
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GenericErrorResponse> handleBadRequest(BadRequestException ex) {
        GenericErrorResponse error = new GenericErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage()
        );
        log.warn("Errore intercettato: {} ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Gestione 403 Accesso Negato
    @ExceptionHandler(AccesDeniedException.class)
    public ResponseEntity<GenericErrorResponse> handleAccessDenied(AccesDeniedException ex) {
        GenericErrorResponse error = new GenericErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                "Permessi insufficienti per completare l'operazione.",
                ex.getMessage()
        );
        log.warn("Errore intercettato: {} ", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    //Gestione 500 Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericErrorResponse> handleGlobalException(Exception ex) {
        GenericErrorResponse error = new GenericErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage()
        );
        log.warn("Errore intercettato:{} ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<GenericErrorResponse> handleDuplicateException(DuplicateException ex) {
        GenericErrorResponse error = new GenericErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Risorsa già presente nel database",
                ex.getMessage()
        );
        log.warn("Errore intercettato: {}", ex); //TODO usare il placeholder, fare prova con log error, log info
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // Gestione 401 Non Autorizzato
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<GenericErrorResponse> handleUnauthorizedException(UnauthorizedException ex) {
        GenericErrorResponse error = new GenericErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Mancano credenziali di autenticazione valide",
                ex.getMessage()
        );
        log.warn("Errore intercettato: {}", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }


}
