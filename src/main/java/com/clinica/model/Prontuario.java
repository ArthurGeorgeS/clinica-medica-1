package com.clinica.model;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;

@Entity @Getter @Setter
public class Prontuario extends Base {
    @ManyToOne(optional = false) private Consulta consulta;
    @Column(length = 4000) private String descricao;
    @Column(length = 1000) private String diagnostico, prescricao;
}
