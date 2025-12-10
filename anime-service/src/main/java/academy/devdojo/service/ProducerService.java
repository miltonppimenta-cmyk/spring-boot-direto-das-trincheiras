package academy.devdojo.service;

import academy.devdojo.domain.Producers;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class ProducerService {
    private ProducerHardCodedRepository repository; // está acoplado com repository pois caso contrário não funciona

    public ProducerService() { //construtor
        this.repository = new ProducerHardCodedRepository();
    }

    public List<Producers> findAll(String name) {
        return name == null ? repository.findAll() : repository.findByName(name);
    }

    public Producers findByIdOrThrowNotFoung(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producer not found"));
    }

    public Producers save(Producers producer) {
        return repository.save(producer);
    }

    public void delete(Long id) {
        var producer = findByIdOrThrowNotFoung(id);
        repository.delete(producer);
    }

    public void update(Producers producerToUpdate) {
        var producer = findByIdOrThrowNotFoung(producerToUpdate.getId());
        producerToUpdate.setCreatedAt(producer.getCreatedAt());
        repository.update(producerToUpdate);
    }

}
