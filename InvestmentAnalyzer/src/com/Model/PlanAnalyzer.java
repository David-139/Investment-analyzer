package com.Model;

/**
 * this class has logic behind calculating yields and our subjective scoring which will we use to analyze and sort individual investment plans
 */

public class PlanAnalyzer {

    public void analyzePlan(YieldsRecorder yieldsRecorder ,double interestRatePerAnum, double subjectiveRiskFactor, int moneyAmount, int perAnumInterests, boolean compoundInterest) {

        double planScore = 5;

        if (perAnumInterests == 4 && compoundInterest) {
            interestRatePerAnum /= 4;
            yieldsRecorder.setYearOneYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 4)) - moneyAmount);
            yieldsRecorder.setYearThreeYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 12)) - moneyAmount);
            yieldsRecorder.setYearFiveYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 20)) - moneyAmount);
            yieldsRecorder.setYearTenYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 40)) - moneyAmount);
            yieldsRecorder.setYearFifteenYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 60)) - moneyAmount);
            yieldsRecorder.setYearTwentyYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 80)) - moneyAmount);
        } else if (perAnumInterests == 2 && compoundInterest) {
            interestRatePerAnum /= 2;
            yieldsRecorder.setYearOneYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 2)) - moneyAmount);
            yieldsRecorder.setYearThreeYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 6)) - moneyAmount);
            yieldsRecorder.setYearFiveYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 10)) - moneyAmount);
            yieldsRecorder.setYearTenYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 20)) - moneyAmount);
            yieldsRecorder.setYearFifteenYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 30)) - moneyAmount);
            yieldsRecorder.setYearTwentyYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 40)) - moneyAmount);
        } else if (perAnumInterests == 1 && compoundInterest) {
            yieldsRecorder.setYearOneYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 1)) - moneyAmount);
            yieldsRecorder.setYearThreeYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 3)) - moneyAmount);
            yieldsRecorder.setYearFiveYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 5)) - moneyAmount);
            yieldsRecorder.setYearTenYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 10)) - moneyAmount);
            yieldsRecorder.setYearFifteenYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 15)) - moneyAmount);
            yieldsRecorder.setYearTwentyYield(moneyAmount*(Math.pow(1+(interestRatePerAnum/100), 20)) - moneyAmount);
        } else {
            interestRatePerAnum /= perAnumInterests;
            yieldsRecorder.setYearOneYield((moneyAmount*(1+(interestRatePerAnum/100)) - moneyAmount)*perAnumInterests);
            yieldsRecorder.setYearThreeYield((moneyAmount*(1+(interestRatePerAnum/100)) - moneyAmount)*perAnumInterests*3);
            yieldsRecorder.setYearFiveYield((moneyAmount*(1+(interestRatePerAnum/100)) - moneyAmount)*perAnumInterests*5);
            yieldsRecorder.setYearTenYield((moneyAmount*(1+(interestRatePerAnum/100)) - moneyAmount)*perAnumInterests*10);
            yieldsRecorder.setYearFifteenYield((moneyAmount*(1+(interestRatePerAnum/100)) - moneyAmount)*perAnumInterests*15);
            yieldsRecorder.setYearTwentyYield((moneyAmount*(1+(interestRatePerAnum/100)) - moneyAmount)*perAnumInterests*20);
        }

    /*
    * Here we are calculating our subjective score, we will decide on the score according to data for year 5
    * for yield in year 5 we can score MAX + 5 points
    * for risk factor we can deduct MAX - 5 points
    * as the starting score is 5, this scoring system ensures we stay in the 0-10 points range
    */

        double percentageInYearFive = yieldsRecorder.getYearFiveYield() / moneyAmount;

        if (subjectiveRiskFactor != 0) {
            double index = subjectiveRiskFactor/2;
            planScore -= index;
        }

        for (int i = 5; i > 0; i--) {
            if (percentageInYearFive*100 > i*5) {
                planScore += i;
                break;
            }
        }


        // Here we are calculating yields with subjective risk factor in mind, giving more weight to higher risk

        double badScenarioYieldIndex;

        if (subjectiveRiskFactor == 10) {
            badScenarioYieldIndex = 0;
        } else if (subjectiveRiskFactor == 0) {
            badScenarioYieldIndex = 1;
        } else if (subjectiveRiskFactor > 6) {
            badScenarioYieldIndex = 1-(subjectiveRiskFactor/10)*0.8;
        } else if (subjectiveRiskFactor > 3) {
            badScenarioYieldIndex = 1-(subjectiveRiskFactor/10)*0.5;
        } else {
            badScenarioYieldIndex = 1-(subjectiveRiskFactor/10)*0.4;
        }

        yieldsRecorder.setBadScenarioIndex(badScenarioYieldIndex);
        yieldsRecorder.setScore(planScore);
    }
}
