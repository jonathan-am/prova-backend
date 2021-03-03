package br.com.compasso.avaliacao.avaliacaobackend.repository;

import br.com.compasso.avaliacao.avaliacaobackend.model.PautaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends MongoRepository<PautaEntity, String> {
}
