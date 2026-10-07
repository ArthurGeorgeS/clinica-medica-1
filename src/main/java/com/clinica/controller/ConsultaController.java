package com.clinica.controller;
import com.clinica.model.Consulta;
import com.clinica.repository.ConsultaRepository;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/consultas")
public class ConsultaController extends CrudController<Consulta> {
    private final ConsultaRepository cr;
    public ConsultaController(ConsultaRepository r) { super(r); this.cr = r; }

    /** Paciente enxerga somente as próprias consultas. */
    @Override protected List<Consulta> consultar() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        boolean paciente = a.getAuthorities().stream().anyMatch(g -> g.getAuthority().equals("ROLE_PACIENTE"));
        return paciente ? cr.findByPacienteLogin(a.getName()) : cr.findAll();
    }
}
