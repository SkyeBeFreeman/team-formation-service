package ehelper.backend.daos;

import ehelper.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Skye on 2018/3/25.
 */
@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private UserRepository userRepository;

    public double findRankById(long id) {
        return userRepository.findOne(id).getRank();
    }

}
