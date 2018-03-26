package ehelper.backend.daos;

import ehelper.backend.entities.Direct;

import java.util.List;

/**
 * Created by Skye on 2018/3/25.
 */
public interface DirectDAO {

    List<Direct> findDirectsByFromId(long fromId);

    List<Direct> findDirectsByToId(long toId);

    double findDirectByFromIdAndToId(long fromId, long toId);

}
