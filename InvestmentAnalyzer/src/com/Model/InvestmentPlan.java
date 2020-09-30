package com.Model;

import java.beans.Transient;
import java.io.Serializable;

/**
 * Individual plan, the corner stone of the application which holds basic information about plan (it´s score is saved in the YieldsRecorder class as these data are important to calculate it and to lighten this class)
 */

public class InvestmentPlan implements Comparable<InvestmentPlan>, Serializable {
    private YieldsRecorder yieldsRecorder;
    private String name;
    private double interestRatePerAnum;
    private double subjectiveRiskFactor; // we will use 0-10 int range to rate the potential risk
    private int moneyAmount;
    private int perAnumInterests; // this will show us if interest is paid quarterly, half a year or annually and calculate accordingly
    private boolean compoundInterest;

    public InvestmentPlan(String name, double interestRatePerAnum, int subjectiveRiskFactor, int moneyAmount, int perAnumInterests, boolean compoundInterest) {
        yieldsRecorder = new YieldsRecorder();
        this.name = name;
        this.interestRatePerAnum = interestRatePerAnum;
        this.subjectiveRiskFactor = subjectiveRiskFactor;
        this.moneyAmount = moneyAmount;
        this.perAnumInterests = perAnumInterests;
        this.compoundInterest = compoundInterest;
    }

    public YieldsRecorder getYieldsRecorder() {
        return yieldsRecorder;
    }

    public void setYieldsRecorder(YieldsRecorder yieldsRecorder) {
        this.yieldsRecorder = yieldsRecorder;
    }

    public boolean isCompoundInterest() {
        return compoundInterest;
    }

    public void setCompoundInterest(boolean compoundInterest) {
        this.compoundInterest = compoundInterest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInterestRatePerAnum() {
        return interestRatePerAnum;
    }

    public void setInterestRatePerAnum(double interestRatePerAnum) {
        this.interestRatePerAnum = interestRatePerAnum;
    }

    public double getSubjectiveRiskFactor() {
        return subjectiveRiskFactor;
    }

    public void setSubjectiveRiskFactor(int subjectiveRiskFactor) {
        this.subjectiveRiskFactor = subjectiveRiskFactor;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public int getPerAnumInterests() {
        return perAnumInterests;
    }

    public void setPerAnumInterests(int perAnumInterests) {
        this.perAnumInterests = perAnumInterests;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(InvestmentPlan investmentPlan) {
        if (this.getYieldsRecorder().getScore() > investmentPlan.getYieldsRecorder().getScore()) {
            return -1;
        } else if (this.getYieldsRecorder().getScore() < investmentPlan.getYieldsRecorder().getScore()) {
            return 1;
        } else {
            return 0;
        }
    }
}
