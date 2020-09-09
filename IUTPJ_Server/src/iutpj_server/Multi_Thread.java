/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_server;

import java.io.IOException;
import java.net.Socket;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 *
 * @author ASADUZZAMAN HEROK
 */
public class Multi_Thread implements Runnable {

    private final SocketForClient clientSocket;
    private final Database database;
    private String userType;
    private DataPacket dataPacket;

    Multi_Thread(Socket sc, Database db) throws IOException {
        this.clientSocket = new SocketForClient(sc);
        this.database = db;
        userType = null;

    }

    @Override
    public void run() {
        dataPacket = clientSocket.getPacket();
        userType = (String) dataPacket.getValue("UserType");
        dataPacket.addPayLoad("Status", "Connection OK");
        clientSocket.sendDataPacket(dataPacket);

        while (true) {
            dataPacket = clientSocket.getPacket();
            if (dataPacket == null) {
                break;
            }
            System.out.println(dataPacket.getRoute());
            String route = dataPacket.getRoute().split("/")[0];
            switch (route) {
                case "user":
                    routeUser();
                    break;
                case "offline":
                    routeOffline();
                    break;
                case "contest":
                    routeContest();
                    break;
                default:
                    System.out.println("Unrecognized root route");
                    dataPacket.addPayLoad("Error", "Unrecognized root route :" + dataPacket.getRoute());
                    clientSocket.sendDataPacket(dataPacket);
                    break;
            }
        }
        System.out.println("A user is Disconnected");
    }

    private void routeUser() {
        String route = dataPacket.getRoute().split("/")[1];

        switch (route) {
            case "login":
                routeUserLogin();
                break;
            case "signUp":
                routeUserSignUp();
                break;
            case "forgottenPassword":
                routeUserForgottenPassword();
                break;
            case "updateInfo":
                routeUserUpdateInfo();
                break;
            default:
                System.err.println("Unrecognized user route : "+dataPacket.getRoute());
                dataPacket.addPayLoad("Error", "Unrecognized user route :" + dataPacket.getRoute());
                clientSocket.sendDataPacket(dataPacket);
                break;
        }
    }

    private void routeUserLogin() {
        String userName = (String) dataPacket.getValue("Username");
        String password = (String) dataPacket.getValue("Password");
        UserAuthenticationAuthorization userLogin = new UserAuthenticationAuthorization(userType, userName, password, database);
        DataPacket response = new DataPacket();

        response.setUserID(userLogin.getUserID());
        response.addPayLoad("Authorization", userLogin.isAuthorized());
        clientSocket.sendDataPacket(response);
    }

    private void routeUserSignUp(){
        String userName = (String) dataPacket.getValue("Username");
        String password = (String) dataPacket.getValue("Password");
        UserAuthenticationAuthorization userSignUp = new UserAuthenticationAuthorization(userType, userName, password, database);

        DataPacket response = new DataPacket();
        if (userSignUp.doesExist()) {
            response.addPayLoad("Status", false);
            response.addPayLoad("Error", "User Name Exist");
            clientSocket.sendDataPacket(response);
            return;
        }
        response.addPayLoad("Status", userSignUp.SignUp());
        response.setUserID(userSignUp.getUserID());
        clientSocket.sendDataPacket(response);
    }

    private void routeUserForgottenPassword() {

    }

    private void routeUserUpdateInfo() {

    }

    private void routeContest() {
        String route = dataPacket.getRoute().split("/")[1];

        switch (route) {
            case "new":
                routeContestNew();
                break;
            case "update":
                routeContestUpdate();
                break;
            case "info":
                routeContestInfo();
                break;
            case "table":
                routeContestTable();
                break;
            case "problem":
                routeContestProblem();
                break;
            case "submission":
                routeContestSubmission();
                break;
            case "status":
                routeContestStatus();
                break;
            case "standing":
                routeContestStandingRawData();
                break;
            default:
                dataPacket.addPayLoad("Error", "Unrecognized Contest route :" + dataPacket.getRoute());
                clientSocket.sendDataPacket(dataPacket);
                break;
        }

    }

    private void routeContestNew() {
        ContestInfo contestInfo = (ContestInfo) dataPacket.getValue("ContestInfo");
        DataPacket response = new DataPacket();

        String status = database.addContest(contestInfo, dataPacket.getUserID());
        response.addPayLoad("Status", status);
        clientSocket.sendDataPacket(response);
    }

    private void routeContestUpdate() {

    }
    
    private void routeContestInfo(){
        String contestID = (String)dataPacket.getValue("ContestID");
        DataPacket response = dataPacket;
        response.addPayLoad("ContestInfo", database.getContestInfo(contestID));
        clientSocket.sendDataPacket(response);    
    }

