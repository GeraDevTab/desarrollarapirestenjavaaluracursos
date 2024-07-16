package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;

import java.time.Duration;
import java.time.LocalDate;

public class HorarioAnticipacion {

    public void validar(DatosAgendarConsulta datos){
        var ahora = LocalDate.now();
        var horaDeConsulta = datos.fecha();

        var diferencia30min = Duration.between(ahora,horaDeConsulta).toMinutes()<30;
        if (diferencia30min){
            throw new ValidationException("Las consultas deben programarse con al menos 30 minutosw de anticipacion");
        }
    }
}
