package com.Model;

import java.io.Serializable;

/**
 * this class will store yields for more specific years and overall score number of the InvestmentPlan class to lighten it
 * badScenarioIndex is used to calculate how much lower yields would be if the bad scenario would come (based on risk factor)
 * compareTo is set to compare calculated score
 */

public class YieldsRecorder implements Serializable, Comparable<YieldsRecorder> {

    private double yearOneYield;
    private double yearThreeYield;
    private double yearFiveYield;
    private double yearTenYield;
    private double yearFifteenYield;
    private double yearTwentyYield;
    private double score;

    public double getBadScenarioIndex() {
        return badScenarioIndex;
    }

    public void setBadScenarioIndex(double badScenarioIndex) {
        this.badScenarioIndex = badScenarioIndex;
    }

    private double badScenarioIndex;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getYearOneYield() {
        return yearOneYield;
    }

    public void setYearOneYield(double yearOneYield) {
        this.yearOneYield = yearOneYield;
    }

    public double getYearThreeYield() {
        return yearThreeYield;
    }

    public void setYearThreeYield(double yearThreeYield) {
        this.yearThreeYield = yearThreeYield;
    }

    public double getYearFiveYield() {
        return yearFiveYield;
    }

    public void setYearFiveYield(double yearFiveYield) {
        this.yearFiveYield = yearFiveYield;
    }

    public double getYearTenYield() {
        return yearTenYield;
    }

    public void setYearTenYield(double yearTenYield) {
        this.yearTenYield = yearTenYield;
    }

    public double getYearFifteenYield() {
        return yearFifteenYield;
    }

    public void setYearFifteenYield(double yearFifteenYield) {
        this.yearFifteenYield = yearFifteenYield;
    }

    public double getYearTwentyYield() {
        return yearTwentyYield;
    }

    public void setYearTwentyYield(double yearTwentyYield) {
        this.yearTwentyYield = yearTwentyYield;
    }

    @Override
    public int compareTo(YieldsRecorder recorder) {
        if (score == recorder.getScore()) {
            return 0;
        }  else if (score > recorder.getScore()) {
            return -1;
        } else {
            return 1;
        }
    }
}
