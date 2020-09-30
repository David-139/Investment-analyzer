package com.Model;

import java.util.List;

/*
*  This Interface will take care of adding, sorting and removing our investment plans as well as hold instance of analyzer class for analyzing them
*/

public interface Evidence {

    void addPlan(InvestmentPlan plan);
    void removePlan(InvestmentPlan plan);
    List<InvestmentPlan> getInvestmentPlans();
    void sortPlansByScore();
    void sortPlansByName();
}
