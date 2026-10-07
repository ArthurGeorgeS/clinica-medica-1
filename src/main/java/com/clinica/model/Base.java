package com.clinica.model;
import jakarta.persistence.*;
import lombok.Getter; import lombok.Setter;

@MappedSuperclass @Getter @Setter
public abstract class Base {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
}
