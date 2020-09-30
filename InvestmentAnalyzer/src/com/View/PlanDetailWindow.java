package com.View;

import com.Model.InvestmentPlan;
import com.Model.PlanAnalyzer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * investment window is made into Grid layout, with 6 rows (first and last is dummy-filled or empty for margin purposes)
 * each grid part then is filled with flow-layout part with labels and input parts to make logical units
 * an Instance of PlanAnalyzer is created and used to analyze and create/update plans yieldsRecorder
 */

public class PlanDetailWindow extends JDialog {

    private String[] interestRates = {"annually", "semi-annually", "quarterly"};

    private JTextField tfName = new JTextField(25);
    private JTextField tfAmount = new JTextField(10);
    private JTextField tfInterestRate = new JTextField(5);
    private JCheckBox cbCompoundInterest =  new JCheckBox("Compound Interest");
    private JComboBox jcPerAnumInterests = new JComboBox(interestRates);
    private JSlider slRiskFactor = new JSlider(JSlider.HORIZONTAL ,0, 10, 5);
    private JButton btnBack;
    private JButton btnOk;
    private PlanAnalyzer analyzer = new PlanAnalyzer();
    private boolean planOk = false;


    public PlanDetailWindow(Frame owner) {
        super(owner, "plan details", true);

        initGui();
    }

    private void initGui() {

        JPanel pnlOne = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labName = new JLabel("Name");
        labName.setLabelFor(tfName);
        labName.setDisplayedMnemonic('N');;
        pnlOne.add(labName);
        pnlOne.add(tfName);

        JPanel pnlTwo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labAmount = new JLabel("Money amount");
        labAmount.setLabelFor(tfAmount);
        labAmount.setDisplayedMnemonic('A');;
        pnlTwo.add(labAmount);
        pnlTwo.add(tfAmount);

        JPanel pnlThree = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labInterestRate = new JLabel("Interest rate p.a.");
        labName.setLabelFor(tfInterestRate);
        labName.setDisplayedMnemonic('I');
        JLabel percentageSymbol = new JLabel("%");
        pnlThree.add(labInterestRate);
        pnlThree.add(tfInterestRate);
        pnlThree.add(percentageSymbol);

        JPanel pnlFour = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlFour.add(cbCompoundInterest);

        JPanel pnlFive = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labInterestRateTimeFrames = new JLabel("interest payment time frame");
        labInterestRateTimeFrames.setLabelFor(jcPerAnumInterests);
        pnlFive.add(labInterestRateTimeFrames);
        pnlFive.add(jcPerAnumInterests);

        JPanel pnlSix = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel labRiskFactorSlider = new JLabel("subjective risk factor");
        labRiskFactorSlider.setLabelFor(slRiskFactor);
        slRiskFactor.setMinorTickSpacing(1);
        slRiskFactor.setMajorTickSpacing(1);
        slRiskFactor.setPaintTicks(true);
        slRiskFactor.setPaintLabels(true);
        pnlSix.add(labRiskFactorSlider);
        pnlSix.add(slRiskFactor);

        setLayout(new GridLayout(6,2, 30, 10));

        JLabel dummyLabelForGridMargins = new JLabel("");
        JLabel dummyLabelForGridMargins2 = new JLabel("");

        add(dummyLabelForGridMargins);
        add(dummyLabelForGridMargins2);
        add(pnlOne);
        add(pnlTwo);
        add(pnlThree);
        add(pnlFour);
        add(pnlFive);
        add(pnlSix);

        btnOk = new JButton("OK");
        btnBack = new JButton("Back");

        ActionListener btnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (planOk = actionEvent.getSource() == btnOk) {
                    if (checkTextFieldsTypeValidity()) {
                        setVisible(false);
                    }
                } else {
                    setVisible(false);
                }
             }
        };

        btnOk.addActionListener(btnListener);
        btnBack.addActionListener(btnListener);

        getRootPane().setDefaultButton(btnOk);

        add(btnOk);
        add(btnBack);

        pack();
    }

    public InvestmentPlan createNewPlan() {
        setVisible(true);

        if (planOk) {
            int interestsPerAnnum;
            switch (String.valueOf(jcPerAnumInterests.getSelectedItem())) {
                case "annually": interestsPerAnnum = 1; break;
                case "semi-annually" : interestsPerAnnum = 2; break;
                case "quarterly" : interestsPerAnnum = 4; break;
                default:
                    throw new IllegalStateException("Unexpected value: " + jcPerAnumInterests.getSelectedItem());
            }
            InvestmentPlan p = new InvestmentPlan(tfName.getText(), Double.parseDouble(tfInterestRate.getText()), slRiskFactor.getValue(), Integer.parseInt(tfAmount.getText().replaceAll(" ", "")),
                    interestsPerAnnum, cbCompoundInterest.isSelected());
            innerPlanAnalyze(p);
            return p;
        } else {
            return null;
        }
    }

    public InvestmentPlan editPlan(InvestmentPlan plan) {
        tfName.setText(plan.getName());
        tfInterestRate.setText(String.valueOf(plan.getInterestRatePerAnum()));
        slRiskFactor.setValue((int) plan.getSubjectiveRiskFactor());
        tfAmount.setText(String.valueOf(plan.getMoneyAmount()));
        if (plan.isCompoundInterest()) {
            cbCompoundInterest.setSelected(true);
        } else {
            cbCompoundInterest.setSelected(false);
        }

        switch (plan.getPerAnumInterests()) {
            case 1:
                jcPerAnumInterests.setSelectedItem("annually");
                break;
            case 2:
                jcPerAnumInterests.setSelectedItem("semi-annually");
                break;
            case 4:
                jcPerAnumInterests.setSelectedItem("quarterly");
                break;
        }

        setVisible(true);

            if (planOk) {

                int interestsPerAnnum;
                switch (String.valueOf(jcPerAnumInterests.getSelectedItem())) {
                    case "annually":
                        interestsPerAnnum = 1;
                        break;
                    case "semi-annually":
                        interestsPerAnnum = 2;
                        break;
                    case "quarterly":
                        interestsPerAnnum = 4;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + jcPerAnumInterests.getSelectedItem());
                }
                InvestmentPlan p = new InvestmentPlan(tfName.getText(), Double.parseDouble(tfInterestRate.getText()), slRiskFactor.getValue(), Integer.parseInt(tfAmount.getText().replaceAll(" ", "")),
                        interestsPerAnnum, cbCompoundInterest.isSelected());
                innerPlanAnalyze(p);
                return p;
            } else {
                return null;
            }
    }

    private void innerPlanAnalyze (InvestmentPlan p) {
        analyzer.analyzePlan(p.getYieldsRecorder(), p.getInterestRatePerAnum(), p.getSubjectiveRiskFactor(), p.getMoneyAmount(), p.getPerAnumInterests(), p.isCompoundInterest());
    }

    private boolean checkTextFieldsTypeValidity() {
        try {
            tfAmount.setText(tfAmount.getText().replaceAll(" ", ""));
            Integer.parseInt(tfAmount.getText());
            tfInterestRate.setText(tfInterestRate.getText().replace(",", "."));
            Double.parseDouble(tfInterestRate.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Please check your Amount field for a valid integer and Interest rate for a valid decimal number");
            return false;
        }
        if (tfName.getText().length() < 1) {
            JOptionPane.showMessageDialog(new JFrame(), "Please enter the name of the plan");
            return false;
        }
        return true;
    }

}
