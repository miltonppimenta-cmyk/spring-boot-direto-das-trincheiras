package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producers {
    @EqualsAndHashCode.Include
    private Long id; //quando trabalhamos com ID o long possui o equals, então trabalhamos com Wrapper do long primitivo
    @JsonProperty("name")
    private String name;
    private LocalDateTime createdAt;
    private String address; //esta alteração não tem nenhum tipo de impacto no contrato com os clientes devido ao desacoplamento

    private static List<Producers> producers = new ArrayList<>();

    static {
        var mappa = Producers.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var kyotoAnimation = Producers.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        var madhouse = Producers.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();
        producers.addAll(List.of(mappa, kyotoAnimation, madhouse));
    }

    public static List<Producers> getProducers() {
        return producers;
    }
}

//    public static List<Anime> getAnimes() {
//
/// /
/// /        return List.of(ninjaKamui, kaijuu, kimetsuNoYaiba); //Para o exercício 3, não é possível usar o Lis.of pois ele retorna uma lista imutável, e com isso não podemos adicionar elementos nesta lista
//
//    }




