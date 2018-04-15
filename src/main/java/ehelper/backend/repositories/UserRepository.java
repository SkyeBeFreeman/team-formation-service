package ehelper.backend.repositories;

import ehelper.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Skye on 2018/3/23.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select * from user where ABS(longtitude - ?1) <= 10 and ABS(latitude - ?2) <= 10", nativeQuery = true)
    List<User> findNearByUsers(double longtitude, double latitude);
}