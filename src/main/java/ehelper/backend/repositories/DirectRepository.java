package ehelper.backend.repositories;

import ehelper.backend.entities.Direct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Skye on 2018/3/24.
 */
public interface DirectRepository extends JpaRepository<Direct, Long> {

    List<Direct> findDirectsByFromId(long fromId);

    List<Direct> findDirectsByToId(long toId);

    Direct findDirectByFromIdAndToId(long fromId, long toId);

}
