package br.com.lactare.connect.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_nutriz")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Nutriz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, unique = true, length = 20)
    private String telefone;

    @Column(length = 160)
    private String email;

    @Column(nullable = false, length = 80)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(nullable = false)
    private Integer semanasPosParto;

    @Column(nullable = false)
    private Boolean consentimentoLgpd;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NutrizStatus status = NutrizStatus.ATIVA;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "nutriz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizResposta> respostasQuiz = new ArrayList<>();

    @OneToMany(mappedBy = "nutriz")
    private List<Agendamento> agendamentos = new ArrayList<>();

    @OneToMany(mappedBy = "nutriz")
    private List<Doacao> doacoes = new ArrayList<>();

    @OneToMany(mappedBy = "nutriz")
    private List<ScorePropensao> scores = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        criadoEm = LocalDateTime.now();
        if (status == null) {
            status = NutrizStatus.ATIVA;
        }
    }
}
