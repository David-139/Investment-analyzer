package com.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Renderer to show name and score of the InvestmentPlan in 1 field
 */

public class InvestmentPlanRenderer extends JLabel implements ListCellRenderer<InvestmentPlan> {

    @Override
    public Component getListCellRendererComponent(JList<? extends InvestmentPlan> list, InvestmentPlan plan, int i, boolean isSelected, boolean cellHasFocus) {
            String finalText;
            if (plan.getName().length() > 30) {
                finalText = plan.getName().substring(0, 26) + "...";
            } else {
                finalText = plan.getName();
            }
            finalText = finalText.concat("    - score: " + plan.getYieldsRecorder().getScore());
            setText(finalText);
            setOpaque(true);

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }

}
