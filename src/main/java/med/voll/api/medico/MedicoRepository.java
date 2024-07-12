package med.voll.api.medico;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;

import java.util.Optional;

//al extender de JpaRepository se requiere el tipo de clase, y el tipo de dato del id, los dos deben ser indicados
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);
}
