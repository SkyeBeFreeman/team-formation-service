package ehelper.backend.daos;

import ehelper.backend.entities.User;
import ehelper.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Skye on 2018/3/25.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findOne(long id) {
        return userRepository.findOne(id);
    }

    @Override
    public List<User> findNearByUsers(double longtitude, double latitude) {
        return userRepository.findNearByUsers(longtitude, latitude);
    }

    public double findRankById(long id) {
        return userRepository.findOne(id).getRank();
    }

}
