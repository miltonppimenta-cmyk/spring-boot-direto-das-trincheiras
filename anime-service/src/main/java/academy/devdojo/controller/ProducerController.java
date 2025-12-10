package academy.devdojo.controller;


import academy.devdojo.mapper.ProducerMapper;
import academy.devdojo.request.ProducerPutRequest;
import academy.devdojo.request.ProducersPostRequest;
import academy.devdojo.response.ProducersGetResponse;
import academy.devdojo.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/producers")
@Slf4j
public class ProducerController {
    private static final ProducerMapper MAPPER = ProducerMapper.INSTANCE;
    private ProducerService service;

    public ProducerController() {
        this.service = new ProducerService();
    }


    @GetMapping
    public ResponseEntity<List<ProducersGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("Request received to list all producers, param name: '{}'", name);// Boa prática sempre termos logs
        var producers = service.findAll(name);

        var producersGetResponses = MAPPER.toProducerGetResponseList(producers);

        return ResponseEntity.ok(producersGetResponses);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProducersGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find producer by id: {}", id);// Boa prática sempre termos logs

        var producer = service.findByIdOrThrowNotFoung(id);

        var producerGetResponse = MAPPER.toProducersGetResponse(producer);

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
        var producerSaved = service.save(producer);
        var producersGetResponse = MAPPER.toProducersGetResponse(producerSaved);


//        producer.setId(ThreadLocalRandom.current().nextLong(100_000));


//        var responseHeaders = new HttpHeaders();
//        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Authorization", "My Key"); //adiciona o header My Key e toda a informação que passamos obtemos de volta
        return ResponseEntity.status(HttpStatus.CREATED).body(producersGetResponse); //Wrapper utilizado para retornar dados extras. Nesse caso retornando o status 201 created
//        return ResponseEntity.noContent().build(); //Quando não queremos retornar nada 204 No Content, por exemplo no UPDATE, na chamada do método, trocamos para void

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete producer by id: {}", id);

        service.delete(id);

        return ResponseEntity.noContent().build();
    }


    @PutMapping
    public ResponseEntity<Void> update(@RequestBody ProducerPutRequest request) {
        log.debug("Request to update producer by id: {}", request);

        var producerToUpdate = MAPPER.toProducers(request);

        service.update(producerToUpdate);
        return ResponseEntity.noContent().build();
    }
}
