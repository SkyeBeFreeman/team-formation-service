package ehelper.backend.controllers;

import ehelper.backend.daos.UserDAO;
import ehelper.backend.entities.User;
import ehelper.backend.services.TeamFormation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Skye on 2018/4/5.
 */

@RestController
@RequestMapping(value = "/team-formation")
public class TeamFormationController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TeamFormation teamFormation;

    /**
     * 基础的获取一个适合的队伍
     * @param session
     * @return finalTeam
     */
    @GetMapping(value = "/create")
    public List<User> getTeam(HttpSession session) {
//        long userId = (long) session.getAttribute("userId");
//        User i = userDAO.findOne(userId);
        User i = userDAO.findOne(5l);

        // 一级筛选
        List<User> nearbyList = teamFormation.firstFliter(i);
        List<User> oldList = new ArrayList<>();
        List<User> newList = new ArrayList<>();
        for (User x : nearbyList) {
            if (x.getIsNew() == 0) {
                oldList.add(x);
            } else {
                newList.add(x);
            }
        }

        // 选择队长
        User leader = teamFormation.chooseLeader(oldList, i);
        if (leader != null) {
            int leaderIndex = 0;
            for (int j = 0; j < oldList.size(); j++) {
                if (leader.getId() == oldList.get(j).getId()) {
                    leaderIndex = j;
                }
            }
            oldList.remove(leaderIndex);
        } else {
            // 随机选取
            Random random = new Random();
            int index = random.nextInt(newList.size());
            leader = newList.get(index);
            newList.remove(index);
        }

        // 二级筛选
        List<User> finalTeam = teamFormation.secondFilter(oldList, newList, leader, i);

        return finalTeam;
    }

    @PostMapping(value = "/create/lose/people")
    public List<User> getTeamWhenLosePeople(List<Long> okList, List<Long> noList, HttpSession session) {
//        long userId = (long) session.getAttribute("userId");
//        User i = userDAO.findOne(userId);
        User i = userDAO.findOne(5l);

        // 一级筛选
        List<User> nearbyList = teamFormation.firstFliter(i);
        List<User> oldList = new ArrayList<>();
        List<User> newList = new ArrayList<>();
        for (User x : nearbyList) {
            if (!noList.contains(x.getId())) {
                if (x.getIsNew() == 0) {
                    oldList.add(x);
                } else {
                    newList.add(x);
                }
            }
        }

        // 选择队长
        User leader = teamFormation.chooseLeader(oldList, i);
        if (leader != null) {
            int leaderIndex = 0;
            for (int j = 0; j < oldList.size(); j++) {
                if (leader.getId() == oldList.get(j).getId()) {
                    leaderIndex = j;
                }
            }
            oldList.remove(leaderIndex);
        } else {
            // 随机选取
            Random random = new Random();
            int index = random.nextInt(newList.size());
            leader = newList.get(index);
            newList.remove(index);
        }

        // 二级筛选
        List<User> finalTeam = teamFormation.secondFilter(oldList, newList, leader, i);

        return finalTeam;
    }

}
