package br.com.lactare.connect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tb_doacao")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nutriz_id", nullable = false)
    private Nutriz nutriz;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blh_id", nullable = false)
    private Blh blh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;

    @Column(nullable = false)
    private Integer volumeMl;

    @Column(nullable = false)
    private LocalDate dataDoacao;

    @Column(nullable = false)
    private Integer bebesBeneficiados;

    @Column(length = 300)
    private String observacoes;
}
