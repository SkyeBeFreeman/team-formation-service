package ehelper.backend.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Skye on 2018/3/23.
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private long id;

    /**
     * 男性 1
     * 女性 0
     */
    @Column(nullable = false)
    private int sex;

    @Column(nullable = false)
    private int age;

    /**
     * 医护类 0
     * 安保类 1
     * 其他   2
     */
    @Column(nullable = false)
    private int occupation;

    @Column(nullable = false)
    private double rank;

    @Column(nullable = false)
    private double longtitude;

    @Column(nullable = false)
    private double latitude;


    public User() {

    }

    public User(int sex, int age, int occupation, double rank, double longtitude, double latitude) {
        this.sex = sex;
        this.age = age;
        this.occupation = occupation;
        this.rank = rank;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getOccupation() {
        return occupation;
    }

    public void setOccupation(int occupation) {
        this.occupation = occupation;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
