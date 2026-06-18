package videominer.src.main.java.aiss.videominer.controller;

import videominer.src.main.java.aiss.videominer.exceptions.ChannelNotFoundException;
import videominer.src.main.java.aiss.videominer.model.Channel;
import videominer.src.main.java.aiss.videominer.repository.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Channel API", description = "Endpoints para la administración de canales en VideoMiner")
@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    private final ChannelRepository repository;

    @Autowired
    public ChannelController(ChannelRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Devuelve el listado completo de canales registrados")
    @GetMapping
    public List<Channel> fetchAllChannels() {
        return repository.findAll();
    }

    @Operation(summary = "Busca los datos de un canal específico mediante su ID")
    @GetMapping("/{id}")
    public Channel fetchChannelById(@Parameter(description = "Identificador del canal")
                                    @PathVariable("id") String id) throws ChannelNotFoundException {
        return repository.findById(id).orElseThrow(() -> new ChannelNotFoundException());
    }

    @Operation(summary = "Da de alta un nuevo canal en la base de datos")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Channel insertNewChannel(@Valid @RequestBody Channel newChannel) {
        return repository.save(newChannel);
    }

    @Operation(summary = "Borra un canal del sistema")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeChannel(@PathVariable("id") String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}