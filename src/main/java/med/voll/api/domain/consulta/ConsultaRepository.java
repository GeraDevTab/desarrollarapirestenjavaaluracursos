package med.voll.api.domain.consulta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


//al extender de JpaRepository se requiere el tipo de clase, y el tipo de dato del id, los dos deben ser indicados

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByPacienteIdAndDataBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoIdAndData(Long idMedico, LocalDateTime fecha);
}
