package com.View;

import com.Model.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 *  Main window of the application which will show side bar with plans and action buttons, right part is to show the details of the selected plan from the side bar
 *  body is made of BorderBox, both left and center part are then inserted with Box layout and in most cases, in each of them inserted another panel to show labels and fields next to each other
 *  After building the window, action listeners are set to listen for change in selection to immediately show the details of the selected plan and to sort plans after changing the order
 *  I made saving and loading automatic, so there are no in-app options to save and load them
 */

public class InvestmentPlansWindow extends JFrame {

    private String[] orderOptions = { "alphabetically" , "score"};

    private EvidenceImpl evidence = new EvidenceImpl();
    private PersistenceManager persistenceManager = new SerializationPersistenceManager("data.ser");
    DefaultListModel<InvestmentPlan> listModel = new DefaultListModel<>();
    JList<InvestmentPlan> plansList = new JList<>(listModel);;
    private JTextField tfName = new JTextField(35);
    private JTextField tfScore = new JTextField(3);
    private JTextField tfAmount = new JTextField(10);
    private JTextField tfInterestRate = new JTextField(5);
    private JTextField tfSubjectiveRisk = new JTextField(3);
    private JTextField tfInterestRatesPerAnum = new JTextField( 10);
    private JTextField tfCompoundInterest =  new JTextField(3);
    private JTextArea tfTextEvaluation = new JTextArea(8,1);
    private DefaultTableModel tableModel = new DefaultTableModel(7, 5);
    private JTable yieldsTable = new JTable(tableModel);
    private JComboBox<String> cbOrdering = new JComboBox<>(orderOptions);

