package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.medico.DatosListadoMedico;
import med.voll.api.medico.DatosRegistroMedico;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){
        System.out.println("Elr equest llega correctamente");
        System.out.println(datosRegistroMedico);
        medicoRepository.save(new Medico(datosRegistroMedico));
    }

    //el codigo siguiente es para tgrabajar con listas de objetos
//    @GetMapping
//    public List<DatosListadoMedico> listadoMedicos(){
//        return medicoRepository.findAll().stream()
//                .map(DatosListadoMedico::new)
//                .toList();
//    }

    //el codigo siguiente es para tgrabajar con paginacion osea "pages"
    @GetMapping
    public Page<DatosListadoMedico> listadoMedicos(Pageable paginacion){
        return medicoRepository.findAll(paginacion)
                .map(DatosListadoMedico::new);
    }
}
