package br.com.compasso.avaliacao.avaliacaobackend.repository;

import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends MongoRepository<AssociadoEntity, String> {

    /**
     * Busca no banco de dados, um associado, pelo CPF
     *
     * @param cpf atributo primario que vai ser usado como chave de busca
     * @return o AssociadoEntity que pegar pelo CPF
     */
    AssociadoEntity findByCpf(String cpf);

}
