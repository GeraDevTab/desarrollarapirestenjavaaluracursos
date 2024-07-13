package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping//se coloca el tipo de dato generico que retornara REsponseEntity
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
        System.out.println("Elr equest llega correctamente");
        System.out.println(datosRegistroMedico);
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
        URI url = uriComponentsBuilder.path("medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
        // return 201 created
        //deberia de devolver la ruta donde puedes ver el objeto creado
        // http://localhots:8080/medico/xx
    }

    //el codigo siguiente es para tgrabajar con listas de objetos
//    @GetMapping
//    public List<DatosListadoMedico> listadoMedicos(){
//        return medicoRepository.findAll().stream()
//                .map(DatosListadoMedico::new)
//                .toList();
//    }

    //el codigo siguiente es para tgrabajar con paginacion osea "pages"
    @GetMapping//la anotacion @pageabledefault, para establecer parametros por defecto, en este caso el size en 2
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault(size = 3) Pageable paginacion){
//        return medicoRepository.findAll(paginacion)
//                .map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion)
                .map(DatosListadoMedico::new));
    }

    @PutMapping
    @Transactional //@Transsactional esta anotacion es para asegurar que se ejecute la operacion enla base de datos,
    // ademas que si tengo 2 o mas acciones a la base de datos y una falla, se hace un roll back a la
    // base de datos y se regresa a su estado anterior, es decir, no sucedio nada.
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));

    }

    //en la sig linea, para que sea dinamico el dato, se le coloca entre llaves {id} para señalar que ahi llegaran
    //datos variables
    //con la anotacion @pathvariable es para que spring detecte que la variable id en este ejemplo va a venir de path variable
    @DeleteMapping("/{id}")
    @Transactional //si queremos que los datos sean cambiados y se ejecuten las consultas se le agrega @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

//  metodo que borra el registro en la base de datos
//    public void eliminarMedico(@PathVariable Long id){
//        Medico medico = medicoRepository.getReferenceById(id);
//        //medicoRepository.delete(medico);
//
//    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedicos(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(),
                medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }
}