    private void routeContestTable() {
        DataPacket response = new DataPacket();
        response.setRoute(dataPacket.getRoute());
        response.addPayLoad("Table", database.getContestTable());
        clientSocket.sendDataPacket(response);
    }

    private void routeContestProblem() {
        String route = dataPacket.getRoute().split("/")[2];
        switch (route) {
            case "table":
                routeContestProblemTable();
                break;
            case "file":
                routeContestProblemFile();
                break;
            default:
                System.out.println("Unrecognized Contest Problem Route: \n" + route);
                dataPacket.addPayLoad("Error", "Unrecognized Contest Problem Route: " + dataPacket.getRoute());
                clientSocket.sendDataPacket(dataPacket);
                break;
        }
    }

    private void routeContestProblemTable() {
        String contestID = (String) dataPacket.getValue("Option");
        System.out.println("Contest Problem set Request: "+contestID);
        DataPacket response = new DataPacket();

        response.setUserID(dataPacket.getUserID());
        response.setRoute(dataPacket.getRoute());
        response.addPayLoad("Table", database.getContestProblemSet(contestID));

        clientSocket.sendDataPacket(response);
    }

    private void routeContestProblemFile() {
        String problemID = (String) dataPacket.getValue("ProblemID");
        System.err.println(problemID);
        DataPacket response = new DataPacket();

        response.setUserID(dataPacket.getUserID());
        response.addPayLoad("File", database.getProblem(problemID,"Contest"));

        clientSocket.sendDataPacket(response);
    }

    private void routeContestSubmission() {
        String route = dataPacket.getRoute().split("/")[2];
        switch (route) {
            case "new":
                routeContestSubmissionNew();
                break;
            case "file":
                routeContestSubmissionFile();
                break;
            default:
                System.out.println("Unrecognized Contest Submission Route: \n" + route);
                dataPacket.addPayLoad("Error", "Unrecognized Contest Submission Route:" + dataPacket.getRoute());
                clientSocket.sendDataPacket(dataPacket);
                break;
        }
    }

    private void routeContestSubmissionNew() {
        NewSubmission submission = (NewSubmission) dataPacket.getValue("File");
        String contestID = (String)dataPacket.getValue("ContestID");
        if (submission == null) {
            dataPacket.addPayLoad("Status", "Submission File Not Found");
            clientSocket.sendDataPacket(dataPacket);
            return;
        }
        String submissionID = database.addContestSubmissionToDB(submission, dataPacket.getUserID(),contestID);
        System.err.println(submissionID);
        DataPacket response = new DataPacket();
        
        if (submissionID.length() != 14) {
            response = dataPacket;
            response.addPayLoad("Status", submissionID);
            clientSocket.sendDataPacket(response);
            return;
        }
        response.addPayLoad("SubmisionID", submissionID);
        NewProblem problem = database.getProblem(submission.getProblemID(),"submision");
        if (problem != null) {
            Thread run = new Thread(new CompileAndRun(problem, submission, submissionID, database));
            run.start();

        } else {
            response.addPayLoad("Status", "Problem Not Found");
            clientSocket.sendDataPacket(response);
            return;
        }
        response.addPayLoad("Status", "OK");
        clientSocket.sendDataPacket(response);
    }

    private void routeContestSubmissionFile() {

    }

    private void routeContestStatus() {
        String option = (String)dataPacket.getValue("Option");
        String contestID = (String)dataPacket.getValue("ContestID");
        DataPacket response = new DataPacket();
        
        response.addPayLoad("Table", database.getContestStatusTable(contestID, option));
        response.setRoute(dataPacket.getRoute());
        clientSocket.sendDataPacket(response); 

    }

    private void routeContestStandingRawData() {
        String contestID = (String)dataPacket.getValue("ContestID");
        DataPacket response = new DataPacket();
        
        response.addPayLoad("Table", database.getContestStandingRawData(contestID));
        System.out.println(response.getPayLoad());
        response.setRoute(dataPacket.getRoute());
        clientSocket.sendDataPacket(response); 
    }

    private void routeOffline() {
        String route = dataPacket.getRoute().split("/")[1];

        switch (route) {
            case "problem":
                routeOfflineProblem();
                break;
            case "submission":
                routeOfflineSubmission();
                break;
            case "status":
                routeOfflineStatus();
                break;
            case "standing":
                routeOfflineStanding();
                break;
            default:
                System.out.println("Unrecognized Offline Route");
                dataPacket.addPayLoad("Error", "Unrecognized Offline route :" + dataPacket.getRoute());
                clientSocket.sendDataPacket(dataPacket);
                break;
        }
    }

