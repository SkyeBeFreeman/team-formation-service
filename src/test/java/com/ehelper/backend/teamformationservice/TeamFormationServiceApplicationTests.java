package com.ehelper.backend.teamformationservice;

import ehelper.backend.TeamFormationServiceApplication;
import ehelper.backend.daos.UserDAO;
import ehelper.backend.entities.User;
import ehelper.backend.services.TeamFormation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TeamFormationServiceApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TeamFormationServiceApplicationTests {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TeamFormation teamFormation;

	@Test
	public void testOldPeople() {
	    User i = userDAO.findOne(75l);
        teamFormationRunner(i);

    }

    @Test
    public void testYoungPeople() {
        User i = userDAO.findOne(5l);
        teamFormationRunner(i);
    }

    private void teamFormationRunner(User i) {
        List<User> nearbyList = teamFormation.firstFliter(i);
//        System.out.println("***************************nearbyList***************************");
//        for (User x : nearbyList) {
//            System.out.println(x.toString());
//        }

        List<User> oldList = new ArrayList<>();
        List<User> newList = new ArrayList<>();
        for (User x : nearbyList) {
            if (x.getIsNew() == 0) {
                oldList.add(x);
            } else {
                newList.add(x);
            }
        }
//        System.out.println("***************************oldList***************************");
//        for (User x : oldList) {
//            System.out.println(x.toString());
//        }
//        System.out.println("***************************newList***************************");
//        for (User x : newList) {
//            System.out.println(x.toString());
//        }

        System.out.println("***************************leader***************************");
        User leader = teamFormation.chooseLeader(oldList, i);
        System.out.println(leader.toString());

        int leaderIndex = 0;
        for (int j = 0; j < oldList.size(); j++) {
            if (leader.getId() == oldList.get(j).getId()) {
                leaderIndex = j;
            }
        }
        oldList.remove(leaderIndex);
        System.out.println("***************************team***************************");
        List<User> finalTeam = teamFormation.secondFilter(oldList, newList, leader, i);
        for (User x : finalTeam) {
            System.out.println(x.toString());
        }
    }

}
