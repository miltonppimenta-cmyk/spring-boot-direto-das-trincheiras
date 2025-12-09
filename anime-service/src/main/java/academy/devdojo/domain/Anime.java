package academy.devdojo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Anime {
    private Long id; //quando trabalhamos com ID o long possui o equals, então trabalhamos com Wrapper do long primitivo
    private String name;
    private static List<Anime> animes = new ArrayList<>();

    static {
        var ninjaKamui = new Anime(1L, "Ninja Kamui");
        var kaijuu = new Anime(2L, "Kaijuu-8gou");
        var kimetsuNoYaiba = new Anime(3L, "Kimetsu No Yaiba");
        animes.addAll(List.of(ninjaKamui, kaijuu, kimetsuNoYaiba));
    }

    public static List<Anime> getAnimes() {
        return animes;
    }
}

//    public static List<Anime> getAnimes() {
//
/// /
/// /        return List.of(ninjaKamui, kaijuu, kimetsuNoYaiba); //Para o exercício 3, não é possível usar o Lis.of pois ele retorna uma lista imutável, e com isso não podemos adicionar elementos nesta lista
//
//    }




