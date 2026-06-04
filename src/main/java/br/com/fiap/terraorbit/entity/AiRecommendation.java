package br.com.fiap.terraorbit.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TO_AI_RECOMMENDATIONS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RECOMMENDATION")
    private Long id;

    @Column(name = "RECOMMENDATION", length = 1000)
    private String recommendation;

    @Column(name = "RISK_LEVEL", length = 30)
    private String riskLevel;

    @Column(name = "GENERATED_AT")
    private LocalDateTime generatedAt;

    @ManyToOne
    @JoinColumn(name = "ID_FARM")
    private Farm farm;
}