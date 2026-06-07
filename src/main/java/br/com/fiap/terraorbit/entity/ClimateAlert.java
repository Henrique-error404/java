package br.com.fiap.terraorbit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TO_CLIMATE_ALERTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClimateAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALERT")
    private Long id;

    @Column(name = "ALERT_TYPE", length = 100)
    private String alertType;

    @Column(name = "SEVERITY", length = 30)
    private String severity;

    @Column(name = "MESSAGE", length = 500)
    private String message;

    @Column(name = "ALERT_DATE")
    private LocalDateTime alertDate;

    @ManyToOne
    @JoinColumn(name = "ID_FARM")
    private Farm farm;

    public ClimateAlert(String drought, String high, String potentialDroughtDetected, LocalDateTime now, Farm farm) {
        this.alertType = drought;
        this.severity = high;
        this.message = potentialDroughtDetected;
        this.alertDate = now;
        this.farm = farm;
    }
}