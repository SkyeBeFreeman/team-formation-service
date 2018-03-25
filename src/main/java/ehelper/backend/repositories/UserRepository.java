package ehelper.backend.repositories;

import ehelper.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Skye on 2018/3/23.
 */
public interface UserRepository extends JpaRepository<User, Long> {

}