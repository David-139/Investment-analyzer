package com.Model;

import java.util.*;

/**
 * This class holds and manage all our investment plans - adds, removes, saves, loads and sorts
 */

public class EvidenceImpl implements Evidence {

    private List<InvestmentPlan> plans = new ArrayList<>();
    private PlanAnalyzer analyzer = new PlanAnalyzer();
    private PlanTextEvaluation textEvaluation = new PlanTextEvaluation();

    public void analyzePlan(InvestmentPlan plan) {
        analyzer.analyzePlan(plan.getYieldsRecorder(),plan.getInterestRatePerAnum(), plan.getSubjectiveRiskFactor(), plan.getMoneyAmount(), plan.getPerAnumInterests(), plan.isCompoundInterest());
    }

    public PlanTextEvaluation getTextEvaluation() {
        return textEvaluation;
    }

    @Override
    public void addPlan(InvestmentPlan plan) {
        plans.add(plan);
    }

    @Override
    public void removePlan(InvestmentPlan plan) {
        plans.remove(plan);
    }

    @Override
    public List<InvestmentPlan> getInvestmentPlans() {
        return plans;
    }

    @Override
    public void sortPlansByScore() {
        plans.sort(Comparator.comparing(InvestmentPlan::getYieldsRecorder));
    }

    @Override
    public void sortPlansByName() {
        plans.sort(Comparator.comparing(InvestmentPlan::getName, String.CASE_INSENSITIVE_ORDER));
    }

}
