package fr.matawan.football.management.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;
/**
 * Gestionnaire global des exceptions pour capturer les erreurs de validation
 * et formater les messages d'erreur de manière plus lisible et personnalisée.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> addErrorDetails(fieldError))
                .collect(Collectors.joining("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    private String addErrorDetails(FieldError fieldError) {
        // Exemple: joueurs[0].dateDebut
        String field = fieldError.getField();
        String message = fieldError.getDefaultMessage();
        // Si le champ concerne la liste joueurs, on extrait l'indice
        if (field.startsWith("equipePayload.joueurs[")) {
            // Extraire l'indice
            int start = field.indexOf('[') + 1;
            int end = field.indexOf(']');
            String index = field.substring(start, end);
            return message + " (joueur n°" + (Integer.parseInt(index) + 1) + ")";
        }
        return message;
    }
}
