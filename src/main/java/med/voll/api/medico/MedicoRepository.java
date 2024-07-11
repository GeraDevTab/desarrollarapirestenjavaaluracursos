package med.voll.api.medico;

import org.springframework.data.jpa.repository.JpaRepository;

//al extender de JpaRepository se requiere el tipo de clase, y el tipo de dato del id, los dos deben ser indicados
public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
