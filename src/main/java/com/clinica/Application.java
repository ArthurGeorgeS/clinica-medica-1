package com.clinica;

import com.clinica.model.Usuario;
import com.clinica.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application {
    public static void main(String[] args) { SpringApplication.run(Application.class, args); }

    /** Cria o administrador inicial (admin / admin123) se ainda não existir. */
    @Bean CommandLineRunner seed(UsuarioRepository r, PasswordEncoder enc) {
        return a -> {
            if (r.findByLogin("admin").isEmpty()) {
                Usuario u = new Usuario();
                u.setLogin("admin"); u.setSenha(enc.encode("admin123")); u.setPerfil(Usuario.Perfil.ADMIN);
                r.save(u);
            }
        };
    }
}
