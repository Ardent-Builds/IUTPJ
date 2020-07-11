/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_user;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 *
 * @author KAWSAR
 */
public class UserDashboard extends javax.swing.JFrame {

    /**
     * Creates new form UserDashboard
     */
    private final UserSocket usersocket;
    private File codefile;
    private UserDashboard temporary;
    private Login login;

    public UserDashboard(UserSocket usersocket, Login login) {
        initComponents();
        this.usersocket = usersocket;
        this.codefile = null;
        temporary = this;
        this.login = login;

        setBackground(new Color(0, 0, 0));
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 1 ? new Color(242, 242, 242) : Color.WHITE);

                return c;
            }

        };
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        StatusTable.setDefaultRenderer(Object.class, centerRenderer);
        StatusTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        StatusTable.setRowHeight(25);
        JTableHeader statustableheader = StatusTable.getTableHeader();
        ((DefaultTableCellRenderer) statustableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        MySubTable.setDefaultRenderer(Object.class, centerRenderer);
        MySubTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        MySubTable.setRowHeight(25);
        JTableHeader mysubtableheader = MySubTable.getTableHeader();
        ((DefaultTableCellRenderer) mysubtableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        StandingsTable.setDefaultRenderer(Object.class, centerRenderer);
        StandingsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        StandingsTable.setRowHeight(25);
        StandingsTable.setRowHeight(25);
        JTableHeader standingstableheader = StandingsTable.getTableHeader();
        ((DefaultTableCellRenderer) standingstableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        //ProblemsetTable.getTableHeader().setOpaque(false);
        ProblemsetTable.setDefaultRenderer(Object.class, centerRenderer);
        ProblemsetTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        ProblemsetTable.setRowHeight(25);
        JTableHeader problemsettableheader = ProblemsetTable.getTableHeader();
        ((DefaultTableCellRenderer) problemsettableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        //ProblemsetTable.setPreferredSize(new Dimension(920, 620));
        //ProblemsetTable.setPreferredScrollableViewportSize(ProblemsetTable.getParent().getPreferredSize());
        //ProblemsetTable.setFillsViewportHeight(true);

        ProblemsetTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) {
                    evt.consume();
                    int row = ProblemsetTable.rowAtPoint(evt.getPoint());
                    int col = ProblemsetTable.columnAtPoint(evt.getPoint());

                    if (row >= 0 && (col == 0 || col == 1)) {
                        DefaultTableModel tablemodel = (DefaultTableModel) ProblemsetTable.getModel();
                        if (tablemodel.getValueAt(row, 0) != null) {
                            String temp = tablemodel.getValueAt(row, 0).toString();
                            int x = temp.indexOf('<', 28);
                            String problemid = temp.substring(28, x);

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

        StatusTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) {
                    evt.consume();
                    int row = StatusTable.rowAtPoint(evt.getPoint());
                    int col = StatusTable.columnAtPoint(evt.getPoint());
                    if (row >= 0 && col == 3) {
                        DefaultTableModel tablemodel = (DefaultTableModel) StatusTable.getModel();
                        if (tablemodel.getValueAt(row, 3) != null) {
                            String temp = tablemodel.getValueAt(row, 3).toString();
                            int x = temp.indexOf('-', 28);
                            String problemid = temp.substring(28, x);

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
                            SubmissionShow subshow = new SubmissionShow(usersocket, temporary);
                            String temp = tablemodel.getValueAt(row, 0).toString();
                            int x = temp.indexOf('<', 28);
                            String submissionid = temp.substring(28, x);
                            subshow.setSubDetailsTable(submissionid, tablemodel.getValueAt(row, 2), tablemodel.getValueAt(row, 3), tablemodel.getValueAt(row, 4), tablemodel.getValueAt(row, 5), tablemodel.getValueAt(row, 6), tablemodel.getValueAt(row, 1));

                            usersocket.sendData("SrcCode-[" + submissionid + "]");
                            NewSubmission submission = usersocket.getSubmission();
                            subshow.setSourceCode(submission);
                        }

                    } else if (row >= 0 && col == 3) {
                        DefaultTableModel tablemodel = (DefaultTableModel) MySubTable.getModel();
                        if (tablemodel.getValueAt(row, 3) != null) {
                            String temp = tablemodel.getValueAt(row, 3).toString();
                            int x = temp.indexOf('-', 28);
                            String problemid = temp.substring(28, x);

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
        ContestsPanelTabSwitcher = new javax.swing.JTabbedPane();
        PrevContestsPanel = new javax.swing.JPanel();
        PrevContestsjScrollPane = new javax.swing.JScrollPane();
        PrevContestsTable = new javax.swing.JTable();
        UpContestsPanel = new javax.swing.JPanel();
        UpContestsjScrollPane = new javax.swing.JScrollPane();
        UpContestsTable = new javax.swing.JTable();

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
        ProblemsetTable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                ProblemsetTableComponentResized(evt);
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
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        UserDashboardTabSwitcher.addTab("Problemset", ProblemsetPanel);

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
        StatusScrollPane.setViewportView(StatusTable);

        StatusPanel.add(StatusScrollPane, java.awt.BorderLayout.CENTER);

        UserDashboardTabSwitcher.addTab("Status", StatusPanel);

        MySubPanel.setLayout(new java.awt.BorderLayout());

        MySubScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        MySubTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
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
        MySubScrollPane.setViewportView(MySubTable);

        MySubPanel.add(MySubScrollPane, java.awt.BorderLayout.CENTER);

        UserDashboardTabSwitcher.addTab("My Submissions", MySubPanel);

        StandingsPanel.setLayout(new java.awt.BorderLayout());

        StandingsScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        StandingsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
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

        ContestsPanelTabSwitcher.setForeground(new java.awt.Color(54, 33, 89));
        ContestsPanelTabSwitcher.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ContestsPanelTabSwitcher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ContestsPanelTabSwitcherMouseClicked(evt);
            }
        });

        PrevContestsjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        PrevContestsjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        PrevContestsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        PrevContestsTable.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null}
            },
            new String [] {
                "Contest ID", "Contest Name", "Author"
            }
        ));
        PrevContestsTable.setFocusable(false);
        PrevContestsTable.setGridColor(new java.awt.Color(255, 255, 255));
        PrevContestsTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        PrevContestsTable.setOpaque(false);
        PrevContestsTable.setRequestFocusEnabled(false);
        PrevContestsTable.setRowHeight(25);
        PrevContestsTable.setRowSelectionAllowed(false);
        PrevContestsTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        PrevContestsTable.setShowHorizontalLines(false);
        PrevContestsTable.getTableHeader().setReorderingAllowed(false);
        PrevContestsTable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                PrevContestsTableComponentResized(evt);
            }
        });
        PrevContestsjScrollPane.setViewportView(PrevContestsTable);

        javax.swing.GroupLayout PrevContestsPanelLayout = new javax.swing.GroupLayout(PrevContestsPanel);
        PrevContestsPanel.setLayout(PrevContestsPanelLayout);
        PrevContestsPanelLayout.setHorizontalGroup(
            PrevContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 808, Short.MAX_VALUE)
            .addGroup(PrevContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PrevContestsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE))
        );
        PrevContestsPanelLayout.setVerticalGroup(
            PrevContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 261, Short.MAX_VALUE)
            .addGroup(PrevContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PrevContestsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
        );

        ContestsPanelTabSwitcher.addTab("Previous Contests", PrevContestsPanel);

        UpContestsjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        UpContestsjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        UpContestsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        UpContestsTable.setModel(new javax.swing.table.DefaultTableModel(
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
                {null, null, null}
            },
            new String [] {
                "Contest ID", "Contest Name", "Author"
            }
        ));
        UpContestsTable.setFocusable(false);
        UpContestsTable.setGridColor(new java.awt.Color(255, 255, 255));
        UpContestsTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        UpContestsTable.setOpaque(false);
        UpContestsTable.setRequestFocusEnabled(false);
        UpContestsTable.setRowHeight(25);
        UpContestsTable.setRowSelectionAllowed(false);
        UpContestsTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        UpContestsTable.setShowHorizontalLines(false);
        UpContestsTable.getTableHeader().setReorderingAllowed(false);
        UpContestsTable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                UpContestsTableComponentResized(evt);
            }
        });
        UpContestsjScrollPane.setViewportView(UpContestsTable);

        javax.swing.GroupLayout UpContestsPanelLayout = new javax.swing.GroupLayout(UpContestsPanel);
        UpContestsPanel.setLayout(UpContestsPanelLayout);
        UpContestsPanelLayout.setHorizontalGroup(
            UpContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 808, Short.MAX_VALUE)
            .addGroup(UpContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(UpContestsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE))
        );
        UpContestsPanelLayout.setVerticalGroup(
            UpContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 261, Short.MAX_VALUE)
            .addGroup(UpContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(UpContestsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
        );

        ContestsPanelTabSwitcher.addTab("Upcoming Contests", UpContestsPanel);

        javax.swing.GroupLayout ContestsPanelLayout = new javax.swing.GroupLayout(ContestsPanel);
        ContestsPanel.setLayout(ContestsPanelLayout);
        ContestsPanelLayout.setHorizontalGroup(
            ContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContestsPanelLayout.createSequentialGroup()
                .addComponent(ContestsPanelTabSwitcher)
                .addGap(0, 0, 0))
        );
        ContestsPanelLayout.setVerticalGroup(
            ContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContestsPanelLayout.createSequentialGroup()
                .addComponent(ContestsPanelTabSwitcher)
                .addGap(0, 0, 0))
        );

        UserDashboardTabSwitcher.addTab("Contests", ContestsPanel);

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

    public void setTab(int x, String ID) {
        UserDashboardTabSwitcher.setSelectedIndex(x);
        txtProblemID.setText(ID);
        txtProblemID.setEditable(false);
    }
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
        Object[][] table;
        switch (x) {

            case 1:

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

                    ProblemsetTable.setModel(tablemodel);
                }

                break;
            case 2:
                txtProblemID.setEditable(true);
                break;
            case 3:

                usersocket.sendData("StTable-[nullus]");
                table = usersocket.getStatusTable();
                if (table == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"};
                    DefaultTableModel tablemodel = new DefaultTableModel(table, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };

                    StatusTable.setModel(tablemodel);
                }

                break;
            case 4:
                usersocket.sendData("StTable-[My]");
                table = usersocket.getStatusTable();
                if (table == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"#", "When", "Who", "Problem", "Lang", "Verdict", "Time"};
                    DefaultTableModel tablemodel = new DefaultTableModel(table, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };

                    MySubTable.setModel(tablemodel);
                }
                break;
            case 5:
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
    }//GEN-LAST:event_UserDashboardTabSwitcherMouseClicked

    private void ProblemsetTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_ProblemsetTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_ProblemsetTableComponentResized

    private void PrevContestsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PrevContestsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_PrevContestsTableComponentResized

    private void UpContestsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_UpContestsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_UpContestsTableComponentResized

    private void ContestsPanelTabSwitcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContestsPanelTabSwitcherMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ContestsPanelTabSwitcherMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ChooseFileButton;
    private javax.swing.JLabel ChooseFileLabel;
    private javax.swing.JPanel ContestsPanel;
    private javax.swing.JTabbedPane ContestsPanelTabSwitcher;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JComboBox LanguageComboBox;
    private javax.swing.JLabel LanguageLabel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JPanel MySubPanel;
    private javax.swing.JScrollPane MySubScrollPane;
    private javax.swing.JTable MySubTable;
    private javax.swing.JPanel PrevContestsPanel;
    private javax.swing.JTable PrevContestsTable;
    private javax.swing.JScrollPane PrevContestsjScrollPane;
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
    private javax.swing.JPanel UpContestsPanel;
    private javax.swing.JTable UpContestsTable;
    private javax.swing.JScrollPane UpContestsjScrollPane;
    private javax.swing.JTabbedPane UserDashboardTabSwitcher;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField txtProblemID;
    // End of variables declaration//GEN-END:variables
}
