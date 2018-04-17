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
        List<User> nearBy = userDAO.findNearByUsers(i.getLongtitude(), i.getLatitude());
        List<User> result = new ArrayList<>();
        for (User x : nearBy) {
            if (i.getId() != x.getId()
                    && (reputationCal.calReputation(i.getId(), x.getId()) >= 3.5 || x.getIsNew() == 1)) {
                result.add(x);
            }
        }
        return result;
    }

    @Override
    public User chooseLeader(List<User> oldList, User i) {

        if (oldList.isEmpty()) {
            return null;
        }

        boolean isOld = false;
        if (i.getAge() >= 50) {
            isOld = true;
        }

        // 获取有符合技能的人群
        List<User> leaderList1 = new ArrayList<>();
        if (isOld) {
            for (User x : oldList) {
                if (x.getOccupation() == 0) {
                    leaderList1.add(x);
                }
            }
            if (leaderList1.isEmpty()) {
                for (User x : oldList) {
                    if (x.getOccupation() == 1) {
                        leaderList1.add(x);
                    }
                }
            }
        } else {
            for (User x : oldList) {
                if (x.getOccupation() == 1) {
                    leaderList1.add(x);
                }
            }
            if (leaderList1.isEmpty()) {
                for (User x : oldList) {
                    if (x.getOccupation() == 0) {
                        leaderList1.add(x);
                    }
                }
            }
        }
        if (leaderList1.isEmpty()) {
            leaderList1.addAll(oldList);
        }
        if (leaderList1.size() == 1) {
            return leaderList1.get(0);
        }

        // 获取领导力较高的用户
        List<User> leaderList2 = new ArrayList<>();
        for (User x : leaderList1) {
            if (x.getLeadership() >= 4) {
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
    public List<User> secondFilter(List<User> oldList, List<User> newList, User leader, User i) {
        boolean isOld = false;
        if (i.getAge() >= 50) {
            isOld = true;
        }

        // 构建最终队伍并加入队长
        List<User> finalTeam = new ArrayList<>();
        finalTeam.add(leader);

        // 加入一名新用户（如果存在）
        Random random = new Random();
        if (!newList.isEmpty()) {
            int index = random.nextInt(newList.size());
            finalTeam.add(newList.get(index));
            newList.remove(index);
        }

        while (finalTeam.size() < 5 && (!oldList.isEmpty() || !newList.isEmpty())) {
            if (oldList.isEmpty()) {
                int index = random.nextInt(newList.size());
                finalTeam.add(newList.get(index));
                newList.remove(index);
            } else {
                double maxRank = 0;
                int maxIndex = 0;
                for (int j = 0; j < oldList.size(); j++) {
                    User x = oldList.get(j);
                    double reputation = reputationFromATeam(finalTeam, x);
                    double ageRank = (x.getAge() >= 20 && x.getAge() <= 50) ? 5 : 3;
                    double skillRank = 0;
                    if (isOld) {
                        skillRank = (x.getOccupation() == 0) ? 4.5 : 4;
                    } else {
                        skillRank = (x.getOccupation() == 1) ? 4.5 : 4;
                    }
                    double rank = (reputation + ageRank + skillRank) / 3;
                    if (rank > maxRank) {
                        maxRank = rank;
                        maxIndex = j;
                    }
                }
                finalTeam.add(oldList.get(maxIndex));
                oldList.remove(maxIndex);
            }

        }

        return finalTeam;
    }

    private double reputationFromATeam(List<User> finalTeam, User x) {
        List<User> onlyOldList = new ArrayList<>();
        for (User i : finalTeam) {
            if (i.getIsNew() == 0) {
                onlyOldList.add(i);
            }
        }
        int length = onlyOldList.size();
        double reputation = 0;
        for (User i : onlyOldList) {
            reputation += reputationCal.calReputation(i.getId(), x.getId());
        }
        return reputation / length;
    }

}
