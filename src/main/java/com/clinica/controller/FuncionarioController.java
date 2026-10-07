package com.clinica.controller;
import com.clinica.model.Funcionario;
import com.clinica.repository.FuncionarioRepository;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/funcionarios")
public class FuncionarioController extends CrudController<Funcionario> {
    public FuncionarioController(FuncionarioRepository r) { super(r); }
}
