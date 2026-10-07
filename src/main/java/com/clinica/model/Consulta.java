package com.clinica.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter; import lombok.Setter;

@Entity @Getter @Setter
public class Consulta extends Base {
    @ManyToOne(optional = false) private Paciente paciente;
    @ManyToOne(optional = false) private Medico medico;
    @Column(nullable = false) private LocalDateTime dataHora;
    private String motivo;
    private String status = "AGENDADA"; // AGENDADA, REALIZADA, CANCELADA
}
