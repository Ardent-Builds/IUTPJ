/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 *
 * @author ASADUZZAMAN HEROK
 */
public class SocketForClient {

    private Socket socket;
    private DataOutputStream dataout;
    private DataInputStream datain;
    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    public SocketForClient(Socket socket) throws IOException {
        this.socket = socket;
        try {

            dataout = new DataOutputStream(socket.getOutputStream());
            datain = new DataInputStream(socket.getInputStream());
            objectIn = new ObjectInputStream(socket.getInputStream());
            objectOut = new ObjectOutputStream(socket.getOutputStream());

        } catch (IOException ex) {

        }
    }

    public DataPacket getPacket() {
        if (socket.isConnected() == false) {
            return null;
        }
        try {
            return (DataPacket) objectIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SocketForClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public boolean sendDataPacket(DataPacket packet) {
        if (socket.isConnected() == false) {
            return false;
        }
        try {
            objectOut.writeObject(packet);
            objectOut.flush();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(SocketForClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int sendData(String data) {
        try {
            socket.setSoTimeout(5000);
            dataout.writeUTF(data);
            return data.length();
        } catch (SocketException ex) {
            return -2;
        } catch (IOException ex) {
            return -1;
        }

    }

    public String readData() {
        try {
            socket.setSoTimeout(0);
            return datain.readUTF();
        } catch (IOException ex) {
            return null;
        }
    }

    public NewProblem saveProblem() throws IOException, ClassNotFoundException {
        NewProblem newproblem = (NewProblem) objectIn.readObject();

        return newproblem;

    }

    public ContestInfo saveContestInfo() throws IOException, ClassNotFoundException {
        ContestInfo contestInfo = (ContestInfo) objectIn.readObject();
        return contestInfo;
    }

    public NewSubmission saveSubmission() throws IOException, ClassNotFoundException {
        NewSubmission newsubmission = (NewSubmission) objectIn.readObject();

        return newsubmission;
    }

    public boolean sendProblem(NewProblem newproblem) {
        try {
            objectOut.writeObject(newproblem);
            return true;
        } catch (IOException ex) {
            System.out.println("SocketProblemSending Err:\n " + ex.getMessage());
            return false;
        }
    }

    public boolean sendSubmission(NewSubmission submission) {
        try {
            objectOut.writeObject(submission);
            return true;
        } catch (IOException ex) {
            System.out.println("SocketSubmisionSending Err:\n " + ex.getMessage());
            return false;
        }
    }

    public boolean sendProblemTable(List<String[]> table) {
        try {
            objectOut.writeObject(table);
            return true;
        } catch (IOException ex) {
            System.out.println("SocketProblemTableSending Err:\n " + ex.getMessage());
            return false;
        }
    }

    public boolean sendContestTable(List<String[]> table) {
        try {
            objectOut.writeObject(table);
            return true;
        } catch (IOException ex) {
            System.out.println("SocketProblemTableSending Err :\n " + ex.getMessage());
            return false;
        }
    }

    public boolean sendStatusTable(List<String[]> table) {
        try {
            objectOut.writeObject(table);
            return true;
        } catch (IOException ex) {
            System.out.println("SocketStatusTableSending Err:\n  " + ex.getMessage());
            return false;
        }
    }

    public boolean sendStandingsTable(List<String[]> table) {
        try {
            objectOut.writeObject(table);
            return true;
        } catch (IOException ex) {
            System.out.println("SocketStatusTableSending Err:\n  " + ex.getMessage());
            return false;
        }
    }

    public boolean sendContestInfo(ContestInfo contestInfo) {
        try {
            objectOut.writeObject(contestInfo);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(SocketForClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void close() throws IOException {
        socket.close();
    }

}