    private void routeOfflineProblem() {
        String route = dataPacket.getRoute().split("/")[2];
        switch (route) {
            case "table":
                routeOfflineProblemTable();
                break;
            case "new":
                routeOfflineProblemNew();
                break;
            case "file":
                routeOfflineProblemFile();
                break;
            case "delete":
                routeOfflineProblemDelete();
                break;
            case "update":
                routeOfflineProblemUpdate();
                break;
            case "lock":
                routeOfflineProblemLock();
                break;
            default:
                System.out.println("Unrecognized Offline Problem Route");
                dataPacket.addPayLoad("Error", "Unrecognized Offline Problem route :" + dataPacket.getRoute());
                clientSocket.sendDataPacket(dataPacket);
                break;
        }

    }

    private void routeOfflineProblemNew() {
        NewProblem newProblem = (NewProblem) dataPacket.getValue("File");
        DataPacket response = new DataPacket();
        String status = database.addProblemToDB(newProblem, dataPacket.getUserID());

        response.addPayLoad("Status", status);
        clientSocket.sendDataPacket(response);

    }

    private void routeOfflineProblemTable() {
        String option = (String) dataPacket.getValue("Option");
        DataPacket response = new DataPacket();

        response.setUserID(dataPacket.getUserID());
        response.setRoute(dataPacket.getRoute());
        response.addPayLoad("Table", database.getProblemTable(option));

        clientSocket.sendDataPacket(response);
    }

    private void routeOfflineProblemFile() {
        String problemID = (String) dataPacket.getValue("ProblemID");
        System.err.println(problemID);
        DataPacket response = new DataPacket();

        response.setUserID(dataPacket.getUserID());
        response.addPayLoad("File", database.getProblem(problemID,userType));

        clientSocket.sendDataPacket(response);
    }

    private void routeOfflineProblemDelete() {
        String userID = dataPacket.getUserID();
        String problemID = (String) dataPacket.getValue("ProblemID");
        String status = database.deleteProblem(problemID, userID);

        DataPacket response = new DataPacket();
        response.setUserID(userID);
        response.setRoute(dataPacket.getRoute());
        response.addPayLoad("Status", status);
        clientSocket.sendDataPacket(response);

    }
    private void routeOfflineProblemLock() {
        String userID = dataPacket.getUserID();
        String problemID = (String) dataPacket.getValue("ProblemID");
        String state = (String) dataPacket.getValue("State");
        String status = database.changeProblemLockState(problemID, state);

        DataPacket response = new DataPacket();
        response.setUserID(userID);
        response.setRoute(dataPacket.getRoute());
        response.addPayLoad("Status", status);
        clientSocket.sendDataPacket(response);
    }

    private void routeOfflineProblemUpdate() {
    }

    private void routeOfflineSubmission() {
        String route = dataPacket.getRoute().split("/")[2];
        switch (route) {
            case "new":
                routeOfflineSubmissionNew();
                break;
            case "file":
                routeOfflineSubmissionFile();
                break;
            default:
                System.out.println("Unrecognized Offline Submission Route");
                dataPacket.addPayLoad("Error", "Unrecognized Offline Submission route :" + dataPacket.getRoute());
                clientSocket.sendDataPacket(dataPacket);

                break;
        }
    }

    private void routeOfflineSubmissionNew() {
        NewSubmission submission = (NewSubmission) dataPacket.getValue("File");
        if (submission == null) {
            dataPacket.addPayLoad("Error", "Submission File Not Found");
            clientSocket.sendDataPacket(dataPacket);
            return;
        }
        String submissionID = database.addSubmissionToDB(submission, dataPacket.getUserID());
        DataPacket response = new DataPacket();
        
        if (submissionID.length() != 14) {
            response = dataPacket;
            response.addPayLoad("Error", submissionID);
            clientSocket.sendDataPacket(response);
            return;
        }
        response.addPayLoad("SubmisionID", submissionID);
        NewProblem problem = database.getProblem(submission.getProblemID(),userType);
        if (problem != null) {
            Thread run = new Thread(new CompileAndRun(problem, submission, submissionID, database));
            run.start();

        } else {
            response.addPayLoad("Error", "Problem Not Found");
            clientSocket.sendDataPacket(response);
            return;
        }
        response.addPayLoad("Status", "OK");
        clientSocket.sendDataPacket(response);
    }

    private void routeOfflineSubmissionFile() {
        String submissionID = (String)dataPacket.getValue("SubmissionID");
        DataPacket response = new DataPacket();
        NewSubmission submission = database.getSubmission(submissionID);
        
        response.addPayLoad("File", submission);
        clientSocket.sendDataPacket(response); 
    }

    private void routeOfflineStatus() {
        String option = (String)dataPacket.getValue("Option");
        DataPacket response = new DataPacket();
        
        response.addPayLoad("Table", database.getStatusTable(option));
        response.setRoute(dataPacket.getRoute());
        clientSocket.sendDataPacket(response); 
    }

    private void routeOfflineStanding() {
        DataPacket response = dataPacket;
        response.addPayLoad("Table", database.getStandingsTable());
        clientSocket.sendDataPacket(response);
    }
}
