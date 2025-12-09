package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.domain.Anime;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.request.AnimePostRequest;
import academy.devdojo.request.AnimePutRequest;
import academy.devdojo.response.AnimeGetResponse;
import academy.devdojo.response.AnimePostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("v1/animes")
@Slf4j
public class AnimeController {
    private static final AnimeMapper MAPPER = AnimeMapper.INSTANCE; //Criamos atributo de classe que pode ser utilizado em todos os nossos métodos

    @GetMapping
    public ResponseEntity<List<AnimeGetResponse>> listAll(@RequestParam(required = false) String name) {
        log.debug("Request received to list all animes, param name: '{}'", name);// Boa prática sempre termos logs

        var animes = Anime.getAnimes();
        var animeGetResponseList = MAPPER.toAnimeGetResponseList(animes);

        if (name == null) {
            return ResponseEntity.ok(animeGetResponseList);
        }
        var response = animeGetResponseList.stream().filter(anime -> anime.getName().equalsIgnoreCase(name)).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimeGetResponse> findById(@PathVariable Long id) {
        log.debug("Request to find anime by id: {}", id);// Boa prática sempre termos logs

        var animeGetResponse = Anime.getAnimes()
                .stream().filter(anime -> anime.getId().equals(id))
                .findFirst().map(MAPPER::toAnimeGetResponse).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "anime not found"));

        return ResponseEntity.ok(animeGetResponse);
    }

    @PostMapping
    public ResponseEntity<AnimePostResponse> save(@RequestBody AnimePostRequest request) {
        log.debug("Request to save anime: {}");// Boa prática sempre termos logs

        var anime = MAPPER.toAnime(request);

        Anime.getAnimes().add(anime);

        var response = MAPPER.toAnimePostResponse(anime);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

//                request.setId(ThreadLocalRandom.current().nextLong(100_000));
//        Anime.getAnimes().add(anime);
//        return anime;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        log.debug("Request to delete anime by id: {}", id);

        var animeToDelete = Anime.getAnimes()
                .stream().filter(anime -> anime.getId().equals(id))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
        Anime.getAnimes().remove(animeToDelete);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AnimePutRequest request) {
        log.debug("Request to update anime by id: {}", request);

        var animeToRemove = Anime.getAnimes()
                .stream()
                .filter(anime -> anime.getId().equals(request.getId()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));

        var animeUpdate = MAPPER.toAnime(request);

        Anime.getAnimes().remove(animeToRemove);
        Anime.getAnimes().add(animeUpdate);

        return ResponseEntity.noContent().build();
    }
}
