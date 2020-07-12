/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_server;


import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author KAWSAR
 */
public class ServerGUI extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    private Server server;
    private Thread service;
    public ServerGUI() {
        initComponents();
        server = null;
        service = null;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LeftPanel = new javax.swing.JPanel();
        logoLabel = new javax.swing.JLabel();
        RightPanel = new javax.swing.JPanel();
        WelcomeLabel = new javax.swing.JLabel();
        closeLabel = new javax.swing.JLabel();
        minimizeLabel = new javax.swing.JLabel();
        PortLabel = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();
        Start = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LeftPanel.setBackground(new java.awt.Color(54, 33, 89));
        LeftPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/newlogo.png"))); // NOI18N
        LeftPanel.add(logoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 190, 170));

        getContentPane().add(LeftPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 430, 560));

        RightPanel.setBackground(new java.awt.Color(255, 255, 255));
        RightPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        WelcomeLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 60)); // NOI18N
        WelcomeLabel.setForeground(new java.awt.Color(54, 33, 89));
        WelcomeLabel.setText("Server ");
        RightPanel.add(WelcomeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 220, 70));

        closeLabel.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        closeLabel.setForeground(new java.awt.Color(54, 33, 89));
        closeLabel.setText("x");
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
        });
        RightPanel.add(closeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 30, 20));

        minimizeLabel.setFont(new java.awt.Font("Tahoma", 1, 25)); // NOI18N
        minimizeLabel.setForeground(new java.awt.Color(54, 33, 89));
        minimizeLabel.setText("_");
        minimizeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeLabelMouseClicked(evt);
            }
        });
        RightPanel.add(minimizeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, -10, 30, 40));

        PortLabel.setFont(new java.awt.Font("Segoe UI Emoji", 1, 60)); // NOI18N
        PortLabel.setForeground(new java.awt.Color(54, 33, 89));
        PortLabel.setText("Port:");
        RightPanel.add(PortLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 170, 60));

        txtPort.setFont(new java.awt.Font("Segoe UI Light", 0, 50)); // NOI18N
        txtPort.setForeground(new java.awt.Color(102, 102, 102));
        txtPort.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 102, 102)));
        txtPort.setSelectionColor(new java.awt.Color(110, 89, 222));
        txtPort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPortFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPortFocusLost(evt);
            }
        });
        txtPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPortActionPerformed(evt);
            }
        });
        RightPanel.add(txtPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 280, 50));

        Start.setBackground(new java.awt.Color(54, 33, 89));
        Start.setFont(new java.awt.Font("Segoe UI Emoji", 1, 48)); // NOI18N
        Start.setForeground(new java.awt.Color(54, 33, 89));
        Start.setText("Start");
        Start.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(54, 33, 89)));
        Start.setContentAreaFilled(false);
        Start.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Start.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartButtonActionPerformed(evt);
            }
        });
        RightPanel.add(Start, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 430, 320, 70));

        getContentPane().add(RightPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 0, 530, 560));

        setSize(new java.awt.Dimension(954, 556));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    void startGUI(){
        this.setVisible(true);
        ProcessBuilder pb = new ProcessBuilder("g++","--version");
        try {
            pb.start();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "g++ Compiler path not Found, Err:"+ex.getMessage(),"Compiler Error",JOptionPane.ERROR_MESSAGE);
        }
        pb.command("Javac", "--version");
        try{
            pb.start();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Java Compiler path not Found, Err:"+ex.getMessage(),"Compiler Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        this.dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_closeLabelMouseClicked

    private void minimizeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeLabelMouseClicked
        this.setExtendedState(JFrame.ICONIFIED);      // TODO add your handling code here:
    }//GEN-LAST:event_minimizeLabelMouseClicked
    static int xx, yy;
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xx=evt.getX();
        yy=evt.getY();// TODO add your handling code here:
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x,y;
        x=evt.getXOnScreen();
        y=evt.getYOnScreen();
        this.setLocation(x-xx, y-yy);// TODO add your handling code here:
    }//GEN-LAST:event_formMouseDragged

    private void txtPortFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPortFocusGained
        if(txtPort.getText().equals("Enter Username"))
        {
            txtPort.setText("");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtPortFocusGained

    private void txtPortFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPortFocusLost
        if(txtPort.getText().equals(""))
        {
            txtPort.setText("");
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtPortFocusLost

    private void txtPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPortActionPerformed

    private void StartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartButtonActionPerformed
        int port;
        try 
        {
            port= Integer.parseInt(txtPort.getText());
        }
        catch (NumberFormatException e)
        {
            port = 0;
            JOptionPane.showMessageDialog(null,"Port Error","Status",JOptionPane.ERROR_MESSAGE);
        }
      
        server = new Server(port);
        service = new Thread(server);
        service.start();
        
    }//GEN-LAST:event_StartButtonActionPerformed

    Server getServer(){
        return server;
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel LeftPanel;
    private javax.swing.JLabel PortLabel;
    private javax.swing.JPanel RightPanel;
    private javax.swing.JButton Start;
    private javax.swing.JLabel WelcomeLabel;
    private javax.swing.JLabel closeLabel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JLabel minimizeLabel;
    private javax.swing.JTextField txtPort;
    // End of variables declaration//GEN-END:variables

}
