package cmpt276.assn2.models;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByName(String name);
    void deleteById(int sid);
    Student findById(int sid);
    
}
