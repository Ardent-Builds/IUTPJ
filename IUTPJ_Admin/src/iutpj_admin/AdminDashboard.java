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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
public class AdminDashboard extends javax.swing.JFrame {

    /**
     * Creates new form UserDashboard
     */
    private final AdminSocket adminsocket;
    private File problem, inputs, outputs;
    Login parent;
    private Object[][] problemTable, statusTable, myProblemTable, standingTable;

    public AdminDashboard(AdminSocket adminsocket, Login parent) {
        initComponents();
        this.adminsocket = adminsocket;
        this.parent = parent;

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
        StatusTable.setRowHeight(25);
        JTableHeader statustableheader = StatusTable.getTableHeader();
        ((DefaultTableCellRenderer) statustableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        StandingsTable.setDefaultRenderer(Object.class, centerRenderer);
        StandingsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        StandingsTable.setRowHeight(25);
        StandingsTable.setRowHeight(25);
        JTableHeader standingstableheader = StandingsTable.getTableHeader();
        ((DefaultTableCellRenderer) standingstableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        ProblemsetTable.setDefaultRenderer(Object.class, centerRenderer);
        ProblemsetTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        ProblemsetTable.setRowHeight(25);
        JTableHeader problemsettableheader = ProblemsetTable.getTableHeader();
        ((DefaultTableCellRenderer) problemsettableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        //ProblemsetTable.getTableHeader().setOpaque(false);

        DelProblemsetTable.setDefaultRenderer(Object.class, centerRenderer);
        DelProblemsetTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        DelProblemsetTable.setRowHeight(25);
        ProblemsetTable.setRowHeight(25);
        JTableHeader delproblemsettableheader = DelProblemsetTable.getTableHeader();
        ((DefaultTableCellRenderer) delproblemsettableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        //DelProblemsetTable.getTableHeader().setBackground(new Color(0, 181, 204));
        //DelProblemsetTable.getTableHeader().setBackground(new Color(255, 255, 255));

        MyProblemsTable.setDefaultRenderer(Object.class, centerRenderer);
        MyProblemsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        MyProblemsTable.setRowHeight(25);
        ProblemsetTable.setRowHeight(25);
        JTableHeader myproblemstableheader = MyProblemsTable.getTableHeader();
        ((DefaultTableCellRenderer) myproblemstableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        //MyProblemsTable.getTableHeader().setBackground(new Color(0, 181, 204));
        //MyProblemsTable.getTableHeader().setBackground(new Color(255, 255, 255));

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
                            String problemid = problemTable[row][4].toString();

                            adminsocket.sendData("ProbFile[" + problemid + "]");
                            NewProblem problem = adminsocket.getProblem();
                            try {
                                FileOutputStream fos = new FileOutputStream(problemid + ".pdf");
                                fos.write(problem.getProb());
                                fos.close();
                            } catch (FileNotFoundException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            } catch (IOException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            }

                            ProblemShow problemshow = new ProblemShow(problem.getProblemName(), problem.getTimeLimit(), problem.getMemoryLimit());
                            problemshow.viewPdf(new File(problemid + ".pdf"));
                        }
                    }
                }
            }
        });

        MyProblemsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) {
                    evt.consume();
                    int row = MyProblemsTable.rowAtPoint(evt.getPoint());
                    int col = MyProblemsTable.columnAtPoint(evt.getPoint());

                    if (row >= 0 && (col == 0 || col == 1)) {
                        DefaultTableModel tablemodel = (DefaultTableModel) MyProblemsTable.getModel();
                        if (tablemodel.getValueAt(row, 0) != null) {
                            String problemid = myProblemTable[row][4].toString();

                            adminsocket.sendData("ProbFile[" + problemid + "]");
                            NewProblem problem = adminsocket.getProblem();
                            try {
                                FileOutputStream fos = new FileOutputStream(problemid + ".pdf");
                                fos.write(problem.getProb());
                                fos.close();
                            } catch (FileNotFoundException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            } catch (IOException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            }
                            ProblemShow problemshow = new ProblemShow(problem.getProblemName(), problem.getTimeLimit(), problem.getMemoryLimit());
                            problemshow.viewPdf(new File(problemid + ".pdf"));
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
                    if (row >= 0 && col == 0) {
                        DefaultTableModel tablemodel = (DefaultTableModel) StatusTable.getModel();
                        if (tablemodel.getValueAt(row, 0) != null) {
                            SubmissionShow subshow = new SubmissionShow(adminsocket);
                            String submissionid = statusTable[row][8].toString();
                            subshow.setSubDetailsTable(submissionid, tablemodel.getValueAt(row, 2), tablemodel.getValueAt(row, 3), tablemodel.getValueAt(row, 4), tablemodel.getValueAt(row, 5), tablemodel.getValueAt(row, 6), tablemodel.getValueAt(row, 1), statusTable[row][7]);

                            adminsocket.sendData("SrcCode-[" + submissionid + "]");
                            NewSubmission submission = adminsocket.getSubmission();
                            subshow.setSourceCOde(submission);
                        }
                    } else if (row >= 0 && col == 3) {
                        DefaultTableModel tablemodel = (DefaultTableModel) StatusTable.getModel();
                        if (tablemodel.getValueAt(row, 3) != null) {
                            String temp = tablemodel.getValueAt(row, 3).toString();
                            int x = temp.indexOf('-', 28);
                            String problemid = temp.substring(28, x);

                            adminsocket.sendData("ProbFile[" + problemid + "]");
                            NewProblem problem = adminsocket.getProblem();
                            try {
                                FileOutputStream fos = new FileOutputStream(problemid + ".pdf");
                                fos.write(problem.getProb());
                                fos.close();
                            } catch (FileNotFoundException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            } catch (IOException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            }

                            ProblemShow problemshow = new ProblemShow(problem.getProblemName(), problem.getTimeLimit(), problem.getMemoryLimit());
                            problemshow.viewPdf(new File(problemid + ".pdf"));
                        }
                    }
                }
            }
        });

        DelProblemsetTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) {
                    evt.consume();
                    int row = DelProblemsetTable.rowAtPoint(evt.getPoint());
                    int col = DelProblemsetTable.columnAtPoint(evt.getPoint());
                    if (row >= 0 && (col == 0 || col == 1)) {
                        DefaultTableModel tablemodel = (DefaultTableModel) DelProblemsetTable.getModel();
                        if (tablemodel.getValueAt(row, 0) != null) {
                            String problemid = myProblemTable[row][4].toString();

                            adminsocket.sendData("ProbFile[" + problemid + "]");
                            NewProblem problem = adminsocket.getProblem();
                            try {
                                FileOutputStream fos = new FileOutputStream(problemid + ".pdf");
                                fos.write(problem.getProb());
                                fos.close();
                            } catch (FileNotFoundException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            } catch (IOException ex) {
                                System.out.println("At probshow problem write Err: " + ex.getMessage());
                            }
                            ProblemShow problemshow = new ProblemShow(problem.getProblemName(), problem.getTimeLimit(), problem.getMemoryLimit());
                            problemshow.viewPdf(new File(problemid + ".pdf"));
                        }
                    } else if (row >= 0 && col == 3) {

                        DefaultTableModel tablemodel = (DefaultTableModel) DelProblemsetTable.getModel();
                        String temp;
                        int x;
                        if (tablemodel.getValueAt(row, 0) != null) {
                            String problemid = myProblemTable[row][4].toString();
                            temp = tablemodel.getValueAt(row, 1).toString();
                            x = temp.indexOf('<', 28);
                            String problemname = temp.substring(28, x);

                            if (JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete this problem: " + problemid + "-" + problemname + "?", "Delete Problem", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                adminsocket.sendData("DelProb-[" + problemid + "]");

                                Object[][] table;
                                table = adminsocket.getProblemTable();
                                if (table == null) {
                                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    String[] columns = {"Problem ID", "Problem Name", "Problem Setter", " "};
                                    DefaultTableModel model = new DefaultTableModel(table, columns) {
                                        public boolean isCellEditable(int row, int col) {
                                            return false;
                                        }
                                    };
                                    DelProblemsetTable.setModel(model);
                                }
                            }
                        }

                    }
                }
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
        ContestsPanelTabSwitcher = new javax.swing.JTabbedPane();
        PrevContestsPanel = new javax.swing.JPanel();
        PrevContestsjScrollPane = new javax.swing.JScrollPane();
        PrevContestsTable = new javax.swing.JTable();
        UpContestsPanel = new javax.swing.JPanel();
        UpContestsjScrollPane = new javax.swing.JScrollPane();
        UpContestsTable = new javax.swing.JTable();
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
        DeleteProblemPanel = new javax.swing.JPanel();
        DelProblemSetjScrollPane = new javax.swing.JScrollPane();
        DelProblemsetTable = new javax.swing.JTable();
        AddContestPanel = new javax.swing.JPanel();
        AddOutputButton1 = new javax.swing.JButton();
        ChProblemStatementButton1 = new javax.swing.JButton();
        AddInputButton1 = new javax.swing.JButton();
        MemoryLimitLabel1 = new javax.swing.JLabel();
        ProblemNameLabel1 = new javax.swing.JLabel();
        TimeLimitLabel1 = new javax.swing.JLabel();
        txtProblemName1 = new javax.swing.JTextField();
        SubmitButton1 = new javax.swing.JButton();
        txtTimeLimit1 = new javax.swing.JFormattedTextField();
        txtMemoryLimit1 = new javax.swing.JFormattedTextField();
        DeleteContestPanel = new javax.swing.JPanel();
        DelProblemSetjScrollPane1 = new javax.swing.JScrollPane();
        DelProblemsetTable1 = new javax.swing.JTable();
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
                .addContainerGap(383, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(383, Short.MAX_VALUE))
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(WelcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        AdminDashboardTabSwitcher.addTab("Home", HomePanel);

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
        ProblemsetTable.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout ProblemsetPanelLayout = new javax.swing.GroupLayout(ProblemsetPanel);
        ProblemsetPanel.setLayout(ProblemsetPanelLayout);
        ProblemsetPanelLayout.setHorizontalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        ProblemsetPanelLayout.setVerticalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        AdminDashboardTabSwitcher.addTab("Problemset", ProblemsetPanel);

        MyProblemsjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        MyProblemsjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        MyProblemsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
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
        MyProblemsTable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                MyProblemsTableComponentResized(evt);
            }
        });
        MyProblemsjScrollPane.setViewportView(MyProblemsTable);

        javax.swing.GroupLayout MyProblemsPanelLayout = new javax.swing.GroupLayout(MyProblemsPanel);
        MyProblemsPanel.setLayout(MyProblemsPanelLayout);
        MyProblemsPanelLayout.setHorizontalGroup(
            MyProblemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyProblemsPanelLayout.createSequentialGroup()
                .addComponent(MyProblemsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        MyProblemsPanelLayout.setVerticalGroup(
            MyProblemsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyProblemsPanelLayout.createSequentialGroup()
                .addComponent(MyProblemsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        AdminDashboardTabSwitcher.addTab("My Problems", MyProblemsPanel);

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
            .addGap(0, 851, Short.MAX_VALUE)
            .addGroup(PrevContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PrevContestsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE))
        );
        PrevContestsPanelLayout.setVerticalGroup(
            PrevContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
            .addGroup(PrevContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(PrevContestsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
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
            .addGap(0, 851, Short.MAX_VALUE)
            .addGroup(UpContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(UpContestsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE))
        );
        UpContestsPanelLayout.setVerticalGroup(
            UpContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
            .addGroup(UpContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(UpContestsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
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

        javax.swing.GroupLayout AddProblemPanelLayout = new javax.swing.GroupLayout(AddProblemPanel);
        AddProblemPanel.setLayout(AddProblemPanelLayout);
        AddProblemPanelLayout.setHorizontalGroup(
            AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddProblemPanelLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(AddProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChProblemStatementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddInputButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddOutputButton, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                    .addGroup(AddProblemPanelLayout.createSequentialGroup()
                        .addComponent(ProblemNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProblemName, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                    .addGroup(AddProblemPanelLayout.createSequentialGroup()
                        .addComponent(TimeLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimeLimit))
                    .addGroup(AddProblemPanelLayout.createSequentialGroup()
                        .addComponent(MemoryLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMemoryLimit)))
                .addGap(367, 367, 367))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddProblemPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(100, 100, 100)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(SubmitButton)
                .addGap(50, 50, 50))
        );

        ManagePanelTabSwitcher.addTab("Add Problem", AddProblemPanel);

        DelProblemSetjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        DelProblemSetjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        DelProblemsetTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
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
        DelProblemsetTable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                DelProblemsetTableComponentResized(evt);
            }
        });
        DelProblemSetjScrollPane.setViewportView(DelProblemsetTable);

        javax.swing.GroupLayout DeleteProblemPanelLayout = new javax.swing.GroupLayout(DeleteProblemPanel);
        DeleteProblemPanel.setLayout(DeleteProblemPanelLayout);
        DeleteProblemPanelLayout.setHorizontalGroup(
            DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 851, Short.MAX_VALUE)
            .addGroup(DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(DelProblemSetjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE))
        );
        DeleteProblemPanelLayout.setVerticalGroup(
            DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 535, Short.MAX_VALUE)
            .addGroup(DeleteProblemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(DelProblemSetjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
        );

        ManagePanelTabSwitcher.addTab("Delete Problem", DeleteProblemPanel);

        AddContestPanel.setBackground(new java.awt.Color(255, 255, 255));
        AddContestPanel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        AddOutputButton1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        AddOutputButton1.setText("Add Output (*.txt)");
        AddOutputButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddOutputButton1ActionPerformed(evt);
            }
        });

        ChProblemStatementButton1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        ChProblemStatementButton1.setText("Choose Problem Statement (*.pdf,*.txt)");
        ChProblemStatementButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChProblemStatementButton1ActionPerformed(evt);
            }
        });

        AddInputButton1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        AddInputButton1.setText("Add Input (*.txt)");
        AddInputButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddInputButton1ActionPerformed(evt);
            }
        });

        MemoryLimitLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        MemoryLimitLabel1.setForeground(new java.awt.Color(54, 33, 89));
        MemoryLimitLabel1.setText("Memory Limit (KB):");

        ProblemNameLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ProblemNameLabel1.setForeground(new java.awt.Color(54, 33, 89));
        ProblemNameLabel1.setText("Problem Name:");

        TimeLimitLabel1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        TimeLimitLabel1.setForeground(new java.awt.Color(54, 33, 89));
        TimeLimitLabel1.setText("Time Limit (ms):");

        SubmitButton1.setBackground(new java.awt.Color(54, 33, 89));
        SubmitButton1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SubmitButton1.setForeground(new java.awt.Color(54, 33, 89));
        SubmitButton1.setText("Submit");
        SubmitButton1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(54, 33, 89)));
        SubmitButton1.setContentAreaFilled(false);
        SubmitButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        SubmitButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        SubmitButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitButton1ActionPerformed(evt);
            }
        });

        txtTimeLimit1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        txtMemoryLimit1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));

        javax.swing.GroupLayout AddContestPanelLayout = new javax.swing.GroupLayout(AddContestPanel);
        AddContestPanel.setLayout(AddContestPanelLayout);
        AddContestPanelLayout.setHorizontalGroup(
            AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddContestPanelLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChProblemStatementButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddInputButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddOutputButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                    .addGroup(AddContestPanelLayout.createSequentialGroup()
                        .addComponent(ProblemNameLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProblemName1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                    .addGroup(AddContestPanelLayout.createSequentialGroup()
                        .addComponent(TimeLimitLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimeLimit1))
                    .addGroup(AddContestPanelLayout.createSequentialGroup()
                        .addComponent(MemoryLimitLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMemoryLimit1)))
                .addGap(367, 367, 367))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddContestPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SubmitButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AddContestPanelLayout.setVerticalGroup(
            AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddContestPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(ChProblemStatementButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(AddInputButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(AddOutputButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProblemNameLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProblemName1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TimeLimitLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txtTimeLimit1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MemoryLimitLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txtMemoryLimit1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(SubmitButton1)
                .addGap(50, 50, 50))
        );

        ManagePanelTabSwitcher.addTab("Add Contest", AddContestPanel);

        DelProblemSetjScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        DelProblemSetjScrollPane1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        DelProblemsetTable1.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        DelProblemsetTable1.setModel(new javax.swing.table.DefaultTableModel(
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
                "Contest ID", "Contest Name", "Author", ""
            }
        ));
        DelProblemsetTable1.setFocusable(false);
        DelProblemsetTable1.setGridColor(new java.awt.Color(255, 255, 255));
        DelProblemsetTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
        DelProblemsetTable1.setOpaque(false);
        DelProblemsetTable1.setRequestFocusEnabled(false);
        DelProblemsetTable1.setRowHeight(25);
        DelProblemsetTable1.setRowSelectionAllowed(false);
        DelProblemsetTable1.setSelectionBackground(new java.awt.Color(0, 181, 204));
        DelProblemsetTable1.setShowHorizontalLines(false);
        DelProblemsetTable1.getTableHeader().setReorderingAllowed(false);
        DelProblemsetTable1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                DelProblemsetTable1ComponentResized(evt);
            }
        });
        DelProblemSetjScrollPane1.setViewportView(DelProblemsetTable1);

        javax.swing.GroupLayout DeleteContestPanelLayout = new javax.swing.GroupLayout(DeleteContestPanel);
        DeleteContestPanel.setLayout(DeleteContestPanelLayout);
        DeleteContestPanelLayout.setHorizontalGroup(
            DeleteContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 844, Short.MAX_VALUE)
            .addGroup(DeleteContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(DelProblemSetjScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 844, Short.MAX_VALUE))
        );
        DeleteContestPanelLayout.setVerticalGroup(
            DeleteContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
            .addGroup(DeleteContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(DelProblemSetjScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE))
        );

        ManagePanelTabSwitcher.addTab("Delete Contest", DeleteContestPanel);

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
        StatusTable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                StatusTableComponentResized(evt);
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
            .addComponent(StatusScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
        );
        StatusPanelLayout.setVerticalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StatusScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );

        AdminDashboardTabSwitcher.addTab("Status", StatusPanel);

        StandingsScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        StandingsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
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
        StandingsTable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                StandingsTableComponentResized(evt);
            }
        });
        StandingsScrollPane.setViewportView(StandingsTable);

        javax.swing.GroupLayout StandingsPanelLayout = new javax.swing.GroupLayout(StandingsPanel);
        StandingsPanel.setLayout(StandingsPanelLayout);
        StandingsPanelLayout.setHorizontalGroup(
            StandingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StandingsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
        );
        StandingsPanelLayout.setVerticalGroup(
            StandingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StandingsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
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

    private void AdminDashboardTabSwitcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AdminDashboardTabSwitcherMouseClicked
        int x = AdminDashboardTabSwitcher.getSelectedIndex();
        switch (x) {

            case 1:

                adminsocket.sendData("PrbTable[null]");
                problemTable = adminsocket.getProblemTable();
                if (problemTable == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"Problem ID", "Problem Name", "Problem Setter"};
                    DefaultTableModel tablemodel = new DefaultTableModel(problemTable, columns) {

                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    ProblemsetTable.setModel(tablemodel);
                }

                break;
            case 2:
                adminsocket.sendData("PrbTable[My]");
                myProblemTable = adminsocket.getProblemTable();
                if (myProblemTable == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"Problem ID", "Problem Name", "Problem Setter"};
                    DefaultTableModel tablemodel = new DefaultTableModel(myProblemTable, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    MyProblemsTable.setModel(tablemodel);
                }
                break;
            case 4:
                adminsocket.sendData("StTable-[nullad]");
                statusTable = adminsocket.getStatusTable();
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
            case 5:
                adminsocket.sendData("StdTable[null]");
                standingTable = adminsocket.getStandingsTable();
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
            default:
                break;

        }

    }//GEN-LAST:event_AdminDashboardTabSwitcherMouseClicked

    private void StatusTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_StatusTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusTableComponentResized

    private void DelProblemsetTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_DelProblemsetTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_DelProblemsetTableComponentResized

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

        try {
            adminsocket.sendData("AddProb-[" + problem.getName() + "][" + inputs.getName() + "][" + outputs.getName() + "]");
            if (adminsocket.addProblem(problem, inputs, outputs, "null", problemname, timelimit, memorylimit) > 0) {
                JOptionPane.showMessageDialog(null, "Problem file Sent", "Status", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Problem Submission failed\nCheck Connection", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    private void MyProblemsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_MyProblemsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_MyProblemsTableComponentResized

    private void ProblemsetTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_ProblemsetTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_ProblemsetTableComponentResized

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutButtonActionPerformed
        parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogOutButtonActionPerformed

    private void ManagePanelTabSwitcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ManagePanelTabSwitcherMouseClicked
        int x = ManagePanelTabSwitcher.getSelectedIndex();
        if (x == 1) {
            Object[][] table;
            adminsocket.sendData("PrbTable[MyDel]");
            table = adminsocket.getProblemTable();
            if (table == null) {
                JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String[] columns = {"Problem ID", "Problem Name", "Problem Setter", " "};
                DefaultTableModel tablemodel = new DefaultTableModel(table, columns) {
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
                DelProblemsetTable.setModel(tablemodel);
            }
        }
    }//GEN-LAST:event_ManagePanelTabSwitcherMouseClicked

    private void StandingsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_StandingsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_StandingsTableComponentResized

    private void PrevContestsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PrevContestsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_PrevContestsTableComponentResized

    private void ContestsPanelTabSwitcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContestsPanelTabSwitcherMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ContestsPanelTabSwitcherMouseClicked

    private void UpContestsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_UpContestsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_UpContestsTableComponentResized

    private void AddOutputButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddOutputButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddOutputButton1ActionPerformed

    private void ChProblemStatementButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChProblemStatementButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChProblemStatementButton1ActionPerformed

    private void AddInputButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddInputButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddInputButton1ActionPerformed

    private void SubmitButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SubmitButton1ActionPerformed

    private void DelProblemsetTable1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_DelProblemsetTable1ComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_DelProblemsetTable1ComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddContestPanel;
    private javax.swing.JButton AddInputButton;
    private javax.swing.JButton AddInputButton1;
    private javax.swing.JButton AddOutputButton;
    private javax.swing.JButton AddOutputButton1;
    private javax.swing.JPanel AddProblemPanel;
    private javax.swing.JDesktopPane AdminDashboardDesktopPane;
    private javax.swing.JTabbedPane AdminDashboardTabSwitcher;
    private javax.swing.JButton ChProblemStatementButton;
    private javax.swing.JButton ChProblemStatementButton1;
    private javax.swing.JPanel ContestsPanel;
    private javax.swing.JTabbedPane ContestsPanelTabSwitcher;
    private javax.swing.JScrollPane DelProblemSetjScrollPane;
    private javax.swing.JScrollPane DelProblemSetjScrollPane1;
    private javax.swing.JTable DelProblemsetTable;
    private javax.swing.JTable DelProblemsetTable1;
    private javax.swing.JPanel DeleteContestPanel;
    private javax.swing.JPanel DeleteProblemPanel;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JPanel ManagePanel;
    private javax.swing.JTabbedPane ManagePanelTabSwitcher;
    private javax.swing.JLabel MemoryLimitLabel;
    private javax.swing.JLabel MemoryLimitLabel1;
    private javax.swing.JPanel MyProblemsPanel;
    private javax.swing.JTable MyProblemsTable;
    private javax.swing.JScrollPane MyProblemsjScrollPane;
    private javax.swing.JPanel PrevContestsPanel;
    private javax.swing.JTable PrevContestsTable;
    private javax.swing.JScrollPane PrevContestsjScrollPane;
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
    private javax.swing.JButton SubmitButton1;
    private javax.swing.JLabel TimeLimitLabel;
    private javax.swing.JLabel TimeLimitLabel1;
    private javax.swing.JPanel UpContestsPanel;
    private javax.swing.JTable UpContestsTable;
    private javax.swing.JScrollPane UpContestsjScrollPane;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JFormattedTextField txtMemoryLimit;
    private javax.swing.JFormattedTextField txtMemoryLimit1;
    private javax.swing.JTextField txtProblemName;
    private javax.swing.JTextField txtProblemName1;
    private javax.swing.JFormattedTextField txtTimeLimit;
    private javax.swing.JFormattedTextField txtTimeLimit1;
    // End of variables declaration//GEN-END:variables
}
