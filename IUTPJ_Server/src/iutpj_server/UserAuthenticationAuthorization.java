/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iutpj_server;

/**
 *
 * @author ASADUZZAMAN HEROK
 */
public class UserAuthenticationAuthorization {

    private final String userName, password, clientType;
    private final Database database;

    public UserAuthenticationAuthorization(String clientType, String userName, String password,  Database db) {
        this.userName = userName;
        this.password = password;
        this.clientType = clientType;
        this.database = db;
    }

    public String getUserID(){
        if(clientType.equals("Admin"))
        return database.getAdminID(userName);
        else return database.getUserID(userName);
    }

    public boolean isAuthorized() {
        switch (clientType) {
            case "Admin": {
                return database.getAdminPassword(userName).equals(password);
            }
            case "User": {
                return database.getUserPassword(userName).equals(password);
            }
            default:
                return false;
        }
    }

    public boolean SignUp() {

        switch (clientType) {
            case "Admin": {
                return database.updateAdmin(userName, password).equals("SUCCESS");
            }
            case "User": {
                return database.updateUser(userName, password).equals("SUCCESS");
            }
            default:
                return false;
        }
    }

    public boolean doesExist() {
        switch (clientType) {
            case "Admin": {
                return !database.getAdminID(userName).equals("No#Data");
            }
            case "User": {
                return !database.getUserID(userName).equals("No#Data");
            }
            default:
                return false;
        }
    }
}
