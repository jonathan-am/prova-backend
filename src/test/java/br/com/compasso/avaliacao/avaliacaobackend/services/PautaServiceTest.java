package br.com.compasso.avaliacao.avaliacaobackend.services;

import br.com.compasso.avaliacao.avaliacaobackend.dto.PautaEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.PautaEntity;
import br.com.compasso.avaliacao.avaliacaobackend.repository.PautaRepository;
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
public class PautaServiceTest {

    private PautaService service;
    @Mock
    private PautaRepository repo;
    //##
    private PautaEntity entidade1;
    private PautaEntity entidade2;
    private PautaEntradaDTO dto;

    @Before
    public void setup() {
        this.repo = Mockito.mock(PautaRepository.class);
        this.service = new PautaService(repo);
        this.entidade1 = new PautaEntity();
        this.entidade1.setTitulo("Pauta de teste");
        this.entidade1.setDescricao("Descrição da pauta de teste");
        this.entidade1.setMax_sessoes(2);
        //
        dto = new PautaEntradaDTO();
        dto.setTitulo(entidade1.getTitulo());
        dto.setDescricao(entidade1.getDescricao());
        dto.setMax_sessoes(entidade1.getMax_sessoes());
        //
        entidade2 = dto.dtoToEntity();
    }

    @Test
    public void testCreate() {
        Mockito.when(repo.save(entidade2)).thenReturn(entidade1);
        PautaEntity ent = service.createNewPauta(entidade2);

        Assert.assertEquals(ent, entidade1);
    }

    @Test
    public void testDelete() {
        service.deletePauta(entidade2.getId());

        Mockito.verify(repo).deleteById(entidade2.getId());
    }

    @Test
    public void testGetById() {
        Mockito.when(repo.findById(entidade2.getId())).thenReturn(Optional.ofNullable(entidade2));

        PautaEntity ent = service.getPautaPorId(entidade2.getId());

        Assert.assertEquals(ent, entidade2);
    }

    @Test
    public void testFindAll() {
        Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());

        Assert.assertTrue(service.getPautas().isEmpty());
    }

    @Test
    public void testUpdatePauta() {
        Mockito.when(repo.findById(entidade2.getId())).thenReturn(Optional.ofNullable(entidade2));
        Mockito.when(repo.save(entidade2)).thenReturn(entidade1);

        PautaEntity update = service.editPauta(entidade2.getId(), entidade2);

        Assert.assertNotEquals(entidade2, update);
    }

    @Test
    public void testExistsById() {
        Mockito.when(repo.existsById(entidade2.getId())).thenReturn(true);

        Assert.assertTrue(service.existById(entidade2.getId()));
    }

    @Test
    public void testFalseExistsById() {
        Mockito.when(repo.existsById(entidade2.getId())).thenReturn(false);

        Assert.assertFalse(service.existById(entidade2.getId()));
    }

}
