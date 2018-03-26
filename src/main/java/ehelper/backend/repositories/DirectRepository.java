package ehelper.backend.repositories;

import ehelper.backend.entities.Direct;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Skye on 2018/3/24.
 */
public interface DirectRepository extends JpaRepository<Direct, Long> {

    Direct findDirectByFromId(long fromId);

    Direct findDirectByFromIdAndToId(long fromId, long toId);

}
