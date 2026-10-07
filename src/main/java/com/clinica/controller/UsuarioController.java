package com.clinica.controller;
import com.clinica.model.Usuario;
import com.clinica.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/usuarios")
public class UsuarioController extends CrudController<Usuario> {
    private final PasswordEncoder enc;
    public UsuarioController(UsuarioRepository r, PasswordEncoder enc) { super(r); this.enc = enc; }

    @Override protected Usuario preparar(Usuario novo, Usuario antigo) {
        boolean vazia = novo.getSenha() == null || novo.getSenha().isBlank();
        novo.setSenha(vazia && antigo != null ? antigo.getSenha() : enc.encode(novo.getSenha()));
        return novo;
    }
}
