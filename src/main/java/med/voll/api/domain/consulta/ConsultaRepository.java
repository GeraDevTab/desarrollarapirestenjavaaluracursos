package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



//al extender de JpaRepository se requiere el tipo de clase, y el tipo de dato del id, los dos deben ser indicados

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

}
