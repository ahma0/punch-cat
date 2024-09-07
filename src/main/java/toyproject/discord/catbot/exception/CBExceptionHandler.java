package toyproject.discord.catbot.exception;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CBExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleNotSavedException(@NotNull CBNotSavedException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handle(@NotNull Throwable exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
