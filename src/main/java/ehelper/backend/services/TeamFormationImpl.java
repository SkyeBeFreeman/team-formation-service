package ehelper.backend.services;

import ehelper.backend.daos.UserDAO;
import ehelper.backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Skye on 2018/4/5.
 */
@Service
@Transactional
public class TeamFormationImpl implements TeamFormation {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ReputationCal reputationCal;

    @Override
    public List<User> firstFliter(User i) {
        List<User> global = userDAO.findAll();
        List<User> result = new ArrayList<>();
        for (User x : global) {
            if (i.getId() != x.getId()
                    && Math.abs(x.getLatitude() - i.getLatitude()) <= 100
                    && Math.abs(x.getLongtitude() - i.getLongtitude()) <= 100
                    && reputationCal.calReputation(i.getId(), x.getId()) >= 3.5) {
                result.add(x);
            }
        }
        return result;
    }

    @Override
    public User chooseLeader(List<User> userList, User i) {
        boolean isOld = false;
        if (i.getAge() >= 50) {
            isOld = true;
        }

        // 获取有符合技能的人群
        List<User> leaderList1 = new ArrayList<>();
        if (isOld) {
            for (User x : userList) {
                if (x.getOccupation() == 0) {
                    leaderList1.add(x);
                }
            }
            if (leaderList1.isEmpty()) {
                for (User x : userList) {
                    if (x.getOccupation() == 1) {
                        leaderList1.add(x);
                    }
                }
            }
        } else {
            for (User x : userList) {
                if (x.getOccupation() == 1) {
                    leaderList1.add(x);
                }
            }
            if (leaderList1.isEmpty()) {
                for (User x : userList) {
                    if (x.getOccupation() == 0) {
                        leaderList1.add(x);
                    }
                }
            }
        }
        if (leaderList1.isEmpty()) {
            leaderList1.addAll(userList);
        }
        if (leaderList1.size() == 1) {
            return leaderList1.get(0);
        }

        // 获取领导力较高的用户
        List<User> leaderList2 = new ArrayList<>();
        for (User x : leaderList1) {
            if (x.getLeadership() >= 3.5) {
                leaderList2.add(x);
            }
        }
        if (leaderList2.isEmpty()) {
            leaderList2 = leaderList1;
        }

        // 获取最高点对点信任值的人群
        Stack<User> leaderStack1 = new Stack<>();
        double reputationTemp = 0;
        for (User x : leaderList2) {
            double reputationix = reputationCal.calReputation(i.getId(), x.getId());
            if (reputationix > reputationTemp) {
                reputationTemp = reputationix;
                leaderStack1.clear();
                leaderStack1.push(x);
            } else if (reputationix == reputationTemp) {
                leaderStack1.push(x);
            }
        }
        if (leaderStack1.size() == 1) {
            return leaderStack1.peek();
        }

        // 获取最高领导力的人群
        Stack<User> leaderStack2 = new Stack<>();
        double leadershipTemp = 0;
        for (User x : leaderStack1) {
            if (x.getLeadership() > leadershipTemp) {
                leadershipTemp = x.getLeadership();
                leaderStack2.clear();
                leaderStack2.push(x);
            } else if (x.getLeadership() == leadershipTemp) {
                leaderStack2.push(x);
            }
        }
        if (leaderStack2.size() == 1) {
            return leaderStack2.peek();
        }

        // 获取40岁左右的人群
        List<User> leaderList3 = new ArrayList<>();
        for (User x : leaderStack2) {
            if (Math.abs(x.getAge() - 40) <= 5) {
                leaderList3.add(x);
            }
        }
        if (leaderList3.isEmpty()) {
            leaderList3.addAll(leaderStack2);
        } else if (leaderList3.size() == 1) {
            return leaderList3.get(0);
        }

        // 获取男性人群
        List<User> leaderList4 = new ArrayList<>();
        for (User x : leaderList3) {
            if (x.getSex() == 1) {
                leaderList4.add(x);
            }
        }
        if (leaderList4.isEmpty()) {
            leaderList4.addAll(leaderStack2);
        } else if (leaderList4.size() == 1) {
            return leaderList4.get(0);
        }

        // 随机选取
        Random random = new Random();
        int index = random.nextInt(leaderList4.size());
        return leaderList4.get(index);
    }

    @Override
    public List<User> secondFilter(List<User> userList, User leader) {
        return null;
    }
}
