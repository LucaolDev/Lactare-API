package br.com.lactare.connect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_quiz_resposta")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class QuizResposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nutriz_id", nullable = false)
    private Nutriz nutriz;

    @Column(nullable = false, length = 120)
    private String pergunta;

    @Column(nullable = false, length = 160)
    private String resposta;

    @Column(nullable = false)
    private Boolean elegivel;

    @Column(nullable = false, updatable = false)
    private LocalDateTime respondidaEm;

    @PrePersist
    public void prePersist() {
        respondidaEm = LocalDateTime.now();
    }
}
