package videominer.src.main.java.aiss.videominer.controller;

import videominer.src.main.java.aiss.videominer.exceptions.ChannelNotFoundException;
import videominer.src.main.java.aiss.videominer.exceptions.VideoNotFoundException;
import videominer.src.main.java.aiss.videominer.model.Channel;
import videominer.src.main.java.aiss.videominer.model.Video;
import videominer.src.main.java.aiss.videominer.repository.ChannelRepository;
import videominer.src.main.java.aiss.videominer.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Video API", description = "Operaciones de consulta sobre el catálogo de vídeos")
@RestController
@RequestMapping("/videominer")
public class VideoController {

    private final VideoRepository videoRepo;
    private final ChannelRepository channelRepo;

    @Autowired
    public VideoController(VideoRepository videoRepo, ChannelRepository channelRepo) {
        this.videoRepo = videoRepo;
        this.channelRepo = channelRepo;
    }

    @Operation(summary = "Recupera todos los vídeos indexados")
    @GetMapping("/videos")
    public List<Video> listAllVideos() {
        return videoRepo.findAll();
    }

    @Operation(summary = "Obtiene los detalles de un vídeo por su identificador")
    @GetMapping("/videos/{videoId}")
    public Video fetchVideoById(@Parameter(description = "ID del vídeo")
                                @PathVariable("videoId") String videoId) throws VideoNotFoundException {
        return videoRepo.findById(videoId).orElseThrow(() -> new VideoNotFoundException());
    }

    @Operation(summary = "Muestra la lista de vídeos que pertenecen a un canal concreto")
    @GetMapping("/channels/{channelId}/videos")
    public List<Video> fetchVideosFromChannel(@Parameter(description = "ID del canal")
                                              @PathVariable("channelId") String channelId) throws ChannelNotFoundException {
        Channel targetChannel = channelRepo.findById(channelId).orElseThrow(() -> new ChannelNotFoundException());
        return targetChannel.getVideos();
    }
}