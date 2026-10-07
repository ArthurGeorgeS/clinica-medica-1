package com.clinica.controller;
import com.clinica.model.Medico;
import com.clinica.repository.MedicoRepository;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/medicos")
public class MedicoController extends CrudController<Medico> {
    public MedicoController(MedicoRepository r) { super(r); }
}
