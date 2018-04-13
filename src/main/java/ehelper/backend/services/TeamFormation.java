package ehelper.backend.services;

import ehelper.backend.entities.User;

import java.util.List;

/**
 * Created by Skye on 2018/4/5.
 */
public interface TeamFormation {

    List<User> firstFliter(User i);

    User chooseLeader(List<User> oldList, User i );

    List<User> secondFilter(List<User> oldList, List<User> newList, User leader, User i);

}
