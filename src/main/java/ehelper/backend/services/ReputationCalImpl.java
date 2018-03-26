package ehelper.backend.services;

import ehelper.backend.daos.DirectDAO;
import ehelper.backend.daos.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Skye on 2018/3/26.
 */
public class ReputationCalImpl implements ReputationCal {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DirectDAO directDAO;

    @Override
    public double calReputation(long fromId, long toId) {
        return 0;
    }

}
