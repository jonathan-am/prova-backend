package br.com.compasso.avaliacao.avaliacaobackend.business;

import br.com.compasso.avaliacao.avaliacaobackend.dto.CpfStatusDTO;
import br.com.compasso.avaliacao.avaliacaobackend.dto.VotoDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.repository.AssociadoRepository;
import br.com.compasso.avaliacao.avaliacaobackend.services.AssociadoService;
import br.com.compasso.avaliacao.avaliacaobackend.services.PautaService;
import br.com.compasso.avaliacao.avaliacaobackend.services.SessaoService;
import br.com.compasso.avaliacao.avaliacaobackend.services.ValidaCpfService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class AssociadoBusinessTest {

    @Mock
    private ValidaCpfService validaService;
    @Mock
    private AssociadoService service;
    private AssociadoBusiness business;
    //
    private CpfStatusDTO status;
    private AssociadoEntity entity;
    private SessaoEntity sessao;
    private VotoDTO voto;

    @Before
    public void setup() {
        this.validaService = Mockito.mock(ValidaCpfService.class);
        this.service = Mockito.mock(AssociadoService.class);

        business = new AssociadoBusiness(validaService, service);

        //

        status = new CpfStatusDTO();
        status.setStatus("ABLE_TO_VOTE");
        entity = new AssociadoEntity();
        entity.setCpf("00000000000");
        sessao = new SessaoEntity();
        voto = new VotoDTO();
        voto.setVoto("Sim");
        //
    }

    @Test
    public void testHasVotedTrue() {
        AssociadoEntity entity = new AssociadoEntity();
        SessaoEntity sessao = new SessaoEntity();
        entity.getSessoes().put(sessao.getId(), "Passou");
        Assert.assertTrue(business.hasVoted(sessao, entity));
    }

    @Test
    public void testHasVotedFalse() {
        AssociadoEntity entity = new AssociadoEntity();
        SessaoEntity sessao = new SessaoEntity();
        Assert.assertFalse(business.hasVoted(sessao, entity));
    }

    @Test
    public void testCheckEComputaVoto() {
        Mockito.when(validaService.check("00000000000")).thenReturn(status);
        Assert.assertTrue(business.checkEComputaVoto(entity.getId(), voto, entity).equals(ResponseEntity.ok(status)));
    }

    @Test
    public void testCheckEComputaVotoEntNull() {
        Mockito.when(validaService.check("00000000000")).thenReturn(status);
        status.setStatus("UNABLE_TO_VOTE");
        Assert.assertTrue(business.checkEComputaVoto(null, voto, entity).equals(ResponseEntity.ok(status)));
    }

    @Test
    public void testCheckEComputaVotoNotFound() {
        Mockito.when(validaService.check("00000000000")).thenReturn(null);
        Assert.assertTrue(business.checkEComputaVoto(entity.getId(), voto, entity).equals(ResponseEntity.notFound().build()));
    }

    @Test
    public void testComputaVotoParaOAssociado() {
        business.computaVotoParaOAssociado(entity, "Sim", sessao);
        Mockito.verify(service).editAssociado(entity.getId(), entity);
    }
}
