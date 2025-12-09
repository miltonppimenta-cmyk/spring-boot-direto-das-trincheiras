package academy.devdojo.repository;

import academy.devdojo.domain.Producers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerHardCodedRepository {
    private static final List<Producers> PRODUCERS = new ArrayList<>();

    static { //Criamos aqui os producers
        var mappa = Producers.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var kyotoAnimation = Producers.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        var madhouse = Producers.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        PRODUCERS.addAll(List.of(mappa, kyotoAnimation, madhouse));
    }

    public static List<Producers> findAll() {
        return PRODUCERS;
    }

    public Optional<Producers> findById(Long id) {
        return PRODUCERS.stream().filter(producer -> producer.getId().equals(id))
                .findFirst();
    }

    public List<Producers> findByName(String name) {
        return PRODUCERS.stream().filter(producer -> producer.getName().equals(name)).toList();
    }
    public Producers save(Producers producer) {
        PRODUCERS.add(producer);
        return producer;
    }
    public void delete(Producers producer) {
        PRODUCERS.remove(producer);
    }

    public void update(Producers producer) {
        delete(producer);
        save(producer);
    }

}
