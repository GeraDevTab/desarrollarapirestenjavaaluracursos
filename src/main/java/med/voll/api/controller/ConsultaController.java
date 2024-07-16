package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaDeConsultaService;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.consulta.DatosDetallesConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos){
        System.out.println(datos);

        service.agendar(datos);
        //*********
        //las lineas sig estarian violando el principio de responsabilidad unica asignandole
        //a nuestro controller que haga validaciones, lo ideal es crear un servicio que se encargue de eso AgendaDeConsultaService
        //if(datos.idMedico()!=null){
          //
        //}
        //********

        return ResponseEntity.ok(new DatosDetallesConsulta(null, null, null, null));

    }
}
