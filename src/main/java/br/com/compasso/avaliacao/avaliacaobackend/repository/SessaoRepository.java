package br.com.compasso.avaliacao.avaliacaobackend.repository;

import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessaoRepository extends MongoRepository<SessaoEntity, String> {
}
