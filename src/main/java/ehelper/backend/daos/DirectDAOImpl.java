package ehelper.backend.daos;

import ehelper.backend.repositories.DirectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Skye on 2018/3/25.
 */
@Repository
public class DirectDAOImpl implements DirectDAO {

    @Autowired
    private DirectRepository directRepository;

    @Override
    public double findDirectByFromId(long fromId) {
        return directRepository.findDirectByFromId(fromId).getRemark();
    }

    @Override
    public double findDirectByFromIdAndToId(long fromId, long toId) {
        return directRepository.findDirectByFromIdAndToId(fromId, toId).getRemark();
    }
}
