package videominer.src.main.java.aiss.videominer.controller;

import videominer.src.main.java.aiss.videominer.exceptions.CaptionNotFoundException;
import videominer.src.main.java.aiss.videominer.exceptions.VideoNotFoundException;
import videominer.src.main.java.aiss.videominer.model.Caption;
import videominer.src.main.java.aiss.videominer.model.Video;
import videominer.src.main.java.aiss.videominer.repository.CaptionRepository;
import videominer.src.main.java.aiss.videominer.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Caption API", description = "Administración de subtítulos")
@RestController
@RequestMapping("/videominer")
public class CaptionController {

    private final CaptionRepository captionRepo;
    private final VideoRepository videoRepo;

    @Autowired
    public CaptionController(CaptionRepository captionRepo, VideoRepository videoRepo) {
        this.captionRepo = captionRepo;
        this.videoRepo = videoRepo;
    }

    @Operation(summary = "Obtiene todos los subtítulos del sistema")
    @GetMapping("/captions")
    public List<Caption> listAllCaptions() {
        return captionRepo.findAll();
    }

    @Operation(summary = "Encuentra un subtítulo usando su identificador")
    @GetMapping("/captions/{captionId}")
    public Caption fetchCaptionById(@Parameter(description = "ID del subtítulo")
                                    @PathVariable("captionId") String captionId) throws CaptionNotFoundException {
        return captionRepo.findById(captionId).orElseThrow(() -> new CaptionNotFoundException());
    }

    @Operation(summary = "Muestra la colección de subtítulos de un vídeo")
    @GetMapping("/videos/{videoId}/captions")
    public List<Caption> fetchCaptionsFromVideo(@Parameter(description = "ID del vídeo")
                                                @PathVariable("videoId") String videoId) throws VideoNotFoundException {
        Video targetVideo = videoRepo.findById(videoId).orElseThrow(() -> new VideoNotFoundException());
        return targetVideo.getCaptions();
    }
}