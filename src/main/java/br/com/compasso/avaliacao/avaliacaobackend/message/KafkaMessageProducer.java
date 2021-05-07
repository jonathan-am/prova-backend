package br.com.compasso.avaliacao.avaliacaobackend.message;

import br.com.compasso.avaliacao.avaliacaobackend.dto.SessaoSaidaKafkaDTO;
import br.com.compasso.avaliacao.avaliacaobackend.model.SessaoEntity;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaMessageProducer {

    public void sendMessage(SessaoEntity entity, String resultado) {
        SessaoSaidaKafkaDTO saida = new SessaoSaidaKafkaDTO(entity.getId(), resultado);
        Properties prop = new Properties();
        prop.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        prop.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        prop.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        Producer<String, String> producer = new KafkaProducer<>(prop);

        producer.send(new ProducerRecord<>("test", entity.getId(), saida.toString()));
        producer.close();
    }

}
