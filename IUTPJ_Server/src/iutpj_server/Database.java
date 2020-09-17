/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 *
 * @author ASADUZZAMAN HEROK
 */
public class Database {

    private final String dbUserName;
    private final String dbPassword;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private CallableStatement callableStatement;
    private Statement statement;
    private final Timer timer;
    private Long nextStartTime, nextFinishTime;

    public Database() {
        dbUserName = "iutpj";
        dbPassword = "IutPj";
        connection = null;
        preparedStatement = null;
        callableStatement = null;
        statement = null;

        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nextStartTime < System.currentTimeMillis() | nextFinishTime < System.currentTimeMillis()) {
                    updateState();
                }
            }
        });
        this.timer.start();
    }

    public void connectToDatebase() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", this.dbUserName, this.dbPassword);
        updateState();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
// Client manupulation:

    public synchronized String getAdminPassword(String usrname) {
        String query = "select PASSWORD from ADMIN_INFO where USER_NAME = '" + usrname + "'";
        String pass = "No#Data";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next() == false) {
                statement.close();
                return pass;
            }
            pass = rs.getString(1);
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return pass;
    }

    public synchronized String getAdminID(String usrname) {
        String query = "select ID from ADMIN_INFO where USER_NAME = '" + usrname + "'";
        String pass = "No#Data";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next() == false) {
                statement.close();
                return pass;
            }
            pass = rs.getString(1);
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return pass;
    }

    public synchronized String getUserID(String usrname) {
        String query = "select ID from USER_INFO where USER_NAME = '" + usrname + "'";
        String pass = "No#Data";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next() == false) {
                statement.close();
                return pass;
            }
            pass = rs.getString(1);
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return pass;
    }

    public synchronized String getUserPassword(String usrname) {
        String query = "select PASSWORD from USER_INFO where USER_NAME = '" + usrname + "'";
        String pass = "No#Data";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            if (rs.next() == false) {
                statement.close();
                return pass;
            }
            pass = rs.getString(1);
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return pass;
    }

    public synchronized String updateAdmin(String usrname, String password) {

        String sql = "{? = call INSERT_ADMIN(?,?,?,?,?)}";
        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.setString(2, usrname);
            callableStatement.setString(3, password);
            callableStatement.setString(4, usrname + password);
            callableStatement.setString(5, usrname + password);
            callableStatement.setString(6, usrname + password);
            callableStatement.execute();
            String status = callableStatement.getString(1);
            callableStatement.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }

    public synchronized String updateUser(String usrname, String password) {

        String sql = "{? = call INSERT_USER(?,?,?,?,?)}";

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.setString(2, usrname);
            callableStatement.setString(3, password);
            callableStatement.setString(4, usrname + password);
            callableStatement.setString(5, usrname + password);
            callableStatement.setString(6, usrname + password);
            callableStatement.execute();
            String status = callableStatement.getString(1);
            callableStatement.close();
            return status;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
    }

