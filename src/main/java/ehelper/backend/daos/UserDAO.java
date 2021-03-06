package ehelper.backend.daos;

import ehelper.backend.entities.User;

import java.util.List;

/**
 * Created by Skye on 2018/3/25.
 */
public interface UserDAO {

    User findOne(long id);

    List<User> findNearByUsers(double longtitude, double latitude);

    double findRankById(long id);

}
