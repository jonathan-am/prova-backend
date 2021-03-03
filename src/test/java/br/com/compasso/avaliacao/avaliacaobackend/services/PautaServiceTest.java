package br.com.compasso.avaliacao.avaliacaobackend.services;

import br.com.compasso.avaliacao.avaliacaobackend.model.PautaEntity;
import br.com.compasso.avaliacao.avaliacaobackend.repository.PautaRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PautaServiceTest {

    @Mock
    private PautaRepository repo;
    //##
    private PautaEntity entidade1;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.repo = Mockito.mock(PautaRepository.class);
        //
        this.entidade1 =new PautaEntity();
        //entidade1.setTime(1);
    }

    @Test
    public void testFind() {
        Mockito.when(repo.findById("idnumeroum")).thenReturn(Optional.of(entidade1));
        PautaEntity pautaTest = repo.findById("idnumeroum").get();
        //
        Assert.assertEquals(entidade1, pautaTest);
    }

}