    public InvestmentPlansWindow() {
        super("Investment Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGui();
    }

    private void initGui() {

        createPlansSideBar();
        if (Files.exists(Paths.get(persistenceManager.getFileName()))) {
            loadPlans();
            sortPlans();
        }
        createPlansDetailPart();
        pack();
    }

    private void createPlansDetailPart() {

        createBasicLayout();

        JPanel planDetail = new JPanel();
        planDetail.setLayout(new BoxLayout(planDetail, BoxLayout.Y_AXIS));

        plansList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                if (plansList.getSelectedValue() == null) { return; }
                InvestmentPlan p = plansList.getSelectedValue();
                tfName.setText(p.getName());
                tfScore.setText(Double.toString(p.getYieldsRecorder().getScore()));
                tfAmount.setText(fmtZeroDigit(p.getMoneyAmount()));
                tfInterestRate.setText(Double.toString(p.getInterestRatePerAnum()));
                int i = p.getPerAnumInterests();
                if (i == 1) {
                    tfInterestRatesPerAnum.setText("annually");
                } else if (i == 2) {
                    tfInterestRatesPerAnum.setText("semi-annually");
                } else if (i == 4) {
                    tfInterestRatesPerAnum.setText("quarterly");
                }
                tfSubjectiveRisk.setText(fmtZeroDigit(p.getSubjectiveRiskFactor()));
                if (p.isCompoundInterest()) {
                    tfCompoundInterest.setText("Yes");
                } else {
                    tfCompoundInterest.setText("No");
                }
                fillTable(p);
                tfTextEvaluation.setWrapStyleWord(true);
                tfTextEvaluation.setText(evidence.getTextEvaluation().textEvaluation(p));
            }

            private void fillTable(InvestmentPlan p) {
                YieldsRecorder r = p.getYieldsRecorder();
                String[][] data = { { "Year 1", fmtZeroDigit(r.getYearOneYield()), fmtPercentOneDigit(r.getYearOneYield()/p.getMoneyAmount()), fmtZeroDigit(r.getYearOneYield()*r.getBadScenarioIndex()), fmtPercentOneDigit((r.getYearOneYield()*r.getBadScenarioIndex())/p.getMoneyAmount()) },
                        { "Year 3", fmtZeroDigit(r.getYearThreeYield()), fmtPercentOneDigit(r.getYearThreeYield()/p.getMoneyAmount()), fmtZeroDigit(r.getYearThreeYield()*r.getBadScenarioIndex()), fmtPercentOneDigit((r.getYearThreeYield()*r.getBadScenarioIndex())/p.getMoneyAmount()) },
                        { "Year 5", fmtZeroDigit(r.getYearFiveYield()), fmtPercentOneDigit(r.getYearFiveYield()/p.getMoneyAmount()), fmtZeroDigit(r.getYearFiveYield()*r.getBadScenarioIndex()), fmtPercentOneDigit((r.getYearFiveYield()*r.getBadScenarioIndex())/p.getMoneyAmount()) },
                        { "Year 10", fmtZeroDigit(r.getYearTenYield()), fmtPercentOneDigit(r.getYearTenYield()/p.getMoneyAmount()), fmtZeroDigit(r.getYearTenYield()*r.getBadScenarioIndex()), fmtPercentOneDigit((r.getYearTenYield()*r.getBadScenarioIndex())/p.getMoneyAmount()) },
                        { "Year 15", fmtZeroDigit(r.getYearFifteenYield()), fmtPercentOneDigit(r.getYearFifteenYield()/p.getMoneyAmount()), fmtZeroDigit(r.getYearFifteenYield()*r.getBadScenarioIndex()), fmtPercentOneDigit((r.getYearFifteenYield()*r.getBadScenarioIndex())/p.getMoneyAmount()) },
                        { "Year 20", fmtZeroDigit(r.getYearTwentyYield()), fmtPercentOneDigit(r.getYearTwentyYield()/p.getMoneyAmount()), fmtZeroDigit(r.getYearTwentyYield()*r.getBadScenarioIndex()), fmtPercentOneDigit((r.getYearTwentyYield()*r.getBadScenarioIndex())/p.getMoneyAmount()) },
                 };

                String[] columns = { "", "Expected yields", "%", "Bad scenario yields", "%"};

                tableModel.setDataVector(data, columns);
            }
        });

    }

    private void createBasicLayout() {
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));
        JPanel pnlOne = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labName = new JLabel("Name: ");
        tfName.setEditable(false);
        pnlOne.add(labName);
        pnlOne.add(tfName);

        JPanel pnlTwo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labScore = new JLabel("Score: ");
        tfScore.setEditable(false);
        pnlTwo.add(labScore);
        pnlTwo.add(tfScore);

        JPanel pnlThree = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labAmount = new JLabel("Money amount: ");
        tfAmount.setEditable(false);
        pnlThree.add(labAmount);
        pnlThree.add(tfAmount);

        JPanel pnlFour = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labInterestRate = new JLabel("Interest rate p.a.: ");
        JLabel percentageSymbol = new JLabel("%");
        tfInterestRate.setEditable(false);
        pnlFour.add(labInterestRate);
        pnlFour.add(tfInterestRate);
        pnlFour.add(percentageSymbol);

        JPanel pnlFive = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbCompoundInterest = new JLabel("Compound interest: ");
        tfCompoundInterest.setEditable(false);
        pnlFive.add(lbCompoundInterest);
        pnlFive.add(tfCompoundInterest);

        JPanel pnlSix = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labInterestRateTimeFrames = new JLabel("interest payment time frame: ");
        tfInterestRatesPerAnum.setEditable(false);
        pnlSix.add(labInterestRateTimeFrames);
        pnlSix.add(tfInterestRatesPerAnum);

        JPanel pnlSeven = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel labRiskFactorSlider = new JLabel("subjective risk factor: ");
        tfSubjectiveRisk.setEditable(false);
        pnlSeven.add(labRiskFactorSlider);
        pnlSeven.add(tfSubjectiveRisk);

        JScrollPane tableWrapper = new JScrollPane();
        tableWrapper.setViewportView(yieldsTable);
        tableWrapper.setPreferredSize(new Dimension(600, yieldsTable.getRowHeight()*8+1));
        tfTextEvaluation.setEditable(false);
        tfTextEvaluation.setLineWrap(true);

        centralPanel.add(pnlOne, BorderLayout.CENTER);
        centralPanel.add(pnlTwo, BorderLayout.CENTER);
        centralPanel.add(pnlThree, BorderLayout.CENTER);
        centralPanel.add(pnlFour, BorderLayout.CENTER);
        centralPanel.add(pnlFive, BorderLayout.CENTER);
        centralPanel.add(pnlSix, BorderLayout.CENTER);
        centralPanel.add(pnlSeven, BorderLayout.CENTER);
        centralPanel.add(Box.createRigidArea(new Dimension(0,20)));
        centralPanel.add(tableWrapper, BorderLayout.CENTER);
        centralPanel.add(Box.createRigidArea(new Dimension(0,20)));
        centralPanel.add(tfTextEvaluation, BorderLayout.CENTER);
        centralPanel.add(Box.createRigidArea(new Dimension(0,20)));

        add(centralPanel, BorderLayout.CENTER);
    }

    private void createPlansSideBar () {
        JPanel plansPanel = new JPanel();
        plansPanel.setLayout(new BoxLayout(plansPanel, BoxLayout.Y_AXIS));

        JLabel lbPlans = new JLabel("Investment plans:");

        JLabel lbOrdering = new JLabel("  Order by:");
        JPanel orderComboBoxWrapper = new JPanel();
        orderComboBoxWrapper.setMaximumSize(new Dimension(150, 40));
        leftJustify(cbOrdering);
        orderComboBoxWrapper.add(cbOrdering);
        cbOrdering.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                sortPlans();
            }
        });

        plansList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        plansList.setFixedCellWidth(280);
        plansList.setCellRenderer(new InvestmentPlanRenderer());

        JButton btnAddPlan = new JButton("Add plan");
        btnAddPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PlanDetailWindow planDetail = new PlanDetailWindow(InvestmentPlansWindow.this);
                InvestmentPlan plan = planDetail.createNewPlan();
                if (plan != null) {
                    evidence.addPlan(plan);
                    sortPlans();
                    savePlans();
                } else {
                    System.out.println("Something went wrong, plan could not be added");
                }
            }
        });

        JButton btnEditPlan = new JButton("Edit plan");
        btnEditPlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                InvestmentPlan p = plansList.getSelectedValue();
                if (p != null) {
                    PlanDetailWindow planDetail = new PlanDetailWindow(InvestmentPlansWindow.this);
                    InvestmentPlan newPlan = planDetail.editPlan(p);
                    if (newPlan == null) { return; }
                    evidence.removePlan(p);
                    evidence.addPlan(newPlan);
                    sortPlans();
                    savePlans();
                    plansList.setSelectedIndex(listModel.getSize());
                } else {
                    showMessageDialog(null, "You did not select any plan to edit");
                }
            }
        });

        JButton btnRemovePlan = new JButton("Remove plan");
        btnRemovePlan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                InvestmentPlan p = plansList.getSelectedValue();
                if (p != null) {
                    int confirmDialogResult = JOptionPane.showConfirmDialog(null, "Do you really want to delete this plan ?", "Delete confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirmDialogResult == 0) {
                        evidence.removePlan(p);
                        listModel.remove(plansList.getSelectedIndex());
                        savePlans();
                        clearDetailOfPlan();
                        }
                } else {
                    showMessageDialog(null, "You did not select any plan to delete");
                }
            }
        });

        plansPanel.add(Box.createRigidArea(new Dimension(0,20)));
        plansPanel.add(leftJustify(lbPlans));
        plansPanel.add(Box.createRigidArea(new Dimension(0,20)));
        plansPanel.add(leftJustify(lbOrdering));
        plansPanel.add(leftJustify(orderComboBoxWrapper));
        plansPanel.add(Box.createRigidArea(new Dimension(0,10)));
        plansPanel.add(leftJustify(plansList));
        plansPanel.add(Box.createRigidArea(new Dimension(0,10)));
        plansPanel.add(leftJustify(btnAddPlan));
        plansPanel.add(Box.createRigidArea(new Dimension(0,10)));
        plansPanel.add(leftJustify(btnEditPlan));
        plansPanel.add(Box.createRigidArea(new Dimension(0,10)));
        plansPanel.add(leftJustify(btnRemovePlan));
        plansPanel.add(Box.createRigidArea(new Dimension(0,100)));

        add(plansPanel, BorderLayout.WEST);
        }

        private void loadPlans() {
            try {
                evidence = (EvidenceImpl) persistenceManager.loadAll();
            } catch (PersistenceException e) {
                e.printStackTrace();
            }
            listModel.clear();
            for (InvestmentPlan p : evidence.getInvestmentPlans()) {
                listModel.addElement(p);
            }
            actualizeListModelPlans();
        }

        private void savePlans() {
            try {
                persistenceManager.saveAll(evidence);
            } catch (PersistenceException e) {
                e.printStackTrace();
            }
        }

        private void sortPlans() {
            if (cbOrdering.getSelectedItem() == "alphabetically") {
                evidence.sortPlansByName();
                actualizeListModelPlans();
            } else {
                evidence.sortPlansByScore();
                actualizeListModelPlans();
            }
        }

        private void actualizeListModelPlans() {
            listModel.clear();
            for (InvestmentPlan p : evidence.getInvestmentPlans()) {
                listModel.addElement(p);
            }
        }

        private void clearDetailOfPlan() {
            tfName.setText("");
            tfAmount.setText("");
            tfCompoundInterest.setText("");
            tfInterestRate.setText("");;
            tfInterestRatesPerAnum.setText("");
            tfTextEvaluation.setText("");
            tfScore.setText("");
            tfSubjectiveRisk.setText("");
            tableModel.setRowCount(0);
        }

        private Component leftJustify( Component panel )  {
            Box  b = Box.createHorizontalBox();
            b.add( panel );
            b.add( Box.createHorizontalGlue() );
            return b;
    }

        private String fmtZeroDigit(double d) {
        int no = (int)d;
        return String.format("%,d", no);
    }

        private String fmtPercentOneDigit(double d) {
        d *= 100;
        return String.format("%.1f", d) + "%";
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InvestmentPlansWindow().setVisible(true);
            }
        });
    }
}
