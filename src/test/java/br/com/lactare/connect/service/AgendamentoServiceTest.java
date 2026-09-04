package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.AgendamentoRequest;
import br.com.lactare.connect.entity.Agendamento;
import br.com.lactare.connect.entity.AgendamentoStatus;
import br.com.lactare.connect.entity.Blh;
import br.com.lactare.connect.entity.Nutriz;
import br.com.lactare.connect.entity.TipoColeta;
import br.com.lactare.connect.exception.BusinessException;
import br.com.lactare.connect.repository.AgendamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository repository;

    @Mock
    private NutrizService nutrizService;

    @Mock
    private BlhService blhService;

    @InjectMocks
    private AgendamentoService service;

    @Test
    void deveRecusarDoisAgendamentosAtivosNoMesmoHorarioDoBlh() {
        LocalDateTime dataHora = LocalDateTime.now().plusDays(2);
        Nutriz nutriz = nutriz(1L);
        Blh blh = blh(2L);
        when(nutrizService.getActiveEntity(1L)).thenReturn(nutriz);
        when(blhService.getActiveEntity(2L)).thenReturn(blh);
        when(repository.existsByBlhIdAndDataHoraAndStatusIn(eq(2L), eq(dataHora), any()))
                .thenReturn(true);

        assertThrows(BusinessException.class, () -> service.save(request(dataHora, null)));
        verify(repository, never()).save(any(Agendamento.class));
    }

    @Test
    void deveRecusarAlteracaoDeAgendamentoConcluido() {
        Agendamento agendamento = agendamento(10L, AgendamentoStatus.CONCLUIDO);
        when(repository.findById(10L)).thenReturn(Optional.of(agendamento));

        assertThrows(BusinessException.class,
                () -> service.updateStatus(10L, AgendamentoStatus.CONFIRMADO));
        verify(repository, never()).existsByBlhIdAndDataHoraAndStatusInAndIdNot(any(), any(), any(), any());
    }

    @Test
    void deveRecusarNovoAgendamentoComStatusFinal() {
        LocalDateTime dataHora = LocalDateTime.now().plusDays(2);
        when(nutrizService.getActiveEntity(1L)).thenReturn(nutriz(1L));
        when(blhService.getActiveEntity(2L)).thenReturn(blh(2L));

        assertThrows(BusinessException.class,
                () -> service.save(request(dataHora, AgendamentoStatus.CONCLUIDO)));
        verify(repository, never()).save(any(Agendamento.class));
    }

    private AgendamentoRequest request(LocalDateTime dataHora, AgendamentoStatus status) {
        return new AgendamentoRequest(1L, 2L, dataHora, TipoColeta.BLH, null, status);
    }

    private Agendamento agendamento(Long id, AgendamentoStatus status) {
        Agendamento agendamento = new Agendamento();
        agendamento.setId(id);
        agendamento.setNutriz(nutriz(1L));
        agendamento.setBlh(blh(2L));
        agendamento.setDataHora(LocalDateTime.now().plusDays(2));
        agendamento.setTipoColeta(TipoColeta.BLH);
        agendamento.setStatus(status);
        return agendamento;
    }

    private Nutriz nutriz(Long id) {
        Nutriz nutriz = new Nutriz();
        nutriz.setId(id);
        return nutriz;
    }

    private Blh blh(Long id) {
        Blh blh = new Blh();
        blh.setId(id);
        blh.setAceitaColetaDomiciliar(true);
        return blh;
    }
}
