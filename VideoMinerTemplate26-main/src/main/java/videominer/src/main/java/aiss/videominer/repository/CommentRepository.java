package videominer.src.main.java.aiss.videominer.repository;

import videominer.src.main.java.aiss.videominer.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {}
