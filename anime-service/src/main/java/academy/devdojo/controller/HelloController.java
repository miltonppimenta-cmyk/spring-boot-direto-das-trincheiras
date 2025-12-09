package academy.devdojo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

// Um bean é uma instância de um objeto iniciado pelo Spring
// em nenhum momento foi criado um objeto do HelloController mais conseguimos usar os métodos dessa classe


// As anotações abaixo identificam a classe como um bean

@Component
@Service
@Repository
@Slf4j
@RestController
@RequestMapping("v1/greetings")
public class HelloController {

    @GetMapping("hi")
    public String hi() {
        return "Hello World!";
    }

    @PostMapping
    public Long save(@RequestBody String name) { //A anotação @RequestBody fala para o Spring pra pegar o que está na requisição e tentar fazer a conversão para String name
        log.info("save '{}'", name);
        return ThreadLocalRandom.current().nextLong(1, 1000);
    }
}
