package sbd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sbd.domain.Category;

/**
 * Created by benoit on 12/08/15.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
