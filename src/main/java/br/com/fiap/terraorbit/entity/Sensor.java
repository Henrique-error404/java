package br.com.fiap.terraorbit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TO_SENSORS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SENSOR")
    private Long id;

    @Column(name = "SENSOR_NAME", nullable = false)
    private String sensorName;

    @Column(name = "SENSOR_TYPE")
    private String sensorType;

    @Column(name = "SENSOR_STATUS")
    private String sensorStatus;

    @Column(name = "LAST_READING")
    private BigDecimal lastReading;

    @Column(name = "INSTALLED_AT")
    private LocalDateTime installedAt;

    @ManyToOne
    @JoinColumn(name = "ID_FARM")
    private Farm farm;
}