package med.voll.api.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//con esta clase se personaliza en comportamiento de las respuestas de errores http
//aqui digamos que tratamos los errores y personalizamos el mensaje devuelto hacia el cliente
@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream()
                .map(DatosErrorValidacion::new).toList();
        //con body ya no necesitamos el built
        return ResponseEntity.badRequest().body(errores);
    }

    //agregando la clase ValidacionDeIntegridad para que los mensajes de esas validaciones sean enviados en la
    // respuesta de la solicitud
    @ExceptionHandler(ValidacionDeIntegridad.class)
    public ResponseEntity errorHandlerValidacionesDeNegocio(Exception e){

        //con body ya no necesitamos el built
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    private record DatosErrorValidacion(String campo, String error){
        public DatosErrorValidacion(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
