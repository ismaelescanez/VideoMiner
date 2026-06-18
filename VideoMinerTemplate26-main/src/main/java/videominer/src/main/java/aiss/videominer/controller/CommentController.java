package videominer.src.main.java.aiss.videominer.controller;

import videominer.src.main.java.aiss.videominer.exceptions.CommentNotFoundException;
import videominer.src.main.java.aiss.videominer.exceptions.VideoNotFoundException;
import videominer.src.main.java.aiss.videominer.model.Comment;
import videominer.src.main.java.aiss.videominer.model.Video;
import videominer.src.main.java.aiss.videominer.repository.CommentRepository;
import videominer.src.main.java.aiss.videominer.repository.VideoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment API", description = "Gestión y extracción de comentarios")
@RestController
@RequestMapping("/videominer")
public class CommentController {

    private final CommentRepository commentRepo;
    private final VideoRepository videoRepo;

    @Autowired
    public CommentController(CommentRepository commentRepo, VideoRepository videoRepo) {
        this.commentRepo = commentRepo;
        this.videoRepo = videoRepo;
    }

    @Operation(summary = "Lista todos los comentarios globales")
    @GetMapping("/comments")
    public List<Comment> listAllComments() {
        return commentRepo.findAll();
    }

    @Operation(summary = "Busca un comentario específico por ID")
    @GetMapping("/comments/{commentId}")
    public Comment fetchCommentById(@Parameter(description = "ID del comentario")
                                    @PathVariable("commentId") String commentId) throws CommentNotFoundException {
        return commentRepo.findById(commentId).orElseThrow(() -> new CommentNotFoundException());
    }

    @Operation(summary = "Recupera los comentarios asociados a un vídeo")
    @GetMapping("/videos/{videoId}/comments")
    public List<Comment> fetchCommentsFromVideo(@Parameter(description = "ID del vídeo")
                                                @PathVariable("videoId") String videoId) throws VideoNotFoundException {
        Video targetVideo = videoRepo.findById(videoId).orElseThrow(() -> new VideoNotFoundException());
        return targetVideo.getComments();
    }
}