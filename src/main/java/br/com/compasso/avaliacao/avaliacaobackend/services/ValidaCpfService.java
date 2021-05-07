package br.com.compasso.avaliacao.avaliacaobackend.services;

import br.com.compasso.avaliacao.avaliacaobackend.dto.CpfStatusDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidaCpfService {

    private final RestTemplate restTemplate;

    public ValidaCpfService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Cacheable(value = "cpfstatus", key = "#cpf")
    public CpfStatusDTO check(String cpf) {
        String cpfParsed = cpf.replace(".", "").replace("-", "");
        String url = "https://user-info.herokuapp.com/users/" + cpfParsed;
        ResponseEntity<CpfStatusDTO> response = this.restTemplate.getForEntity(url, CpfStatusDTO.class);
        return response.getStatusCodeValue() == 200 ? response.getBody() : null;
    }

}
