package com.Model;

/**
 * Text Evaluation will consists of these parts:
 *  1 - defensiveness or aggressiveness of the plan
 *  2 - short or long term orientation
 *  3 - score defense and some passive aggressive finish ;)
 *  logic behind the final text evaluation is mainly based on interest rate, risk factor and the final score
 */

public class PlanTextEvaluation {

    public String textEvaluation(InvestmentPlan plan) {

        String evaluationMessage;

        if (plan.getInterestRatePerAnum() > 11 && plan.getSubjectiveRiskFactor() == 0) {
            return "Hmmmm .... I think you are making fun of me, these kind of yields with no risks ? Get the hell outta here.";
        } else if (plan.getInterestRatePerAnum() < 2 && plan.getSubjectiveRiskFactor() > 6) {
            evaluationMessage = "Aw hell no, why would you even suggest something like this ? ";
        } else if (plan.getInterestRatePerAnum() < 3 && plan.getSubjectiveRiskFactor() < 3) {
            evaluationMessage = "This plan is very defensively oriented, the interest rate is low as well as the expected risk. ";
        } else if (plan.getInterestRatePerAnum() < 6 && plan.getSubjectiveRiskFactor() < 3) {
            evaluationMessage = "This plan is defensively oriented, the interest rate is moderate, yet the expected risk is not perceived as high. ";
        } else if (plan.getInterestRatePerAnum() < 6 && plan.getSubjectiveRiskFactor() < 6) {
            evaluationMessage = "We could say this plan is balanced in its interest rate and perceived risk. ";
        } else if (plan.getInterestRatePerAnum() < 6 && plan.getSubjectiveRiskFactor() > 5) {
            evaluationMessage = "This plan is pretty defensive yet with high risk, i would not suggest such an investment ... But I´m just a doubtful algorithm, so what do i know ? ";
        } else if (plan.getInterestRatePerAnum() < 12 && plan.getSubjectiveRiskFactor() < 6 ) {
            evaluationMessage = "This plan is well balanced, the risk is perceived as low against this high yield ... not bad, not bad. ";
        } else if (plan.getInterestRatePerAnum() < 12 && plan.getSubjectiveRiskFactor() > 5) {
            evaluationMessage = "this plan is more aggressively oriented, the interest is pretty high, but the perceived risk as well. ";
        } else if (plan.getInterestRatePerAnum() > 11 && plan.getSubjectiveRiskFactor() < 6) {
            evaluationMessage = "Well, this plan yields really high interest rate at the low perceived risk, either you made mistake at evaluation or you met the chance of a lifetime. ";
        } else {
            evaluationMessage = "This is very aggressive plan, high yield with high risk potential, you should know the business in which you are going to invest if you want to proceed ... just a friendly note. ";
        }


        double longTermIndex = (plan.getYieldsRecorder().getYearFifteenYield() / plan.getMoneyAmount()) - (plan.getYieldsRecorder().getYearThreeYield() / plan.getMoneyAmount());

        if (longTermIndex < 0.8 && !plan.isCompoundInterest()) {
            evaluationMessage += "Your longer term yields are not that high, as it is more conservative strategy. You could try compounding if it is possible. ";
        } else if (longTermIndex < 0.8 && plan.isCompoundInterest()) {
            evaluationMessage += "Even with compound effect this strategy is not yielding that much in the long term, it could work as a conservative strategy for keeping money out of harms way, but that would be all. ";
        } else if (!plan.isCompoundInterest()) {
            evaluationMessage += "As a long term investment it has pretty high yields, yet you could consider using compound effect, it would get you much further. ";
        } else {
            evaluationMessage += "nice yields man (or woman, i know, it´s 2020), if you are happy with level of risk, its your call. ";
        }

        double score = plan.getYieldsRecorder().getScore();

        if (score >= 8) {
            evaluationMessage += "What do you want to hear more ? score " + score + " is pretty damn high according to my standards, get that money flowing ... Maybe one last thing, i forgot to take taxes into consideration, silly me ... if the financial office will ask something, you don´t know me, okay ? thx ";
        } else if (score >= 6) {
            evaluationMessage += "Overall not bad investment, with score of " + score + " you are set for a good start, go tell your partner you are going to invest your hard earned money according to a suggestion of a cheap algorithm ... have fun!";
        } else if (score >= 4) {
            evaluationMessage += "Well, score of " + score + " is not that bad. as your first investment we can consider it excusable ... Otherwise, I was programmed for people with IQ higher than turned off automatic washing machine. No offense ofc";
        } else if (score >= 2) {
            evaluationMessage += "Score of " + score + " ?? Where would you get such an investment ? Even my cable management has more logic than this plan.";
        } else {
            evaluationMessage += "I really don´t know what to say ... It was thought impossible to get score of " + score + ". If it´s a joke, it´s a bad one. If you really mean it, then rather try slot machines, it would suit you more than investing.";
        }

        return evaluationMessage;
    }

}
