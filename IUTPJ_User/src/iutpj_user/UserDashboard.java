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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
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
public class UserDashboard extends javax.swing.JFrame {

    /**
     * Creates new form UserDashboard
     */
    private final UserSocket usersocket;
    private SwingController pdfController;
    private JPanel pdfViewerPanel;
    private File codefile;
    private UserDashboard temporary;
    private Login login;
    private Object[][] problemTable, statusTable, myStatusTable, standingTable, contestTable;

    public UserDashboard(UserSocket usersocket, Login login) {
        initComponents();

        this.pdfController = new SwingController();
        this.pdfViewerPanel = new SwingViewBuilder(pdfController).buildViewerPanel();
        this.pdfPanel.add(this.pdfViewerPanel);

        this.usersocket = usersocket;
        this.codefile = null;
        temporary = this;
        this.login = login;
        problemTable = null;
        statusTable = null;
        myStatusTable = null;
        standingTable = null;
        contestTable = null;

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
                if (null != table.getClientProperty(table.getColumnName(column)) && value != null) {
                    JButton cd = new JButton();
                    cd.setText(value.toString());
                    cd.setForeground(Color.BLUE);
                    cd.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    cd.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    cd.setBackground((row % 2 == 0 ? new Color(242, 242, 189) : Color.WHITE));
                    cd.setEnabled(true);
                    return (Component) cd;
                }
                c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                c.setBackground((row % 2 == 0 ? new Color(242, 242, 189) : Color.WHITE));
                return c;
            }
        };

        StatusTable.setDefaultRenderer(Object.class, cellRenderer);
        StatusTable.getTableHeader().setDefaultRenderer(cellRenderer);
        StatusTable.putClientProperty("Problem", Color.BLUE);

        MySubTable.setDefaultRenderer(Object.class, cellRenderer);
        MySubTable.getTableHeader().setDefaultRenderer(cellRenderer);
        MySubTable.putClientProperty("Problem", Color.BLUE);
        MySubTable.putClientProperty("#", Color.BLUE);

        StandingsTable.setDefaultRenderer(Object.class, cellRenderer);
        StandingsTable.getTableHeader().setDefaultRenderer(cellRenderer);

        //ProblemsetTable.getTableHeader().setOpaque(false);
        ProblemsetTable.setDefaultRenderer(Object.class, cellRenderer);
        ProblemsetTable.getTableHeader().setDefaultRenderer(cellRenderer);
        ProblemsetTable.putClientProperty("Problem Name", Color.BLUE);

        ContestTable.setDefaultRenderer(Object.class, cellRenderer);
        ContestTable.getTableHeader().setDefaultRenderer(cellRenderer);
        ContestTable.putClientProperty("Contest Name", Color.BLUE);

        MySubTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) {
                    evt.consume();
                    int row = MySubTable.rowAtPoint(evt.getPoint());
                    int col = MySubTable.columnAtPoint(evt.getPoint());
                    if (row >= 0 && col == 0) {
                        DefaultTableModel tablemodel = (DefaultTableModel) MySubTable.getModel();
                        if (tablemodel.getValueAt(row, 0) != null) {
                            String submissionid = myStatusTable[row][8].toString();
                            SubmissionShow subshow = new SubmissionShow(usersocket, temporary);
                            subshow.setSubDetailsTable(submissionid, tablemodel.getValueAt(row, 2), tablemodel.getValueAt(row, 3), tablemodel.getValueAt(row, 4), tablemodel.getValueAt(row, 5), tablemodel.getValueAt(row, 6), tablemodel.getValueAt(row, 1), myStatusTable[row][7]);

                            usersocket.sendData("SrcCode-[" + submissionid + "]");
                            NewSubmission submission = usersocket.getSubmission();
                            subshow.setSourceCode(submission);
                        }

                    } else if (row >= 0 && col == 3) {
                        DefaultTableModel tablemodel = (DefaultTableModel) MySubTable.getModel();
                        if (tablemodel.getValueAt(row, 3) != null) {
                            String problemid = myStatusTable[row][7].toString();

                            usersocket.sendData("ProbFile[" + problemid + "]");
                            NewProblem problem = usersocket.getProblem();
                            try {
                                FileOutputStream fos = new FileOutputStream(problemid + ".pdf");
                                fos.write(problem.getProb());
                                fos.close();
                            } catch (FileNotFoundException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            } catch (IOException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            }

                            ProblemShow problemshow = new ProblemShow(temporary, problem.getProblemName(), problem.getTimeLimit(), problem.getMemoryLimit());
                            problemshow.viewPdf(new File(problemid + ".pdf"), problemid);
                        }

                    }
                }
            }
        });

        //this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel6 = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        UserDashboardTabSwitcher = new javax.swing.JTabbedPane();
        HomePanel = new javax.swing.JPanel();
        WelcomeLabel = new javax.swing.JLabel();
        LogOutButton = new javax.swing.JButton();
        ProblemsetPanel = new javax.swing.JPanel();
        ProblemSetjScrollPane = new javax.swing.JScrollPane();
        ProblemsetTable = new javax.swing.JTable();
        problemPanel = new javax.swing.JPanel();
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
        LanguageComboBox = new javax.swing.JComboBox();
        SourceCodeLabel = new javax.swing.JLabel();
        ChooseFileButton = new javax.swing.JButton();
        SubmitButton = new javax.swing.JButton();
        StatusPanel = new javax.swing.JPanel();
        StatusScrollPane = new javax.swing.JScrollPane();
        StatusTable = new javax.swing.JTable();
        MySubPanel = new javax.swing.JPanel();
        MySubScrollPane = new javax.swing.JScrollPane();
        MySubTable = new javax.swing.JTable();
        StandingsPanel = new javax.swing.JPanel();
        StandingsScrollPane = new javax.swing.JScrollPane();
        StandingsTable = new javax.swing.JTable();
        ContestsPanel = new javax.swing.JPanel();
        ContestTableScrollPane = new javax.swing.JScrollPane();
        ContestTable = new javax.swing.JTable();

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

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPane1.setLayout(new java.awt.BorderLayout());

        UserDashboardTabSwitcher.setBackground(new java.awt.Color(255, 255, 255));
        UserDashboardTabSwitcher.setForeground(new java.awt.Color(54, 33, 89));
        UserDashboardTabSwitcher.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        UserDashboardTabSwitcher.setFont(new java.awt.Font("Segoe UI Emoji", 0, 29)); // NOI18N
        UserDashboardTabSwitcher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserDashboardTabSwitcherMouseClicked(evt);
            }
        });

        HomePanel.setBackground(new java.awt.Color(255, 255, 255));
        HomePanel.setLayout(new java.awt.GridBagLayout());

        WelcomeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 36)); // NOI18N
        WelcomeLabel.setForeground(new java.awt.Color(54, 33, 89));
        WelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeLabel.setText("Welcome To IUTOJ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(43, 40, 95, 40);
        HomePanel.add(WelcomeLabel, gridBagConstraints);

        LogOutButton.setBackground(new java.awt.Color(54, 33, 89));
        LogOutButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        LogOutButton.setForeground(new java.awt.Color(54, 33, 89));
        LogOutButton.setText("Log Out");
        LogOutButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(54, 33, 89)));
        LogOutButton.setContentAreaFilled(false);
        LogOutButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        LogOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogOutButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BELOW_BASELINE;
        gridBagConstraints.insets = new java.awt.Insets(38, 38, 0, 38);
        HomePanel.add(LogOutButton, gridBagConstraints);

        UserDashboardTabSwitcher.addTab("Home", HomePanel);

        ProblemSetjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        ProblemSetjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        ProblemsetTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        ProblemsetTable.setModel(new javax.swing.table.DefaultTableModel(
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
        ProblemsetTable.setFocusable(false);
        ProblemsetTable.setGridColor(new java.awt.Color(255, 255, 255));
        ProblemsetTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        ProblemsetTable.setMinimumSize(new java.awt.Dimension(0, 0));
        ProblemsetTable.setOpaque(false);
        ProblemsetTable.setRequestFocusEnabled(false);
        ProblemsetTable.setRowHeight(25);
        ProblemsetTable.setRowSelectionAllowed(false);
        ProblemsetTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        ProblemsetTable.setShowHorizontalLines(false);
        ProblemsetTable.getTableHeader().setReorderingAllowed(false);
        ProblemsetTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                problemSetTableMouseClicked(evt);
            }
        });
        ProblemSetjScrollPane.setViewportView(ProblemsetTable);

        javax.swing.GroupLayout ProblemsetPanelLayout = new javax.swing.GroupLayout(ProblemsetPanel);
        ProblemsetPanel.setLayout(ProblemsetPanelLayout);
        ProblemsetPanelLayout.setHorizontalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 813, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        ProblemsetPanelLayout.setVerticalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        UserDashboardTabSwitcher.addTab("Problemset", ProblemsetPanel);

        problemPanel.setBackground(new java.awt.Color(255, 255, 255));

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
                        .addComponent(timeLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timeLimitText, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(memoryLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(memoryLimitText, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(problemPanelLayout.createSequentialGroup()
                        .addComponent(selectProblemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectProblemCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(submitProblemSolution, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        problemPanelLayout.setVerticalGroup(
            problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(problemPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitProblemSolution, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectProblemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectProblemCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(timeLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(memoryLimitLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(problemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(memoryLimitText, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(timeLimitText, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pdfPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addContainerGap())
        );

        UserDashboardTabSwitcher.addTab("Problem", problemPanel);

        SubmitSolPanel.setBackground(new java.awt.Color(255, 255, 255));
        SubmitSolPanel.setLayout(new java.awt.GridBagLayout());

        ChooseFileLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ChooseFileLabel.setForeground(new java.awt.Color(54, 33, 89));
        ChooseFileLabel.setText("Or choose File:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 43;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 50, 0, 0);
        SubmitSolPanel.add(ChooseFileLabel, gridBagConstraints);

        txtProblemID.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 156;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 0, 0, 0);
        SubmitSolPanel.add(txtProblemID, gridBagConstraints);

        ProblemIDLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ProblemIDLabel.setForeground(new java.awt.Color(54, 33, 89));
        ProblemIDLabel.setText("Problem ID: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 61;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 40, 0, 0);
        SubmitSolPanel.add(ProblemIDLabel, gridBagConstraints);

        LanguageLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        LanguageLabel.setForeground(new java.awt.Color(54, 33, 89));
        LanguageLabel.setText("Language: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 74;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 40, 0, 0);
        SubmitSolPanel.add(LanguageLabel, gridBagConstraints);

        SourceCodeTextArea.setColumns(20);
        SourceCodeTextArea.setRows(5);
        SourceCodeScrollPane.setViewportView(SourceCodeTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 540;
        gridBagConstraints.ipady = 320;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(18, 0, 0, 160);
        SubmitSolPanel.add(SourceCodeScrollPane, gridBagConstraints);

        LanguageComboBox.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        LanguageComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "C", "C++", "Java" }));
        LanguageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LanguageComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        SubmitSolPanel.add(LanguageComboBox, gridBagConstraints);

        SourceCodeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        SourceCodeLabel.setForeground(new java.awt.Color(54, 33, 89));
        SourceCodeLabel.setText("Source Code:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 40, 0, 0);
        SubmitSolPanel.add(SourceCodeLabel, gridBagConstraints);

        ChooseFileButton.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        ChooseFileButton.setText("Choose File");
        ChooseFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChooseFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 19;
        gridBagConstraints.ipady = -2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        SubmitSolPanel.add(ChooseFileButton, gridBagConstraints);

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
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.ipady = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(40, 100, 60, 0);
        SubmitSolPanel.add(SubmitButton, gridBagConstraints);

        UserDashboardTabSwitcher.addTab("Submit Solution", SubmitSolPanel);

        StatusPanel.setLayout(new java.awt.BorderLayout());

        StatusScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        StatusTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
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
        ));
        StatusTable.setFocusable(false);
        StatusTable.setGridColor(new java.awt.Color(255, 255, 255));
        StatusTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        StatusTable.setOpaque(false);
        StatusTable.setRequestFocusEnabled(false);
        StatusTable.setRowHeight(25);
        StatusTable.setRowSelectionAllowed(false);
        StatusTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        StatusTable.setShowHorizontalLines(false);
        StatusTable.getTableHeader().setReorderingAllowed(false);
        StatusTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                statusTableMouseClicked(evt);
            }
        });
        StatusScrollPane.setViewportView(StatusTable);

        StatusPanel.add(StatusScrollPane, java.awt.BorderLayout.CENTER);

        UserDashboardTabSwitcher.addTab("Status", StatusPanel);

        MySubPanel.setLayout(new java.awt.BorderLayout());

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
        MySubTable.setRowHeight(25);
        MySubTable.setRowSelectionAllowed(false);
        MySubTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        MySubTable.setShowHorizontalLines(false);
        MySubTable.getTableHeader().setReorderingAllowed(false);
        MySubTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mySubmissionTableMouseClicked(evt);
            }
        });
        MySubScrollPane.setViewportView(MySubTable);

        MySubPanel.add(MySubScrollPane, java.awt.BorderLayout.CENTER);

        UserDashboardTabSwitcher.addTab("My Submissions", MySubPanel);

        StandingsPanel.setLayout(new java.awt.BorderLayout());

        StandingsScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        StandingsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 16)); // NOI18N
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
        StandingsTable.setRowHeight(25);
        StandingsTable.setRowSelectionAllowed(false);
        StandingsTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        StandingsTable.setShowHorizontalLines(false);
        StandingsTable.getTableHeader().setReorderingAllowed(false);
        StandingsScrollPane.setViewportView(StandingsTable);

        StandingsPanel.add(StandingsScrollPane, java.awt.BorderLayout.CENTER);

        UserDashboardTabSwitcher.addTab("Standings", StandingsPanel);

        ContestsPanel.setBackground(new java.awt.Color(255, 255, 255));
        ContestsPanel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N

        ContestTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        ContestTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ContestTable.setFocusable(false);
        ContestTable.setRowHeight(23);
        ContestTable.setRowSelectionAllowed(false);
        ContestTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ContestTableMouseClicked(evt);
            }
        });
        ContestTableScrollPane.setViewportView(ContestTable);

        javax.swing.GroupLayout ContestsPanelLayout = new javax.swing.GroupLayout(ContestsPanel);
        ContestsPanel.setLayout(ContestsPanelLayout);
        ContestsPanelLayout.setHorizontalGroup(
            ContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContestsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ContestTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 793, Short.MAX_VALUE)
                .addContainerGap())
        );
        ContestsPanelLayout.setVerticalGroup(
            ContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContestsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ContestTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                .addContainerGap())
        );

        UserDashboardTabSwitcher.addTab("All Contests", ContestsPanel);

        jDesktopPane1.add(UserDashboardTabSwitcher, java.awt.BorderLayout.CENTER);

        getContentPane().add(jDesktopPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LanguageComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LanguageComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LanguageComboBoxActionPerformed

    private void ChooseFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChooseFileButtonActionPerformed
        JFileChooser filemanager = new JFileChooser("Documents");

        filemanager.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filemanager.addChoosableFileFilter(new FileNameExtensionFilter("C++ Documents", "cpp"));
        filemanager.showOpenDialog(this);
        codefile = filemanager.getSelectedFile();

        if (codefile != null) {
            String language = (String) LanguageComboBox.getSelectedItem();
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

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButtonActionPerformed

        String problemid = txtProblemID.getText();
        System.out.println(problemid);
        try {
            if (Long.parseLong(problemid) < 0) {
                JOptionPane.showMessageDialog(null, "Problem ID cannot be negative", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Problem ID: " + ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String language = (String) LanguageComboBox.getSelectedItem();
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

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutButtonActionPerformed
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogOutButtonActionPerformed

    private void UserDashboardTabSwitcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserDashboardTabSwitcherMouseClicked
        txtProblemID.setText(null);
        ChooseFileButton.setText("Choose file");
        codefile = null;
        SourceCodeTextArea.setText(null);

        int x = UserDashboardTabSwitcher.getSelectedIndex();
        switch (x) {

            case 1:
                usersocket.sendData("PrbTable[null]");
                problemTable = usersocket.getProblemTable();
                if (problemTable == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"Problem ID", "Problem Name", "ProblemSetter"};
                    DefaultTableModel tablemodel = new DefaultTableModel(problemTable, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    ProblemsetTable.setModel(tablemodel);
                }
                break;
            case 2:
                txtProblemID.setEditable(true);
                break;
            case 3:
                usersocket.sendData("StTable-[nullus]");
                statusTable = usersocket.getStatusTable();
                if (statusTable == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"};
                    DefaultTableModel tablemodel = new DefaultTableModel(statusTable, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    StatusTable.setModel(tablemodel);
                }
                break;
            case 4:
                usersocket.sendData("StTable-[My]");
                myStatusTable = usersocket.getStatusTable();
                if (myStatusTable == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"};
                    DefaultTableModel tablemodel = new DefaultTableModel(myStatusTable, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    MySubTable.setModel(tablemodel);
                }
                break;
            case 5:
                usersocket.sendData("StdTable[null]");
                standingTable = usersocket.getStandingsTable();
                if (standingTable == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"#", "ID", "Problems Solved"};
                    DefaultTableModel tablemodel = new DefaultTableModel(standingTable, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    StandingsTable.setModel(tablemodel);
                }
                break;
            case 6:
                usersocket.sendData("CntstTab[null]");
                contestTable = usersocket.getContestTable();
                if (contestTable == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"Contest_ID", "Contest Name", "Author", "Start Time", "Duration(minutes)", "Status"};
                    DefaultTableModel tablemodel = new DefaultTableModel(contestTable, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    ContestTable.setModel(tablemodel);
                }
            default:
                break;

        }
    }//GEN-LAST:event_UserDashboardTabSwitcherMouseClicked

    private void ContestTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContestTableMouseClicked
        if (evt.getClickCount() == 1 & !evt.isConsumed()) {
            evt.consume();
            int row = ContestTable.rowAtPoint(evt.getPoint());
            int col = ContestTable.columnAtPoint(evt.getPoint());

            if (row >= 0 && col == 0) {
                DefaultTableModel tablemodel = (DefaultTableModel) ContestTable.getModel();
                if (tablemodel.getValueAt(row, 0) != null) {
                    String contestID = contestTable[row][6].toString();
                    ContestInfo contest = usersocket.getContestInfo(contestID);
                    ContestDashboard contestArea = new ContestDashboard(usersocket, this, contest);
                    contestArea.setVisible(rootPaneCheckingEnabled);
                    this.setVisible(false);
                }
            }
        }
    }//GEN-LAST:event_ContestTableMouseClicked

    private void problemSetTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_problemSetTableMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            evt.consume();
            int row = ProblemsetTable.rowAtPoint(evt.getPoint());
            int col = ProblemsetTable.columnAtPoint(evt.getPoint());
            if (row >= 0 && col == 1 && ProblemsetTable.getValueAt(row, col) != null) {
                usersocket.sendData("ProbFile[" + problemTable[row][4].toString() + "]");
                NewProblem problem = usersocket.getProblem();
                pdfController.openDocument(problem.getProb(), 0, problem.getProb().length, null, null);
                pdfViewerPanel.revalidate();
                UserDashboardTabSwitcher.setSelectedIndex(2);

            }
        }
    }//GEN-LAST:event_problemSetTableMouseClicked

    private void statusTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_statusTableMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            evt.consume();
            int row = StatusTable.rowAtPoint(evt.getPoint());
            int col = StatusTable.columnAtPoint(evt.getPoint());
            if (row >= 0 && col == 3 && StatusTable.getValueAt(row, col) != null) {
                usersocket.sendData("ProbFile[" + statusTable[row][col].toString() + "]");
                NewProblem problem = usersocket.getProblem();
                pdfController.openDocument(problem.getProb(), 0, problem.getProb().length, null, null);
                pdfViewerPanel.revalidate();
                UserDashboardTabSwitcher.setSelectedIndex(2);
            }
        }
    }//GEN-LAST:event_statusTableMouseClicked

    private void mySubmissionTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mySubmissionTableMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            evt.consume();
            int row = MySubTable.rowAtPoint(evt.getPoint());
            int col = MySubTable.columnAtPoint(evt.getPoint());
            if (row >= 0 && col == 3 && MySubTable.getValueAt(row, col) != null) {
                usersocket.sendData("ProbFile[" + myStatusTable[row][col].toString() + "]");
                NewProblem problem = usersocket.getProblem();
                pdfController.openDocument(problem.getProb(), 0, problem.getProb().length, null, null);
                pdfViewerPanel.revalidate();
                UserDashboardTabSwitcher.setSelectedIndex(2);
            }
            else if(row>=0 && col==1 && MySubTable.getValueAt(row, col) != null)
            {
                
            }
        }
    }//GEN-LAST:event_mySubmissionTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChooseFileButton;
    private javax.swing.JLabel ChooseFileLabel;
    private javax.swing.JTable ContestTable;
    private javax.swing.JScrollPane ContestTableScrollPane;
    private javax.swing.JPanel ContestsPanel;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JComboBox LanguageComboBox;
    private javax.swing.JLabel LanguageLabel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JPanel MySubPanel;
    private javax.swing.JScrollPane MySubScrollPane;
    private javax.swing.JTable MySubTable;
    private javax.swing.JLabel ProblemIDLabel;
    private javax.swing.JScrollPane ProblemSetjScrollPane;
    private javax.swing.JPanel ProblemsetPanel;
    private javax.swing.JTable ProblemsetTable;
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
    private javax.swing.JTabbedPane UserDashboardTabSwitcher;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel memoryLimitLabel;
    private javax.swing.JTextField memoryLimitText;
    private javax.swing.JPanel pdfPanel;
    private javax.swing.JPanel problemPanel;
    private javax.swing.JComboBox<String> selectProblemCombo;
    private javax.swing.JLabel selectProblemLabel;
    private javax.swing.JButton submitProblemSolution;
    private javax.swing.JLabel timeLimitLabel;
    private javax.swing.JTextField timeLimitText;
    private javax.swing.JTextField txtProblemID;
    // End of variables declaration//GEN-END:variables
}
