package br.com.compasso.avaliacao.avaliacaobackend.services;

import br.com.compasso.avaliacao.avaliacaobackend.dto.AssociadoEntradaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.dto.AssociadoSaidaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.exception.AssociadoExisteException;
import br.com.compasso.avaliacao.avaliacaobackend.exception.NaoExisteException;
import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.repository.AssociadoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe Service da entidade Associado
 */
@Service
public class AssociadoService {

    private AssociadoRepository repository;

    public AssociadoService(AssociadoRepository repository) {
        this.repository = repository;
    }

    /**
     * Metodo que cria um novo Associado.
     *
     * @param entity Recebe uma entidade DTO com os dados para gerar e insertar um novo Associado
     * @return AssociadoSaidaDTO caso tenho conseguido salvar o associado, throw AssociadoExisteException caso o associado ja exista
     */
    @CacheEvict(value = "associados", allEntries = true)
    public AssociadoSaidaDTO createNewAssociado(AssociadoEntity entity) {
        if (getByCpf(entity.getCpf()) == null) {//Verifica se ja exise um associado com o CPF
            AssociadoEntity atual = repository.save(entity);
            return new AssociadoSaidaDTO(atual.getId(), atual.getNome(), atual.getSessoes());
        }
        throw new AssociadoExisteException("O CPF em questão ja está vinculado a outro associado!");
    }

    /**
     * Metodo que apaga o associado.
     *
     * @param id Chave de delete do associado no repositorio
     */
    @CacheEvict(value = "associados", allEntries = true)
    public void deleteAssociado(String id) {
        repository.deleteById(id);
    }

    /**
     * Busca um associado pelo ID
     *
     * @param id parametro que sera usado para buscar a Entidade, pela chave de busca ID
     * @return AssociadoSaidaDTO um DTO com os dados do associado buscado pelo ID, ou gera uma Exception de associado não existente
     */
    @Cacheable(value = "associados", key = "#id")
    public AssociadoSaidaDTO getAssociadoPorId(String id) {
        Optional<AssociadoEntity> associado = repository.findById(id);
        AssociadoEntity atual = associado.orElseThrow(() -> new NaoExisteException("Não foi possivel encontrar o associado."));
        return new AssociadoSaidaDTO(atual.getId(), atual.getNome(), atual.getSessoes());
    }

    /**
     * Busca e retorna todos os associados no banco.
     *
     * @return uma lista com todos os associados que foram achados no banco
     */
    @Cacheable(value = "associados")
    public List<AssociadoSaidaDTO> getAssociados() {
        List<AssociadoSaidaDTO> atuais = new ArrayList<>();
        repository.findAll().forEach((ass) -> {
            atuais.add(new AssociadoSaidaDTO(ass.getId(), ass.getNome(), ass.getSessoes()));
        });
        return atuais;
    }

    /**
     * Metodo que faz as alteraçoes do Associado no banco de dados
     *
     * @param id        Chave de busca do associado para ser editado
     * @param associado Objeto associados com os dados novos que vao substituir os antigos
     * @return DTO da entidade com os dados alterados
     */
    @CacheEvict(value = "associados", allEntries = true)
    public AssociadoSaidaDTO editAssociado(String id, AssociadoEntity associado) {
        AssociadoEntity associadoOld = repository.findById(id).get();
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

    /**
     * Metodo que faz as alteraçoes do Associado no banco de dados
     * pelo AssociadoEntradaDTO
     *
     * @param id        Chave de busca do associado para ser editado
     * @param associado Objeto associados com os dados novos que vao substituir os antigos
     * @return DTO da entidade com os dados alterados
     */
    @CacheEvict(value = "associados", allEntries = true)
    public AssociadoSaidaDTO editAssociado(String id, AssociadoEntradaDTO associado) {
        AssociadoEntity associadoOld = repository.findById(id).get();
        if (associado.getCpf() != null) {
            associadoOld.setCpf(associado.getCpf());
        }
        if (associado.getNome() != null) {
            associadoOld.setNome(associado.getNome());
        }
        AssociadoEntity ent = repository.save(associadoOld);
        return new AssociadoSaidaDTO(ent.getId(), ent.getNome(), ent.getSessoes());
    }

    /**
     * Busca um associado pelo CPF como chave de busca
     *
     * @param cpf Chave de busca para identificar o associado no banco
     * @return o associado encontrado pelo CPF
     */
    @Cacheable(value = "associados", key = "#cpf")
    public AssociadoEntity getByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    /**
     * Metodo que busca o Associado no banco pelo ID
     *
     * @param id Chave de busca
     * @return o associado encontrado pelo ID
     */
    @Cacheable(value = "associados", key = "#id")
    public AssociadoEntity getById(String id) {
        return repository.findById(id).get();
    }


}
