package br.com.compasso.avaliacao.avaliacaobackend.services;

import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.repository.SessaoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class SessaoServiceTest {

    @Mock
    private SessaoRepository repo;
    private SessaoService service;
    //
    private SessaoEntity sessao1;

    @Before
    public void setup() {
        repo = Mockito.mock(SessaoRepository.class);
        service = new SessaoService(repo);
        //
        sessao1 = new SessaoEntity();
        sessao1.setAberta(true);
        sessao1.setTempo(1);
    }

    @Test
    public void testDelete() {
        service.deleteSessao(sessao1.getId());
        Mockito.verify(repo).deleteById(sessao1.getId());
    }

    @Test
    public void testGetById() {
        Mockito.when(repo.findById(sessao1.getId())).thenReturn(Optional.ofNullable(sessao1));
        SessaoEntity ent = service.getSessaoPorId(sessao1.getId());
        Assert.assertEquals(ent, sessao1);
    }

    @Test
    public void testEditSessao() {
        SessaoEntity sessao2 = new SessaoEntity();
        Mockito.when(repo.findById(sessao2.getId())).thenReturn(Optional.ofNullable(sessao1));
        Mockito.when(repo.save(sessao1)).thenReturn(sessao1);
        //
        SessaoEntity saida = service.editSessao(sessao2.getId(), sessao1);
        Assert.assertEquals(saida, sessao1);
    }

    @Test
    public void testGetAll() {
        Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());
        Assert.assertTrue(service.getAll().isEmpty());
    }

    @Test
    public void testExistsById() {
        Mockito.when(repo.existsById(sessao1.getId())).thenReturn(true);
        Assert.assertTrue(service.existsById(sessao1.getId()));
    }

    @Test
    public void testSave() {
        service.save(sessao1);
        Mockito.verify(repo).save(sessao1);
    }

}
