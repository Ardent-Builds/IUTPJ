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
public class Contests extends javax.swing.JFrame {

    /**
     * Creates new form UserDashboard
     */
    private final AdminSocket adminsocket;
    private File problem, inputs, outputs;
    Login parent;

    public Contests(AdminSocket adminsocket, Login parent) {
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

        PrevContestsTable.setDefaultRenderer(Object.class, centerRenderer);
        PrevContestsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        PrevContestsTable.setRowHeight(25);
        JTableHeader problemsettableheader = PrevContestsTable.getTableHeader();
        ((DefaultTableCellRenderer) problemsettableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        //ProblemsetTable.getTableHeader().setOpaque(false);

        DelContestsTable.setDefaultRenderer(Object.class, centerRenderer);
        DelContestsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        DelContestsTable.setRowHeight(25);
        PrevContestsTable.setRowHeight(25);
        JTableHeader delproblemsettableheader = DelContestsTable.getTableHeader();
        ((DefaultTableCellRenderer) delproblemsettableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        //DelProblemsetTable.getTableHeader().setBackground(new Color(0, 181, 204));
        //DelProblemsetTable.getTableHeader().setBackground(new Color(255, 255, 255));

        MyProblemsTable.setDefaultRenderer(Object.class, centerRenderer);
        MyProblemsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 20));
        MyProblemsTable.setRowHeight(25);
        PrevContestsTable.setRowHeight(25);
        JTableHeader myproblemstableheader = MyProblemsTable.getTableHeader();
        ((DefaultTableCellRenderer) myproblemstableheader.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        //MyProblemsTable.getTableHeader().setBackground(new Color(0, 181, 204));
        //MyProblemsTable.getTableHeader().setBackground(new Color(255, 255, 255));

        PrevContestsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) {
                    evt.consume();
                    int row = PrevContestsTable.rowAtPoint(evt.getPoint());
                    int col = PrevContestsTable.columnAtPoint(evt.getPoint());

                    if (row >= 0 && (col == 0 || col == 1)) {
                        DefaultTableModel tablemodel = (DefaultTableModel) PrevContestsTable.getModel();
                        if (tablemodel.getValueAt(row, 0) != null) {
                            String temp = tablemodel.getValueAt(row, 0).toString();
                            int x = temp.indexOf('<', 28);
                            System.out.println(temp + '\n' + x);
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
                            String temp = tablemodel.getValueAt(row, 0).toString();
                            int x = temp.indexOf('<', 28);
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
                            String temp = tablemodel.getValueAt(row, 0).toString();
                            System.out.println(temp);
                            int x = temp.indexOf('<', 28);
                            String submissionid = temp.substring(28, x);
                            subshow.setSubDetailsTable(submissionid, tablemodel.getValueAt(row, 2), tablemodel.getValueAt(row, 3), tablemodel.getValueAt(row, 4), tablemodel.getValueAt(row, 5), tablemodel.getValueAt(row, 6), tablemodel.getValueAt(row, 1));

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

        DelContestsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 1 && !evt.isConsumed()) {
                    evt.consume();
                    int row = DelContestsTable.rowAtPoint(evt.getPoint());
                    int col = DelContestsTable.columnAtPoint(evt.getPoint());
                    if (row >= 0 && (col == 0 || col == 1)) {
                        DefaultTableModel tablemodel = (DefaultTableModel) DelContestsTable.getModel();
                        if (tablemodel.getValueAt(row, 0) != null) {
                            String temp = tablemodel.getValueAt(row, 0).toString();
                            int x = temp.indexOf('<', 28);
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
                    } else if (row >= 0 && col == 3) {

                        DefaultTableModel tablemodel = (DefaultTableModel) DelContestsTable.getModel();
                        String temp;
                        int x;
                        if (tablemodel.getValueAt(row, 0) != null) {
                            temp = tablemodel.getValueAt(row, 0).toString();
                            x = temp.indexOf('<', 28);
                            String problemid = temp.substring(28, x);
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
                                    DelContestsTable.setModel(model);
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
        ContestsDesktopPane = new javax.swing.JDesktopPane();
        ContestsTabSwitcher = new javax.swing.JTabbedPane();
        HomePanel = new javax.swing.JPanel();
        WelcomeLabel = new javax.swing.JLabel();
        LogOutButton = new javax.swing.JButton();
        PrevContestsPanel = new javax.swing.JPanel();
        PrevContestsjScrollPane = new javax.swing.JScrollPane();
        PrevContestsTable = new javax.swing.JTable();
        UpContestsPanel = new javax.swing.JPanel();
        UpContestsjScrollPane = new javax.swing.JScrollPane();
        UpContestsTable = new javax.swing.JTable();
        MyContestsPanel = new javax.swing.JPanel();
        MyContestsjScrollPane = new javax.swing.JScrollPane();
        MyContestsTable = new javax.swing.JTable();
        ManagePanel = new javax.swing.JPanel();
        ManagePanelTabSwitcher = new javax.swing.JTabbedPane();
        AddContestPanel = new javax.swing.JPanel();
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
        DeleteContestPanel = new javax.swing.JPanel();
        DelContestsjScrollPane = new javax.swing.JScrollPane();
        DelContestsTable = new javax.swing.JTable();

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

        ContestsDesktopPane.setBackground(new java.awt.Color(255, 255, 255));

        ContestsTabSwitcher.setBackground(new java.awt.Color(255, 255, 255));
        ContestsTabSwitcher.setForeground(new java.awt.Color(0, 181, 204));
        ContestsTabSwitcher.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        ContestsTabSwitcher.setFont(new java.awt.Font("Segoe UI Emoji", 0, 29)); // NOI18N
        ContestsTabSwitcher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ContestsTabSwitcherMouseClicked(evt);
            }
        });

        HomePanel.setBackground(new java.awt.Color(255, 255, 255));

        WelcomeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 36)); // NOI18N
        WelcomeLabel.setForeground(new java.awt.Color(0, 181, 204));
        WelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeLabel.setText("Welcome To IUTOJ");

        LogOutButton.setBackground(new java.awt.Color(0, 181, 204));
        LogOutButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        LogOutButton.setForeground(new java.awt.Color(0, 181, 204));
        LogOutButton.setText("Log Out");
        LogOutButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 181, 204)));
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
                .addContainerGap(371, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(370, Short.MAX_VALUE))
        );
        HomePanelLayout.setVerticalGroup(
            HomePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomePanelLayout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(WelcomeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 183, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );

        ContestsTabSwitcher.addTab("Home", HomePanel);

        PrevContestsjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        PrevContestsjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        PrevContestsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        PrevContestsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Contest ID", "Contest Name", "Author", "Starting Time", "Finishing Time"
            }
        ));
        PrevContestsTable.setFocusable(false);
        PrevContestsTable.setGridColor(new java.awt.Color(255, 255, 255));
        PrevContestsTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        PrevContestsTable.setMinimumSize(new java.awt.Dimension(0, 0));
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
        PrevContestsTable.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout PrevContestsPanelLayout = new javax.swing.GroupLayout(PrevContestsPanel);
        PrevContestsPanel.setLayout(PrevContestsPanelLayout);
        PrevContestsPanelLayout.setHorizontalGroup(
            PrevContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrevContestsPanelLayout.createSequentialGroup()
                .addComponent(PrevContestsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        PrevContestsPanelLayout.setVerticalGroup(
            PrevContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrevContestsPanelLayout.createSequentialGroup()
                .addComponent(PrevContestsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        ContestsTabSwitcher.addTab("Previous Contests", PrevContestsPanel);

        UpContestsjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        UpContestsjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        UpContestsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        UpContestsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Contest ID", "Contest Name", "Author", "Starting Time", "Finishing Time"
            }
        ));
        UpContestsTable.setFocusable(false);
        UpContestsTable.setGridColor(new java.awt.Color(255, 255, 255));
        UpContestsTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        UpContestsTable.setMinimumSize(new java.awt.Dimension(0, 0));
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
            .addGroup(UpContestsPanelLayout.createSequentialGroup()
                .addComponent(UpContestsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        UpContestsPanelLayout.setVerticalGroup(
            UpContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UpContestsPanelLayout.createSequentialGroup()
                .addComponent(UpContestsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        ContestsTabSwitcher.addTab("Upcoming Contests", UpContestsPanel);

        MyContestsjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        MyContestsjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        MyContestsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        MyContestsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Contest ID", "Contest Name", "Author", "Starting Time", "Finishing Time"
            }
        ));
        MyContestsTable.setFocusable(false);
        MyContestsTable.setGridColor(new java.awt.Color(255, 255, 255));
        MyContestsTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        MyContestsTable.setMinimumSize(new java.awt.Dimension(0, 0));
        MyContestsTable.setOpaque(false);
        MyContestsTable.setRequestFocusEnabled(false);
        MyContestsTable.setRowHeight(25);
        MyContestsTable.setRowSelectionAllowed(false);
        MyContestsTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        MyContestsTable.setShowHorizontalLines(false);
        MyContestsTable.getTableHeader().setReorderingAllowed(false);
        MyContestsTable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                MyContestsTableComponentResized(evt);
            }
        });
        MyContestsjScrollPane.setViewportView(MyContestsTable);

        javax.swing.GroupLayout MyContestsPanelLayout = new javax.swing.GroupLayout(MyContestsPanel);
        MyContestsPanel.setLayout(MyContestsPanelLayout);
        MyContestsPanelLayout.setHorizontalGroup(
            MyContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyContestsPanelLayout.createSequentialGroup()
                .addComponent(MyContestsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        MyContestsPanelLayout.setVerticalGroup(
            MyContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyContestsPanelLayout.createSequentialGroup()
                .addComponent(MyContestsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        ContestsTabSwitcher.addTab("My Contests", MyContestsPanel);

        ManagePanel.setBackground(new java.awt.Color(255, 255, 255));
        ManagePanel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N

        ManagePanelTabSwitcher.setForeground(new java.awt.Color(0, 181, 204));
        ManagePanelTabSwitcher.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ManagePanelTabSwitcher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ManagePanelTabSwitcherMouseClicked(evt);
            }
        });

        AddContestPanel.setBackground(new java.awt.Color(255, 255, 255));
        AddContestPanel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

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
        MemoryLimitLabel.setForeground(new java.awt.Color(0, 181, 204));
        MemoryLimitLabel.setText("Memory Limit (KB):");

        ProblemNameLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        ProblemNameLabel.setForeground(new java.awt.Color(0, 181, 204));
        ProblemNameLabel.setText("Problem Name:");

        TimeLimitLabel.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        TimeLimitLabel.setForeground(new java.awt.Color(0, 181, 204));
        TimeLimitLabel.setText("Time Limit (ms):");

        SubmitButton.setBackground(new java.awt.Color(0, 181, 204));
        SubmitButton.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        SubmitButton.setForeground(new java.awt.Color(0, 181, 204));
        SubmitButton.setText("Submit");
        SubmitButton.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 181, 204)));
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

        javax.swing.GroupLayout AddContestPanelLayout = new javax.swing.GroupLayout(AddContestPanel);
        AddContestPanel.setLayout(AddContestPanelLayout);
        AddContestPanelLayout.setHorizontalGroup(
            AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddContestPanelLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ChProblemStatementButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddInputButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddOutputButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(AddContestPanelLayout.createSequentialGroup()
                        .addComponent(ProblemNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtProblemName))
                    .addGroup(AddContestPanelLayout.createSequentialGroup()
                        .addComponent(TimeLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimeLimit))
                    .addGroup(AddContestPanelLayout.createSequentialGroup()
                        .addComponent(MemoryLimitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMemoryLimit)))
                .addGap(367, 367, 367))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AddContestPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AddContestPanelLayout.setVerticalGroup(
            AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddContestPanelLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(ChProblemStatementButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(AddInputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(AddOutputButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ProblemNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtProblemName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TimeLimitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txtTimeLimit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(AddContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(MemoryLimitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txtMemoryLimit))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(SubmitButton)
                .addGap(50, 50, 50))
        );

        ManagePanelTabSwitcher.addTab("Add Contest", AddContestPanel);

        DelContestsjScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        DelContestsjScrollPane.setFont(new java.awt.Font("Segoe UI Emoji", 1, 25)); // NOI18N

        DelContestsTable.setFont(new java.awt.Font("Segoe UI Emoji", 1, 18)); // NOI18N
        DelContestsTable.setModel(new javax.swing.table.DefaultTableModel(
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
        DelContestsTable.setFocusable(false);
        DelContestsTable.setGridColor(new java.awt.Color(255, 255, 255));
        DelContestsTable.setIntercellSpacing(new java.awt.Dimension(0, 0));
        DelContestsTable.setOpaque(false);
        DelContestsTable.setRequestFocusEnabled(false);
        DelContestsTable.setRowHeight(25);
        DelContestsTable.setRowSelectionAllowed(false);
        DelContestsTable.setSelectionBackground(new java.awt.Color(0, 181, 204));
        DelContestsTable.setShowHorizontalLines(false);
        DelContestsTable.getTableHeader().setReorderingAllowed(false);
        DelContestsTable.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                DelContestsTableComponentResized(evt);
            }
        });
        DelContestsjScrollPane.setViewportView(DelContestsTable);

        javax.swing.GroupLayout DeleteContestPanelLayout = new javax.swing.GroupLayout(DeleteContestPanel);
        DeleteContestPanel.setLayout(DeleteContestPanelLayout);
        DeleteContestPanelLayout.setHorizontalGroup(
            DeleteContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
            .addGroup(DeleteContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(DelContestsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE))
        );
        DeleteContestPanelLayout.setVerticalGroup(
            DeleteContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
            .addGroup(DeleteContestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(DelContestsjScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))
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

        ContestsTabSwitcher.addTab("Manage", ManagePanel);

        ContestsDesktopPane.setLayer(ContestsTabSwitcher, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout ContestsDesktopPaneLayout = new javax.swing.GroupLayout(ContestsDesktopPane);
        ContestsDesktopPane.setLayout(ContestsDesktopPaneLayout);
        ContestsDesktopPaneLayout.setHorizontalGroup(
            ContestsDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContestsDesktopPaneLayout.createSequentialGroup()
                .addComponent(ContestsTabSwitcher)
                .addGap(0, 0, 0))
        );
        ContestsDesktopPaneLayout.setVerticalGroup(
            ContestsDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContestsDesktopPaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(ContestsTabSwitcher)
                .addContainerGap())
        );

        getContentPane().add(ContestsDesktopPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ContestsTabSwitcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContestsTabSwitcherMouseClicked
        int x = ContestsTabSwitcher.getSelectedIndex();
        switch (x) {

            case 1:

                adminsocket.sendData("PrbTable[null]");
                Object[][] table = adminsocket.getProblemTable();
                if (table == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"Problem ID", "Problem Name", "Problem Setter"};
                    DefaultTableModel tablemodel = new DefaultTableModel(table, columns) {

                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    PrevContestsTable.setModel(tablemodel);
                }

                break;
            case 2:
                adminsocket.sendData("PrbTable[My]");
                table = adminsocket.getProblemTable();
                if (table == null) {
                    JOptionPane.showMessageDialog(null, "Table Not found", "Table Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String[] columns = {"Problem ID", "Problem Name", "Problem Setter"};
                    DefaultTableModel tablemodel = new DefaultTableModel(table, columns) {
                        public boolean isCellEditable(int row, int col) {
                            return false;
                        }
                    };
                    MyProblemsTable.setModel(tablemodel);
                }
                break;
            case 4:
                adminsocket.sendData("StTable-[nullad]");
                table = adminsocket.getStatusTable();
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
            case 5:
                adminsocket.sendData("StdTable[null]");
                table = adminsocket.getStandingsTable();
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

    }//GEN-LAST:event_ContestsTabSwitcherMouseClicked

    private void DelContestsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_DelContestsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_DelContestsTableComponentResized

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

    private void PrevContestsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PrevContestsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_PrevContestsTableComponentResized

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
                DelContestsTable.setModel(tablemodel);
            }
        }
    }//GEN-LAST:event_ManagePanelTabSwitcherMouseClicked

    private void UpContestsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_UpContestsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_UpContestsTableComponentResized

    private void MyContestsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_MyContestsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_MyContestsTableComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddContestPanel;
    private javax.swing.JButton AddInputButton;
    private javax.swing.JButton AddOutputButton;
    private javax.swing.JButton ChProblemStatementButton;
    private javax.swing.JDesktopPane ContestsDesktopPane;
    private javax.swing.JTabbedPane ContestsTabSwitcher;
    private javax.swing.JTable DelContestsTable;
    private javax.swing.JScrollPane DelContestsjScrollPane;
    private javax.swing.JPanel DeleteContestPanel;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JPanel ManagePanel;
    private javax.swing.JTabbedPane ManagePanelTabSwitcher;
    private javax.swing.JLabel MemoryLimitLabel;
    private javax.swing.JPanel MyContestsPanel;
    private javax.swing.JTable MyContestsTable;
    private javax.swing.JScrollPane MyContestsjScrollPane;
    private javax.swing.JPanel PrevContestsPanel;
    private javax.swing.JTable PrevContestsTable;
    private javax.swing.JScrollPane PrevContestsjScrollPane;
    private javax.swing.JLabel ProblemNameLabel;
    private javax.swing.JButton SubmitButton;
    private javax.swing.JLabel TimeLimitLabel;
    private javax.swing.JPanel UpContestsPanel;
    private javax.swing.JTable UpContestsTable;
    private javax.swing.JScrollPane UpContestsjScrollPane;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JFormattedTextField txtMemoryLimit;
    private javax.swing.JTextField txtProblemName;
    private javax.swing.JFormattedTextField txtTimeLimit;
    // End of variables declaration//GEN-END:variables
}
