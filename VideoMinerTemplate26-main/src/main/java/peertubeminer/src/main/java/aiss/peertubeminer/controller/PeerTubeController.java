package peertubeminer.src.main.java.aiss.peertubeminer.controller;

import peertubeminer.src.main.java.aiss.peertubeminer.model.Channel;
import peertubeminer.src.main.java.aiss.peertubeminer.service.PeerTubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/peertube")
public class PeerTubeController {

    private final PeerTubeService service;

    @Autowired
    public PeerTubeController(PeerTubeService service) {
        this.service = service;
    }

    // OPERACIÓN POST: Descarga de PeerTube y envía a VideoMiner
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Channel mineAndSendChannel(
            @PathVariable("id") String id,
            @RequestParam(value = "maxVideos", defaultValue = "${peertubeminer.maxVideos}") int maxVideos,
            @RequestParam(value = "maxComments", defaultValue = "${peertubeminer.maxComments}") int maxComments) {

        Channel completeChannel = service.fetchCompleteChannel(id, maxVideos, maxComments);

        if (completeChannel != null) {
            return service.exportToVideoMiner(completeChannel);
        }

        return null;
    }

    // OPERACIÓN GET (Recomendada en el enunciado): Solo descarga de PeerTube para probar
    @GetMapping("/{id}")
    public Channel getChannel(
            @PathVariable("id") String id,
            @RequestParam(value = "maxVideos", defaultValue = "${peertubeminer.maxVideos}") int maxVideos,
            @RequestParam(value = "maxComments", defaultValue = "${peertubeminer.maxComments}") int maxComments) {

        return service.fetchCompleteChannel(id, maxVideos, maxComments);
    }
}