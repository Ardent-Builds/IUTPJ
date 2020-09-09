/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ASADUZZAMAN HEROK
 */
public class Server implements Runnable {

    private ServerSocket serverSocket;
    private final Database database;
    private final int port;
    private boolean serverRunning;

    public Server(int port) {
        this.port = port;
        database = new Database();
        serverRunning = true;
        serverSocket = null;
    }

    private boolean startServer() {
        try {
            this.serverSocket = new ServerSocket(port);
            this.serverSocket.setSoTimeout(10000);
            database.connectToDatebase();
            JOptionPane.showMessageDialog(null, "Server Started on Port: " + port, null, JOptionPane.INFORMATION_MESSAGE);
            return true;

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Server Start Failed, Err:" + ex.getMessage(), "Server Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Database connection failed,\n Err: " + ex.toString() + "\nServer Start Failed", "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void stopServer() {
        serverRunning = false;
        System.out.println("Server Stopping in 10 second....");
        try {
            serverSocket.close();
            database.closeConnection();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("server didn't closed " + ex);
        }
    }

    @Override
    public void run() {
        System.out.println("Server Started........");
        if (!startServer()) {
            return;
        }

        while (serverRunning) {
            try {
                Socket sc = serverSocket.accept();
                Thread t = new Thread(new Multi_Thread(sc, database));
                t.start();
                System.out.println("Client " + t.getId() + " connected");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }  
        }
    }
}
