/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_server;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 *
 * @author ASADUZZAMAN HEROK
 */
public class Multi_Thread implements Runnable {

    private final SocketForClient sc;
    private final Database database;
    private String clienttype;
    private String userID;

    Multi_Thread(Socket sc, Database db) throws IOException {
        this.sc = new SocketForClient(sc);
        this.database = db;
        clienttype = null;

    }

    @Override
    public void run() {

        clienttype = sc.readData();
        System.out.println(clienttype);
        while (true) {
            String data = sc.readData();
            if (data == null) {
                break;
            }

            String code = data.substring(0, 8);
            System.out.println(data + " " + code);
            switch (code) {
                case "Login---":
                    LoginSignUpHandler loginhandler = new LoginSignUpHandler(data, clienttype, database);
                    if (loginhandler.isValid()) {
                        sc.sendData("LoginTrue");
                        int x = data.indexOf(']', 9);
                        userID = loginhandler.getUserID();

                    } else {
                        sc.sendData("LoginFalse");
                    }
                    break;

                case "SignUp--":
                    LoginSignUpHandler signUPhandler = new LoginSignUpHandler(data, clienttype, database);
                    if (signUPhandler.doesExist()) {
                        sc.sendData("Exist---");
                    } else if (signUPhandler.SignUp()) {
                        sc.sendData("SignUpTr");
                    } else {
                        sc.sendData("SignUpFl");
                    }
                    break;

                case "AddProb-":
                    System.out.println("AddProb- called");
                    NewProblem newproblem;
                    try {
                        newproblem = sc.saveProblem();
                        String status = database.addProblemToDB(newproblem, userID);
                        if (status.equals("SUCCESS")) {
                            System.out.println("Problem Added");
                        } else {
                            System.out.println("Problem adding failed " + status);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        System.out.println("Problem Object reading err " + ex.getMessage());
                    }
                    break;

                case "AddSub--":
                    System.out.println("AddSub-- called");
                    String submissionID;

                    NewSubmission newsubmission = null;

                    try {
                        newsubmission = sc.saveSubmission();
                        submissionID = database.addSubmissionToDB(newsubmission, userID);
                        if (submissionID != null && submissionID.length() == 14) {
                            System.out.println("submission Added");
                        } else {
                            System.out.println("submission adding failed");
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        System.out.println("Submission Object reading err " + ex.getMessage());
                        submissionID = null;
                    }
                    if (newsubmission != null) {
                        NewProblem problem = database.getProblem(newsubmission.getProblemID());
                        if (problem != null) {
                            Thread t = new Thread(new CompileAndRun(problem, newsubmission, submissionID, database));
                            t.start();
                        }
                    }
                    break;

                case "PrbTable":

                    int x = data.indexOf(']', 9);

                    String tableForWhom = data.substring(9, x);

                    if (sc.sendProblemTable(database.getProblemTable((tableForWhom.equals("MyDel") || tableForWhom.equals("My")) ? userID : null))) {
                        System.out.println("ProblemTable Sent");
                    } else {
                        System.out.println("ProblemTable Sending Failed");
                    }
                    break;

                case "StTable-":

                    x = data.indexOf(']', 9);
                    tableForWhom = data.substring(9, x);

                    if (sc.sendStatusTable(database.getStatusTable((tableForWhom.equals("My") ? userID : null), clienttype))) {
                        System.out.println("StatusTable Sent");
                    } else {
                        System.out.println("StatusTable Sending Failed");
                    }
                    break;

                case "StdTable":

                    x = data.indexOf(']', 9);
                    String identifier = data.substring(9, x);
                    System.out.println(identifier);

                    if (sc.sendStandingsTable(database.getStandingsTable())) {
                        System.out.println("StandingsTable Sent");
                    } else {
                        System.out.println("StandingsTable Sending Failed");
                    }
                    break;

                case "SrcCode-":
                    x = data.indexOf(']', 9);
                    submissionID = data.substring(9, x);

                    if (sc.sendSubmission(database.getSubmission(submissionID))) {
                        System.out.println("Submission Sent");
                    } else {
                        System.out.println("Submission Sending Failed");
                    }
                    break;

                case "ProbFile":
                    x = data.indexOf(']', 9);
                    String problemID = data.substring(9, x);

                    if (sc.sendProblem(database.getProblem(problemID))) {
                        System.out.println("Problem Sent");
                    } else {
                        System.out.println("Problem Sending Failed");
                    }
                    break;
                case "DelProb-":
                    x = data.indexOf(']', 9);
                    problemID = data.substring(9, x);

                    String status = database.deleteProblem(problemID, userID);
                    System.out.println(status);
                    if (sc.sendProblemTable(database.getProblemTable(userID))) {
                        System.out.println("ProblemTable Sent");
                    } else {
                        System.out.println("ProblemTable Sending Failed");
                    }
                case "CntstTab":
                    if (sc.sendContestTable(database.getContestTable())) {
                        System.out.println("ProblemTable Sent");
                    } else {
                        System.out.println("ProblemTable Sending Failed");
                    }
                    break;
                case "CntstInf":
                    try {
                        System.out.println(database.addContest(sc.saveContestInfo(), userID));
                    } catch (IOException | ClassNotFoundException ex) {
                        System.out.println("Contest Save Failled " + ex.toString());
                    }
                    break;
                case "getCntst":
                    x = data.indexOf(']', 9);
                    String contestID = data.substring(9, x);
                    sc.sendContestInfo(database.getContestInfo(contestID));
                    
                    break;
                default:
                    break;
            }

        }
        System.out.println("A user is Disconnected");
    }

}
