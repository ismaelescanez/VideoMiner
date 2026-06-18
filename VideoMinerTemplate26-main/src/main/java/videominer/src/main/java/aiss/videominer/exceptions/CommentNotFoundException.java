package videominer.src.main.java.aiss.videominer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Error 404: El comentario indicado no existe en la base de datos")
public class CommentNotFoundException extends Exception {}