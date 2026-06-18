package videominer.src.main.java.aiss.videominer.repository;

import videominer.src.main.java.aiss.videominer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {}