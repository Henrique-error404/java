package br.com.fiap.terraorbit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TO_INCIDENTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INCIDENT")
    private Long id;

    @Column(name = "INCIDENT_TYPE", length = 100)
    private String incidentType;

    @Column(name = "INCIDENT_DESCRIPTION", length = 500)
    private String incidentDescription;

    @Column(name = "INCIDENT_DATE")
    @Builder.Default
    private LocalDateTime incidentDate = LocalDateTime.now();

    @Column(name = "INCIDENT_STATUS")
    private String incidentStatus;

    @ManyToOne
    @JoinColumn(name = "ID_FARM")
    private Farm farm;
}