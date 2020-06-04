package org.acme.database;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "REPORT_DATA")
public class ReportDataEntity extends PanacheEntity {
    private long recID;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String gender;
    private double usdBalance;
    private double eurBalance;
    private double yenBalance;
    private double gbpBalance;

    public long getRecID() {
        return recID;
    }

    public ReportDataEntity setRecID(long id) {
        this.recID = id;
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

    public static Optional<ReportDataEntity> findByRecID(Long recID) {
        return find("recID", recID.longValue()).firstResultOptional();
    }

    public static Optional<List<ReportDataEntity>> getAll() {
        return Optional.of(listAll());
    }

    @Override
    public String toString() {
        return "ReportDataEntity{" +
                "id=" + id +
                ", recID=" + recID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", gender='" + gender + '\'' +
                ", usdBalance=" + usdBalance +
                ", eurBalance=" + eurBalance +
                ", yenBalance=" + yenBalance +
                ", gbpBalance=" + gbpBalance +
                '}';
    }
}
