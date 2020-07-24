/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import newproblem.NewProblem;
import newsubmission.NewSubmission;

/**
 *
 * @author ASADUZZAMAN HEROK
 */
public class Database {

    private final String dbUserName;
    private final String dbPassword;
    Connection conn;
    PreparedStatement prprdstmnt;
    CallableStatement callableStatement;
    Statement stmnt;

    public Database() {
        dbUserName = "iutpj";
        dbPassword = "IutPj";
        conn = null;
        prprdstmnt = null;
        callableStatement = null;
        stmnt = null;
    }

    public void connectToDatebase() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", this.dbUserName, this.dbPassword);
    }

// Client manupulation:
    public synchronized String getAdminPassword(String usrname) {
        String query = "select PASSWORD from ADMIN_INFO where USER_NAME = '" + usrname + "'";

        try {
            stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            if (rs.next() == false) {
                return "No#Data";
            }
            return rs.getString(1);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Statement error " + ex);
            return "No#Data";
        }

    }

    public synchronized String getAdminID(String usrname) {
        String query = "select ID from ADMIN_INFO where USER_NAME = '" + usrname + "'";
        try {
            stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            if (rs.next() == false) {
                return "No#Data";
            }

            return rs.getString(1);

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Statement error " + ex);
            return "No#Data";
        }
    }

    public synchronized String getUserID(String usrname) {
        String query = "select ID from USER_INFO where USER_NAME = '" + usrname + "'";
        try {
            stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            if (rs.next() == false) {
                return "No#Data";
            }
            return rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Statement error " + ex);
            return "No#Data";
        }
    }

    public synchronized String getUserPassword(String usrname) {
        String query = "select PASSWORD from USER_INFO where USER_NAME = '" + usrname + "'";
        try {
            stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(query);
            if (rs.next() == false) {
                return "No#Data";
            }
            return rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Statement error " + ex);
            return "No#Data";
        }
    }

    public synchronized String updateAdmin(String usrname, String password) {

        String sql = "{? = call INSERT_ADMIN(?,?,?,?,?)}";
        try {
            callableStatement = conn.prepareCall(sql);
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
            callableStatement = conn.prepareCall(sql);
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

        try {
            Blob problemStatement = conn.createBlob();
            problemStatement.setBytes(1, problem.getProb());
            Blob dataSetIn = conn.createBlob();
            dataSetIn.setBytes(1, problem.getInp());
            Blob dataSetOut = conn.createBlob();
            dataSetOut.setBytes(1, problem.getOutp());

            callableStatement = conn.prepareCall(sql);
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.setString(2, problem.getProblemName());
            callableStatement.setString(3, setter);
            callableStatement.setInt(4, Integer.parseInt(problem.getTimeLimit()));
            callableStatement.setInt(5, Integer.parseInt(problem.getMemoryLimit()));
            callableStatement.setString(6, "YES");
            callableStatement.setBlob(7, problemStatement);
            callableStatement.setBlob(8, dataSetIn);
            callableStatement.setBlob(9, dataSetOut);

            callableStatement.execute();
            String status = callableStatement.getString(1);
            callableStatement.close();
            return status;

        } catch (SQLException ex) {
            System.out.println("Databasse.java: " + ex.toString());
            return ex.toString();
        }
    }

    public synchronized NewProblem getProblem(String problemID) {

        String query = "SELECT * FROM PROBLEM_SET WHERE ID = '" + problemID + "'";

        try {
            stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(query);

            if (rs.next() == false) {
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
            System.out.println("DB GET PROBLEM: " + ex.toString());
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
            callableStatement = conn.prepareCall(sql);
            callableStatement.registerOutParameter(1, Types.VARCHAR);
            callableStatement.setString(2, problemID);
            callableStatement.setString(3, userID);

            callableStatement.execute();
            String status = callableStatement.getString(1);
            callableStatement.close();
            return status;
        } catch (SQLException ex) {
            System.out.println("DB Delete Problem " + ex.toString());
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
            Blob codeFile = conn.createBlob();
            codeFile.setBytes(1, submission.getCodeF());

            callableStatement = conn.prepareCall(sql);

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
            System.out.println("DB add Submission: " + ex.toString());
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
            callableStatement = conn.prepareCall(sql);
            callableStatement.registerOutParameter(1, Types.VARCHAR);

            callableStatement.setString(2, submissionID);
            callableStatement.setString(3, verdict);
            callableStatement.setInt(4, timetaken);

            callableStatement.execute();
            String status = callableStatement.getString(1);
            callableStatement.close();
            return status;

        } catch (SQLException ex) {
            System.out.println("DB updateVerdict Err: " + ex.getMessage());
            return ex.toString();
        }
    }

    public synchronized NewSubmission getSubmission(String submissionID) {

        String query = "SELECT PROBLEM_ID, CODE_LANGUAGE, CODE_FILE FROM SUBMISSION WHERE ID = '" + submissionID + "'";

        try {
            stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery(query);

            if (rs.next() == false) {
                return null;
            }

            NewSubmission submission = new NewSubmission();

            submission.setProblemID(rs.getString("PROBLEM_ID"));
            submission.setLanguage(rs.getString("CODE_LANGUAGE"));
            submission.setCodeF(rs.getBytes("CODE_FILE"));

            return submission;

        } catch (SQLException ex) {
            System.out.println("problem query Err " + ex.getMessage());
            return null;
        }

    }

    public synchronized List<String[]> getProblemTable(String userID) {

        List<String[]> table = new ArrayList<>();
        String[] rowData = new String[5];

        String query = "SELECT PROBLEM_SET.ID AS ID, PROBLEM_NAME, USER_NAME FROM PROBLEM_SET INNER JOIN ADMIN_INFO ON SETTER_ID = ADMIN_INFO.ID WHERE SETTER_ID = " + ((userID == null) ? "SETTER_ID" : "'" + userID + "'");
        System.out.println(query);
        ResultSet rs;
        try {
            stmnt = conn.createStatement();
            rs = stmnt.executeQuery(query);

            while (rs.next()) {
                rowData[0] = "<HTML><U><FONT COLOR='BLUE'>" + rs.getString("ID") + "</FONT></U></HTML>";
                rowData[1] = "<HTML><U><FONT COLOR='BLUE'>" + rs.getString("PROBLEM_NAME") + "</FONT></U></HTML>";
                rowData[2] = rs.getString("USER_NAME");
                rowData[3] = "<HTML><U><FONT COLOR='RED'>DELETE</FONT></U></HTML>";
                rowData[4] = rs.getString("ID");
                table.add(rowData.clone());
                System.out.println(Arrays.toString(rowData));
            }
            return table;
        } catch (SQLException ex) {
            System.out.println("DB getProblemTable err" + ex.getMessage());
            return null;
        }

    }

    public synchronized List<String[]> getStatusTable(String userID, String clientType) {

        List<String[]> tableData = new ArrayList<>();
        String[] rowData = new String[9];

        String query = "SELECT SUBMISSION.ID AS SID, CODE_LANGUAGE AS CL, VERDICT AS VR, RUNNING_TIME AS RT, "
                + "PROBLEM_SET.PROBLEM_NAME AS PNAME, PROBLEM_SET.ID AS PID, "
                + "USER_INFO.USER_NAME AS UNAME "
                + "FROM SUBMISSION, PROBLEM_SET, USER_INFO "
                + "WHERE PROBLEM_SET.ID = PROBLEM_ID AND USER_INFO.ID = USER_ID AND USER_ID = " + ((userID == null) ? "USER_ID" : "'" + userID + "'");
        ResultSet rs;
        try {
            stmnt = conn.createStatement();
            rs = stmnt.executeQuery(query);

            while (rs.next()) {

                rowData[0] = rs.getString("SID");
                rowData[1] = rowData[0].substring(0, 2) + "/"
                        + rowData[0].substring(2, 4) + "/"
                        + rowData[0].substring(4, 8) + ":"
                        + rowData[0].substring(8, 10) + ":"
                        + rowData[0].substring(10, 12) + ":"
                        + rowData[0].substring(12, 14);
                rowData[2] = rs.getString("UNAME");
                rowData[3] = "<HTML><U><FONT COLOR='BLUE'>" + rs.getString("PNAME") + "</FONT></U></HTML>";
                rowData[4] = rs.getString("CL");
                rowData[5] = rs.getString("VR");
                rowData[6] = Integer.toString(rs.getInt("RT"));
                rowData[7] = rs.getString("PID");
                rowData[8] = rs.getString("SID");

                if (userID != null || clientType.equals("Admin")) {
                    rowData[0] = "<HTML><U><FONT COLOR='BLUE'>" + rowData[0] + "</FONT></U></HTML>";
                }
                tableData.add(rowData.clone());
                System.out.println(Arrays.toString(rowData));
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
            stmnt = conn.createStatement();
            rs = stmnt.executeQuery(query);
            int position = 1;
            while (rs.next()) {
                rowData[0] = Integer.toString(position++);
                rowData[1] = rs.getString("UNAME");
                rowData[2] = rs.getString("Problems");
                tableData.add(rowData.clone());
                System.out.println(rowData);
            }
            return tableData;

        } catch (SQLException ex) {
            System.out.println("DB getProblemTable err" + ex.toString());
            return null;
        }
    }

    public synchronized List<String[]> getContestTable() {
        List<String[]> tableData = new ArrayList<>();
        String[] rowData = new String[7];

        String query = "SELECT CONTEST_INFO.ID AS CID, CONTEST_NAME, START_TIME, DURATION_MIN, FINISHED, ADMIN_INFO.USER_NAME AS SETTER FROM CONTEST_INFO, ADMIN_INFO WHERE SETTER_ID = ADMIN_INFO.ID ORDER BY START_TIME DESC";

        ResultSet rs;
        try {
            stmnt = conn.createStatement();
            rs = stmnt.executeQuery(query);
            while (rs.next()) {
                rowData[0] = rs.getString("CID");
                rowData[1] = rs.getString("CONTEST_NAME");
                rowData[2] = rs.getString("SETTER");
                rowData[3] = rs.getTimestamp("START_TIME").toString();
                rowData[4] = Integer.toString(rs.getInt("DURATION_MIN"));
                rowData[5] = (rs.getString("FINISHED").equals("YES"))? "Previous":"Upcomming";
                rowData[6] = rowData[0];
                rowData[0] = "<HTML><U><FONT COLOR='BLUE'>" + rowData[0] + "</FONT></U></HTML>";
                tableData.add(rowData.clone());
                System.out.println(rowData);
            }
            return tableData;

        } catch (SQLException ex) {
            System.out.println("DB getProblemTable err" + ex.toString());
            return null;
        }
    }

}
