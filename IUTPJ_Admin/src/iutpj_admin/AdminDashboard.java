/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_admin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import newproblem.NewProblem;
import newsubmission.NewSubmission;
import iutpj_server.ContestInfo;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author KAWSAR
 */
public class AdminDashboard extends javax.swing.JFrame {

    /**
     * Creates new form UserDashboard
     */
    private final AdminSocket adminSocket;
    private File problem, inputs, outputs;
    Login parent;
    private Object[][] problemTable, statusTable, myProblemTable, standingTable, contestTable;
    private ContestInfo contestInfo;

    public AdminDashboard(AdminSocket adminsocket, Login parent) {
        initComponents();
        this.adminSocket = adminsocket;
        this.parent = parent;
        this.adminSocket.setParentFrame(this);
        this.contestInfo = new ContestInfo();

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
                    cd.setForeground((Color) table.getClientProperty(table.getColumnName(column)));
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
        StatusTable.putClientProperty("#", Color.BLUE);

        ContestTable.setDefaultRenderer(Object.class, cellRenderer);
        ContestTable.getTableHeader().setDefaultRenderer(cellRenderer);
        ContestTable.putClientProperty("Contest Name", Color.BLUE);

        addContestProblemTable.setDefaultRenderer(Object.class, cellRenderer);
        addContestProblemTable.getTableHeader().setDefaultRenderer(cellRenderer);
        addContestProblemTable.putClientProperty("Problem Name", Color.BLUE);
        addContestProblemTable.putClientProperty("", Color.red);

        StandingsTable.setDefaultRenderer(Object.class, cellRenderer);
        StandingsTable.getTableHeader().setDefaultRenderer(cellRenderer);

        ProblemsetTable.setDefaultRenderer(Object.class, cellRenderer);
        ProblemsetTable.getTableHeader().setDefaultRenderer(cellRenderer);
        ProblemsetTable.putClientProperty("Problem Name", Color.BLUE);

        DelProblemsetTable.setDefaultRenderer(Object.class, cellRenderer);
        DelProblemsetTable.getTableHeader().setDefaultRenderer(cellRenderer);
        DelProblemsetTable.putClientProperty(" ", Color.RED);
        DelProblemsetTable.putClientProperty("Locked", Color.GREEN);

        MyProblemsTable.setDefaultRenderer(Object.class, cellRenderer);
        MyProblemsTable.getTableHeader().setDefaultRenderer(cellRenderer);
        MyProblemsTable.putClientProperty("Problem Name", Color.BLUE);

        StatusTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

            }
        });

        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        AdminDashboardDesktopPane = new javax.swing.JDesktopPane();
        AdminDashboardTabSwitcher = new javax.swing.JTabbedPane();
        HomePanel = new javax.swing.JPanel();
        WelcomeLabel = new javax.swing.JLabel();
        LogOutButton = new javax.swing.JButton();
        ProblemsetPanel = new javax.swing.JPanel();
        ProblemSetjScrollPane = new javax.swing.JScrollPane();
        ProblemsetTable = new javax.swing.JTable();
        MyProblemsPanel = new javax.swing.JPanel();
        MyProblemsjScrollPane = new javax.swing.JScrollPane();
        MyProblemsTable = new javax.swing.JTable();
        ContestsPanel = new javax.swing.JPanel();
        ContestTableScrollPane = new javax.swing.JScrollPane();
        ContestTable = new javax.swing.JTable();
        ManagePanel = new javax.swing.JPanel();
        ManagePanelTabSwitcher = new javax.swing.JTabbedPane();
        AddProblemPanel = new javax.swing.JPanel();
        AddOutputButton = new javax.swing.JButton();
        ChProblemStatementButton = new javax.swing.JButton();
        AddInputButton = new javax.swing.JButton();
        MemoryLimitLabel = new javax.swing.JLabel();
        ProblemNameLabel = new javax.swing.JLabel();
        TimeLimitLabel = new javax.swing.JLabel();
        txtProblemName = new javax.swing.JTextField();
        SubmitButton = new javax.swing.JButton();
        txtTimeLimit = new javax.swing.JFormattedTextField();
        txtMemoryLimit = new javax.swing.JFormattedTextField();
        problemLockCheckBox = new javax.swing.JCheckBox();
        DeleteProblemPanel = new javax.swing.JPanel();
        DelProblemSetjScrollPane = new javax.swing.JScrollPane();
        DelProblemsetTable = new javax.swing.JTable();
        AddContestPanel = new javax.swing.JPanel();
        ProblemNameLabel1 = new javax.swing.JLabel();
        TimeLimitLabel1 = new javax.swing.JLabel();
        contestNameTextField = new javax.swing.JTextField();
        SubmitContest = new javax.swing.JButton();
        DurationLabel = new javax.swing.JLabel();
        dateTimeTextField = new javax.swing.JTextField();
        OpenCalendarButton = new javax.swing.JButton();
        durationTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        addContestProblemTable = new javax.swing.JTable();
        addProblemToContestLabel = new javax.swing.JLabel();
        contestProblemCombo = new javax.swing.JComboBox<>();
        markContestLabel = new javax.swing.JLabel();
        StatusPanel = new javax.swing.JPanel();
        StatusScrollPane = new javax.swing.JScrollPane();
        StatusTable = new javax.swing.JTable();
        StandingsPanel = new javax.swing.JPanel();
        StandingsScrollPane = new javax.swing.JScrollPane();
        StandingsTable = new javax.swing.JTable();

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
        setBackground(new java.awt.Color(54, 33, 89));

        AdminDashboardDesktopPane.setBackground(new java.awt.Color(255, 255, 255));

        AdminDashboardTabSwitcher.setBackground(new java.awt.Color(255, 255, 255));
        AdminDashboardTabSwitcher.setForeground(new java.awt.Color(54, 33, 89));
        AdminDashboardTabSwitcher.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        AdminDashboardTabSwitcher.setFont(new java.awt.Font("Segoe UI Emoji", 0, 29)); // NOI18N
        AdminDashboardTabSwitcher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AdminDashboardTabSwitcherMouseClicked(evt);
            }
        });

        HomePanel.setBackground(new java.awt.Color(255, 255, 255));

        WelcomeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 36)); // NOI18N
        WelcomeLabel.setForeground(new java.awt.Color(54, 33, 89));
        WelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeLabel.setText("Welcome To IUTOJ");

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

        javax.swing.GroupLayout HomePanelLayout = new javax.swing.GroupLayout(HomePanel);
        HomePanel.setLayout(HomePanelLayout);
        HomePanelLayout.setHorizontalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(WelcomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HomePanelLayout.createSequentialGroup()
                .addContainerGap(382, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(382, Short.MAX_VALUE))
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(WelcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        AdminDashboardTabSwitcher.addTab("Home", HomePanel);

        ProblemSetjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        ProblemSetjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        ProblemsetTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
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
                ProblemsetTableMouseClicked(evt);
            }
        });
        ProblemSetjScrollPane.setViewportView(ProblemsetTable);
        ProblemsetTable.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout ProblemsetPanelLayout = new javax.swing.GroupLayout(ProblemsetPanel);
        ProblemsetPanel.setLayout(ProblemsetPanelLayout);
        ProblemsetPanelLayout.setHorizontalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        ProblemsetPanelLayout.setVerticalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        AdminDashboardTabSwitcher.addTab("Problemset", ProblemsetPanel);

        MyProblemsjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        MyProblemsjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        MyProblemsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        MyProblemsTable.setForeground(new java.awt.Color(0, 0, 51));
        MyProblemsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        MyProblemsTable.setFocusable(false);
        MyProblemsTable.setGridColor(new java.awt.Color(255, 255, 255));
        MyProblemsTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        MyProblemsTable.setMinimumSize(new java.awt.Dimension(0, 0));
        MyProblemsTable.setOpaque(false);
        MyProblemsTable.setRequestFocusEnabled(false);
        MyProblemsTable.setRowHeight(25);
        MyProblemsTable.setRowSelectionAllowed(false);
        MyProblemsTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        MyProblemsTable.setShowHorizontalLines(false);
        MyProblemsTable.getTableHeader().setReorderingAllowed(false);
        MyProblemsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MyProblemsTableMouseClicked(evt);
            }
        });
        MyProblemsjScrollPane.setViewportView(MyProblemsTable);

        javax.swing.GroupLayout MyProblemsPanelLayout = new javax.swing.GroupLayout(MyProblemsPanel);
        MyProblemsPanel.setLayout(MyProblemsPanelLayout);
        MyProblemsPanelLayout.setHorizontalGroup(
            MyProblemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyProblemsPanelLayout.createSequentialGroup()
                .addComponent(MyProblemsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        MyProblemsPanelLayout.setVerticalGroup(
            MyProblemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyProblemsPanelLayout.createSequentialGroup()
                .addComponent(MyProblemsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        AdminDashboardTabSwitcher.addTab("My Problems", MyProblemsPanel);

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
                .addComponent(ContestTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
                .addContainerGap())
        );
        ContestsPanelLayout.setVerticalGroup(
            ContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContestsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ContestTableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                .addContainerGap())
        );

        AdminDashboardTabSwitcher.addTab("Contests", ContestsPanel);

        ManagePanel.setBackground(new java.awt.Color(255, 255, 255));
        ManagePanel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N

        ManagePanelTabSwitcher.setForeground(new java.awt.Color(54, 33, 89));
        ManagePanelTabSwitcher.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ManagePanelTabSwitcher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ManagePanelTabSwitcherMouseClicked(evt);
            }
        });

        AddProblemPanel.setBackground(new java.awt.Color(255, 255, 255));
        AddProblemPanel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        AddOutputButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        AddOutputButton.setText("Add Output (*.txt)");
        AddOutputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddOutputButtonActionPerformed(evt);
            }
        });

        ChProblemStatementButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ChProblemStatementButton.setText("Choose Problem Statement (*.pdf,*.txt)");
        ChProblemStatementButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChProblemStatementButtonActionPerformed(evt);
            }
        });

        AddInputButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        AddInputButton.setText("Add Input (*.txt)");
        AddInputButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddInputButtonActionPerformed(evt);
            }
        });

        MemoryLimitLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        MemoryLimitLabel.setForeground(new java.awt.Color(54, 33, 89));
        MemoryLimitLabel.setText("Memory Limit (KB):");

        ProblemNameLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ProblemNameLabel.setForeground(new java.awt.Color(54, 33, 89));
        ProblemNameLabel.setText("Problem Name:");

        TimeLimitLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        TimeLimitLabel.setForeground(new java.awt.Color(54, 33, 89));
        TimeLimitLabel.setText("Time Limit (ms):");

        SubmitButton.setBackground(new java.awt.Color(54, 33, 89));
        SubmitButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SubmitButton.setForeground(new java.awt.Color(54, 33, 89));
        SubmitButton.setText("Submit");
        SubmitButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(54, 33, 89)));
        SubmitButton.setContentAreaFilled(false);
        SubmitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SubmitButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitButtonActionPerformed(evt);
            }
        });

        txtTimeLimit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        txtMemoryLimit.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        problemLockCheckBox.setBackground(new java.awt.Color(255, 255, 255));
        problemLockCheckBox.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        problemLockCheckBox.setForeground(new java.awt.Color(54, 33, 89));
        problemLockCheckBox.setSelected(true);
        problemLockCheckBox.setText("Lock Problem For Contest");
        problemLockCheckBox.setFocusPainted(false);
        problemLockCheckBox.setFocusable(false);
        problemLockCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        problemLockCheckBox.setIconTextGap(160);
        problemLockCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        problemLockCheckBox.setPreferredSize(new java.awt.Dimension(376, 28));

        javax.swing.GroupLayout AddProblemPanelLayout = new javax.swing.GroupLayout(AddProblemPanel);
        AddProblemPanel.setLayout(AddProblemPanelLayout);
        AddProblemPanelLayout.setHorizontalGroup(
            AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddProblemPanelLayout.createSequentialGroup()
                .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(AddProblemPanelLayout.createSequentialGroup()
                        .addGap(290, 290, 290)
                        .addComponent(SubmitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(AddProblemPanelLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ChProblemStatementButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AddInputButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AddOutputButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AddProblemPanelLayout.createSequentialGroup()
                                .addComponent(ProblemNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtProblemName, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AddProblemPanelLayout.createSequentialGroup()
                                .addComponent(TimeLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimeLimit))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, AddProblemPanelLayout.createSequentialGroup()
                                .addComponent(MemoryLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMemoryLimit))
                            .addComponent(problemLockCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(367, 367, 367))
        );
        AddProblemPanelLayout.setVerticalGroup(
            AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddProblemPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(ChProblemStatementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(AddInputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(AddOutputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109)
                .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProblemNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProblemName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TimeLimitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txtTimeLimit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MemoryLimitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txtMemoryLimit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(problemLockCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SubmitButton)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        ManagePanelTabSwitcher.addTab("Add Problem", AddProblemPanel);

        DelProblemSetjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        DelProblemSetjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        DelProblemsetTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        DelProblemsetTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Problem ID", "Problem Name", "Problem Setter", ""
            }
        ));
        DelProblemsetTable.setFocusable(false);
        DelProblemsetTable.setGridColor(new java.awt.Color(255, 255, 255));
        DelProblemsetTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        DelProblemsetTable.setOpaque(false);
        DelProblemsetTable.setRequestFocusEnabled(false);
        DelProblemsetTable.setRowHeight(25);
        DelProblemsetTable.setRowSelectionAllowed(false);
        DelProblemsetTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        DelProblemsetTable.setShowHorizontalLines(false);
        DelProblemsetTable.getTableHeader().setReorderingAllowed(false);
        DelProblemsetTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DelProblemsetTableMouseClicked(evt);
            }
        });
        DelProblemSetjScrollPane.setViewportView(DelProblemsetTable);

        javax.swing.GroupLayout DeleteProblemPanelLayout = new javax.swing.GroupLayout(DeleteProblemPanel);
        DeleteProblemPanel.setLayout(DeleteProblemPanelLayout);
        DeleteProblemPanelLayout.setHorizontalGroup(
            DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 849, Short.MAX_VALUE)
            .addGroup(DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(DelProblemSetjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 849, Short.MAX_VALUE))
        );
        DeleteProblemPanelLayout.setVerticalGroup(
            DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 522, Short.MAX_VALUE)
            .addGroup(DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(DelProblemSetjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE))
        );

        ManagePanelTabSwitcher.addTab("Delete Problem", DeleteProblemPanel);

        AddContestPanel.setBackground(new java.awt.Color(255, 255, 255));
        AddContestPanel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        ProblemNameLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ProblemNameLabel1.setForeground(new java.awt.Color(54, 33, 89));
        ProblemNameLabel1.setText("Contest Name:");
        ProblemNameLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        TimeLimitLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        TimeLimitLabel1.setForeground(new java.awt.Color(54, 33, 89));
        TimeLimitLabel1.setText("Start Time:");
        TimeLimitLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        contestNameTextField.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        contestNameTextField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));

        SubmitContest.setBackground(new java.awt.Color(54, 33, 89));
        SubmitContest.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SubmitContest.setForeground(new java.awt.Color(54, 33, 89));
        SubmitContest.setText("Submit");
        SubmitContest.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 204)), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        SubmitContest.setContentAreaFilled(false);
        SubmitContest.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SubmitContest.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SubmitContest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitContestActionPerformed(evt);
            }
        });

        DurationLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        DurationLabel.setForeground(new java.awt.Color(54, 33, 89));
        DurationLabel.setText("Duration:");
        DurationLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        dateTimeTextField.setEditable(false);
        dateTimeTextField.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        dateTimeTextField.setText("DD/MM/YYYY:HH24:MM");
        dateTimeTextField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));

        OpenCalendarButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        OpenCalendarButton.setText("Open Calendar");
        OpenCalendarButton.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        OpenCalendarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenCalendarButtonActionPerformed(evt);
            }
        });

        durationTextField.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        durationTextField.setText("Minutes");
        durationTextField.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));

        addContestProblemTable.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        addContestProblemTable.setModel(new javax.swing.table.DefaultTableModel(
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
        addContestProblemTable.setFocusable(false);
        addContestProblemTable.setRowHeight(23);
        addContestProblemTable.setRowSelectionAllowed(false);
        addContestProblemTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addContestProblemTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(addContestProblemTable);

        addProblemToContestLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        addProblemToContestLabel.setForeground(new java.awt.Color(54, 33, 89));
        addProblemToContestLabel.setText("Added Problems");
        addProblemToContestLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        contestProblemCombo.setMaximumRowCount(-1);
        contestProblemCombo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        markContestLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        markContestLabel.setForeground(new java.awt.Color(54, 33, 89));
        markContestLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        markContestLabel.setText("Mark Problem for Contest");
        markContestLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        javax.swing.GroupLayout AddContestPanelLayout = new javax.swing.GroupLayout(AddContestPanel);
        AddContestPanel.setLayout(AddContestPanelLayout);
        AddContestPanelLayout.setHorizontalGroup(
            AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddContestPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(markContestLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(AddContestPanelLayout.createSequentialGroup()
                        .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(DurationLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ProblemNameLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                                .addComponent(TimeLimitLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(addProblemToContestLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contestNameTextField)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddContestPanelLayout.createSequentialGroup()
                                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateTimeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                                    .addComponent(durationTextField))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(OpenCalendarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(contestProblemCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddContestPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(SubmitContest, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        AddContestPanelLayout.setVerticalGroup(
            AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddContestPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProblemNameLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contestNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OpenCalendarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TimeLimitLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DurationLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(durationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contestProblemCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addProblemToContestLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(markContestLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SubmitContest))
        );

        ManagePanelTabSwitcher.addTab("Add Contest", AddContestPanel);

        javax.swing.GroupLayout ManagePanelLayout = new javax.swing.GroupLayout(ManagePanel);
        ManagePanel.setLayout(ManagePanelLayout);
        ManagePanelLayout.setHorizontalGroup(
            ManagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManagePanelLayout.createSequentialGroup()
                .addComponent(ManagePanelTabSwitcher)
                .addGap(0, 0, 0))
        );
        ManagePanelLayout.setVerticalGroup(
            ManagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ManagePanelLayout.createSequentialGroup()
                .addComponent(ManagePanelTabSwitcher)
                .addGap(0, 0, 0))
        );

        AdminDashboardTabSwitcher.addTab("Manage", ManagePanel);

        StatusScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        StatusTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
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
                StatusTableMouseClicked(evt);
            }
        });
        StatusScrollPane.setViewportView(StatusTable);
        if (StatusTable.getColumnModel().getColumnCount() > 0) {
            StatusTable.getColumnModel().getColumn(1).setHeaderValue("When");
            StatusTable.getColumnModel().getColumn(4).setHeaderValue("Lang");
            StatusTable.getColumnModel().getColumn(5).setHeaderValue("Verdict");
            StatusTable.getColumnModel().getColumn(6).setHeaderValue("Time");
        }

        javax.swing.GroupLayout StatusPanelLayout = new javax.swing.GroupLayout(StatusPanel);
        StatusPanel.setLayout(StatusPanelLayout);
        StatusPanelLayout.setHorizontalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StatusScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
        );
        StatusPanelLayout.setVerticalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StatusScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
        );

        AdminDashboardTabSwitcher.addTab("Status", StatusPanel);

        StandingsScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        StandingsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 14)); // NOI18N
        StandingsTable.setModel(new javax.swing.table.DefaultTableModel(
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
                "#", "ID", "Problems"
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

        javax.swing.GroupLayout StandingsPanelLayout = new javax.swing.GroupLayout(StandingsPanel);
        StandingsPanel.setLayout(StandingsPanelLayout);
        StandingsPanelLayout.setHorizontalGroup(
            StandingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StandingsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
        );
        StandingsPanelLayout.setVerticalGroup(
            StandingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StandingsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
        );

        AdminDashboardTabSwitcher.addTab("Standings", StandingsPanel);

        AdminDashboardDesktopPane.setLayer(AdminDashboardTabSwitcher, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout AdminDashboardDesktopPaneLayout = new javax.swing.GroupLayout(AdminDashboardDesktopPane);
        AdminDashboardDesktopPane.setLayout(AdminDashboardDesktopPaneLayout);
        AdminDashboardDesktopPaneLayout.setHorizontalGroup(
            AdminDashboardDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardDesktopPaneLayout.createSequentialGroup()
                .addComponent(AdminDashboardTabSwitcher)
                .addGap(0, 0, 0))
        );
        AdminDashboardDesktopPaneLayout.setVerticalGroup(
            AdminDashboardDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminDashboardDesktopPaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(AdminDashboardTabSwitcher)
                .addContainerGap())
        );

        getContentPane().add(AdminDashboardDesktopPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void updateProblemSetTab() {
        problemTable = adminSocket.getProblemTable(null);
        if (problemTable == null) {
            JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] columns = {"Problem ID", "Problem Name", "Problem Setter", "Locked"};
            DefaultTableModel tablemodel = new DefaultTableModel(problemTable, columns) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            ProblemsetTable.setModel(tablemodel);
        }
    }

    private void updateMyProblemTab() {
        myProblemTable = adminSocket.getProblemTable("my");
        if (myProblemTable == null) {
            JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] columns = {"Problem ID", "Problem Name", "Problem Setter", "Locked"};
            DefaultTableModel tablemodel = new DefaultTableModel(myProblemTable, columns) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            MyProblemsTable.setModel(tablemodel);
        }
    }

    private void updateContestTab() {
        contestTable = adminSocket.getContestTable();
        if (contestTable == null) {
            JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] columns = {"Contest_ID", "Contest Name", "Author", "Start Time", "Duration(min)", "Status"};
            DefaultTableModel tablemodel = new DefaultTableModel(contestTable, columns) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            ContestTable.setModel(tablemodel);
        }
    }

    private void updateStatusTab() {
        statusTable = adminSocket.getStatusTable();
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
    }

    private void updateStandingTab() {
        standingTable = adminSocket.getStandingsTable();
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
    }

    private void AdminDashboardTabSwitcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdminDashboardTabSwitcherMouseClicked

        int x = AdminDashboardTabSwitcher.getSelectedIndex();
        switch (x) {
            case 1:
                updateProblemSetTab();
                break;
            case 2:
                updateMyProblemTab();
                break;
            case 3:
                updateContestTab();
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
    }//GEN-LAST:event_AdminDashboardTabSwitcherMouseClicked

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutButtonActionPerformed
        parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogOutButtonActionPerformed

    private void updateDeleteProblemTab() {
        Object[][] table;
        table = adminSocket.getProblemTable("my");
        if (table == null) {
            JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] columns = {"Problem ID", "Problem Name", "Problem Setter", "Locked"," "};
            DefaultTableModel tablemodel = new DefaultTableModel(table, columns) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };
            DelProblemsetTable.setModel(tablemodel);
        }
    }

    private void updateAddContestTab() {
        problemTable = adminSocket.getProblemTable(null);
        for (int i = 0; problemTable[i] != null; i++) {
            problemTable[i][3] = "Not Added";
        }
        if (problemTable == null) {
            JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String[] columns = {"Problem ID", "Problem Name", "Problem Setter", ""};
            DefaultTableModel tablemodel = new DefaultTableModel(problemTable, columns) {
                @Override
                public boolean isCellEditable(int row, int col) {
                    return false;
                }
            };

            addContestProblemTable.setModel(tablemodel);
        }
    }
    private void ContestTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContestTableMouseClicked
        if (evt.getClickCount() == 1 & !evt.isConsumed()) {
            evt.consume();
            int row = ContestTable.rowAtPoint(evt.getPoint());
            int col = ContestTable.columnAtPoint(evt.getPoint());
            if (row >= 0 && col == 1 && ContestTable.getValueAt(row, col) != null) {
                String contestID = contestTable[row][0].toString();
                ContestInfo contest = adminSocket.getContestInfo(contestID);
                ContestDashboard contestArea = new ContestDashboard(adminSocket, this, contest);
                contestArea.setVisible(true);
                this.setVisible(false);
            }
        }
    }//GEN-LAST:event_ContestTableMouseClicked

    private void ProblemsetTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ProblemsetTableMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            evt.consume();
            int row = ProblemsetTable.rowAtPoint(evt.getPoint());
            int col = ProblemsetTable.columnAtPoint(evt.getPoint());
            if (row >= 0 && col == 1 && ProblemsetTable.getValueAt(row, col) != null) {
                NewProblem newProblem = adminSocket.getProblem(ProblemsetTable.getValueAt(row, 0).toString());
                ProblemShow showDialog = new ProblemShow();
                showDialog.showProblem(newProblem);
            }
        }
    }//GEN-LAST:event_ProblemsetTableMouseClicked

    private void MyProblemsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MyProblemsTableMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            evt.consume();
            int row = MyProblemsTable.rowAtPoint(evt.getPoint());
            int col = MyProblemsTable.columnAtPoint(evt.getPoint());
            if (row >= 0 && col == 1 && MyProblemsTable.getValueAt(row, col) != null) {
                NewProblem newProblem = adminSocket.getProblem(MyProblemsTable.getValueAt(row, 0).toString());
                ProblemShow showDialog = new ProblemShow();
                showDialog.showProblem(newProblem);
            }
        }
    }//GEN-LAST:event_MyProblemsTableMouseClicked

    private void StatusTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StatusTableMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            evt.consume();
            int row = StatusTable.rowAtPoint(evt.getPoint());
            int col = StatusTable.columnAtPoint(evt.getPoint());

            if (row >= 0 && col == 0 && StatusTable.getValueAt(row, col) != null) {
                SubmissionShow subshow = new SubmissionShow();
                String submissionid = statusTable[row][0].toString();
                subshow.setSubDetailsTable(statusTable[row]);
                NewSubmission submission = adminSocket.getSubmission(submissionid);
                subshow.setSourceCode(submission);

            } else if (row >= 0 && col == 3 && StatusTable.getValueAt(row, col) != null) {
                ProblemShow showDialog = new ProblemShow();
                showDialog.showProblem(adminSocket.getProblem(statusTable[row][7].toString()));
            }
        }
    }//GEN-LAST:event_StatusTableMouseClicked

    private void ManagePanelTabSwitcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ManagePanelTabSwitcherMouseClicked

        int x = ManagePanelTabSwitcher.getSelectedIndex();
        switch (x) {
            case 1:
            updateDeleteProblemTab();
            break;
            case 2:
            updateAddContestTab();
            break;

        }
    }//GEN-LAST:event_ManagePanelTabSwitcherMouseClicked

    private void addContestProblemTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addContestProblemTableMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            evt.consume();
            int row = addContestProblemTable.rowAtPoint(evt.getPoint());
            int col = addContestProblemTable.columnAtPoint(evt.getPoint());
            if (row >= 0 && col == 1 && addContestProblemTable.getValueAt(row, col) != null) {

                NewProblem newProblem = adminSocket.getProblem(addContestProblemTable.getValueAt(row, 0).toString());
                ProblemShow showDialog = new ProblemShow();
                showDialog.showProblem(newProblem);

            } else if (row >= 0 && col == 3 && addContestProblemTable.getValueAt(row, col) != null) {
                if (addContestProblemTable.getValueAt(row, col).equals("Not Added")) {
                    contestInfo.addProblem(problemTable[row][0].toString());
                    contestProblemCombo.addItem(addContestProblemTable.getValueAt(row, 1).toString());
                    addContestProblemTable.setValueAt("Remove", row, col);
                } else if (addContestProblemTable.getValueAt(row, col).equals("Remove")) {
                    contestInfo.removeProblem(problemTable[row][0].toString());
                    contestProblemCombo.removeItem(addContestProblemTable.getValueAt(row, 1).toString());
                    addContestProblemTable.setValueAt("Not Added", row, col);
                }
            }
        }
    }//GEN-LAST:event_addContestProblemTableMouseClicked

    private void OpenCalendarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenCalendarButtonActionPerformed
        DateTimePicker pickDateTime = new DateTimePicker(this, true);
        pickDateTime.setVisible(rootPaneCheckingEnabled);
        contestInfo.setStartTime(pickDateTime.getDateTime());
        dateTimeTextField.setText(contestInfo.getStartTime().toString());
    }//GEN-LAST:event_OpenCalendarButtonActionPerformed

    private void SubmitContestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitContestActionPerformed

        contestInfo.setContestName(contestNameTextField.getText());
        try {
            Integer.parseUnsignedInt(durationTextField.getText());
            contestInfo.setDurationMinutes(durationTextField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Duration must be Postive Number", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (contestInfo.getContestName() == null || contestInfo.getStartTime() == null || contestInfo.getdurationMinutes() == null || contestInfo.getProblemIDs().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Some input fields are empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (adminSocket.submitContest(contestInfo)) {
            JOptionPane.showMessageDialog(null, "Submitted", "Status", JOptionPane.INFORMATION_MESSAGE);
            contestInfo = new ContestInfo();
            contestNameTextField.setText(null);
            durationTextField.setText(null);
            dateTimeTextField.setText(null);
            contestProblemCombo.removeAllItems();
            for (int i = 0; problemTable[i] != null; i++) {
                addContestProblemTable.setValueAt("Not Added", i, 3);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Submission Failed", "Status", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_SubmitContestActionPerformed

    private void DelProblemsetTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DelProblemsetTableMouseClicked
        if (evt.getClickCount() == 1 && !evt.isConsumed()) {
            evt.consume();
            int row = DelProblemsetTable.rowAtPoint(evt.getPoint());
            int col = DelProblemsetTable.columnAtPoint(evt.getPoint());
            if (row >= 0 && col == 4 && DelProblemsetTable.getValueAt(row, col) != null) {
                String problemID = DelProblemsetTable.getValueAt(row, 0).toString();
                String problemName = DelProblemsetTable.getValueAt(row, 1).toString();
                if (JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete this problem: " + problemID + "-" + problemName + "?", "Delete Problem", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    String status = adminSocket.deleteProblem(problemID);
                    JOptionPane.showMessageDialog(rootPane, status);
                    updateDeleteProblemTab();
                }
            }
            if (row >= 0 && col == 3 && DelProblemsetTable.getValueAt(row, col) != null) {
                String problemID = DelProblemsetTable.getValueAt(row, 0).toString();
                String state = DelProblemsetTable.getValueAt(row, col).toString();
                state = ((state.compareTo("YES")==0)? "NO":"YES");
                String status = adminSocket.changeProblemLockedState(problemID, state);
                JOptionPane.showMessageDialog(rootPane, status);
                updateDeleteProblemTab();   
            }

        }
    }//GEN-LAST:event_DelProblemsetTableMouseClicked

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButtonActionPerformed

        String problemname = txtProblemName.getText();
        String timelimit = txtTimeLimit.getText();
        String memorylimit = txtMemoryLimit.getText();
        if (problemname.length() <= 0 || timelimit.length() <= 0 || memorylimit.length() <= 0) {
            JOptionPane.showMessageDialog(null, "Please fill Problem Name, Timelimit, Memorylimit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (Integer.parseInt(timelimit) < 0 || Integer.parseInt(memorylimit) < 0) {
            JOptionPane.showMessageDialog(null, "Timelimit or Memorylimit cannot be negative", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (inputs == null || problem == null || outputs == null) {
            JOptionPane.showMessageDialog(null, "Missing Files", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String locked = ((problemLockCheckBox.isSelected())? "YES":"NO");
        adminSocket.addProblem(problem, inputs, outputs, locked, problemname, timelimit, memorylimit);
    }//GEN-LAST:event_SubmitButtonActionPerformed

    private void AddInputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddInputButtonActionPerformed
        JFileChooser filemanager = new JFileChooser("Documents");

        filemanager.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filemanager.addChoosableFileFilter(new FileNameExtensionFilter("Text Documents", "txt"));
        filemanager.showOpenDialog(this);
        inputs = filemanager.getSelectedFile();

        if (inputs != null) {
            String extension = inputs.getName();
            int x = extension.lastIndexOf('.');
            if (x > 0) {
                extension = extension.substring(x);
            }
            if (extension.equals(".txt")) {
                AddInputButton.setText(inputs.getName());
            } else {
                JOptionPane.showMessageDialog(null, "Select txt file", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Input files missing", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AddInputButtonActionPerformed

    private void ChProblemStatementButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChProblemStatementButtonActionPerformed
        JFileChooser filemanager = new JFileChooser("Documents");

        filemanager.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filemanager.addChoosableFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
        filemanager.showOpenDialog(this);
        problem = filemanager.getSelectedFile();
        if (problem != null) {

            String extension = problem.getName();
            int x = extension.lastIndexOf('.');
            if (x > 0) {
                extension = extension.substring(x);
            }

            if (extension.equals(".pdf") || extension.equals(".PDF")) {
                ChProblemStatementButton.setText(problem.getName());
            } else {
                JOptionPane.showMessageDialog(null, "Select pdf file", "Error", JOptionPane.ERROR_MESSAGE);
                problem = null;
            }

        } else {
            JOptionPane.showMessageDialog(null, "Problem file missing", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_ChProblemStatementButtonActionPerformed

    private void AddOutputButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddOutputButtonActionPerformed
        JFileChooser filemanager = new JFileChooser("Documents");

        filemanager.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filemanager.addChoosableFileFilter(new FileNameExtensionFilter("Text Document", "txt"));
        filemanager.showOpenDialog(this);
        outputs = filemanager.getSelectedFile();
        if (outputs != null) {
            String extension = outputs.getName();
            int x = extension.lastIndexOf('.');
            if (x > 0) {
                extension = extension.substring(x);
            }

            if (extension.equals(".txt")) {
                AddOutputButton.setText(outputs.getName());
            } else {
                JOptionPane.showMessageDialog(null, "Select txt file", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Output file missing", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AddOutputButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddContestPanel;
    private javax.swing.JButton AddInputButton;
    private javax.swing.JButton AddOutputButton;
    private javax.swing.JPanel AddProblemPanel;
    private javax.swing.JDesktopPane AdminDashboardDesktopPane;
    private javax.swing.JTabbedPane AdminDashboardTabSwitcher;
    private javax.swing.JButton ChProblemStatementButton;
    private javax.swing.JTable ContestTable;
    private javax.swing.JScrollPane ContestTableScrollPane;
    private javax.swing.JPanel ContestsPanel;
    private javax.swing.JScrollPane DelProblemSetjScrollPane;
    private javax.swing.JTable DelProblemsetTable;
    private javax.swing.JPanel DeleteProblemPanel;
    private javax.swing.JLabel DurationLabel;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JPanel ManagePanel;
    private javax.swing.JTabbedPane ManagePanelTabSwitcher;
    private javax.swing.JLabel MemoryLimitLabel;
    private javax.swing.JPanel MyProblemsPanel;
    private javax.swing.JTable MyProblemsTable;
    private javax.swing.JScrollPane MyProblemsjScrollPane;
    private javax.swing.JButton OpenCalendarButton;
    private javax.swing.JLabel ProblemNameLabel;
    private javax.swing.JLabel ProblemNameLabel1;
    private javax.swing.JScrollPane ProblemSetjScrollPane;
    private javax.swing.JPanel ProblemsetPanel;
    private javax.swing.JTable ProblemsetTable;
    private javax.swing.JPanel StandingsPanel;
    private javax.swing.JScrollPane StandingsScrollPane;
    private javax.swing.JTable StandingsTable;
    private javax.swing.JPanel StatusPanel;
    private javax.swing.JScrollPane StatusScrollPane;
    private javax.swing.JTable StatusTable;
    private javax.swing.JButton SubmitButton;
    private javax.swing.JButton SubmitContest;
    private javax.swing.JLabel TimeLimitLabel;
    private javax.swing.JLabel TimeLimitLabel1;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JTable addContestProblemTable;
    private javax.swing.JLabel addProblemToContestLabel;
    private javax.swing.JTextField contestNameTextField;
    private javax.swing.JComboBox<String> contestProblemCombo;
    private javax.swing.JTextField dateTimeTextField;
    private javax.swing.JTextField durationTextField;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel markContestLabel;
    private javax.swing.JCheckBox problemLockCheckBox;
    private javax.swing.JFormattedTextField txtMemoryLimit;
    private javax.swing.JTextField txtProblemName;
    private javax.swing.JFormattedTextField txtTimeLimit;
    // End of variables declaration//GEN-END:variables
}
