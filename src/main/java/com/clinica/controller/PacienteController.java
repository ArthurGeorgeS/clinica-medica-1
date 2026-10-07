package com.clinica.controller;
import com.clinica.model.Paciente;
import com.clinica.repository.PacienteRepository;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/pacientes")
public class PacienteController extends CrudController<Paciente> {
    public PacienteController(PacienteRepository r) { super(r); }
}
