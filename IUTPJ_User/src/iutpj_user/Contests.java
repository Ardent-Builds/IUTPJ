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
                .addContainerGap(338, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(338, Short.MAX_VALUE))
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
                .addComponent(PrevContestsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
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
                .addComponent(UpContestsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
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
                .addComponent(MyContestsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        MyContestsPanelLayout.setVerticalGroup(
            MyContestsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyContestsPanelLayout.createSequentialGroup()
                .addComponent(MyContestsjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        ContestsTabSwitcher.addTab("My Contests", MyContestsPanel);

        ContestsDesktopPane.setLayer(ContestsTabSwitcher, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout ContestsDesktopPaneLayout = new javax.swing.GroupLayout(ContestsDesktopPane);
        ContestsDesktopPane.setLayout(ContestsDesktopPaneLayout);
        ContestsDesktopPaneLayout.setHorizontalGroup(
            ContestsDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContestsDesktopPaneLayout.createSequentialGroup()
                .addComponent(ContestsTabSwitcher, javax.swing.GroupLayout.DEFAULT_SIZE, 1037, Short.MAX_VALUE)
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

    private void PrevContestsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_PrevContestsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_PrevContestsTableComponentResized

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutButtonActionPerformed
        parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogOutButtonActionPerformed

    private void UpContestsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_UpContestsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_UpContestsTableComponentResized

    private void MyContestsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_MyContestsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_MyContestsTableComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane ContestsDesktopPane;
    private javax.swing.JTabbedPane ContestsTabSwitcher;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JPanel MyContestsPanel;
    private javax.swing.JTable MyContestsTable;
    private javax.swing.JScrollPane MyContestsjScrollPane;
    private javax.swing.JPanel PrevContestsPanel;
    private javax.swing.JTable PrevContestsTable;
    private javax.swing.JScrollPane PrevContestsjScrollPane;
    private javax.swing.JPanel UpContestsPanel;
    private javax.swing.JTable UpContestsTable;
    private javax.swing.JScrollPane UpContestsjScrollPane;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JPanel jPanel6;
    // End of variables declaration//GEN-END:variables
}
