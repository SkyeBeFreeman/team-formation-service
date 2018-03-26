package ehelper.backend.daos;

import ehelper.backend.entities.Direct;
import ehelper.backend.repositories.DirectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Skye on 2018/3/25.
 */
@Repository
public class DirectDAOImpl implements DirectDAO {

    @Autowired
    private DirectRepository directRepository;

    @Override
    public List<Direct> findDirectsByFromId(long fromId) {
        return directRepository.findDirectsByFromId(fromId);
    }

    @Override
    public List<Direct> findDirectsByToId(long toId) {
        return directRepository.findDirectsByToId(toId);
    }

    @Override
    public double findDirectByFromIdAndToId(long fromId, long toId) {
        Direct temp = directRepository.findDirectByFromIdAndToId(fromId, toId);
        if (temp != null) {
            return temp.getRemark();
        } else {
            return -1;
        }
    }
}
