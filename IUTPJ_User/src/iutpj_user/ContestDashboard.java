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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer.UIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import newproblem.NewProblem;
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
    private final UserSocket usersocket;
    private File codefile;
    private JFrame parent;
    private SwingController pdfController;
    private JPanel pdfViewerPanel;
    private final ContestInfo contestInfo;
    private final Timer timer;

    public ContestDashboard(UserSocket usersocket, JFrame parent, ContestInfo contestInfo) {
        initComponents();
        this.pdfController = new SwingController();
        pdfViewerPanel = new SwingViewBuilder(pdfController).buildViewerPanel();
        pdfPanel.add(pdfViewerPanel);

        this.usersocket = usersocket;
        this.codefile = null;
        this.parent = parent;
        this.contestInfo = contestInfo;
        this.contestNameText.setText(contestInfo.getContestName());
        this.setterText.setText(contestInfo.getContestSetter());
        this.durationMinutesText.setText(contestInfo.getdurationMinutes());
        this.startTimeText.setText(contestInfo.getStartTime().toString());
        this.NumberOfProblemText.setText("" + contestInfo.getProblemIDs().size());
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
                    
                    c.setFont(new Font("Segoe UI", Font.BOLD, 16));
                    c.setBorder(new LineBorder(Color.BLACK, 1, false));
                    c.setBackground(Color.green);
                    return c;
                }
                if (null != table.getClientProperty(table.getColumnName(column))&&value!=null) {
                    JButton cd = new JButton();
                    cd.setText(value.toString());
                    cd.setForeground(Color.BLUE);
                    cd.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    cd.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    cd.setBackground((row % 2 == 0 ? new Color(242, 242, 189) : Color.WHITE));
                    cd.setEnabled(true);
                    return (Component)cd;
                }
                c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                c.setBackground((row % 2 == 0 ? new Color(242, 242, 189) : Color.WHITE));
                return c;
            }
        };

        StatusTable.getTableHeader().setDefaultRenderer(cellRenderer);
        StatusTable.setDefaultRenderer(Object.class, cellRenderer);
        StatusTable.putClientProperty("Problem", Color.BLUE);

        ProblemSetTable.getTableHeader().setDefaultRenderer(cellRenderer);
        ProblemSetTable.setDefaultRenderer(Object.class, cellRenderer);
        ProblemSetTable.putClientProperty("Problem Name", Color.BLUE);

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
        ProblemSetTable = new javax.swing.JTable();
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
        pdfPanel = new javax.swing.JPanel();
        submitProblemSolution = new javax.swing.JButton();
        SubmitSolPanel = new javax.swing.JPanel();
        ChooseFileLabel = new javax.swing.JLabel();
        txtProblemID = new javax.swing.JTextField();
        ProblemIDLabel = new javax.swing.JLabel();
        LanguageLabel = new javax.swing.JLabel();
        SourceCodeScrollPane = new javax.swing.JScrollPane();
        SourceCodeTextArea = new javax.swing.JTextArea();
        submissionLanguageCombo = new javax.swing.JComboBox();
        SourceCodeLabel = new javax.swing.JLabel();
        ChooseFileButton = new javax.swing.JButton();
        SubmitButton = new javax.swing.JButton();
        StatusPanel = new javax.swing.JPanel();
        StatusScrollPane = new javax.swing.JScrollPane();
        StatusTable = new javax.swing.JTable();
        remainingTimeLabelStatus = new javax.swing.JLabel();
        remainingTimeTextStatus = new javax.swing.JTextField();
        MySubPanel = new javax.swing.JPanel();
        MySubScrollPane = new javax.swing.JScrollPane();
        MySubTable = new javax.swing.JTable();
        remainingTimeLabelMysubmission = new javax.swing.JLabel();
        remainingTimeTextMySubmission = new javax.swing.JTextField();
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
        WelcomeLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 255), 5, true));

        leaveContestArenaButton.setBackground(new java.awt.Color(54, 33, 89));
        leaveContestArenaButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        leaveContestArenaButton.setForeground(new java.awt.Color(54, 33, 89));
        leaveContestArenaButton.setText("Leave Contest Arena");
        leaveContestArenaButton.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 255), 2, true), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        leaveContestArenaButton.setContentAreaFilled(false);
        leaveContestArenaButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        leaveContestArenaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leaveContestArenaButtonActionPerformed(evt);
            }
        });

        setterLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        setterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        setterLabel.setText("Setter");
        setterLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        contestNameLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        contestNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contestNameLabel.setText("Contest Name");
        contestNameLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        contestNameText.setEditable(false);
        contestNameText.setBackground(new java.awt.Color(204, 255, 255));
        contestNameText.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        contestNameText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        contestNameText.setAutoscrolls(false);
        contestNameText.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        contestNameText.setFocusable(false);
        contestNameText.setRequestFocusEnabled(false);
        contestNameText.setVerifyInputWhenFocusTarget(false);

        setterText.setEditable(false);
        setterText.setBackground(new java.awt.Color(204, 255, 255));
        setterText.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        setterText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        setterText.setAutoscrolls(false);
        setterText.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        setterText.setFocusable(false);
        setterText.setRequestFocusEnabled(false);
        setterText.setVerifyInputWhenFocusTarget(false);

        durationMinutesLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        durationMinutesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        durationMinutesLabel.setText("Duration Minutes");
        durationMinutesLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        durationMinutesText.setEditable(false);
        durationMinutesText.setBackground(new java.awt.Color(204, 255, 255));
        durationMinutesText.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        durationMinutesText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        durationMinutesText.setAutoscrolls(false);
        durationMinutesText.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        durationMinutesText.setFocusable(false);
        durationMinutesText.setRequestFocusEnabled(false);
        durationMinutesText.setVerifyInputWhenFocusTarget(false);

        numberOfProblemsLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        numberOfProblemsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        numberOfProblemsLabel.setText("Number Of problems");
        numberOfProblemsLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        NumberOfProblemText.setEditable(false);
        NumberOfProblemText.setBackground(new java.awt.Color(204, 255, 255));
        NumberOfProblemText.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        NumberOfProblemText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        NumberOfProblemText.setAutoscrolls(false);
        NumberOfProblemText.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        NumberOfProblemText.setFocusable(false);
        NumberOfProblemText.setRequestFocusEnabled(false);
        NumberOfProblemText.setVerifyInputWhenFocusTarget(false);

        startTimeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        startTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startTimeLabel.setText("Start Time");
        startTimeLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        startTimeText.setEditable(false);
        startTimeText.setBackground(new java.awt.Color(204, 255, 255));
        startTimeText.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        startTimeText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        startTimeText.setAutoscrolls(false);
        startTimeText.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        startTimeText.setFocusable(false);
        startTimeText.setRequestFocusEnabled(false);
        startTimeText.setVerifyInputWhenFocusTarget(false);

        remainingTimeLabelHome.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeLabelHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelHome.setText("Remaining Time");
        remainingTimeLabelHome.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        remainingTimeText.setEditable(false);
        remainingTimeText.setBackground(new java.awt.Color(204, 255, 255));
        remainingTimeText.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeText.setAutoscrolls(false);
        remainingTimeText.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 360, Short.MAX_VALUE)
                .addComponent(leaveContestArenaButton, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ContestDashboardTabSwitcher.addTab("Home", HomePanel);

        ProblemsetPanel.setBackground(new java.awt.Color(255, 255, 255));

        ProblemSetjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        ProblemSetjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        ProblemSetTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        ProblemSetTable.setModel(new javax.swing.table.DefaultTableModel(
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
        ProblemSetTable.setFocusable(false);
        ProblemSetTable.setGridColor(new java.awt.Color(255, 255, 255));
        ProblemSetTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        ProblemSetTable.setMinimumSize(new java.awt.Dimension(0, 0));
        ProblemSetTable.setOpaque(false);
        ProblemSetTable.setRequestFocusEnabled(false);
        ProblemSetTable.setRowHeight(23);
        ProblemSetTable.setRowSelectionAllowed(false);
        ProblemSetTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        ProblemSetTable.setShowHorizontalLines(false);
        ProblemSetTable.getTableHeader().setReorderingAllowed(false);
        ProblemSetjScrollPane.setViewportView(ProblemSetTable);

        remainingTimeLabelStandingsProblems.setBackground(new java.awt.Color(204, 255, 255));
        remainingTimeLabelStandingsProblems.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeLabelStandingsProblems.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelStandingsProblems.setText("Remaining Time");
        remainingTimeLabelStandingsProblems.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        remainingTimeTextProblems.setEditable(false);
        remainingTimeTextProblems.setBackground(new java.awt.Color(204, 255, 255));
        remainingTimeTextProblems.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeTextProblems.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeTextProblems.setText(durationMinutesText.getText());
        remainingTimeTextProblems.setAutoscrolls(false);
        remainingTimeTextProblems.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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

        ContestDashboardTabSwitcher.addTab("All Problems", ProblemsetPanel);

        problemPanel.setBackground(new java.awt.Color(255, 255, 255));

        remainingTimeLabelProblem.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeLabelProblem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelProblem.setText("Remaining Time");
        remainingTimeLabelProblem.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        remainingTimeTextProblem.setEditable(false);
        remainingTimeTextProblem.setBackground(new java.awt.Color(204, 255, 255));
        remainingTimeTextProblem.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeTextProblem.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeTextProblem.setAutoscrolls(false);
        remainingTimeTextProblem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        remainingTimeTextProblem.setFocusable(false);
        remainingTimeTextProblem.setRequestFocusEnabled(false);
        remainingTimeTextProblem.setVerifyInputWhenFocusTarget(false);

        timeLimitLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        timeLimitLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLimitLabel.setText("Time Limit");
        timeLimitLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        memoryLimitText.setEditable(false);
        memoryLimitText.setBackground(new java.awt.Color(204, 255, 255));
        memoryLimitText.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        memoryLimitText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        memoryLimitText.setAutoscrolls(false);
        memoryLimitText.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        memoryLimitText.setFocusable(false);
        memoryLimitText.setRequestFocusEnabled(false);
        memoryLimitText.setVerifyInputWhenFocusTarget(false);

        memoryLimitLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        memoryLimitLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        memoryLimitLabel.setText("Memory Limit");
        memoryLimitLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        timeLimitText.setEditable(false);
        timeLimitText.setBackground(new java.awt.Color(204, 255, 255));
        timeLimitText.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        timeLimitText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timeLimitText.setAutoscrolls(false);
        timeLimitText.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        timeLimitText.setFocusable(false);
        timeLimitText.setRequestFocusEnabled(false);
        timeLimitText.setVerifyInputWhenFocusTarget(false);

        selectProblemLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        selectProblemLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        selectProblemLabel.setText("Select Problem");
        selectProblemLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        selectProblemCombo.setBackground(new java.awt.Color(204, 255, 255));
        selectProblemCombo.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        selectProblemCombo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        pdfPanel.setLayout(new java.awt.BorderLayout());

        submitProblemSolution.setBackground(new java.awt.Color(255, 255, 255));
        submitProblemSolution.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        submitProblemSolution.setForeground(new java.awt.Color(51, 0, 51));
        submitProblemSolution.setText("Submit Problem");
        submitProblemSolution.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 1, true), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 153), new java.awt.Color(102, 255, 255), new java.awt.Color(0, 153, 153), new java.awt.Color(102, 255, 255))));
        submitProblemSolution.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout problemPanelLayout = new javax.swing.GroupLayout(problemPanel);
        problemPanel.setLayout(problemPanelLayout);
        problemPanelLayout.setHorizontalGroup(
            problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(problemPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pdfPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(problemPanelLayout.createSequentialGroup()
                        .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(selectProblemLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(remainingTimeLabelProblem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(timeLimitLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(problemPanelLayout.createSequentialGroup()
                                .addComponent(timeLimitText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(memoryLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(memoryLimitText, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(selectProblemCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, problemPanelLayout.createSequentialGroup()
                                .addComponent(remainingTimeTextProblem, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(submitProblemSolution, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        problemPanelLayout.setVerticalGroup(
            problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(problemPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(submitProblemSolution, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(remainingTimeLabelProblem, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(remainingTimeTextProblem, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addGap(7, 7, 7)
                .addComponent(pdfPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addContainerGap())
        );

        ContestDashboardTabSwitcher.addTab("Problem", problemPanel);

        SubmitSolPanel.setBackground(new java.awt.Color(255, 255, 255));

        ChooseFileLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ChooseFileLabel.setForeground(new java.awt.Color(54, 33, 89));
        ChooseFileLabel.setText("Or Choose File:");
        ChooseFileLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204)));

        txtProblemID.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtProblemID.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(153, 255, 255), new java.awt.Color(153, 255, 255), new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204)));

        ProblemIDLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ProblemIDLabel.setForeground(new java.awt.Color(54, 33, 89));
        ProblemIDLabel.setText("Problem ID: ");
        ProblemIDLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204)));

        LanguageLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        LanguageLabel.setForeground(new java.awt.Color(54, 33, 89));
        LanguageLabel.setText("Language: ");
        LanguageLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204)));

        SourceCodeTextArea.setColumns(20);
        SourceCodeTextArea.setRows(5);
        SourceCodeScrollPane.setViewportView(SourceCodeTextArea);

        submissionLanguageCombo.setBackground(new java.awt.Color(204, 255, 255));
        submissionLanguageCombo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        submissionLanguageCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "C", "C++", "Java" }));
        submissionLanguageCombo.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204)));

        SourceCodeLabel.setBackground(new java.awt.Color(255, 255, 255));
        SourceCodeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        SourceCodeLabel.setForeground(new java.awt.Color(54, 33, 89));
        SourceCodeLabel.setText("Source Code:");
        SourceCodeLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED, new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204)));

        ChooseFileButton.setBackground(new java.awt.Color(255, 255, 255));
        ChooseFileButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        ChooseFileButton.setText("Choose File");
        ChooseFileButton.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 153), 2, true), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204))));
        ChooseFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseFileButtonActionPerformed(evt);
            }
        });

        SubmitButton.setBackground(new java.awt.Color(54, 33, 89));
        SubmitButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SubmitButton.setForeground(new java.awt.Color(54, 33, 89));
        SubmitButton.setText("Submit");
        SubmitButton.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 153), 2, true), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 255, 255), new java.awt.Color(0, 255, 255), new java.awt.Color(0, 255, 255), new java.awt.Color(0, 255, 255))));
        SubmitButton.setContentAreaFilled(false);
        SubmitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitButtonActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SourceCodeScrollPane)
                    .addGroup(SubmitSolPanelLayout.createSequentialGroup()
                        .addGroup(SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, SubmitSolPanelLayout.createSequentialGroup()
                                    .addComponent(LanguageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(submissionLanguageCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(SubmitSolPanelLayout.createSequentialGroup()
                                    .addComponent(ProblemIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtProblemID, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(SourceCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 254, Short.MAX_VALUE)))
                .addContainerGap())
        );
        SubmitSolPanelLayout.setVerticalGroup(
            SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SubmitSolPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProblemIDLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProblemID, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LanguageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(submissionLanguageCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SourceCodeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SourceCodeScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(SubmitSolPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ChooseFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SubmitButton)
                    .addComponent(ChooseFileLabel))
                .addGap(43, 43, 43))
        );

        ContestDashboardTabSwitcher.addTab("Submit Solution", SubmitSolPanel);

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
        StatusScrollPane.setViewportView(StatusTable);

        remainingTimeLabelStatus.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeLabelStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelStatus.setText("Remaining Time");
        remainingTimeLabelStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        remainingTimeTextStatus.setEditable(false);
        remainingTimeTextStatus.setBackground(new java.awt.Color(204, 255, 255));
        remainingTimeTextStatus.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeTextStatus.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeTextStatus.setAutoscrolls(false);
        remainingTimeTextStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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
                .addGroup(StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remainingTimeLabelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remainingTimeTextStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(StatusScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ContestDashboardTabSwitcher.addTab("Status", StatusPanel);

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
        MySubScrollPane.setViewportView(MySubTable);

        remainingTimeLabelMysubmission.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeLabelMysubmission.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelMysubmission.setText("Remaining Time");
        remainingTimeLabelMysubmission.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        remainingTimeTextMySubmission.setEditable(false);
        remainingTimeTextMySubmission.setBackground(new java.awt.Color(204, 255, 255));
        remainingTimeTextMySubmission.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeTextMySubmission.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeTextMySubmission.setAutoscrolls(false);
        remainingTimeTextMySubmission.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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
                .addGroup(MySubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remainingTimeLabelMysubmission, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remainingTimeTextMySubmission, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(MySubScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 651, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        ContestDashboardTabSwitcher.addTab("My Submissions", MySubPanel);

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

        remainingTimeLabelStandings.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeLabelStandings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        remainingTimeLabelStandings.setText("Remaining Time");
        remainingTimeLabelStandings.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        remainingTimeTextStanding.setEditable(false);
        remainingTimeTextStanding.setBackground(new java.awt.Color(204, 255, 255));
        remainingTimeTextStanding.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        remainingTimeTextStanding.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        remainingTimeTextStanding.setAutoscrolls(false);
        remainingTimeTextStanding.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
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
                .addGap(5, 5, 5)
                .addGroup(StandingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remainingTimeLabelStandings, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(remainingTimeTextStanding, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void ContestDashboardTabSwitcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContestDashboardTabSwitcherMouseClicked
        txtProblemID.setText(null);
        ChooseFileButton.setText("Choose file");
        codefile = null;
        SourceCodeTextArea.setText(null);
        int x = ContestDashboardTabSwitcher.getSelectedIndex();
        Object[][] table;
        switch (x) {
            case 0:
                System.out.println("home");
                break;
            case 1:
                if (this.contestInfo.getStartTime().getTime() > System.currentTimeMillis()) {
                    ContestDashboardTabSwitcher.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Contest Not Started Yet", "Wait", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                usersocket.sendData("PrbTable[null]");
                table = usersocket.getProblemTable();
                if (table == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"Problem ID", "Problem Name", "ProblemSetter"};
                    DefaultTableModel tablemodel = new DefaultTableModel(table, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };

                    ProblemSetTable.setModel(tablemodel);
                }

                break;
            case 2:
                if (this.contestInfo.getStartTime().getTime() > System.currentTimeMillis()) {
                    ContestDashboardTabSwitcher.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Contest Not Started Yet", "Wait", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                pdfController.openDocument("D:/Netbeans/IUTPJ/IUTPJ_Admin/24072020141258.pdf");
                pdfViewerPanel.revalidate();
                break;
            case 3:
                if (this.contestInfo.getStartTime().getTime() > System.currentTimeMillis()) {
                    ContestDashboardTabSwitcher.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Contest Not Started Yet", "Wait", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (false) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"};
                    String stable[][] = {
                        {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"},
                        {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"},
                        {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"}
                    };
                    DefaultTableModel tablemodel = new DefaultTableModel(stable, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };

                    StatusTable.setModel(tablemodel);
                }

                break;
            case 4:
                if (this.contestInfo.getStartTime().getTime() > System.currentTimeMillis()) {
                    ContestDashboardTabSwitcher.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Contest Not Started Yet", "Wait", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (false) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"};
                    String stable[][] = {
                        {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"},
                        {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"},
                        {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"}
                    };
                    DefaultTableModel tablemodel = new DefaultTableModel(stable, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };

                    StatusTable.setModel(tablemodel);
                }
                break;
            case 5:
                if (this.contestInfo.getStartTime().getTime() > System.currentTimeMillis()) {
                    ContestDashboardTabSwitcher.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null, "Contest Not Started Yet", "Wait", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                usersocket.sendData("StdTable[null]");
                table = usersocket.getStandingsTable();
                if (table == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"#", "ID", "Problems Solved"};
                    DefaultTableModel tablemodel = new DefaultTableModel(table, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    StandingsTable.setModel(tablemodel);
                }
                break;
            default:
                break;

        }
    }//GEN-LAST:event_ContestDashboardTabSwitcherMouseClicked

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButtonActionPerformed

        String problemid = txtProblemID.getText();
        try {
            if (Integer.parseInt(problemid) < 0) {
                JOptionPane.showMessageDialog(null, "Problem ID cannot be negative", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Problem ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String language = (String) submissionLanguageCombo.getSelectedItem();
        if (codefile == null) {
            try {
                codefile = new File("Submission.txt");
                FileWriter txtcodewriter = new FileWriter(codefile);
                txtcodewriter.write(SourceCodeTextArea.getText());
                txtcodewriter.close();
            } catch (IOException ex) {
                System.out.println("Source code writing Err: " + ex.getMessage());
            }
        }
        if (codefile == null) {
            JOptionPane.showMessageDialog(null, "No file chosen!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        usersocket.sendData("AddSub--[" + codefile.getName() + "]");

        if (usersocket.addSubmission(codefile, problemid, language) > 0) {
            JOptionPane.showMessageDialog(null, "Submitted!", "Status", JOptionPane.INFORMATION_MESSAGE);
        }
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
    private javax.swing.JTable ProblemSetTable;
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
    private javax.swing.JLabel selectProblemLabel;
    private javax.swing.JLabel setterLabel;
    private javax.swing.JTextField setterText;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JTextField startTimeText;
    private javax.swing.JComboBox submissionLanguageCombo;
    private javax.swing.JButton submitProblemSolution;
    private javax.swing.JLabel timeLimitLabel;
    private javax.swing.JTextField timeLimitText;
    private javax.swing.JTextField txtProblemID;
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
