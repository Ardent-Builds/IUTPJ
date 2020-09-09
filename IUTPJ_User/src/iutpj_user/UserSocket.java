/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_user;

import iutpj_server.ContestInfo;
import iutpj_server.DataPacket;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.io.*;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 *
 * @author ASADUZZAMAN HEROK
 */
public class UserSocket {

    private Socket userSocket;
    private String userID;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private DataPacket request, response;
    private final JPanel loadingScreen;
    private JFrame parentFrame;

    public UserSocket() throws IOException {
        this.userSocket = new Socket();
        this.parentFrame = null;
        this.request = null;
        this.response = null;
        this.userID = null;
        this.loadingScreen = new JPanel();
        this.loadingScreen.add(new JLabel(new ImageIcon(getClass().getResource("/images/loading.gif"))));
        this.loadingScreen.setOpaque(false);
        this.loadingScreen.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                e.consume();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                e.consume();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                e.consume();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                e.consume();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                e.consume();
            }
        });
    }

    public void setCurrentJFrame(JFrame frame) {
        this.parentFrame = frame;
        this.parentFrame.setGlassPane(this.loadingScreen);
        System.out.println(this.loadingScreen);
    }

    private void sendDataPacket() {
        try {
            objectOut.writeObject(request);
            objectOut.flush();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Sending Error:", JOptionPane.ERROR_MESSAGE);
        }
    }

    private DataPacket getDataPacket() {
        try {
            return (DataPacket) objectIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Data Reading Error:", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public boolean getAuthorization(String userName, String password) {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("user/login");
        request.addPayLoad("Username", userName);
        request.addPayLoad("Password", password);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);
        if (response == null) {
            return false;
        }
        userID = response.getUserID();
        return (Boolean) response.getValue("Authorization");
    }

    public boolean signUp(String userName, String password) {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("user/signUp");
        request.addPayLoad("Username", userName);
        request.addPayLoad("Password", password);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);

        if (response == null) {
            return false;
        }
        if ((Boolean) response.getValue("Status") == false) {
            JOptionPane.showMessageDialog(null, (String) response.getValue("Error"), "Data Reading Error:", JOptionPane.ERROR_MESSAGE);
        }
        return (Boolean) response.getValue("Status");
    }

    public boolean connect(String add, int port) {
        loadingScreen.setVisible(true);
        try {
            userSocket = new Socket(add, port);
            objectOut = new ObjectOutputStream(userSocket.getOutputStream());
            objectIn = new ObjectInputStream(userSocket.getInputStream());

            request = new DataPacket();
            request.setRoute("root");
            request.addPayLoad("UserType", "User");
            this.sendDataPacket();
            response = getDataPacket();
            JOptionPane.showMessageDialog(null, response.getValue("Status"), "Status", JOptionPane.INFORMATION_MESSAGE);
            loadingScreen.setVisible(false);
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Connection Error: "+ex.getMessage(), "Status", JOptionPane.INFORMATION_MESSAGE);
            loadingScreen.setVisible(false);
            return false;
        }
    }

    public Object[][] getProblemTable() {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("offline/problem/table");
        request.setUserID(userID);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);

        int idx = 0;
        List<String[]> rowData = (List<String[]>) response.getValue("Table");
        if (rowData == null) {
            JOptionPane.showMessageDialog(null, "Table Not Found", "Status", JOptionPane.ERROR_MESSAGE);
            return new Object[40][];
        }
        Object[][] table = new Object[Math.max(40, rowData.size())][];

        for (String[] row : rowData) {
            table[idx++] = row;
        }
        return table;

    }
    public Object[][] getContestProblemTable(String contestID) {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("contest/problem/table");
        request.setUserID(userID);
        request.addPayLoad("Option", contestID);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);

        int idx = 0;
        List<String[]> rowData = (List<String[]>) response.getValue("Table");
        if (rowData == null) {
            JOptionPane.showMessageDialog(null, "Table Not Found: "+response.getValue("Error"), "Status", JOptionPane.ERROR_MESSAGE);
            return new Object[40][];
        }
        Object[][] table = new Object[Math.max(40, rowData.size())][];

        for (String[] row : rowData) {
            table[idx++] = row;
        }
        return table;

    }

    public void close() throws IOException {
        userSocket.close();
    }

    public void addSubmission(File codefile, String problemid, String language) {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("offline/submission/new");
        request.setUserID(userID);
        try {
            FileInputStream codefis = new FileInputStream(codefile);
            byte codef[] = new byte[codefis.available()];
            codefis.read(codef);
            codefis.close();

            NewSubmission newsubmission = new NewSubmission();
            newsubmission.setProblemID(problemid);
            newsubmission.setLanguage(language);
            newsubmission.setCodeF(codef);

            request.addPayLoad("File", newsubmission);
            this.sendDataPacket();
            response = getDataPacket();
            JOptionPane.showMessageDialog(null, response.getValue("Status"), "Status", JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Problem Sending Error:", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Problem Sending Error:", JOptionPane.ERROR_MESSAGE);
        }
        loadingScreen.setVisible(false);

    }
    
    public void addContestSubmission(File codefile, String problemid, String language, String contestID) {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("contest/submission/new");
        request.setUserID(userID);
        try {
            FileInputStream codefis = new FileInputStream(codefile);
            byte codef[] = new byte[codefis.available()];
            codefis.read(codef);
            codefis.close();

            NewSubmission newsubmission = new NewSubmission();
            newsubmission.setProblemID(problemid);
            newsubmission.setLanguage(language);
            newsubmission.setCodeF(codef);

            request.addPayLoad("File", newsubmission);
            request.addPayLoad("ContestID", contestID);
            
            this.sendDataPacket();
            response = getDataPacket();
            String status =(String) response.getValue("Status");
            String error = (String) response.getValue("Error");
            JOptionPane.showMessageDialog(null, status+' '+error, "Status", JOptionPane.INFORMATION_MESSAGE);

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Problem Sending Error:", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Problem Sending Error:", JOptionPane.ERROR_MESSAGE);
        }
        loadingScreen.setVisible(false);

    }

    public Object[][] getStatusTable(String option) {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("offline/status");
        request.setUserID(userID);
        if (option != null) {
            request.addPayLoad("Option", userID);
        }

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);

        int idx = 0;
        List<String[]> rowData = (List<String[]>) response.getValue("Table");
        if (rowData == null) {
            JOptionPane.showMessageDialog(null, "Table Not Found", "Status", JOptionPane.ERROR_MESSAGE);
            return new Object[40][];
        }
        Object[][] table = new Object[Math.max(40, rowData.size())][];

        for (String[] row : rowData) {
            table[idx++] = row;
        }
        return table;
    }
    
    public Object[][] getContestStatusTable(String contestID , String option) {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("contest/status");
        request.setUserID(userID);
        if (option != null) {
            request.addPayLoad("Option", userID);
        }
        request.addPayLoad("ContestID", contestID);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);

        int idx = 0;
        List<String[]> rowData = (List<String[]>) response.getValue("Table");
        if (rowData == null) {
            JOptionPane.showMessageDialog(null, "Table Not Found", "Status", JOptionPane.ERROR_MESSAGE);
            return new Object[40][];
        }
        Object[][] table = new Object[Math.max(40, rowData.size())][];

        for (String[] row : rowData) {
            table[idx++] = row;
        }
        return table;
    }

    public Object[][] getStandingsTable() {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("offline/standing");
        request.setUserID(userID);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);

        int idx = 0;
        List<String[]> rowData = (List<String[]>) response.getValue("Table");
        if (rowData == null) {
            JOptionPane.showMessageDialog(null, "Table Not Found", "Status", JOptionPane.ERROR_MESSAGE);
            return new Object[40][];
        }
        Object[][] table = new Object[Math.max(40, rowData.size())][];

        for (String[] row : rowData) {
            table[idx++] = row;
        }
        return table;
    }
    public Object[][] getContestStandingsRawTable(String contestID){
        System.out.println("iutpj_user.UserSocket.getContestStandingsRawTable()");
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("contest/standing");
        request.setUserID(userID);
        request.addPayLoad("ContestID", contestID);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);
        
        System.out.println("data received");
        int idx = 0;
        List<String[]> rowData = (List<String[]>) response.getValue("Table");
        System.out.println(rowData);
        if (rowData == null) {
            JOptionPane.showMessageDialog(null, "Table Not Found", "Status", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        Object[][] table = new Object[rowData.size()][];

        for (String[] row : rowData) {
            table[idx++] = row;
        }
        System.out.println(table);
        return table;
    }

    public Object[][] getContestTable() {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("contest/table");
        request.setUserID(userID);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);

        int idx = 0;
        List<String[]> rowData = (List<String[]>) response.getValue("Table");
        if (rowData == null) {
            JOptionPane.showMessageDialog(null, "Table Not Found", "Status", JOptionPane.ERROR_MESSAGE);
            return new Object[40][];
        }
        Object[][] table = new Object[Math.max(40, rowData.size())][];

        for (String[] row : rowData) {
            table[idx++] = row;
        }
        loadingScreen.setVisible(false);
        return table;

    }

    public NewSubmission getSubmission(String submissionID) {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("offline/submission/file");
        request.setUserID(userID);
        request.addPayLoad("SubmissionID", submissionID);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);

        return (NewSubmission) response.getValue("File");
    }

    public NewProblem getProblem(String problemID) {
        System.err.println(problemID);
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("offline/problem/file");
        request.setUserID(userID);
        request.addPayLoad("ProblemID", problemID);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);

        return (NewProblem) response.getValue("File");
    }
    public NewProblem getContestProblem(String problemID) {
        System.err.println(problemID);
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("contest/problem/file");
        request.setUserID(userID);
        request.addPayLoad("ProblemID", problemID);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);

        return (NewProblem) response.getValue("File");
    }

    public ContestInfo getContestInfo(String contestID) {
        System.err.println(contestID);
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("contest/info");
        request.setUserID(userID);
        request.addPayLoad("ContestID", contestID);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);

        return (ContestInfo) response.getValue("ContestInfo");
    }

}
