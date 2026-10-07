package com.clinica.controller;
import com.clinica.model.*;
import com.clinica.repository.*;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    record Cadastro(String login, String senha, String nome, String cpf) {}
    private final UsuarioRepository usuarios; private final PacienteRepository pacientes; private final PasswordEncoder enc;
    public AuthController(UsuarioRepository u, PacienteRepository p, PasswordEncoder e) { usuarios = u; pacientes = p; enc = e; }

    /** Auto-cadastro público: cria usuário PACIENTE + registro de paciente. */
    @PostMapping("/cadastro") public ResponseEntity<String> cadastrar(@RequestBody Cadastro c) {
        if (c.login() == null || c.login().isBlank() || c.senha() == null || c.senha().length() < 4 || c.nome() == null || c.nome().isBlank())
            return ResponseEntity.badRequest().body("Preencha nome, login e senha (mín. 4 caracteres).");
        if (usuarios.findByLogin(c.login()).isPresent()) return ResponseEntity.status(409).body("Login já existe.");
        Usuario u = new Usuario();
        u.setLogin(c.login()); u.setSenha(enc.encode(c.senha())); u.setPerfil(Usuario.Perfil.PACIENTE);
        usuarios.save(u);
        Paciente p = new Paciente();
        p.setNome(c.nome()); p.setCpf(c.cpf() == null || c.cpf().isBlank() ? null : c.cpf()); p.setLogin(c.login());
        pacientes.save(p);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/api/me") public Map<String, String> me(Authentication a) {
        return Map.of("login", a.getName(), "perfil", a.getAuthorities().iterator().next().getAuthority().substring(5));
    }
}
