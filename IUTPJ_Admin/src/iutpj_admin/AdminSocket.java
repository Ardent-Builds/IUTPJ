/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_admin;

import java.io.*;
import java.net.Socket;
import java.util.List;
import newproblem.NewProblem;
import newsubmission.NewSubmission;
import iutpj_server.ContestInfo;
import iutpj_server.DataPacket;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author ASADUZZAMAN HEROK
 */
public class AdminSocket {

    private Socket adminsocket;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private String userID;
    private DataPacket request, response;
    private final JPanel loadingScreen;
    private JFrame parentFrame;

    public AdminSocket() throws IOException {
        this.adminsocket = new Socket();
        this.userID = null;
        this.request = null;
        this.response = null;
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

    public void setParentFrame(JFrame parent) {
        parentFrame = parent;
        parentFrame.setGlassPane(loadingScreen);
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
            JOptionPane.showMessageDialog(null, (String) response.getValue("Status"), "Data Reading Error:", JOptionPane.ERROR_MESSAGE);
        }
        return (Boolean) response.getValue("Status");
    }

    public void addProblem(File problem, File inputs, File outputs, String locked, String problemname, String timelimit, String memorylimit){
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("offline/problem/new");
        request.setUserID(userID);

        try {
            FileInputStream probis = new FileInputStream(problem);
            byte prob[] = new byte[probis.available()];
            probis.read(prob);
            FileInputStream inpis = new FileInputStream(inputs);
            byte inp[] = new byte[inpis.available()];
            inpis.read(inp);
            FileInputStream outpis = new FileInputStream(outputs);
            byte outp[] = new byte[outpis.available()];
            outpis.read(outp);
            NewProblem newproblem = new NewProblem();
            newproblem.setProblemID(locked);
            newproblem.setProblemName(problemname);
            newproblem.setTimeLimit(timelimit);
            newproblem.setMemoryLimit(memorylimit);
            newproblem.setProb(prob);
            newproblem.setInp(inp);
            newproblem.setOutp(outp);

            request.addPayLoad("File", newproblem);
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

    public boolean submitContest(ContestInfo contest) {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("contest/new");
        request.setUserID(userID);
        request.addPayLoad("ContestInfo", contest);

        this.sendDataPacket();
        response = getDataPacket();
        JOptionPane.showMessageDialog(null, response.getValue("Status"), "Status", JOptionPane.INFORMATION_MESSAGE);
        loadingScreen.setVisible(false);
        return response.getValue("Status").equals("SUCCESSFULL");
    }

    public Object[][] getProblemTable(String option) {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("offline/problem/table");
        request.setUserID(userID);
        if(option!=null) option = userID;
        request.addPayLoad("Option", option);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);
        
        int idx = 0;
        List<String[]> rowData = (List<String[]>) response.getValue("Table");
        if(rowData==null)
        {
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
    
    public String deleteProblem(String problemID)
    {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("offline/problem/delete");
        request.setUserID(userID);
        request.addPayLoad("ProblemID", problemID);
        
        this.sendDataPacket();
        response = this.getDataPacket();
        loadingScreen.setVisible(false);
        return (String)response.getValue("Status");
    }
    
    public String changeProblemLockedState(String problemID, String state){
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("offline/problem/lock");
        request.setUserID(userID);
        request.addPayLoad("ProblemID", problemID);
        request.addPayLoad("State", state);
        
        this.sendDataPacket();
        response = this.getDataPacket();
        loadingScreen.setVisible(false);
        return (String)response.getValue("Status");
    }

    public Object[][] getStatusTable() {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("offline/status");
        request.setUserID(userID);

        this.sendDataPacket();
        response = getDataPacket();
        loadingScreen.setVisible(false);
        
        int idx = 0;
        List<String[]> rowData = (List<String[]>) response.getValue("Table");
        if(rowData==null)
        {
            JOptionPane.showMessageDialog(null, "Table Not Found", "Status", JOptionPane.ERROR_MESSAGE);
            return new Object[40][];
        }
        Object[][] table = new Object[Math.max(40, rowData.size())][];

        for (String[] row : rowData) {
            table[idx++] = row;
        }
        return table;
    }
    
    public Object[][] getContestStatusTable(String contestID) {
        loadingScreen.setVisible(true);
        request = new DataPacket();
        request.setRoute("contest/status");
        request.setUserID(userID);
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
        if(rowData==null)
        {
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

    public boolean connect(String add, int port) {
        loadingScreen.setVisible(true);
        try {
            adminsocket = new Socket(add, port);
            objectOut = new ObjectOutputStream(adminsocket.getOutputStream());
            objectIn = new ObjectInputStream(adminsocket.getInputStream());
            
            request = new DataPacket();
            request.setRoute("root");
            request.addPayLoad("UserType", "Admin");
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

    public void close() throws IOException {
        adminsocket.close();
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
        
        return (NewSubmission)response.getValue("File");
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
        
        return (NewProblem)response.getValue("File");
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
        if(rowData==null)
        {
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
