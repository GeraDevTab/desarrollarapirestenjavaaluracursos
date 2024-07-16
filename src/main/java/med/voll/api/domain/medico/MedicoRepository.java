package med.voll.api.domain.medico;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

//al extender de JpaRepository se requiere el tipo de clase, y el tipo de dato del id, los dos deben ser indicados
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    //LA FORMA DE ESCRIBIR LAS CONSULTAR PERSONALIZADAS CON @QUERY VAN DENTRO DE 3 COMMILAS Y CIERRA CON 3 COMILLAS
    //se usan los dos puntitos antes de escribir el parametro que se recibe en el metodo
    @Query("""
    SELECT m FROM Medico m
    WHERE
        m.activo=1 and
        m.especialidad=:especialidad and
        m.id not in(
            select c.medico.id from Consulta c
            where
         c.data= :fecha)
    order by rand() limit 1
    """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);

    @Query("""
            select m.activo
            from Medico m
            where m.id = :idMedico
            """)
    Boolean findActivoById(Long idMedico);
}
