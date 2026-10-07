package com.clinica.model;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;

@Entity @Getter @Setter
public class Medico extends Base {
    @Column(nullable = false) private String nome;
    @Column(unique = true, nullable = false) private String crm;
    private String especialidade, telefone, login;
}
