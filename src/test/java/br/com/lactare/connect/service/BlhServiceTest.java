package br.com.lactare.connect.service;

import br.com.lactare.connect.dto.BlhMatchingResponse;
import br.com.lactare.connect.entity.Blh;
import br.com.lactare.connect.repository.BlhRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BlhServiceTest {

    @Mock
    private BlhRepository repository;

    @InjectMocks
    private BlhService service;

    @Test
    void deveRetornarBlhsAtivosOrdenadosPorDistanciaEAplicarLimite() {
        Blh maisDistante = blh(1L, -23.60, -46.70);
        Blh maisProximo = blh(2L, -23.55, -46.63);
        Blh distanciaIntermediaria = blh(3L, -23.57, -46.66);
        when(repository.findByAtivoTrueOrderByNome())
                .thenReturn(List.of(maisDistante, maisProximo, distanciaIntermediaria));

        List<BlhMatchingResponse> result = service.findNearest(-23.55, -46.63, 2);

        assertEquals(2, result.size());
        assertEquals(2L, result.get(0).blh().id());
        assertEquals(3L, result.get(1).blh().id());
        assertEquals(0.0, result.get(0).distanciaKm());
    }

    @Test
    void deveIgnorarBlhSemCoordenadas() {
        Blh semCoordenadas = blh(1L, null, null);
        when(repository.findByAtivoTrueOrderByNome()).thenReturn(List.of(semCoordenadas));

        List<BlhMatchingResponse> result = service.findNearest(-23.55, -46.63, 3);

        assertEquals(0, result.size());
    }

    @Test
    void deveInativarBlhSemExcluirRegistro() {
        Blh blh = blh(1L, -23.55, -46.63);
        blh.setAtivo(true);
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(blh));

        service.delete(1L);

        assertEquals(false, blh.getAtivo());
        verify(repository, never()).delete(any(Blh.class));
    }

    private Blh blh(Long id, Double latitude, Double longitude) {
        Blh blh = new Blh();
        blh.setId(id);
        blh.setNome("BLH " + id);
        blh.setLatitude(latitude);
        blh.setLongitude(longitude);
        return blh;
    }
}
