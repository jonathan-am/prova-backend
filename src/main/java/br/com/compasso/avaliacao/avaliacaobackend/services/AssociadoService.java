package br.com.compasso.avaliacao.avaliacaobackend.services;

import br.com.compasso.avaliacao.avaliacaobackend.dto.AssociadoEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.dto.AssociadoSaidaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AssociadoService {

    private AssociadoRepository repository;

    public AssociadoService(AssociadoRepository repository) {
        this.repository = repository;
    }

    public AssociadoSaidaDTO createNewAssociado(AssociadoEntradaDTO entity) {
        AssociadoEntity novo = new AssociadoEntity();
        novo.setNome(entity.getNome());
        novo.setCpf(entity.getCpf());
        //
        AssociadoEntity atual = repository.save(novo);
        //
        return new AssociadoSaidaDTO(atual.getId(), atual.getNome(), atual.getSessoes());
    }

    public void deleteAssociado(String id) {
        repository.deleteById(id);
    }

    public AssociadoSaidaDTO getAssociadoPorId(String id) {
        AssociadoEntity atual = repository.findById(id).get();
        return new AssociadoSaidaDTO(atual.getId(), atual.getNome(), atual.getSessoes());
    }

    public List<AssociadoSaidaDTO> getAssociados() {
        List<AssociadoSaidaDTO> atuais = new ArrayList<>();
        repository.findAll().forEach((ass) -> {
            atuais.add(new AssociadoSaidaDTO(ass.getId(), ass.getNome(), ass.getSessoes()));
        });
        return atuais;
    }

    public AssociadoSaidaDTO editAssociado(String id, AssociadoEntity associado) {
        AssociadoEntity associadoOld =  repository.findById(id).get();
        if (associado.getSessoes() != null) {
            associadoOld.setSessoes(associado.getSessoes());
        }
        if (associado.getCpf() != null) {
            associadoOld.setCpf(associado.getCpf());
        }
        if (associado.getNome() != null) {
            associadoOld.setNome(associado.getNome());
        }
        AssociadoEntity ent = repository.save(associadoOld);
        return new AssociadoSaidaDTO(ent.getId(), ent.getNome(), ent.getSessoes());
    }

    public AssociadoEntity buscaPorCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public AssociadoEntity buscaPorId(String id) {
        return repository.findById(id).get();
    }

    public void computaVotoParaOAssociado(AssociadoEntity ent, String voto, SessaoEntity sessao) {
        HashMap<String, String> votos = ent.getSessoes();
        votos.put(sessao.getId(), voto);
        ent.setSessoes(votos);
        editAssociado(ent.getId(), ent);
    }

}
