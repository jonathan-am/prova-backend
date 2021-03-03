package br.com.compasso.avaliacao.avaliacaobackend.repository;

import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociadoRepository extends MongoRepository<AssociadoEntity, String> {

    AssociadoEntity findByCpf(String cpf);

}
