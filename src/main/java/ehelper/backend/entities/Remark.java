package ehelper.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Skye on 2018/3/24.
 */
@Entity
public class Remark {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private long fromId;

    @Column(nullable = false)
    private long toId;

    @Column(nullable = false)
    private double remark;

    public Remark() {
    }

    public Remark(long fromId, long toId, double remark) {
        this.fromId = fromId;
        this.toId = toId;
        this.remark = remark;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFromId() {
        return fromId;
    }

    public void setFromId(long fromId) {
        this.fromId = fromId;
    }

    public long getToId() {
        return toId;
    }

    public void setToId(long toId) {
        this.toId = toId;
    }

    public double getRemark() {
        return remark;
    }

    public void setRemark(double remark) {
        this.remark = remark;
    }
}
