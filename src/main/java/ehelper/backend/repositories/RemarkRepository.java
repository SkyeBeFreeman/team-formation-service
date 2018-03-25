package ehelper.backend.repositories;

import ehelper.backend.entities.Remark;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Skye on 2018/3/24.
 */
public interface RemarkRepository extends JpaRepository<Remark, Long> {
}
