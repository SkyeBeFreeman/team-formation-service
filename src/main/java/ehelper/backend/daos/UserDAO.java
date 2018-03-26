package ehelper.backend.daos;

import org.springframework.stereotype.Repository;

/**
 * Created by Skye on 2018/3/25.
 */
public interface UserDAO {

    double findRankById(long id);

}
