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
import java.util.Random;

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
	    for (long j = 71l; j <= 80l; j++) {
            User i = userDAO.findOne(j);
            List<User> temp = teamFormationRunner(i);
            int cnt = 0;
            for (User x : temp) {
                if (x.getOccupation() == 0) {
                    cnt++;
                }
            }
            System.out.println("" + j + " " + cnt + "(" + temp.size() + ")");
        }
    }

    @Test
    public void testYoungPeople() {
        for (long j = 71l; j <= 80l; j++) {
            Random random = new Random();
            int index = random.nextInt(70) + 1;
            User i = userDAO.findOne(index);
            List<User> temp = teamFormationRunner(i);
            int cnt = 0;
            for (User x : temp) {
                if (x.getOccupation() == 1) {
                    cnt++;
                }
            }
            System.out.println("" + index + " " + cnt + "(" + temp.size() + ")");
        }
    }

    @Test
    public void testProbability() {
	    double cnt1 = 0;
        double cnt2 = 0;
        double cnt3 = 0;
        double cnt4 = 0;
	    for (long i = 1; i <= 80l; i++) {
            List<User> temp = teamFormationRunner(userDAO.findOne(i));
            for (User x : temp) {
                if (x.getId() >= 81 && x.getId() <= 85) {
                    cnt1++;
                    break;
                }
            }
            for (User x : temp) {
                if (x.getId() >= 86 && x.getId() <= 90) {
                    cnt2++;
                    break;
                }
            }
            for (User x : temp) {
                if (x.getId() >= 91 && x.getId() <= 95) {
                    cnt3++;
                    break;
                }
            }
            for (User x : temp) {
                if (x.getId() >= 96 && x.getId() <= 100) {
                    cnt4++;
                    break;
                }
            }
        }
        System.out.println("包含新用户的比例:       " + cnt1 / 80 * 100 + "%");
        System.out.println("包含信誉虚高用户的比例: " + cnt2 / 80 * 100 + "%");
        System.out.println("包含信誉过低用户的比例: " + cnt3 / 80 * 100 + "%");
        System.out.println("包含恶意用户的比例:     " + cnt4 / 80 * 100 + "%");
    }

    private List<User> teamFormationRunner(User i) {
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

//        System.out.println("***************************leader***************************");
        User leader = teamFormation.chooseLeader(oldList, i);
//        System.out.println(leader.toString());

        int leaderIndex = 0;
        for (int j = 0; j < oldList.size(); j++) {
            if (leader.getId() == oldList.get(j).getId()) {
                leaderIndex = j;
            }
        }
        oldList.remove(leaderIndex);
//        System.out.println("***************************team***************************");
        List<User> finalTeam = teamFormation.secondFilter(oldList, newList, leader, i);
//        for (User x : finalTeam) {
//            System.out.println(x.toString());
//        }
        return finalTeam;
    }

}
