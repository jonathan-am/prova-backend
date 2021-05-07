package br.com.compasso.avaliacao.avaliacaobackend.business;

import br.com.compasso.avaliacao.avaliacaobackend.dto.CpfStatusDTO;
import br.com.compasso.avaliacao.avaliacaobackend.dto.VotoDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.AssociadoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.services.AssociadoService;
import br.com.compasso.avaliacao.avaliacaobackend.services.ValidaCpfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Classe Business da Entidade Associado
 * armazena algumas funçoes.
 */
@Component
public class AssociadoBusiness {

    private ValidaCpfService validaService;
    private AssociadoService service;
    @Autowired
    private SessaoBusiness sessaoBusiness;

    public AssociadoBusiness(ValidaCpfService validaService, AssociadoService service) {
        this.validaService = validaService;
        this.service = service;
    }

    /**
     * Metodo que verifica, se o Associado, ja votou na sessão.
     *
     * @param sessao Entidade Sessão, em questão que vai ser checada se o Associado ja votou
     * @param entity Associado, ao qual sera verificado, se votou na sessao.
     * @return true, se ele votou, false se ele nao votou ainda.
     */
    public boolean hasVoted(SessaoEntity sessao, AssociadoEntity entity) {
        return entity.getSessoes().containsKey(sessao.getId());
    }


    /**
     * Metodo, que checa se o usuario pode votar na sessão pelo CPF, e
     * computa o voto na sessão.
     *
     * @param id        ID da sessão em questão, que sera computado o voto, caso esteja aberta.
     * @param voto      DTO do voto contendo, o atributo, {STRING} voto
     * @param associado Entidade, a qual votou e pela qual vai ser computado o voto
     * @return 200(" ABLE_TO_VOTE "), 200("UNABLE_TO_VOTE"), 404,
     */
    public ResponseEntity<?> checkEComputaVoto(String id, VotoDTO voto, AssociadoEntity associado) {
        if (validaService.check(associado.getCpf()) != null) {
            CpfStatusDTO statusDTO = validaService.check(associado.getCpf());
            if (statusDTO.getStatus().equals("ABLE_TO_VOTE")) {
                SessaoEntity ent = sessaoBusiness.computaVoto(id, voto, associado);
                if (ent == null) {
                    statusDTO.setStatus("UNABLE_TO_VOTE");
                }
            }
            return ResponseEntity.ok(statusDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Metodo que computa o voto para o associado, e faz alteração no banco
     *
     * @param ent    Associado em questao
     * @param voto   voto do associado
     * @param sessao sessao em questao a qual o voto sera computado para o associado
     */
    public void computaVotoParaOAssociado(AssociadoEntity ent, String voto, SessaoEntity sessao) {
        HashMap<String, String> votos = ent.getSessoes();
        votos.put(sessao.getId(), voto);
        ent.setSessoes(votos);
        service.editAssociado(ent.getId(), ent);
    }

}
