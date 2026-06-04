package br.com.fiap.terraorbit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TO_FARMS")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FARM")
    private Long id;

    @Column(name = "FARM_NAME", nullable = false,  length = 120)
    private String farmName;

    @Column(name = "LOCATION", length = 200)
    private String location;

    @Column(name = "FARM_SIZE_HECTARES", precision = 10, scale = 2)
    private Double farmSizeHectares;

    @ManyToOne
    @JoinColumn(name = "ID_USER")
    private User owner;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}