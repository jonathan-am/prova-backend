package br.com.compasso.avaliacao.avaliacaobackend.services;

import br.com.compasso.avaliacao.avaliacaobackend.dto.AssociadoEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.dto.AssociadoSaidaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.repository.AssociadoRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class AssociadoServiceTest {

    @Mock
    private AssociadoRepository repo;
    private AssociadoService service;
    //
    private AssociadoEntity entidade1;
    private AssociadoEntradaDTO dto;

    @Before
    public void setup() {
        this.repo = Mockito.mock(AssociadoRepository.class);
        this.service = new AssociadoService(repo);
        //
        entidade1 = new AssociadoEntity();
        entidade1.setCpf("033.256.981-82");
        entidade1.setNome("Pessoa de Teste");
        //
        dto = new AssociadoEntradaDTO();
        dto.setCpf(entidade1.getCpf());
        dto.setNome(entidade1.getNome());
    }

    @Test
    public void testCreate() {
        Mockito.when(repo.save(entidade1)).thenReturn(entidade1);

        AssociadoSaidaDTO ent = service.createNewAssociado(entidade1);

        Assert.assertEquals(ent.getId(), entidade1.getId());
        Assert.assertEquals(ent.getNome(), entidade1.getNome());
    }

    @Test
    public void testDelete() {
        service.deleteAssociado(entidade1.getId());
        Mockito.verify(repo).deleteById(entidade1.getId());
    }

    @Test
    public void testGetAssociadoById() {
        Mockito.when(repo.findById(entidade1.getId())).thenReturn(Optional.ofNullable(entidade1));
        AssociadoEntity ent = service.getById(entidade1.getId());
        Assert.assertEquals(entidade1, ent);
    }

    @Test
    public void testGetAll() {
        Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());
        List<AssociadoSaidaDTO> associados = service.getAssociados();
        Assert.assertTrue(associados.isEmpty());
    }

    @Test
    public void testEditAssociado() {
        AssociadoEntity entidade2 = new AssociadoEntity();
        Mockito.when(repo.findById(entidade2.getId())).thenReturn(Optional.ofNullable(entidade1));
        Mockito.when(repo.save(entidade1)).thenReturn(entidade1);

        AssociadoSaidaDTO saida = service.editAssociado(entidade2.getId(), entidade2);

        Assert.assertEquals(saida.getId(), entidade1.getId());
    }

    @Test
    public void testEditAssociadoDTO() {
        AssociadoEntradaDTO entidade2 = new AssociadoEntradaDTO();
        Mockito.when(repo.findById(entidade1.getId())).thenReturn(Optional.ofNullable(entidade1));
        Mockito.when(repo.save(entidade1)).thenReturn(entidade1);

        AssociadoSaidaDTO saida = service.editAssociado(entidade1.getId(), entidade2);

        Assert.assertEquals(saida.getId(), entidade1.getId());
    }

    @Test
    public void testGetById() {
        Mockito.when(repo.findById(entidade1.getId())).thenReturn(Optional.ofNullable(entidade1));
        AssociadoEntity saida = service.getById(entidade1.getId());
        Assert.assertEquals(saida, entidade1);
    }

    @Test
    public void testGetByCpf() {
        Mockito.when(repo.findByCpf(entidade1.getCpf())).thenReturn(entidade1);
        AssociadoEntity saida = service.getByCpf(entidade1.getCpf());
        Assert.assertEquals(saida, entidade1);
    }

}
