package com.clinica.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter; import lombok.Setter;

@Entity @Getter @Setter
public class Paciente extends Base {
    @Column(nullable = false) private String nome;
    @Column(unique = true) private String cpf;
    private LocalDate dataNascimento;
    private String telefone, email;
    private String login; // login do usuário vinculado
}
