package br.com.lactare.connect.config;

import br.com.lactare.connect.entity.Agendamento;
import br.com.lactare.connect.entity.AgendamentoStatus;
import br.com.lactare.connect.entity.Blh;
import br.com.lactare.connect.entity.Campanha;
import br.com.lactare.connect.entity.CampanhaStatus;
import br.com.lactare.connect.entity.Doacao;
import br.com.lactare.connect.entity.Nutriz;
import br.com.lactare.connect.entity.PrioridadeIa;
import br.com.lactare.connect.entity.QuizResposta;
import br.com.lactare.connect.entity.ScorePropensao;
import br.com.lactare.connect.entity.TipoColeta;
import br.com.lactare.connect.repository.AgendamentoRepository;
import br.com.lactare.connect.repository.BlhRepository;
import br.com.lactare.connect.repository.CampanhaRepository;
import br.com.lactare.connect.repository.DoacaoRepository;
import br.com.lactare.connect.repository.NutrizRepository;
import br.com.lactare.connect.repository.QuizRespostaRepository;
import br.com.lactare.connect.repository.ScorePropensaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDatabase(NutrizRepository nutrizRepository,
                                   BlhRepository blhRepository,
                                   QuizRespostaRepository quizRepository,
                                   AgendamentoRepository agendamentoRepository,
                                   DoacaoRepository doacaoRepository,
                                   CampanhaRepository campanhaRepository,
                                   ScorePropensaoRepository scoreRepository) {
        return args -> {
            if (nutrizRepository.count() > 0) {
                return;
            }

            Nutriz ana = new Nutriz();
            ana.setNome("Ana Silva");
            ana.setTelefone("11999998888");
            ana.setEmail("ana.silva@example.com");
            ana.setCidade("São Paulo");
            ana.setEstado("SP");
            ana.setSemanasPosParto(6);
            ana.setConsentimentoLgpd(true);
            ana = nutrizRepository.save(ana);

            Nutriz carla = new Nutriz();
            carla.setNome("Carla Mendes");
            carla.setTelefone("11988887777");
            carla.setEmail("carla.mendes@example.com");
            carla.setCidade("São Paulo");
            carla.setEstado("SP");
            carla.setSemanasPosParto(4);
            carla.setConsentimentoLgpd(true);
            carla = nutrizRepository.save(carla);

            Blh santaCasa = new Blh();
            santaCasa.setNome("BLH Santa Casa");
            santaCasa.setEndereco("Rua Dr. Cesário Motta Jr., 61");
            santaCasa.setBairro("Vila Buarque");
            santaCasa.setCidade("São Paulo");
            santaCasa.setEstado("SP");
            santaCasa.setCep("01221-020");
            santaCasa.setHorarioFuncionamento("Seg-Sex 07:00-17:00");
            santaCasa.setAceitaColetaDomiciliar(true);
            santaCasa.setLatitude(-23.5475);
            santaCasa.setLongitude(-46.6515);
            santaCasa = blhRepository.save(santaCasa);

            Blh maternidade = new Blh();
            maternidade.setNome("BLH Maternidade Maria Auxiliadora");
            maternidade.setEndereco("Avenida Nazaré, 1501");
            maternidade.setBairro("Ipiranga");
            maternidade.setCidade("São Paulo");
            maternidade.setEstado("SP");
            maternidade.setCep("04263-200");
            maternidade.setHorarioFuncionamento("Seg-Sex 08:00-16:00");
            maternidade.setAceitaColetaDomiciliar(false);
            maternidade.setLatitude(-23.5901);
            maternidade.setLongitude(-46.6038);
            blhRepository.save(maternidade);

            QuizResposta resposta = new QuizResposta();
            resposta.setNutriz(ana);
            resposta.setPergunta("Você está amamentando atualmente?");
            resposta.setResposta("Sim, estou amamentando");
            resposta.setElegivel(true);
            quizRepository.save(resposta);

            Agendamento agendamento = new Agendamento();
            agendamento.setNutriz(ana);
            agendamento.setBlh(santaCasa);
            agendamento.setDataHora(LocalDateTime.now().plusDays(5).withHour(10).withMinute(30).withSecond(0).withNano(0));
            agendamento.setTipoColeta(TipoColeta.BLH);
            agendamento.setStatus(AgendamentoStatus.CONFIRMADO);
            agendamento.setObservacoes("Levar documento de identificação.");
            agendamentoRepository.save(agendamento);

            Doacao doacao = new Doacao();
            doacao.setNutriz(ana);
            doacao.setBlh(santaCasa);
            doacao.setVolumeMl(120);
            doacao.setDataDoacao(LocalDate.now().minusDays(2));
            doacao.setBebesBeneficiados(2);
            doacao.setObservacoes("Primeira doação registrada.");
            doacaoRepository.save(doacao);

            Campanha campanha = new Campanha();
            campanha.setNome("Doe leite, salve vidas");
            campanha.setRegiao("Norte - SP");
            campanha.setCanal("WHATSAPP");
            campanha.setMensagem("Seu leite pode ajudar bebês prematuros. Faça o quiz do Lactare Connect.");
            campanha.setStatus(CampanhaStatus.ATIVA);
            campanha.setDisparadaEm(LocalDateTime.now().minusDays(1));
            campanhaRepository.save(campanha);

            ScorePropensao scoreAna = new ScorePropensao();
            scoreAna.setNutriz(ana);
            scoreAna.setScore(92);
            scoreAna.setPrioridade(PrioridadeIa.ALTA);
            scoreAna.setFatoresPrincipais("Região crítica; 5 interações no chatbot; pós-parto ideal");
            scoreRepository.save(scoreAna);

            ScorePropensao scoreCarla = new ScorePropensao();
            scoreCarla.setNutriz(carla);
            scoreCarla.setScore(78);
            scoreCarla.setPrioridade(PrioridadeIa.ALTA);
            scoreCarla.setFatoresPrincipais("Pós-parto ideal; 3 interações no chatbot");
            scoreRepository.save(scoreCarla);
        };
    }
}
