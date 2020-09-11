/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_user;

import iutpj_server.ContestInfo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import newproblem.NewProblem;
import newsubmission.NewSubmission;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

/**
 *
 * @author KAWSAR
 */
public class ContestDashboard extends javax.swing.JFrame {

    /**
     * Creates new form UserDashboard
     */
    private final UserSocket userSocket;
    private File codefile;
    private JFrame parent;
    private SwingController pdfController;
    private JPanel pdfViewerPanel;
    private final ContestInfo contestInfo;
    private final Timer timer;
    private Object[][] problemTableModel, statusTableModel, myStatusTableModel, standingTableModel;
    private HashMap<Map.Entry<Long, Long>, String> standingSorter;

    public ContestDashboard(UserSocket usersocket, JFrame parent, ContestInfo contestInfo) {
        initComponents();
        this.pdfController = new SwingController();
        this.pdfViewerPanel = new SwingViewBuilder(pdfController).buildViewerPanel();
        this.pdfPanel.add(pdfViewerPanel);
        this.userSocket = usersocket;
        this.codefile = null;
        this.parent = parent;
        this.contestInfo = contestInfo;
        this.contestNameText.setText(contestInfo.getContestName());
        this.setterText.setText(contestInfo.getContestSetter());
        this.durationMinutesText.setText(contestInfo.getdurationMinutes());
        this.startTimeText.setText(contestInfo.getStartTime().toString());
        this.NumberOfProblemText.setText("" + contestInfo.getProblemIDs().size());
        this.standingSorter = new HashMap<>();
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTimeText.setText(getRemainingTime());

                remainingTimeTextMySubmission.setText(remainingTimeText.getText());
                remainingTimeTextProblem.setText(remainingTimeText.getText());
                remainingTimeTextProblems.setText(remainingTimeText.getText());
                remainingTimeTextStanding.setText(remainingTimeText.getText());
                remainingTimeTextStatus.setText(remainingTimeText.getText());

            }
        });
        this.timer.start();

        TableCellRenderer cellRenderer = new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();

                cellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                JComponent c = (JComponent) cellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (row < 0) {

                    c.setFont(new Font("Segoe UI", Font.BOLD, 20));
                    c.setBorder(new LineBorder(Color.BLACK, 1, false));
                    return c;
                }
                if (null != table.getClientProperty(table.getColumnName(column)) && value != null) {
                    JButton cd = new JButton();
                    cd.setText(value.toString());
                    cd.setForeground(Color.BLUE);
                    cd.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    cd.setFont(new Font("Segoe UI", Font.BOLD, 20));
                    cd.setBackground((row % 2 == 0 ? new Color(242, 242, 242) : Color.WHITE));
                    cd.setEnabled(true);
                    return (Component) cd;
                }
                c.setFont(new Font("Segoe UI", Font.BOLD, 20));
                c.setBackground((row % 2 == 0 ? new Color(242, 242, 242) : Color.WHITE));
                return c;
            }
        };

        StatusTable.getTableHeader().setDefaultRenderer(cellRenderer);
        StatusTable.setDefaultRenderer(Object.class, cellRenderer);
        StatusTable.putClientProperty("Problem", Color.BLUE);

        problemSetTable.getTableHeader().setDefaultRenderer(cellRenderer);
        problemSetTable.setDefaultRenderer(Object.class, cellRenderer);
        problemSetTable.putClientProperty("Problem Name", Color.BLUE);

        MySubTable.getTableHeader().setDefaultRenderer(cellRenderer);
        MySubTable.setDefaultRenderer(Object.class, cellRenderer);
        MySubTable.putClientProperty("Problem", Color.BLUE);
        MySubTable.putClientProperty("#", Color.BLUE);

        StandingsTable.getTableHeader().setDefaultRenderer(cellRenderer);
        StandingsTable.setDefaultRenderer(Object.class, cellRenderer);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        pdfPanel = new javax.swing.JPanel();
        ContestDashboardDesktopPane = new javax.swing.JDesktopPane();
        ContestDashboardTabSwitcher = new javax.swing.JTabbedPane();
        HomePanel = new javax.swing.JPanel();
        WelcomeLabel = new javax.swing.JLabel();
        leaveContestArenaButton = new javax.swing.JButton();
        setterLabel = new javax.swing.JLabel();
        contestNameLabel = new javax.swing.JLabel();
        contestNameText = new javax.swing.JTextField();
        setterText = new javax.swing.JTextField();
        durationMinutesLabel = new javax.swing.JLabel();
        durationMinutesText = new javax.swing.JTextField();
        numberOfProblemsLabel = new javax.swing.JLabel();
        NumberOfProblemText = new javax.swing.JTextField();
        startTimeLabel = new javax.swing.JLabel();
        startTimeText = new javax.swing.JTextField();
        remainingTimeLabelHome = new javax.swing.JLabel();
        remainingTimeText = new javax.swing.JTextField();
        ProblemsetPanel = new javax.swing.JPanel();
        ProblemSetjScrollPane = new javax.swing.JScrollPane();
        problemSetTable = new javax.swing.JTable();
        remainingTimeLabelStandingsProblems = new javax.swing.JLabel();
        remainingTimeTextProblems = new javax.swing.JTextField();
        problemPanel = new javax.swing.JPanel();
        remainingTimeLabelProblem = new javax.swing.JLabel();
        remainingTimeTextProblem = new javax.swing.JTextField();
        timeLimitLabel = new javax.swing.JLabel();
        memoryLimitText = new javax.swing.JTextField();
        memoryLimitLabel = new javax.swing.JLabel();
        timeLimitText = new javax.swing.JTextField();
        selectProblemLabel = new javax.swing.JLabel();
        selectProblemCombo = new javax.swing.JComboBox<>();
        submitProblemSolution = new javax.swing.JButton();
        SubmitSolPanel = new javax.swing.JPanel();
        ChooseFileLabel = new javax.swing.JLabel();
        ProblemIDLabel = new javax.swing.JLabel();
        LanguageLabel = new javax.swing.JLabel();
        SourceCodeScrollPane = new javax.swing.JScrollPane();
        SourceCodeTextArea = new javax.swing.JTextArea();
        submissionLanguageCombo = new javax.swing.JComboBox();
        SourceCodeLabel = new javax.swing.JLabel();
        ChooseFileButton = new javax.swing.JButton();
        SubmitButton = new javax.swing.JButton();
        selectProblemComboSubmit = new javax.swing.JComboBox<>();
        MySubPanel = new javax.swing.JPanel();
        MySubScrollPane = new javax.swing.JScrollPane();
        MySubTable = new javax.swing.JTable();
        remainingTimeLabelMysubmission = new javax.swing.JLabel();
        remainingTimeTextMySubmission = new javax.swing.JTextField();
        StatusPanel = new javax.swing.JPanel();
        StatusScrollPane = new javax.swing.JScrollPane();
        StatusTable = new javax.swing.JTable();
        remainingTimeLabelStatus = new javax.swing.JLabel();
        remainingTimeTextStatus = new javax.swing.JTextField();
        StandingsPanel = new javax.swing.JPanel();
        StandingsScrollPane = new javax.swing.JScrollPane();
        StandingsTable = new javax.swing.JTable();
        remainingTimeLabelStandings = new javax.swing.JLabel();
        remainingTimeTextStanding = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        pdfPanel.setLayout(new java.awt.BorderLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(0, 181, 204));

        ContestDashboardDesktopPane.setBackground(new java.awt.Color(255, 255, 255));
        ContestDashboardDesktopPane.setLayout(new java.awt.BorderLayout());

        ContestDashboardTabSwitcher.setBackground(new java.awt.Color(255, 255, 255));
        ContestDashboardTabSwitcher.setForeground(new java.awt.Color(54, 33, 89));
        ContestDashboardTabSwitcher.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        ContestDashboardTabSwitcher.setFont(new java.awt.Font("Segoe UI Emoji", 0, 29)); // NOI18N
        ContestDashboardTabSwitcher.setName(""); // NOI18N
        ContestDashboardTabSwitcher.setPreferredSize(new java.awt.Dimension(1024, 576));
        ContestDashboardTabSwitcher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ContestDashboardTabSwitcherMouseClicked(evt);
            }
        });

        HomePanel.setBackground(new java.awt.Color(255, 255, 255));

        WelcomeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        WelcomeLabel.setForeground(new java.awt.Color(54, 33, 89));
        WelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeLabel.setText("Contest Area");

        leaveContestArenaButton.setBackground(new java.awt.Color(54, 33, 89));
        leaveContestArenaButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        leaveContestArenaButton.setForeground(new java.awt.Color(54, 33, 89));
        leaveContestArenaButton.setText("Leave Contest Area");
        leaveContestArenaButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(54, 33, 89)));
        leaveContestArenaButton.setContentAreaFilled(false);
        leaveContestArenaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        leaveContestArenaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveContestArenaButtonActionPerformed(evt);
            }
        });

        setterLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        setterLabel.setForeground(new java.awt.Color(54, 33, 89));
        setterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        setterLabel.setText("Setter:");

        contestNameLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        contestNameLabel.setForeground(new java.awt.Color(54, 33, 89));
        contestNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contestNameLabel.setText("Contest Name:");

        contestNameText.setEditable(false);
        contestNameText.setBackground(new java.awt.Color(255, 255, 255));
        contestNameText.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        contestNameText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        contestNameText.setAutoscrolls(false);
        contestNameText.setFocusable(false);
        contestNameText.setRequestFocusEnabled(false);
        contestNameText.setVerifyInputWhenFocusTarget(false);

        setterText.setEditable(false);
        setterText.setBackground(new java.awt.Color(255, 255, 255));
        setterText.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        setterText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        setterText.setAutoscrolls(false);
        setterText.setFocusable(false);
        setterText.setRequestFocusEnabled(false);
        setterText.setVerifyInputWhenFocusTarget(false);

        durationMinutesLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        durationMinutesLabel.setForeground(new java.awt.Color(54, 33, 89));
        durationMinutesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        durationMinutesLabel.setText("Duration (Minutes):");

        durationMinutesText.setEditable(false);
        durationMinutesText.setBackground(new java.awt.Color(255, 255, 255));
        durationMinutesText.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        durationMinutesText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        durationMinutesText.setAutoscrolls(false);
        durationMinutesText.setFocusable(false);
        durationMinutesText.setRequestFocusEnabled(false);
        durationMinutesText.setVerifyInputWhenFocusTarget(false);

        numberOfProblemsLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        numberOfProblemsLabel.setForeground(new java.awt.Color(54, 33, 89));
        numberOfProblemsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberOfProblemsLabel.setText("Number of Problems:");

        NumberOfProblemText.setEditable(false);
        NumberOfProblemText.setBackground(new java.awt.Color(255, 255, 255));
        NumberOfProblemText.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        NumberOfProblemText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NumberOfProblemText.setAutoscrolls(false);
        NumberOfProblemText.setFocusable(false);
        NumberOfProblemText.setRequestFocusEnabled(false);
        NumberOfProblemText.setVerifyInputWhenFocusTarget(false);

        startTimeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        startTimeLabel.setForeground(new java.awt.Color(54, 33, 89));
        startTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startTimeLabel.setText("Start Time:");

        startTimeText.setEditable(false);
        startTimeText.setBackground(new java.awt.Color(255, 255, 255));
        startTimeText.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        startTimeText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        startTimeText.setAutoscrolls(false);
        startTimeText.setFocusable(false);
        startTimeText.setRequestFocusEnabled(false);
        startTimeText.setVerifyInputWhenFocusTarget(false);

        remainingTimeLabelHome.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        remainingTimeLabelHome.setForeground(new java.awt.Color(54, 33, 89));
        remainingTimeLabelHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelHome.setText("Remaining Time:");

        remainingTimeText.setEditable(false);
        remainingTimeText.setBackground(new java.awt.Color(255, 255, 255));
        remainingTimeText.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        remainingTimeText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeText.setAutoscrolls(false);
        remainingTimeText.setFocusable(false);
        remainingTimeText.setRequestFocusEnabled(false);
        remainingTimeText.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout HomePanelLayout = new javax.swing.GroupLayout(HomePanel);
        HomePanel.setLayout(HomePanelLayout);
        HomePanelLayout.setHorizontalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HomePanelLayout.createSequentialGroup()
                        .addGap(0, 277, Short.MAX_VALUE)
                        .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(leaveContestArenaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(WelcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(287, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomePanelLayout.createSequentialGroup()
                        .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(remainingTimeLabelHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(numberOfProblemsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE)
                            .addComponent(durationMinutesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(setterLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(contestNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(startTimeLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(setterText)
                            .addComponent(contestNameText, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(startTimeText)
                            .addComponent(NumberOfProblemText, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(durationMinutesText, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(remainingTimeText))
                        .addContainerGap())))
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(WelcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contestNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contestNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(setterLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(setterText, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(durationMinutesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(durationMinutesText, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numberOfProblemsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NumberOfProblemText, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startTimeText, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remainingTimeLabelHome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remainingTimeText, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 350, Short.MAX_VALUE)
                .addComponent(leaveContestArenaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ContestDashboardTabSwitcher.addTab("Home", HomePanel);

        ProblemsetPanel.setBackground(new java.awt.Color(255, 255, 255));

        ProblemSetjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        ProblemSetjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        problemSetTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        problemSetTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Problem ID", "Problem Name", "Problem Setter"
            }
        ));
        problemSetTable.setFocusable(false);
        problemSetTable.setGridColor(new java.awt.Color(255, 255, 255));
        problemSetTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        problemSetTable.setMinimumSize(new java.awt.Dimension(0, 0));
        problemSetTable.setOpaque(false);
        problemSetTable.setRequestFocusEnabled(false);
        problemSetTable.setRowHeight(23);
        problemSetTable.setRowSelectionAllowed(false);
        problemSetTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        problemSetTable.setShowHorizontalLines(false);
        problemSetTable.getTableHeader().setReorderingAllowed(false);
        problemSetTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                problemSetTableMouseClicked(evt);
            }
        });
        ProblemSetjScrollPane.setViewportView(problemSetTable);

        remainingTimeLabelStandingsProblems.setBackground(new java.awt.Color(255, 255, 255));
        remainingTimeLabelStandingsProblems.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        remainingTimeLabelStandingsProblems.setForeground(new java.awt.Color(54, 33, 89));
        remainingTimeLabelStandingsProblems.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelStandingsProblems.setText("Remaining Time:");

        remainingTimeTextProblems.setEditable(false);
        remainingTimeTextProblems.setBackground(new java.awt.Color(255, 255, 255));
        remainingTimeTextProblems.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        remainingTimeTextProblems.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeTextProblems.setText(durationMinutesText.getText());
        remainingTimeTextProblems.setAutoscrolls(false);
        remainingTimeTextProblems.setFocusable(false);
        remainingTimeTextProblems.setRequestFocusEnabled(false);
        remainingTimeTextProblems.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout ProblemsetPanelLayout = new javax.swing.GroupLayout(ProblemsetPanel);
        ProblemsetPanel.setLayout(ProblemsetPanelLayout);
        ProblemsetPanelLayout.setHorizontalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addComponent(remainingTimeLabelStandingsProblems, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remainingTimeTextProblems))
        );
        ProblemsetPanelLayout.setVerticalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remainingTimeLabelStandingsProblems, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remainingTimeTextProblems, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ContestDashboardTabSwitcher.addTab("Problemset", ProblemsetPanel);

        problemPanel.setBackground(new java.awt.Color(255, 255, 255));

        remainingTimeLabelProblem.setBackground(new java.awt.Color(255, 255, 255));
        remainingTimeLabelProblem.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        remainingTimeLabelProblem.setForeground(new java.awt.Color(54, 33, 89));
        remainingTimeLabelProblem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelProblem.setText("Remaining Time:");

        remainingTimeTextProblem.setEditable(false);
        remainingTimeTextProblem.setBackground(new java.awt.Color(255, 255, 255));
        remainingTimeTextProblem.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        remainingTimeTextProblem.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeTextProblem.setAutoscrolls(false);
        remainingTimeTextProblem.setFocusable(false);
        remainingTimeTextProblem.setRequestFocusEnabled(false);
        remainingTimeTextProblem.setVerifyInputWhenFocusTarget(false);

        timeLimitLabel.setBackground(new java.awt.Color(255, 255, 255));
        timeLimitLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        timeLimitLabel.setForeground(new java.awt.Color(54, 33, 89));
        timeLimitLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLimitLabel.setText("Time Limit (ms):");

        memoryLimitText.setEditable(false);
        memoryLimitText.setBackground(new java.awt.Color(255, 255, 255));
        memoryLimitText.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        memoryLimitText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        memoryLimitText.setAutoscrolls(false);
        memoryLimitText.setFocusable(false);
        memoryLimitText.setRequestFocusEnabled(false);
        memoryLimitText.setVerifyInputWhenFocusTarget(false);

        memoryLimitLabel.setBackground(new java.awt.Color(255, 255, 255));
        memoryLimitLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        memoryLimitLabel.setForeground(new java.awt.Color(54, 33, 89));
        memoryLimitLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        memoryLimitLabel.setText("Memory Limit:");

        timeLimitText.setEditable(false);
        timeLimitText.setBackground(new java.awt.Color(255, 255, 255));
        timeLimitText.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        timeLimitText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timeLimitText.setAutoscrolls(false);
        timeLimitText.setFocusable(false);
        timeLimitText.setRequestFocusEnabled(false);
        timeLimitText.setVerifyInputWhenFocusTarget(false);

        selectProblemLabel.setBackground(new java.awt.Color(255, 255, 255));
        selectProblemLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        selectProblemLabel.setForeground(new java.awt.Color(54, 33, 89));
        selectProblemLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectProblemLabel.setText("Select Problem:");

        selectProblemCombo.setBackground(new java.awt.Color(240, 240, 240));
        selectProblemCombo.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        selectProblemCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectProblemComboItemStateChanged(evt);
            }
        });

        submitProblemSolution.setBackground(new java.awt.Color(54, 33, 89));
        submitProblemSolution.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        submitProblemSolution.setForeground(new java.awt.Color(54, 33, 89));
        submitProblemSolution.setText("Submit");
        submitProblemSolution.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(54, 33, 89)));
        submitProblemSolution.setContentAreaFilled(false);
        submitProblemSolution.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        submitProblemSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitProblemSolutionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout problemPanelLayout = new javax.swing.GroupLayout(problemPanel);
        problemPanel.setLayout(problemPanelLayout);
        problemPanelLayout.setHorizontalGroup(
            problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(problemPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(selectProblemLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remainingTimeLabelProblem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeLimitLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(problemPanelLayout.createSequentialGroup()
                        .addComponent(timeLimitText, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(memoryLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(memoryLimitText, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(selectProblemCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, problemPanelLayout.createSequentialGroup()
                        .addComponent(remainingTimeTextProblem)
                        .addGap(18, 18, 18)
                        .addComponent(submitProblemSolution, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        problemPanelLayout.setVerticalGroup(
            problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(problemPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remainingTimeLabelProblem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remainingTimeTextProblem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(submitProblemSolution, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectProblemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectProblemCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timeLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(memoryLimitLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(memoryLimitText, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(timeLimitText, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(533, 533, 533))
        );

        ContestDashboardTabSwitcher.addTab("Problem", problemPanel);

        SubmitSolPanel.setBackground(new java.awt.Color(255, 255, 255));

        ChooseFileLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ChooseFileLabel.setForeground(new java.awt.Color(54, 33, 89));
        ChooseFileLabel.setText("Or Choose File:");

        ProblemIDLabel.setBackground(new java.awt.Color(255, 255, 255));
        ProblemIDLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        ProblemIDLabel.setForeground(new java.awt.Color(54, 33, 89));
        ProblemIDLabel.setText("Select Problem:");

        LanguageLabel.setBackground(new java.awt.Color(255, 255, 255));
        LanguageLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        LanguageLabel.setForeground(new java.awt.Color(54, 33, 89));
        LanguageLabel.setText("Language: ");

        SourceCodeTextArea.setColumns(20);
        SourceCodeTextArea.setRows(5);
        SourceCodeScrollPane.setViewportView(SourceCodeTextArea);

        submissionLanguageCombo.setBackground(new java.awt.Color(240, 240, 240));
        submissionLanguageCombo.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        submissionLanguageCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "C", "C++", "Java" }));

        SourceCodeLabel.setBackground(new java.awt.Color(255, 255, 255));
        SourceCodeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SourceCodeLabel.setForeground(new java.awt.Color(54, 33, 89));
        SourceCodeLabel.setText("Source Code:");

        ChooseFileButton.setBackground(new java.awt.Color(255, 255, 255));
        ChooseFileButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ChooseFileButton.setText("Choose File");
        ChooseFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseFileButtonActionPerformed(evt);
            }
        });

        SubmitButton.setBackground(new java.awt.Color(54, 33, 89));
        SubmitButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SubmitButton.setForeground(new java.awt.Color(54, 33, 89));
        SubmitButton.setText("Submit");
        SubmitButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(54, 33, 89)));
        SubmitButton.setContentAreaFilled(false);
        SubmitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitButtonActionPerformed(evt);
            }
        });

        selectProblemComboSubmit.setBackground(new java.awt.Color(240, 240, 240));
        selectProblemComboSubmit.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        selectProblemComboSubmit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectProblemComboSubmitItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout SubmitSolPanelLayout = new javax.swing.GroupLayout(SubmitSolPanel);
        SubmitSolPanel.setLayout(SubmitSolPanelLayout);
        SubmitSolPanelLayout.setHorizontalGroup(
            SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubmitSolPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SubmitSolPanelLayout.createSequentialGroup()
                        .addComponent(ChooseFileLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ChooseFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 367, Short.MAX_VALUE)
                        .addComponent(SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SourceCodeScrollPane)
                    .addGroup(SubmitSolPanelLayout.createSequentialGroup()
                        .addComponent(ProblemIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectProblemComboSubmit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(SubmitSolPanelLayout.createSequentialGroup()
                        .addGroup(SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SourceCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(SubmitSolPanelLayout.createSequentialGroup()
                                .addComponent(LanguageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(submissionLanguageCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        SubmitSolPanelLayout.setVerticalGroup(
            SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubmitSolPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ProblemIDLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(selectProblemComboSubmit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LanguageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(SubmitSolPanelLayout.createSequentialGroup()
                        .addComponent(submissionLanguageCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SourceCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SourceCodeScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ChooseFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SubmitButton)
                    .addComponent(ChooseFileLabel))
                .addGap(43, 43, 43))
        );

        ContestDashboardTabSwitcher.addTab("Submit Solution", SubmitSolPanel);

        MySubPanel.setBackground(new java.awt.Color(255, 255, 255));

        MySubScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        MySubTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        MySubTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "When", "Who", "Problem", "Lang", "Verdict", "Time"
            }
        ));
        MySubTable.setFocusable(false);
        MySubTable.setGridColor(new java.awt.Color(255, 255, 255));
        MySubTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        MySubTable.setOpaque(false);
        MySubTable.setRequestFocusEnabled(false);
        MySubTable.setRowHeight(23);
        MySubTable.setRowSelectionAllowed(false);
        MySubTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        MySubTable.setShowHorizontalLines(false);
        MySubTable.getTableHeader().setReorderingAllowed(false);
        MySubTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MySubTableMouseClicked(evt);
            }
        });
        MySubScrollPane.setViewportView(MySubTable);

        remainingTimeLabelMysubmission.setBackground(new java.awt.Color(255, 255, 255));
        remainingTimeLabelMysubmission.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        remainingTimeLabelMysubmission.setForeground(new java.awt.Color(54, 33, 89));
        remainingTimeLabelMysubmission.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelMysubmission.setText("Remaining Time:");

        remainingTimeTextMySubmission.setEditable(false);
        remainingTimeTextMySubmission.setBackground(new java.awt.Color(255, 255, 255));
        remainingTimeTextMySubmission.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        remainingTimeTextMySubmission.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeTextMySubmission.setAutoscrolls(false);
        remainingTimeTextMySubmission.setFocusable(false);
        remainingTimeTextMySubmission.setRequestFocusEnabled(false);
        remainingTimeTextMySubmission.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout MySubPanelLayout = new javax.swing.GroupLayout(MySubPanel);
        MySubPanel.setLayout(MySubPanelLayout);
        MySubPanelLayout.setHorizontalGroup(
            MySubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MySubPanelLayout.createSequentialGroup()
                .addComponent(remainingTimeLabelMysubmission, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remainingTimeTextMySubmission, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addComponent(MySubScrollPane)
        );
        MySubPanelLayout.setVerticalGroup(
            MySubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MySubPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MySubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MySubPanelLayout.createSequentialGroup()
                        .addComponent(remainingTimeLabelMysubmission, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addComponent(remainingTimeTextMySubmission, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(MySubScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ContestDashboardTabSwitcher.addTab("My Submissions", MySubPanel);

        StatusPanel.setBackground(new java.awt.Color(255, 255, 255));

        StatusScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        StatusTable.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        StatusTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "When", "Who", "Problem", "Lang", "Verdict", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        StatusTable.setFocusable(false);
        StatusTable.setGridColor(new java.awt.Color(255, 255, 255));
        StatusTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        StatusTable.setOpaque(false);
        StatusTable.setRequestFocusEnabled(false);
        StatusTable.setRowHeight(23);
        StatusTable.setRowSelectionAllowed(false);
        StatusTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        StatusTable.getTableHeader().setReorderingAllowed(false);
        StatusTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StatusTableMouseClicked(evt);
            }
        });
        StatusScrollPane.setViewportView(StatusTable);

        remainingTimeLabelStatus.setBackground(new java.awt.Color(255, 255, 255));
        remainingTimeLabelStatus.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        remainingTimeLabelStatus.setForeground(new java.awt.Color(54, 33, 89));
        remainingTimeLabelStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelStatus.setText("Remaining Time:");

        remainingTimeTextStatus.setEditable(false);
        remainingTimeTextStatus.setBackground(new java.awt.Color(255, 255, 255));
        remainingTimeTextStatus.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        remainingTimeTextStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeTextStatus.setAutoscrolls(false);
        remainingTimeTextStatus.setFocusable(false);
        remainingTimeTextStatus.setRequestFocusEnabled(false);
        remainingTimeTextStatus.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout StatusPanelLayout = new javax.swing.GroupLayout(StatusPanel);
        StatusPanel.setLayout(StatusPanelLayout);
        StatusPanelLayout.setHorizontalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StatusPanelLayout.createSequentialGroup()
                .addComponent(remainingTimeLabelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remainingTimeTextStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addComponent(StatusScrollPane)
        );
        StatusPanelLayout.setVerticalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StatusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(remainingTimeLabelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(remainingTimeTextStatus))
                .addGap(11, 11, 11)
                .addComponent(StatusScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        ContestDashboardTabSwitcher.addTab("Status", StatusPanel);

        StandingsPanel.setBackground(new java.awt.Color(255, 255, 255));

        StandingsScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        StandingsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        StandingsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "#", "When", "Who", "Problem", "Lang", "Verdict", "Time"
            }
        ));
        StandingsTable.setFocusable(false);
        StandingsTable.setGridColor(new java.awt.Color(255, 255, 255));
        StandingsTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        StandingsTable.setOpaque(false);
        StandingsTable.setRequestFocusEnabled(false);
        StandingsTable.setRowHeight(23);
        StandingsTable.setRowSelectionAllowed(false);
        StandingsTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        StandingsTable.setShowHorizontalLines(false);
        StandingsTable.getTableHeader().setReorderingAllowed(false);
        StandingsScrollPane.setViewportView(StandingsTable);

        remainingTimeLabelStandings.setBackground(new java.awt.Color(255, 255, 255));
        remainingTimeLabelStandings.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        remainingTimeLabelStandings.setForeground(new java.awt.Color(54, 33, 89));
        remainingTimeLabelStandings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelStandings.setText("Remaining Time:");

        remainingTimeTextStanding.setEditable(false);
        remainingTimeTextStanding.setBackground(new java.awt.Color(255, 255, 255));
        remainingTimeTextStanding.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        remainingTimeTextStanding.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeTextStanding.setAutoscrolls(false);
        remainingTimeTextStanding.setFocusable(false);
        remainingTimeTextStanding.setRequestFocusEnabled(false);
        remainingTimeTextStanding.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout StandingsPanelLayout = new javax.swing.GroupLayout(StandingsPanel);
        StandingsPanel.setLayout(StandingsPanelLayout);
        StandingsPanelLayout.setHorizontalGroup(
            StandingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StandingsPanelLayout.createSequentialGroup()
                .addComponent(remainingTimeLabelStandings, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(remainingTimeTextStanding, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE))
            .addComponent(StandingsScrollPane)
        );
        StandingsPanelLayout.setVerticalGroup(
            StandingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StandingsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(StandingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(remainingTimeTextStanding, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remainingTimeLabelStandings, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StandingsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ContestDashboardTabSwitcher.addTab("Standings", StandingsPanel);

        ContestDashboardDesktopPane.add(ContestDashboardTabSwitcher, java.awt.BorderLayout.CENTER);

        getContentPane().add(ContestDashboardDesktopPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private String getRemainingTime() {
        long durationMiliSecond = Long.parseLong(this.contestInfo.getdurationMinutes()) * 60 * 1000;
        long remainingTime = durationMiliSecond - System.currentTimeMillis() + this.contestInfo.getStartTime().getTime();
        boolean aboutToStart = false;

        if (this.contestInfo.getStartTime().getTime() > System.currentTimeMillis()) {
            remainingTime = this.contestInfo.getStartTime().getTime() - System.currentTimeMillis();
            aboutToStart = true;
        }
        if (remainingTime <= 0) {
            this.timer.stop();
            return "Contest Time is Over";
        }
        long second = Math.floorDiv(remainingTime, 1000);
        long minute = Math.floorDiv(second, 60);
        long hour = Math.floorDiv(minute, 60);
        long day = Math.floorDiv(hour, 64);

        return ((aboutToStart) ? "Starts In: " : "") + String.format("%03d", day) + ":" + String.format("%02d", hour % 24) + ":" + String.format("%02d", minute % 60) + ":" + String.format("%02d", second % 60);
    }

    private void updateProblemSetTab() {
        problemTableModel = userSocket.getContestProblemTable(contestInfo.getContestID());

        if (problemTableModel == null) {
            JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            String[] columns = {"Problem ID", "Problem Name", "Time Limit(ms)", "Memory Limit(kB)"};
            DefaultTableModel tablemodel = new DefaultTableModel(problemTableModel, columns) {
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            problemSetTable.setModel(tablemodel);
        }
        selectProblemCombo.removeAllItems();
        selectProblemComboSubmit.removeAllItems();
        for (Object[] row : problemTableModel) {
            if (row != null) {
                selectProblemCombo.addItem(row[1].toString());
                selectProblemComboSubmit.addItem(row[1].toString());
            }
        }
    }

    private void updateSubmitSolutionTab() {
        if (!this.timer.isRunning()) {
            ContestDashboardTabSwitcher.setSelectedIndex(0);
            JOptionPane.showMessageDialog(null, "Contest is Over", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        updateProblemSetTab();
        ChooseFileButton.setText("Choose file");
        codefile = null;
        SourceCodeTextArea.setText(null);

    }

    private void updateStatusTab() {
        statusTableModel = userSocket.getContestStatusTable(contestInfo.getContestID(), null);
        if (statusTableModel == null) {
            JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] columns = {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"};
            DefaultTableModel tablemodel = new DefaultTableModel(statusTableModel, columns) {
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            StatusTable.setModel(tablemodel);
        }
    }

    private void updateMySubmissionTab() {
        myStatusTableModel = userSocket.getContestStatusTable(contestInfo.getContestID(), "my");
        if (myStatusTableModel == null) {
            JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] columns = {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"};
            DefaultTableModel tablemodel = new DefaultTableModel(myStatusTableModel, columns) {
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            MySubTable.setModel(tablemodel);
        }
    }

    private void computeStandingTableModel() {
        System.out.println("iutpj_user.ContestDashboard.computeStandingTableModel()");
        Object[][] table = userSocket.getContestStandingsRawTable(contestInfo.getContestID());
        if(table==null) return;
        System.out.println("iutpj_user.ContestDashboard.computeStandingTableModel()");
        HashMap<String, HashMap<String, Long>> userPenaltyPerProblem = new HashMap<>();
        for (Object[] row : table) {
            if (userPenaltyPerProblem.get(row[0].toString()) == null) {
                userPenaltyPerProblem.put(row[0].toString(), new HashMap<>());
            }
            if (userPenaltyPerProblem.get(row[0].toString()).get(row[1].toString()) == null) {
                userPenaltyPerProblem.get(row[0].toString()).put(row[1].toString(), Long.parseLong(row[2].toString()));
            } else if (userPenaltyPerProblem.get(row[0].toString()).get(row[1].toString()) < Long.parseLong(row[2].toString())) {
                userPenaltyPerProblem.get(row[0].toString()).put(row[1].toString(), Long.parseLong(row[2].toString()));
            }
        }
        System.out.println("extraction Done");
        List<Object[]> rows = new ArrayList<>();
        Object[] value;
        long contestStartTime = contestInfo.getStartTime().getTime();
         System.out.println(contestStartTime);
        for (Map.Entry<String, HashMap<String, Long>> entry : userPenaltyPerProblem.entrySet()) {
            value = new Object[3];
            value[0] = entry.getKey();
            value[1] = entry.getValue().size();
            long total = 0;
            for (Long penalty : entry.getValue().values()) {
                total += (penalty - contestStartTime) / 1000;
                System.out.println("This "+penalty+" "+contestStartTime);
            }
            value[2] = total;
            System.out.println("user "+ value[0].toString());
            rows.add(value);
        }
        System.out.println("Calculation Done");
        Comparator<Object[]> c = new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Object[] a = (Object[]) o1;
                Object[] b = (Object[]) o2;
                if ((Integer) a[1] == (Integer) b[1]) {
                    return Long.compare((Long) a[2], (Long) b[2]);
                } else {
                    return Long.compare((Integer) b[1], (Integer) a[1]);
                }
            }
        };
        rows.sort(c);
        System.out.println("Sort Done");
        int index = 0;
        standingTableModel = new Object[Math.max(40, rows.size())][4];
        for(Object[] row:rows){
            standingTableModel[index][0]=index+1;
            standingTableModel[index][1]=row[0];
            standingTableModel[index][2]=row[2];
            standingTableModel[index][3]=row[1];
            index++;
        }   
    }

    private void updateStandingTab() {
        computeStandingTableModel();
        if (standingTableModel == null) {
            JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] columns = {"Rank", "User", "Penalty", "Problems Solved"};
            DefaultTableModel tablemodel = new DefaultTableModel(standingTableModel, columns) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            StandingsTable.setModel(tablemodel);
        }
    }
    private void ContestDashboardTabSwitcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContestDashboardTabSwitcherMouseClicked
        if (this.contestInfo.getStartTime().getTime() > System.currentTimeMillis()) {
            ContestDashboardTabSwitcher.setSelectedIndex(0);
            JOptionPane.showMessageDialog(null, "Contest Not Started Yet", "Wait", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int x = ContestDashboardTabSwitcher.getSelectedIndex();
        switch (x) {
            case 0:
                System.out.println("home");
                break;
            case 1:
                updateProblemSetTab();
                break;
            case 2:
                updateProblemSetTab();
                break;
            case 3:
                updateSubmitSolutionTab();
                break;
            case 4:
                updateMySubmissionTab();
                break;
            case 5:
                updateStatusTab();
                break;
            case 6:
                updateStandingTab();
                break;
            default:
                break;

        }
    }//GEN-LAST:event_ContestDashboardTabSwitcherMouseClicked

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButtonActionPerformed

        if (!this.timer.isRunning()) {
            JOptionPane.showMessageDialog(null, "Contest is Over", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int row = selectProblemComboSubmit.getSelectedIndex();
        String problemid = problemSetTable.getValueAt(row, 0).toString();

        String language = (String) submissionLanguageCombo.getSelectedItem();
        if (codefile == null) {
            try {
                codefile = new File("Submission.txt");
                try (FileWriter txtcodewriter = new FileWriter(codefile)) {
                    txtcodewriter.write(SourceCodeTextArea.getText());
                }
            } catch (IOException ex) {
                System.out.println("Source code writing Err: " + ex.getMessage());
            }
        }
        if (codefile == null) {
            JOptionPane.showMessageDialog(null, "No file chosen!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        userSocket.addContestSubmission(codefile, problemid, language, contestInfo.getContestID());
    }//GEN-LAST:event_SubmitButtonActionPerformed

    private void ChooseFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseFileButtonActionPerformed
        JFileChooser filemanager = new JFileChooser("Documents");

        filemanager.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filemanager.addChoosableFileFilter(new FileNameExtensionFilter("C++ Documents", "cpp"));
        filemanager.showOpenDialog(this);
        codefile = filemanager.getSelectedFile();

        if (codefile != null) {
            String language = (String) submissionLanguageCombo.getSelectedItem();
            if (language.equals("C")) {
                language = "c";
            }
            if (language.equals("C++")) {
                language = "cpp";
            }
            if (language.equals("Java")) {
                language = "java";
            }
            String extension = codefile.getName().substring(codefile.getName().lastIndexOf(".") + 1);
            if (extension.toLowerCase().equals(language)) {
                ChooseFileButton.setText(codefile.getName());
            } else {
                JOptionPane.showMessageDialog(null, "Select Correct language or Codefile", "Error", JOptionPane.ERROR_MESSAGE);
                codefile = null;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No file chosen!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ChooseFileButtonActionPerformed

    private void leaveContestArenaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leaveContestArenaButtonActionPerformed
        parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_leaveContestArenaButtonActionPerformed

    private void problemSetTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_problemSetTableMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            evt.consume();
            int row = problemSetTable.rowAtPoint(evt.getPoint());
            int col = problemSetTable.columnAtPoint(evt.getPoint());
            if (row >= 0 && col == 1 && problemSetTable.getValueAt(row, col) != null) {
                selectProblemCombo.setSelectedItem(problemSetTable.getValueAt(row, col).toString());
                ContestDashboardTabSwitcher.setSelectedIndex(2);
            }
        }
    }//GEN-LAST:event_problemSetTableMouseClicked

    private void MySubTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MySubTableMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            evt.consume();
            int row = MySubTable.rowAtPoint(evt.getPoint());
            int col = MySubTable.columnAtPoint(evt.getPoint());
            if (row >= 0 && col == 3 && MySubTable.getValueAt(row, col) != null) {
                if (selectProblemCombo.getItemCount() <= 0) {
                    updateProblemSetTab();
                }
                selectProblemCombo.setSelectedItem(MySubTable.getValueAt(row, col).toString());
                ContestDashboardTabSwitcher.setSelectedIndex(2);
            } else if (row >= 0 && col == 0 && MySubTable.getValueAt(row, col) != null) {
                String submissionid = myStatusTableModel[row][0].toString();
                SubmissionShow subshow = new SubmissionShow();
                subshow.setSubDetailsTable(myStatusTableModel[row]);

                NewSubmission submission = userSocket.getSubmission(submissionid);
                subshow.setSourceCode(submission);

            }
        }
    }//GEN-LAST:event_MySubTableMouseClicked

    private void StatusTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StatusTableMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            evt.consume();
            int row = StatusTable.rowAtPoint(evt.getPoint());
            int col = StatusTable.columnAtPoint(evt.getPoint());
            if (row >= 0 && col == 3 && StatusTable.getValueAt(row, col) != null) {
                if (selectProblemCombo.getItemCount() <= 0) {
                    updateProblemSetTab();
                }
                selectProblemCombo.setSelectedItem(StatusTable.getValueAt(row, col).toString());
                ContestDashboardTabSwitcher.setSelectedIndex(2);
            }
        }
    }//GEN-LAST:event_StatusTableMouseClicked

    private void selectProblemComboSubmitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectProblemComboSubmitItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_selectProblemComboSubmitItemStateChanged

    private void selectProblemComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectProblemComboItemStateChanged

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            int problemRow = selectProblemCombo.getSelectedIndex();
            System.out.println(problemRow);
            String problemID = problemTableModel[problemRow][0].toString();

            NewProblem problem = (this.timer.isRunning()? userSocket.getContestProblem(problemID):userSocket.getProblem(problemID));
            if(problem == null){
                timeLimitText.setText("Problem Locked / Not Found");
                memoryLimitText.setText("Problem Locked / Not Found");
                pdfController.closeDocument();
                return;
            }
            timeLimitText.setText(problem.getTimeLimit());
            memoryLimitText.setText(problem.getMemoryLimit());
            pdfController.openDocument(problem.getProb(), 0, problem.getProb().length, null, null);
            pdfViewerPanel.revalidate();
        }
    }//GEN-LAST:event_selectProblemComboItemStateChanged

    private void submitProblemSolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitProblemSolutionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_submitProblemSolutionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChooseFileButton;
    private javax.swing.JLabel ChooseFileLabel;
    private javax.swing.JDesktopPane ContestDashboardDesktopPane;
    private javax.swing.JTabbedPane ContestDashboardTabSwitcher;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JLabel LanguageLabel;
    private javax.swing.JPanel MySubPanel;
    private javax.swing.JScrollPane MySubScrollPane;
    private javax.swing.JTable MySubTable;
    private javax.swing.JTextField NumberOfProblemText;
    private javax.swing.JLabel ProblemIDLabel;
    private javax.swing.JScrollPane ProblemSetjScrollPane;
    private javax.swing.JPanel ProblemsetPanel;
    private javax.swing.JLabel SourceCodeLabel;
    private javax.swing.JScrollPane SourceCodeScrollPane;
    private javax.swing.JTextArea SourceCodeTextArea;
    private javax.swing.JPanel StandingsPanel;
    private javax.swing.JScrollPane StandingsScrollPane;
    private javax.swing.JTable StandingsTable;
    private javax.swing.JPanel StatusPanel;
    private javax.swing.JScrollPane StatusScrollPane;
    private javax.swing.JTable StatusTable;
    private javax.swing.JButton SubmitButton;
    private javax.swing.JPanel SubmitSolPanel;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JLabel contestNameLabel;
    private javax.swing.JTextField contestNameText;
    private javax.swing.JLabel durationMinutesLabel;
    private javax.swing.JTextField durationMinutesText;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JButton leaveContestArenaButton;
    private javax.swing.JLabel memoryLimitLabel;
    private javax.swing.JTextField memoryLimitText;
    private javax.swing.JLabel numberOfProblemsLabel;
    private javax.swing.JPanel pdfPanel;
    private javax.swing.JPanel problemPanel;
    private javax.swing.JTable problemSetTable;
    private javax.swing.JLabel remainingTimeLabelHome;
    private javax.swing.JLabel remainingTimeLabelMysubmission;
    private javax.swing.JLabel remainingTimeLabelProblem;
    private javax.swing.JLabel remainingTimeLabelStandings;
    private javax.swing.JLabel remainingTimeLabelStandingsProblems;
    private javax.swing.JLabel remainingTimeLabelStatus;
    private javax.swing.JTextField remainingTimeText;
    private javax.swing.JTextField remainingTimeTextMySubmission;
    private javax.swing.JTextField remainingTimeTextProblem;
    private javax.swing.JTextField remainingTimeTextProblems;
    private javax.swing.JTextField remainingTimeTextStanding;
    private javax.swing.JTextField remainingTimeTextStatus;
    private javax.swing.JComboBox<String> selectProblemCombo;
    private javax.swing.JComboBox<String> selectProblemComboSubmit;
    private javax.swing.JLabel selectProblemLabel;
    private javax.swing.JLabel setterLabel;
    private javax.swing.JTextField setterText;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JTextField startTimeText;
    private javax.swing.JComboBox submissionLanguageCombo;
    private javax.swing.JButton submitProblemSolution;
    private javax.swing.JLabel timeLimitLabel;
    private javax.swing.JTextField timeLimitText;
    // End of variables declaration//GEN-END:variables
public static void main(String arg[]) {

        //UIManager.put("Table.background", Color.WHITE);
        //UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 14));
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());

                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignUp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        ContestInfo dummy = new ContestInfo();
        dummy.setContestID("TestID");
        dummy.setContestName("Dummy Contest");
        dummy.setContestSetter("Dummy Setter");
        dummy.setDurationMinutes(Integer.toString(1));
        dummy.setStartTime(new Date(System.currentTimeMillis()));
        ContestDashboard dashboard = new ContestDashboard(null, null, dummy);
        dashboard.setVisible(true);
    }
}
