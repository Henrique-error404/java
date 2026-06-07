package br.com.fiap.terraorbit.entity;

import br.com.fiap.terraorbit.dto.response.AiAnalysisResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TO_AI_RECOMMENDATIONS")
@Data
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
    @Enumerated(EnumType.STRING)
    private RISKLEVEL riskLevel;

    @Column(name = "GENERATED_AT")
    @Builder.Default
    private LocalDateTime generatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "ID_FARM")
    private Farm farm;

    public AiRecommendation(AiAnalysisResponse analysisResponse, Farm farm) {
        this.riskLevel = RISKLEVEL.valueOf(analysisResponse.riskLevel().toUpperCase());
        this.recommendation = analysisResponse.recommendation();
        this.farm = farm;
        this.generatedAt = LocalDateTime.now();
    }
}