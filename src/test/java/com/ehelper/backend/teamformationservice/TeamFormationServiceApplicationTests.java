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
	public void test1() {
	    User i = userDAO.findOne(75l);
        List<User> temp = teamFormation.firstFliter(i);
        User leader = teamFormation.chooseLeader(temp, i);
        System.out.println(leader.toString());
	}

}
