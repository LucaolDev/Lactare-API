package br.com.lactare.connect.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_blh")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Blh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 180)
    private String endereco;

    @Column(nullable = false, length = 80)
    private String bairro;

    @Column(nullable = false, length = 80)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = false, length = 100)
    private String horarioFuncionamento;

    @Column(nullable = false)
    private Boolean aceitaColetaDomiciliar = false;

    @Column(nullable = false)
    private Boolean ativo = true;

    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "blh")
    private List<Agendamento> agendamentos = new ArrayList<>();

    @OneToMany(mappedBy = "blh")
    private List<Doacao> doacoes = new ArrayList<>();
}
