package com.clinica.controller;
import com.clinica.model.Prontuario;
import com.clinica.repository.ProntuarioRepository;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/prontuarios")
public class ProntuarioController extends CrudController<Prontuario> {
    public ProntuarioController(ProntuarioRepository r) { super(r); }
}
