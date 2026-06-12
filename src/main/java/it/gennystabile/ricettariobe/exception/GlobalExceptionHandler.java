package it.gennystabile.ricettariobe.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler {

    //per usare i logger
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    // Gestione 204 - No Content
    // Dichiara che questo metodo deve scattare SOLO quando viene lanciata una ResourceNotFoundException.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        GenericErrorResponse error = new GenericErrorResponse(
                HttpStatus.NO_CONTENT.value(),
                "Risorsa non trovata",
                ex.getMessage()
        );
        log.warn("Errore intercettato: {}", ex);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(error);
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
        log.error("Errore intercettato:{} ", ex);
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

    // Gestione 401 Non Autorizzato (Sia per eccezioni custom che per fallimenti di Spring Security)
    // Inserendo entrambe le classi nell'array, l'handler intercetta sia il login fallito sia gli errori manuali
    @ExceptionHandler({UnauthorizedException.class, AuthenticationException.class})
    public ResponseEntity<GenericErrorResponse> handleUnauthorizedException(Exception ex) {
        GenericErrorResponse error = new GenericErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Credenziali di Autenticazione non valide",
                ex.getMessage()
        );
        log.warn("Errore intercettato: {}", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    //  Gestione errori del @RequestBody (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        // Ottiene il risultato del binding che contiene i dettagli degli errori
        List<String> errors = ex.getBindingResult()
                //Estrae l'elenco specifico di tutti i campi che hanno fallito la validazione
                .getFieldErrors()
                //Apre uno stream per elaborare la collezione di errori
                .stream()
                // Mappa ogni oggetto errore in una stringa formattata come "nomeCampo: messaggio di errore"
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                // Colleziona i risultati trasformandoli in una lista immutabile
                .toList();

        // Concatena tutti gli elementi della lista 'errors' in un'unica stringa, separandoli con una virgola e uno spazio
        String dettaglioErrori = String.join(", ", errors);

        GenericErrorResponse error = new GenericErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validazione fallita (Campi del corpo della richiesta non validi)",
                dettaglioErrori
        );
        log.warn("Errore di validazione del DTO intercettato: {}", dettaglioErrori);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    // Gestione errori di @PathVariable e @RequestParam (@Validated)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GenericErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        // Estrae l'insieme (Set) di tutte le violazioni rilevate sui parametri
        List<String> errors = ex.getConstraintViolations()
                // Apre uno stream per elaborare le violazioni
                .stream()
                //mappa ogni singola violazione
                .map(violation -> {
                    // Trasforma il path in stringa (es. "deleteIngredient.nome")
                    String pathStr = violation.getPropertyPath().toString();
                    // Prende solo la parte dopo l'ultimo punto
                    String paramName = pathStr.substring(pathStr.lastIndexOf('.') + 1);
                    // Restituisce la stringa formattata unendo il nome del parametro URL al relativo messaggio di errore (es. "id: deve essere maggiore di 0")
                    return paramName + ": " + violation.getMessage();
                })
                .toList();

        String dettaglioErrori = String.join(", ", errors);

        GenericErrorResponse error = new GenericErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validazione fallita (Parametri URL non validi)",
                dettaglioErrori
        );
        log.warn("Errore di validazione ndei parametri URL intercettato: {}", dettaglioErrori);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

}
