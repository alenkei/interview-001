package org.acme.database;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "REPORT_DATA")
public class ReportDataEntity extends PanacheEntity {
    private long   id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String gender;
    private double usdBalance;
    private double eurBalance;
    private double yenBalance;
    private double gbpBalance;

    public long getId() {
        return id;
    }

    public ReportDataEntity setId(long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ReportDataEntity setFirstName(String firstname) {
        this.firstName = firstname;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ReportDataEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public ReportDataEntity setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public ReportDataEntity setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public double getUsdBalance() {
        return usdBalance;
    }

    public ReportDataEntity setUsdBalance(double usdBallance) {
        this.usdBalance = usdBallance;
        return this;
    }

    public double getEurBalance() {
        return eurBalance;
    }

    public ReportDataEntity setEurBalance(double eurBallance) {
        this.eurBalance = eurBallance;
        return this;
    }

    public double getYenBalance() {
        return yenBalance;
    }

    public ReportDataEntity setYenBalance(double yenBallance) {
        this.yenBalance = yenBallance;
        return this;
    }

    public double getGbpBalance() {
        return gbpBalance;
    }

    public ReportDataEntity setGbpBalance(double gbpBallance) {
        this.gbpBalance = gbpBallance;
        return this;
    }
}
