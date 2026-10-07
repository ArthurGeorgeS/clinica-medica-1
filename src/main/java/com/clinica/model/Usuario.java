package com.clinica.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;

@Entity @Getter @Setter
public class Usuario extends Base {
    public enum Perfil { ADMIN, MEDICO, FUNCIONARIO, PACIENTE }
    @Column(unique = true, nullable = false) private String login;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @Column(nullable = false) private String senha;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private Perfil perfil;
    private boolean ativo = true;
}
