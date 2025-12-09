package academy.devdojo.controller;


import academy.devdojo.domain.Producers;
import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPutRequest;
import academy.devdojo.request.ProducersPostRequest;
import academy.devdojo.response.ProducersGetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<ProducersGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("Request received to list all producers, param name: '{}'", name);// Boa prática sempre termos logs

        var producers = Producers.getProducers();
        var producerGetResponseList = MAPPER.toProducerGetResponseList(producers);

        if (name == null) {
            return ResponseEntity.ok(producerGetResponseList);
        }
        var response = producerGetResponseList.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducersGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find producer by id: {}", id);// Boa prática sempre termos logs

        var producerGetResponse = Producers.getProducers()
                .stream().filter(producer -> producer.getId().equals(id))
                .findFirst().map(MAPPER::toProducersGetResponse).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "producer not found"));

        return ResponseEntity.ok(producerGetResponse);
    }


//    @GetMapping
//    public List<Producers> listAll(@RequestParam(required = false) String name) {
//        var producers = Producers.getProducers();
//        if (name == null) {
//            return producers;
//        }
//        return producers.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
//    }
//
//    @GetMapping("{id}")
//    public Producers findById(@PathVariable Long id) {
//        return Producers.getProducers()
//                .stream().filter(producer -> producer.getId().equals(id))
//                .findFirst().orElse(null);
//    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "x-api-key")
    //Quais tipos de dados são produzidos e consumidos
    //Já o headers torna um determinado header obrigatório. Só mapeia a requisição se determinado header for encontrado.
    public ResponseEntity<ProducersGetResponse> save(@RequestBody ProducersPostRequest producersPostRequest, @RequestHeader HttpHeaders headers) {
        var producer = MAPPER.toProducers(producersPostRequest);
        var response = MAPPER.toProducersGetResponse(producer);


//        producer.setId(ThreadLocalRandom.current().nextLong(100_000));
        Producers.getProducers().add(producer); //Adiciona producers


//        var responseHeaders = new HttpHeaders();
//        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Authorization", "My Key"); //adiciona o header My Key e toda a informação que passamos obtemos de volta
        return ResponseEntity.status(HttpStatus.CREATED).body(response); //Wrapper utilizado para retornar dados extras. Nesse caso retornando o status 201 created
//        return ResponseEntity.noContent().build(); //Quando não queremos retornar nada 204 No Content, por exemplo no UPDATE, na chamada do método, trocamos para void

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete producer by id: {}", id);

        var producerToDelete = Producers.getProducers()
                .stream().filter(producer -> producer.getId().equals(id))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "producer not found"));
        Producers.getProducers().remove(producerToDelete);
        return ResponseEntity.noContent().build();
    }


    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.debug("Request to update producer by id: {}", request);

        var producerToRemove = Producers.getProducers()
                .stream()
                .filter(producer -> producer.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));

        var producerUpdate = MAPPER.toProducers(request, producerToRemove.getCreatedAt());

        Producers.getProducers().remove(producerToRemove);
        Producers.getProducers().add(producerUpdate);

        return ResponseEntity.noContent().build();
    }
}
