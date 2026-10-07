package com.clinica.config;
import com.clinica.model.Usuario;
import com.clinica.repository.UsuarioRepository;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean UserDetailsService uds(UsuarioRepository r) {
        return login -> r.findByLogin(login).filter(Usuario::isAtivo)
            .map(u -> User.withUsername(u.getLogin()).password(u.getSenha()).roles(u.getPerfil().name()).build())
            .orElseThrow(() -> new UsernameNotFoundException(login));
    }

    @Bean SecurityFilterChain chain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable())
            .authorizeHttpRequests(a -> a
                .requestMatchers("/login.html", "/cadastro.html", "/cadastro", "/app.js", "/style.css").permitAll()
                .requestMatchers("/api/usuarios/**", "/api/funcionarios/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/medicos/**", "/api/consultas/**", "/api/me").authenticated()
                .requestMatchers("/api/medicos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/pacientes/**").hasAnyRole("ADMIN", "FUNCIONARIO", "MEDICO")
                .requestMatchers("/api/pacientes/**").hasAnyRole("ADMIN", "FUNCIONARIO")
                .requestMatchers("/api/consultas/**").hasAnyRole("ADMIN", "FUNCIONARIO", "MEDICO")
                .requestMatchers("/api/prontuarios/**").hasAnyRole("ADMIN", "MEDICO")
                .anyRequest().authenticated())
            .formLogin(f -> f.loginPage("/login.html").loginProcessingUrl("/login")
                .defaultSuccessUrl("/index.html", true).failureUrl("/login.html?erro"))
            .logout(l -> l.logoutSuccessUrl("/login.html"))
            .exceptionHandling(e -> e.authenticationEntryPoint((req, res, ex) -> {
                if (req.getRequestURI().startsWith("/api/")) res.sendError(401); else res.sendRedirect("/login.html");
            }));
        return http.build();
    }
}
