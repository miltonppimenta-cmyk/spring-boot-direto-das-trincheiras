package academy.devdojo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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


}

//    public static List<Anime> getAnimes() {
//
/// /
/// /        return List.of(ninjaKamui, kaijuu, kimetsuNoYaiba); //Para o exercício 3, não é possível usar o Lis.of pois ele retorna uma lista imutável, e com isso não podemos adicionar elementos nesta lista
//
//    }




