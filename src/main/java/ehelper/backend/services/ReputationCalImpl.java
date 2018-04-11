package ehelper.backend.services;

import ehelper.backend.daos.DirectDAO;
import ehelper.backend.daos.UserDAO;
import ehelper.backend.entities.Direct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Skye on 2018/3/26.
 */
@Service
@Transactional
public class ReputationCalImpl implements ReputationCal {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DirectDAO directDAO;

    @Override
    public double calReputation(long fromId, long toId) {
        double rank = userDAO.findRankById(toId);
        double direct = directDAO.findDirectByFromIdAndToId(fromId, toId);
        double indirect = calIndirect(fromId, toId);

        if (direct != -1 && indirect != -1) {
            return 0.25 * rank + 0.5 * direct + 0.25 * indirect;
        } else if (indirect != -1) {
            return 0.5 * rank + 0.5 * indirect;
        } else if (direct != -1) {
            return rank / 3 + direct * 2 / 3;
        } else {
            return rank;
        }

    }

    private double calIndirect(long fromId, long toId) {
        List<Long> userlistOfFromId = directDAO.findDirectsByFromId(fromId).stream().map(Direct::getToId).collect(Collectors.toList());
        List<Long> userlistOfToId = directDAO.findDirectsByToId(toId).stream().map(Direct::getFromId).collect(Collectors.toList());

        if (userlistOfFromId.isEmpty() || userlistOfToId.isEmpty()) {
            return -1;
        }

        // 求两个数组的交集，即fromId评价过的也评价过toId的
        List<Long> mid = new ArrayList<>();
        for (long i : userlistOfFromId) {
            if (userlistOfToId.contains(i)) {
                mid.add(i);
            }
        }

        if (mid.isEmpty()) {
            return -1;
        }

        double dividend = 0; // 被除数
        double divisor = 0; // 除数
        for (long i : mid) {
            double temp = directDAO.findDirectByFromIdAndToId(fromId, i);
            divisor += temp;
            dividend += temp * directDAO.findDirectByFromIdAndToId(i, toId);
        }
        return dividend / divisor;
    }

}