// Problem manupulation
    public synchronized String addProblemToDB(NewProblem problem, String setter) {
        /*
        CREATE TABLE PROBLEM_SET(
        ID VARCHAR2(15) PRIMARY KEY,
        PROBLEM_NAME VARCHAR2(50) NOT NULL,
        SETTER_ID VARCHAR2(15) NOT NULL,
        TIME_LIMIT NUMBER(5) NOT NULL,
        MEMORY_LIMIT NUMBER(10) NOT NULL,
        LOCKED VARCHAR2(3) NOT NULL,
        STATEMENT BLOB NOT NULL,
        DATASET_IN BLOB NOT NULL,
        DATASET_OUT BLOB NOT NULL,
        FOREIGN KEY(SETTER_ID) REFERENCES ADMIN_INFO(ID)
        );
        INSERT_PROBLEM_SET(
        PNAME IN PROBLEM_SET.PROBLEM_NAME%TYPE,
        SETTER IN PROBLEM_SET.SETTER_ID%TYPE,
        TL IN PROBLEM_SET.TIME_LIMIT%TYPE,
        ML IN PROBLEM_SET.MEMORY_LIMIT%TYPE,
        LKD IN PROBLEM_SET.LOCKED%TYPE,
        STMNT IN PROBLEM_SET.STATEMENT%TYPE,
        DIN IN PROBLEM_SET.DATASET_IN%TYPE,
        DOUT IN PROBLEM_SET.DATASET_OUT%TYPE
        )
         */
        String sql = "{? = call INSERT_PROBLEM_SET(?,?,?,?,?,?,?,?)}";
        System.out.println(problem.getTimeLimit()+' '+problem.getMemoryLimit());
        try {
            Blob problemStatement = connection.createBlob();
            problemStatement.setBytes(1, problem.getProb());
            Blob dataSetIn = connection.createBlob();
            dataSetIn.setBytes(1, problem.getInp());
            Blob dataSetOut = connection.createBlob();
            dataSetOut.setBytes(1, problem.getOutp());

            callableStatement = connection.prepareCall(sql);
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.setString(2, problem.getProblemName());
            callableStatement.setString(3, setter);
            callableStatement.setInt(4, Integer.parseInt(problem.getTimeLimit()));
            callableStatement.setInt(5, Integer.parseInt(problem.getMemoryLimit()));
            callableStatement.setString(6, problem.getProblemID());
            callableStatement.setBlob(7, problemStatement);
            callableStatement.setBlob(8, dataSetIn);
            callableStatement.setBlob(9, dataSetOut);

            callableStatement.execute();
            String status = callableStatement.getString(1);
            callableStatement.close();
            return status;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return ex.toString();
        }
    }

    public synchronized NewProblem getProblem(String problemID, String userType) {

        String query = "SELECT * FROM PROBLEM_SET WHERE ID = '" + problemID + "'";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next() == false) {
                return null;
            }
            if (userType.equals("User") && rs.getString("LOCKED").equals("YES")) {
                return null;
            }

            NewProblem problem = new NewProblem();
            problem.setProblemName(rs.getString("PROBLEM_NAME"));
            problem.setProblemID(rs.getString("ID"));
            problem.setTimeLimit(Integer.toString(rs.getInt("TIME_LIMIT")));
            problem.setMemoryLimit(Integer.toString(rs.getInt("MEMORY_LIMIT")));
            problem.setProb(rs.getBytes("STATEMENT"));
            problem.setInp(rs.getBytes("DATASET_IN"));
            problem.setOutp(rs.getBytes("DATASET_OUT"));
            return problem;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

    }

    public synchronized String deleteProblem(String problemID, String userID) {
        /*
        CREATE OR REPLACE FUNCTION DELETE_PROBLEM(
        PROBLEM_ID IN PROBLEM_SET.ID%TYPE,
        SETTER IN PROBLEM_SET.SETTER_ID%TYPE
        )
         */
        String sql = "{? = call DELETE_PROBLEM(?,?)}";
        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.setString(2, problemID);
            callableStatement.setString(3, userID);

            callableStatement.execute();
            String status = callableStatement.getString(1);
            callableStatement.close();
            return status;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return ex.toString();
        }
    }

    public synchronized String changeProblemLockState(String problemID, String state) {
        String sql = "{? = call CHANGE_PROBLEM_LOCK_STATE(?, ?)}";
        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.setString(2, problemID);
            callableStatement.setString(3, state);

            callableStatement.execute();
            String status = callableStatement.getString(1);
            callableStatement.close();
            return status;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return ex.toString();
        }
    }

    public synchronized String addSubmissionToDB(NewSubmission submission, String userID) {
        /*
        CREATE OR REPLACE FUNCTION INSERT_SUBMISSION(
        PID IN PROBLEM_SET.ID%TYPE,
        USID IN USER_INFO.ID%TYPE,
        CL IN SUBMISSION.CODE_LANGUAGE%TYPE,
        VR IN SUBMISSION.VERDICT%TYPE,
        RT IN SUBMISSION.RUNNING_TIME%TYPE,
        CDF IN SUBMISSION.CODE_FILE%TYPE
        )
        RETURN VARCHAR2
         */
        System.out.println(submission.getProblemID() + " " + userID);
        String sql = "{? = call INSERT_SUBMISSION(?,?,?,?,?,?)}";
        try {
            Blob codeFile = connection.createBlob();
            codeFile.setBytes(1, submission.getCodeF());

            callableStatement = connection.prepareCall(sql);

            callableStatement.registerOutParameter(1, Types.VARCHAR);

            callableStatement.setString(2, submission.getProblemID());
            callableStatement.setString(3, userID);
            callableStatement.setString(4, submission.getLanguage());
            callableStatement.setString(5, "Not Judged");
            callableStatement.setInt(6, -1);
            callableStatement.setBlob(7, codeFile);

            callableStatement.execute();
            String status = callableStatement.getString(1);
            System.out.println(status);
            callableStatement.close();

            return status;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return ex.toString();
        }

    }

    public synchronized String updateVerdict(String submissionID, String verdict, int timetaken) {
        /*
        CREATE OR REPLACE FUNCTION UPDATE_SUBMISSION(
        SID IN SUBMISSION.ID%TYPE,
        VR IN SUBMISSION.VERDICT%TYPE,
        RT IN SUBMISSION.RUNNING_TIME%TYPE
        )
         RETURN VARCHAR2
         */
        String sql = "{? = call UPDATE_SUBMISSION(?,?,?)}";

        try {
            callableStatement = connection.prepareCall(sql);
            callableStatement.registerOutParameter(1, Types.VARCHAR);

            callableStatement.setString(2, submissionID);
            callableStatement.setString(3, verdict);
            callableStatement.setInt(4, timetaken);

            callableStatement.execute();
            String status = callableStatement.getString(1);
            callableStatement.close();
            return status;

        } catch (SQLException ex) {
            System.out.println("DB updateVerdict Err:\n" + ex.getMessage());
            return ex.toString();
        }
    }

    public synchronized NewSubmission getSubmission(String submissionID) {

        String query = "SELECT PROBLEM_ID, CODE_LANGUAGE, CODE_FILE FROM SUBMISSION WHERE ID = '" + submissionID + "'";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next() == false) {
                return null;
            }

            NewSubmission submission = new NewSubmission();

            submission.setProblemID(rs.getString("PROBLEM_ID"));
            submission.setLanguage(rs.getString("CODE_LANGUAGE"));
            submission.setCodeF(rs.getBytes("CODE_FILE"));

            return submission;

        } catch (SQLException ex) {
            System.out.println("problem query Err\n" + ex.getMessage());
            return null;
        }

    }

    public synchronized List<String[]> getProblemTable(String userID) {

        List<String[]> table = new ArrayList<>();
        String[] rowData = new String[5];

        String query = "SELECT PROBLEM_SET.ID AS ID, PROBLEM_NAME, USER_NAME, LOCKED "
                + "FROM PROBLEM_SET INNER JOIN ADMIN_INFO ON SETTER_ID = ADMIN_INFO.ID "
                + "WHERE SETTER_ID = " + ((userID == null) ? "SETTER_ID" : "'" + userID + "'");
        ResultSet rs;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                rowData[0] = rs.getString("ID");
                rowData[1] = rs.getString("PROBLEM_NAME");
                rowData[2] = rs.getString("USER_NAME");
                rowData[4] = "DELETE";
                rowData[3] = rs.getString("LOCKED");
                table.add(rowData.clone());
            }
            return table;
        } catch (SQLException ex) {
            System.out.println("DB getProblemTable err\n" + ex.getMessage());
            return null;
        }

    }

    public synchronized List<String[]> getStatusTable(String userID) {

        List<String[]> tableData = new ArrayList<>();
        String[] rowData = new String[9];

        String sql = "SELECT SUBMISSION.ID AS SID, CODE_LANGUAGE AS CL, VERDICT AS VR, RUNNING_TIME AS RT, "
                + "PROBLEM_NAME AS PNAME, PROBLEM_SET.ID AS PID, "
                + "USER_NAME AS UNAME "
                + "FROM SUBMISSION "
                + "INNER JOIN PROBLEM_SET ON PROBLEM_SET.ID = PROBLEM_ID "
                + "INNER JOIN USER_INFO ON USER_INFO.ID = USER_ID "
                + ((userID != null) ? "WHERE USER_ID = ?" : " ")
                + "ORDER BY SID DESC";
        ResultSet rs;
        try {
            preparedStatement = connection.prepareCall(sql);
            if (userID != null) {
                preparedStatement.setString(1, userID);
            }

            rs = preparedStatement.executeQuery();

            while (rs.next()) {

                rowData[0] = rs.getString("SID");
                rowData[1] = rowData[0].substring(0, 2) + "/"
                        + rowData[0].substring(2, 4) + "/"
                        + rowData[0].substring(4, 8) + ":"
                        + rowData[0].substring(8, 10) + ":"
                        + rowData[0].substring(10, 12) + ":"
                        + rowData[0].substring(12, 14);
                rowData[2] = rs.getString("UNAME");
                rowData[3] = rs.getString("PNAME");
                rowData[4] = rs.getString("CL");
                rowData[5] = rs.getString("VR");
                rowData[6] = Integer.toString(rs.getInt("RT"));
                rowData[7] = rs.getString("PID");

                tableData.add(rowData.clone());
            }
            return tableData;

        } catch (SQLException ex) {
            System.out.println("DB Fetch Status Table err" + ex.toString());
            return null;
        }

    }

    public synchronized List<String[]> getStandingsTable() {
        List<String[]> tableData = new ArrayList<>();
        String[] rowData = new String[3];

        String query = "SELECT USER_INFO.USER_NAME AS UNAME, Count(DISTINCT PROBLEM_id) AS PROBLEMS FROM SUBMISSION, USER_INFO WHERE VERDICT = 'Accepted' AND USER_ID = USER_INFO.ID GROUP BY USER_INFO.USER_NAME ORDER BY PROBLEMS DESC";

        ResultSet rs;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            int position = 1;
            while (rs.next()) {
                rowData[0] = Integer.toString(position++);
                rowData[1] = rs.getString("UNAME");
                rowData[2] = rs.getString("Problems");
                tableData.add(rowData.clone());
            }
            return tableData;

        } catch (SQLException ex) {
            System.out.println("DB getProblemTable err\n" + ex.toString());
            return null;
        }
    }

    public synchronized List<String[]> getContestTable() {
        List<String[]> tableData = new ArrayList<>();
        String[] rowData = new String[7];

        String query = "SELECT CONTEST_INFO.ID AS CID, CONTEST_NAME, START_TIME, DURATION_MIN, FINISHED, ADMIN_INFO.USER_NAME AS SETTER FROM CONTEST_INFO, ADMIN_INFO WHERE SETTER_ID = ADMIN_INFO.ID ORDER BY START_TIME DESC";

        ResultSet rs;
        try {
            this.timer.stop();
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                rowData[0] = rs.getString("CID");
                rowData[1] = rs.getString("CONTEST_NAME");
                rowData[2] = rs.getString("SETTER");
                Timestamp startTime = rs.getTimestamp("START_TIME");
                rowData[3] = startTime.toString();
                rowData[4] = Integer.toString(rs.getInt("DURATION_MIN"));
                rowData[5] = rs.getString("FINISHED");

                tableData.add(rowData.clone());
            }
            this.timer.start();
            return tableData;

        } catch (SQLException ex) {
            System.out.println("DB getProblemTable err\n" + ex.toString());
            return null;
        }
    }

    public synchronized String addContest(ContestInfo contestInfo, String setter) {

        /*
        ID VARCHAR2(15) PRIMARY KEY,
        CONTEST_NAME VARCHAR2(200) NOT NULL,
        START_TIME TIMESTAMP NOT NULL,
        DURATION_MIN NUMBER(10) NOT NULL,
        FINISHED VARCHAR2(10) NOT NULL,
        SETTER_ID VARCHAR2(15) NOT NULL,
        FOREIGN KEY(SETTER_ID) REFERENCES ADMIN_INFO(ID) ON DELETE CASCADE
        function parameter:
        CNAME IN CONTEST_INFO.CONTEST_NAME%TYPE, 
        STIME IN CONTEST_INFO.START_TIME%TYPE,
        DRTION IN CONTEST_INFO.DURATION_MIN%TYPE, 
        SETTER IN CONTEST_INFO.SETTER_ID%TYPE
         */
        String insert_contest = "{? = call INSERT_CONTEST(?,?,?,?)}";
        String insert_problems = "{? = call ADD_PROBLEM_TO_CONTEST(?,?)}";

        try {
            callableStatement = connection.prepareCall(insert_contest);

            callableStatement.setString(2, contestInfo.getContestName());
            callableStatement.setDate(3, new java.sql.Date(contestInfo.getStartTime().getTime()));
            callableStatement.setInt(4, Integer.parseInt(contestInfo.getdurationMinutes()));
            callableStatement.setString(5, setter);
            callableStatement.registerOutParameter(1, Types.VARCHAR);

            callableStatement.execute();
            String contestID = callableStatement.getString(1);
            callableStatement.close();

            for (String problems : contestInfo.getProblemIDs()) {
                callableStatement = connection.prepareCall(insert_problems);
                callableStatement.setString(2, problems);
                callableStatement.setString(3, contestID);
                callableStatement.registerOutParameter(1, Types.VARCHAR);
                callableStatement.execute();
                if (callableStatement.getString(1).equals("OVER")) {
                    break;
                }
                callableStatement.close();
            }
            updateState();
            return "SUCCESSFULL";
        } catch (SQLException ex) {
            return "Contest saving Failed:\n" + ex.toString();
        }

    }

    public synchronized ContestInfo getContestInfo(String contestID) {
        String contestQuery = "SELECT CONTEST_INFO.ID AS CID, CONTEST_NAME, START_TIME, DURATION_MIN, ADMIN_INFO.USER_NAME AS SETTER FROM CONTEST_INFO, ADMIN_INFO WHERE SETTER_ID = ADMIN_INFO.ID AND CONTEST_INFO.ID = ?";
        String problemQuery = "SELECT PROBLEM_ID FROM CONTEST_PROBLEM_JUNCTION WHERE CONTEST_ID = ?";
        try {
            preparedStatement = connection.prepareStatement(contestQuery);
            preparedStatement.setString(1, contestID);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next() == false) {
                return null;
            }

            ContestInfo contestInfo = new ContestInfo();
            contestInfo.setContestID(rs.getString("CID"));
            contestInfo.setContestName(rs.getString("CONTEST_NAME"));
            contestInfo.setContestSetter(rs.getString("SETTER"));
            contestInfo.setDurationMinutes(Integer.toString(rs.getInt("DURATION_MIN")));
            contestInfo.setStartTime(new java.util.Date(rs.getDate("START_TIME").getTime()));
            preparedStatement.close();

            preparedStatement = connection.prepareCall(problemQuery);
            preparedStatement.setString(1, contestID);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                contestInfo.addProblem(rs.getString("PROBLEM_ID"));
            }
            preparedStatement.close();
            return contestInfo;

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public synchronized void updateContest(ContestInfo contestInfo, String setter) {
        String call = "{? = call Update_contest(?,?,?,?,?,?}";
        String status;
        if (contestInfo.getStartTime().getTime() + Long.parseLong(contestInfo.getdurationMinutes()) < System.currentTimeMillis()) {
            status = "OVER";
        } else if (contestInfo.getStartTime().getTime() < System.currentTimeMillis()) {
            status = "RUNNING";
        } else {
            status = "COMMING";
        }

        try {
            callableStatement = connection.prepareCall(call);

            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.setString(2, contestInfo.getContestID());
            callableStatement.setString(3, contestInfo.getContestName());
            callableStatement.setDate(4, new java.sql.Date(contestInfo.getStartTime().getTime()));
            callableStatement.setString(5, contestInfo.getdurationMinutes());
            callableStatement.setString(6, status);
            callableStatement.setString(7, setter);

            callableStatement.execute();
            callableStatement.close();
            updateState();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private synchronized void updateState() {
        this.timer.stop();
        String call = "{call Update_state(?,?)}";

        try {
            callableStatement = connection.prepareCall(call);
            callableStatement.registerOutParameter(1, Types.DATE);
            callableStatement.registerOutParameter(2, Types.DATE);
            callableStatement.execute();

            nextStartTime = (callableStatement.getDate(1) == null) ? Long.MAX_VALUE : callableStatement.getDate(1).getTime();
            nextFinishTime = (callableStatement.getDate(2) == null) ? Long.MAX_VALUE : callableStatement.getDate(2).getTime();

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.timer.start();
    }

    public synchronized List<String[]> getContestProblemSet(String contestID) {
        String sqlQuery = "SELECT ID, PROBLEM_NAME, TIME_LIMIT, MEMORY_LIMIT "
                + "FROM CONTEST_PROBLEM_JUNCTION INNER JOIN PROBLEM_SET ON PROBLEM_ID = ID "
                + "WHERE CONTEST_ID = ?";

        List<String[]> table = new ArrayList<>();
        String[] rowData = new String[4];
        try {
            preparedStatement = connection.prepareCall(sqlQuery);
            preparedStatement.setString(1, contestID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                rowData[0] = rs.getString("ID");
                rowData[1] = rs.getString("PROBLEM_NAME");
                rowData[2] = rs.getString("TIME_LIMIT");
                rowData[3] = rs.getString("MEMORY_LIMIT");
                table.add(rowData.clone());
            }
            return table;

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            return null;
        }
    }

    public synchronized List<String[]> getContestStatusTable(String contestID, String userID) {

        System.out.println(contestID + ' ' + userID);
        List<String[]> tableData = new ArrayList<>();
        String[] rowData = new String[8];

        String query = "SELECT SUBMISSION.ID AS SID, CODE_LANGUAGE AS CL, VERDICT AS VR, RUNNING_TIME AS RT, "
                + "PROBLEM_SET.PROBLEM_NAME AS PNAME, PROBLEM_SET.ID AS PID, "
                + "USER_INFO.USER_NAME AS UNAME "
                + "FROM SUBMISSION "
                + "INNER JOIN PROBLEM_SET ON PROBLEM_SET.ID = PROBLEM_ID "
                + "INNER JOIN USER_INFO ON USER_INFO.ID = USER_ID "
                + "INNER JOIN CONTEST_SUBMISSION_JUNCTION ON SUBMISSION.ID = SUBMISSION_ID "
                + "WHERE CONTEST_ID = ? AND USER_ID = ? "
                + "ORDER BY SID DESC";
        if (userID == null) {
            query = query.substring(0, query.length() - 33) + " ORDER BY SID DESC";
        }
        ResultSet rs;
        try {
            preparedStatement = connection.prepareCall(query);
            if (userID != null) {
                preparedStatement.setString(2, userID);
            }
            preparedStatement.setString(1, contestID);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {

                rowData[0] = rs.getString("SID");
                rowData[1] = rowData[0].substring(0, 2) + "/"
                        + rowData[0].substring(2, 4) + "/"
                        + rowData[0].substring(4, 8) + ":"
                        + rowData[0].substring(8, 10) + ":"
                        + rowData[0].substring(10, 12) + ":"
                        + rowData[0].substring(12, 14);
                rowData[2] = rs.getString("UNAME");
                rowData[3] = rs.getString("PNAME");
                rowData[4] = rs.getString("CL");
                rowData[5] = rs.getString("VR");
                rowData[6] = Integer.toString(rs.getInt("RT"));
                rowData[7] = rs.getString("PID");

                tableData.add(rowData.clone());
            }
            return tableData;

        } catch (SQLException ex) {
            System.out.println("DB Fetch Status Table err" + ex.toString());
            return null;
        }

    }

    public synchronized String addContestSubmissionToDB(NewSubmission submission, String userID, String contestID) {

        String submissionID = addSubmissionToDB(submission, userID);

        String mapSubmission = "{? = call SUBMISSION_FOR_CONTEST(?,?)}";
        try {
            callableStatement = connection.prepareCall(mapSubmission);
            callableStatement.setString(2, submissionID);
            callableStatement.setString(3, contestID);
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.execute();

            callableStatement.close();
            return submissionID;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return ex.toString();
        }

    }

    public synchronized List<String[]> getContestStandingRawData(String contestID) {
        String sqlQuery = "SELECT USER_INFO.USER_NAME AS UNAME, SUBMISSION.PROBLEM_ID AS PID, SUBMISSION.SUBMISSION_TIME AS STIME "
                + "FROM SUBMISSION "
                + "INNER JOIN USER_INFO ON USER_INFO.ID = USER_ID "
                + "INNER JOIN CONTEST_SUBMISSION_JUNCTION ON SUBMISSION.ID = SUBMISSION_ID "
                + "WHERE VERDICT = 'Accepted' and CONTEST_ID = ?";

        List<String[]> table = new ArrayList<>();
        String[] rowData = new String[3];
        try {
            preparedStatement = connection.prepareCall(sqlQuery);
            preparedStatement.setString(1, contestID);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                rowData[0] = rs.getString("UNAME");
                rowData[1] = rs.getString("PID");
                System.out.println(rs.getDate("STIME"));
                rowData[2] = Long.toString(rs.getDate("STIME").getTime());
                System.out.println("STIME " + rowData[2]);
                table.add(rowData.clone());
            }
            return table;

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
            return null;
        }
    }
}
