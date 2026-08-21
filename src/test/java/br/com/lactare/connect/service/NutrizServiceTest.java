package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.NutrizRequest;
import br.com.lactare.connect.dto.NutrizResponse;
import br.com.lactare.connect.entity.Nutriz;
import br.com.lactare.connect.exception.BusinessException;
import br.com.lactare.connect.exception.ResourceNotFoundException;
import br.com.lactare.connect.repository.NutrizRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NutrizServiceTest {

    @Mock
    private NutrizRepository repository;

    @InjectMocks
    private NutrizService service;

    @Test
    void deveRecusarCadastroComTelefoneDuplicado() {
        NutrizRequest request = requestPadrao();
        when(repository.findByTelefone(request.telefone())).thenReturn(Optional.of(new Nutriz()));

        assertThrows(BusinessException.class, () -> service.save(request));
        verify(repository, never()).save(any(Nutriz.class));
    }

    @Test
    void deveSalvarNutrizNormalizandoEstado() {
        NutrizRequest request = requestPadrao();
        when(repository.findByTelefone(request.telefone())).thenReturn(Optional.empty());
        when(repository.save(any(Nutriz.class))).thenAnswer(invocation -> {
            Nutriz entity = invocation.getArgument(0);
            entity.setId(1L);
            return entity;
        });

        NutrizResponse response = service.save(request);

        assertEquals(1L, response.id());
        assertEquals("SP", response.estado());
        verify(repository).save(any(Nutriz.class));
    }

    @Test
    void deveLancarExcecaoQuandoNutrizNaoExistir() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    private NutrizRequest requestPadrao() {
        return new NutrizRequest(
                "Ana Silva",
                "11999998888",
                "ana@email.com",
                "São Paulo",
                "sp",
                6,
                true,
                null
        );
    }
}
