package br.com.lactare.connect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_campanha")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Campanha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 80)
    private String regiao;

    @Column(nullable = false, length = 30)
    private String canal;

    @Column(length = 500)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CampanhaStatus status = CampanhaStatus.RASCUNHO;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadaEm;

    private LocalDateTime disparadaEm;

    @PrePersist
    public void prePersist() {
        criadaEm = LocalDateTime.now();
        if (status == null) {
            status = CampanhaStatus.RASCUNHO;
        }
    }
}
