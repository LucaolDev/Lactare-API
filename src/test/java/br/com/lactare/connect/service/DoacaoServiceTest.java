package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.DoacaoRequest;
import br.com.lactare.connect.entity.Agendamento;
import br.com.lactare.connect.entity.AgendamentoStatus;
import br.com.lactare.connect.entity.Blh;
import br.com.lactare.connect.entity.Doacao;
import br.com.lactare.connect.entity.Nutriz;
import br.com.lactare.connect.repository.AgendamentoRepository;
import br.com.lactare.connect.repository.DoacaoRepository;
import br.com.lactare.connect.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoacaoServiceTest {

    @Mock
    private DoacaoRepository repository;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private NutrizService nutrizService;

    @Mock
    private BlhService blhService;

    @InjectMocks
    private DoacaoService service;

    @Test
    void deveRecusarDoacaoVinculadaAAgendamentoCancelado() {
        Nutriz nutriz = nutriz(1L);
        Blh blh = blh(2L);
        Agendamento agendamento = new Agendamento();
        agendamento.setId(3L);
        agendamento.setNutriz(nutriz);
        agendamento.setBlh(blh);
        agendamento.setStatus(AgendamentoStatus.CANCELADO);
        when(nutrizService.getActiveEntity(1L)).thenReturn(nutriz);
        when(blhService.getActiveEntity(2L)).thenReturn(blh);
        when(agendamentoRepository.findById(3L)).thenReturn(Optional.of(agendamento));

        DoacaoRequest request = new DoacaoRequest(
                1L, 2L, 3L, 120, LocalDate.now(), null, null);

        assertThrows(BusinessException.class, () -> service.save(request));
        verify(repository, never()).save(any(Doacao.class));
    }

    private Nutriz nutriz(Long id) {
        Nutriz nutriz = new Nutriz();
        nutriz.setId(id);
        return nutriz;
    }

    private Blh blh(Long id) {
        Blh blh = new Blh();
        blh.setId(id);
        return blh;
    }
}
