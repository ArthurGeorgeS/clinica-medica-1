package com.clinica.model;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;

@Entity @Getter @Setter
public class Funcionario extends Base {
    @Column(nullable = false) private String nome;
    @Column(unique = true) private String cpf;
    private String cargo, telefone, login;
}
