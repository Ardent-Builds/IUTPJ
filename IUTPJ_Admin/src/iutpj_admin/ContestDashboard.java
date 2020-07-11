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
public class ContestDashboard extends javax.swing.JFrame {

    /**
     * Creates new form UserDashboard
     */
    private final AdminSocket adminsocket;
    private File problem, inputs, outputs;
    Login parent;

    public ContestDashboard(AdminSocket adminsocket, Login parent) {
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
        ContestDashboardDesktopPane = new javax.swing.JDesktopPane();
        ContestDashboardTabSwitcher = new javax.swing.JTabbedPane();
        HomePanel = new javax.swing.JPanel();
        WelcomeLabel = new javax.swing.JLabel();
        LogOutButton = new javax.swing.JButton();
        ProblemsetPanel = new javax.swing.JPanel();
        ProblemSetjScrollPane = new javax.swing.JScrollPane();
        ProblemsetTable = new javax.swing.JTable();
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

        ContestDashboardDesktopPane.setBackground(new java.awt.Color(255, 255, 255));

        ContestDashboardTabSwitcher.setBackground(new java.awt.Color(255, 255, 255));
        ContestDashboardTabSwitcher.setForeground(new java.awt.Color(54, 33, 89));
        ContestDashboardTabSwitcher.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        ContestDashboardTabSwitcher.setFont(new java.awt.Font("Segoe UI Emoji", 0, 29)); // NOI18N
        ContestDashboardTabSwitcher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ContestDashboardTabSwitcherMouseClicked(evt);
            }
        });

        HomePanel.setBackground(new java.awt.Color(255, 255, 255));

        WelcomeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 36)); // NOI18N
        WelcomeLabel.setForeground(new java.awt.Color(54, 33, 89));
        WelcomeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        WelcomeLabel.setText("Contest Area");

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
                .addContainerGap(400, Short.MAX_VALUE)
                .addComponent(LogOutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(401, Short.MAX_VALUE))
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

        ContestDashboardTabSwitcher.addTab("Home", HomePanel);

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
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        ProblemsetPanelLayout.setVerticalGroup(
            ProblemsetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProblemsetPanelLayout.createSequentialGroup()
                .addComponent(ProblemSetjScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        ContestDashboardTabSwitcher.addTab("Problems", ProblemsetPanel);

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
            .addComponent(StatusScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
        );
        StatusPanelLayout.setVerticalGroup(
            StatusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StatusScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );

        ContestDashboardTabSwitcher.addTab("Status", StatusPanel);

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
            .addComponent(StandingsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
        );
        StandingsPanelLayout.setVerticalGroup(
            StandingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(StandingsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
        );

        ContestDashboardTabSwitcher.addTab("Standings", StandingsPanel);

        ContestDashboardDesktopPane.setLayer(ContestDashboardTabSwitcher, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout ContestDashboardDesktopPaneLayout = new javax.swing.GroupLayout(ContestDashboardDesktopPane);
        ContestDashboardDesktopPane.setLayout(ContestDashboardDesktopPaneLayout);
        ContestDashboardDesktopPaneLayout.setHorizontalGroup(
            ContestDashboardDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContestDashboardDesktopPaneLayout.createSequentialGroup()
                .addComponent(ContestDashboardTabSwitcher)
                .addGap(0, 0, 0))
        );
        ContestDashboardDesktopPaneLayout.setVerticalGroup(
            ContestDashboardDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContestDashboardDesktopPaneLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(ContestDashboardTabSwitcher)
                .addContainerGap())
        );

        getContentPane().add(ContestDashboardDesktopPane, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ContestDashboardTabSwitcherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ContestDashboardTabSwitcherMouseClicked
        int x = ContestDashboardTabSwitcher.getSelectedIndex();
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
                    ProblemsetTable.setModel(tablemodel);
                }

                break;
            case 2:
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
            case 3:
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

    }//GEN-LAST:event_ContestDashboardTabSwitcherMouseClicked

    private void StatusTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_StatusTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusTableComponentResized

    private void ProblemsetTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_ProblemsetTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_ProblemsetTableComponentResized

    private void LogOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogOutButtonActionPerformed
        parent.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogOutButtonActionPerformed

    private void StandingsTableComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_StandingsTableComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_StandingsTableComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane ContestDashboardDesktopPane;
    private javax.swing.JTabbedPane ContestDashboardTabSwitcher;
    private javax.swing.JPanel HomePanel;
    private javax.swing.JButton LogOutButton;
    private javax.swing.JScrollPane ProblemSetjScrollPane;
    private javax.swing.JPanel ProblemsetPanel;
    private javax.swing.JTable ProblemsetTable;
    private javax.swing.JPanel StandingsPanel;
    private javax.swing.JScrollPane StandingsScrollPane;
    private javax.swing.JTable StandingsTable;
    private javax.swing.JPanel StatusPanel;
    private javax.swing.JScrollPane StatusScrollPane;
    private javax.swing.JTable StatusTable;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JPanel jPanel6;
    // End of variables declaration//GEN-END:variables
}
