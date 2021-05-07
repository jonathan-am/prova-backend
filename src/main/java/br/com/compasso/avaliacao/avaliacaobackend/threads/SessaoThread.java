package br.com.compasso.avaliacao.avaliacaobackend.threads;

import br.com.compasso.avaliacao.avaliacaobackend.business.AssociadoBusiness;
import br.com.compasso.avaliacao.avaliacaobackend.business.SessaoBusiness;
import br.com.compasso.avaliacao.avaliacaobackend.dto.SessaoSaidaKafkaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.message.KafkaMessageProducer;
import br.com.compasso.avaliacao.avaliacaobackend.model.PautaEntity;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import br.com.compasso.avaliacao.avaliacaobackend.services.PautaService;
import br.com.compasso.avaliacao.avaliacaobackend.services.SessaoService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Properties;

public class SessaoThread extends Thread {

    private SessaoEntity entity;
    private int tempo;
    private SessaoService service;
    private PautaService pautaService;
    private SessaoBusiness business;
    private AssociadoBusiness associadoBusiness;
    private KafkaMessageProducer kafkaMessageProducer;

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoThread.class);

    public SessaoThread(SessaoEntity entity, int tempo, SessaoService service, PautaService pautaService, AssociadoBusiness associadoBusiness) {
        super();
        this.entity = entity;
        this.tempo = tempo;
        this.associadoBusiness = associadoBusiness;
        this.service = service;
        this.pautaService = pautaService;
        this.business = new SessaoBusiness(service, pautaService);
        this.kafkaMessageProducer = new KafkaMessageProducer();
        this.start();
    }

    public void run() {
        try {
            sleep(1000L * 60 * tempo);
            entity = service.getSessaoPorId(entity.getId());
            String resultado = business.resultadoDaVotacao(entity);

            kafkaMessageProducer.sendMessage(entity, resultado);
            fechaSessao(entity, resultado);
        } catch (InterruptedException e) {
            fechaSessao(entity, null);
        }
    }

    private void fechaSessao(SessaoEntity entity, String resultado) {
        PautaEntity ent = entity.getPautaEmQuestao();
        HashMap<String, String> sessoes = ent.getSessoes();
        sessoes.remove(entity.getId());
        sessoes.put(entity.getId(), resultado);

        pautaService.editPauta(ent.getId(), ent);
        entity.setAberta(false);
        service.editSessao(entity.getId(), entity);
        //
        LOGGER.info("Sessão finalizada, com {} votos, ID: " + entity.getId(), entity.getVotos().size());
    }

}
