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
public class LoginSignUpHandler {

    private final String userName, password, clienttype;
    private final Database database;

    public LoginSignUpHandler(String data, String clienttype, Database db) {

        int x = data.indexOf(']', 9);
        int y = data.lastIndexOf(']');

        this.userName = data.substring(9, x);
        this.password = data.substring(x + 2, y);
        this.clienttype = clienttype;
        this.database = db;
    }

    public String getUserID(){
        if(clienttype.equals("Admin"))
        return database.getAdminID(userName);
        else return database.getUserID(userName);
    }

    public boolean isValid() {
        switch (clienttype) {
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

        switch (clienttype) {
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
        switch (clienttype) {
            case "Admin": {
                return !database.getAdminPassword(userName).equals("No#Data");
            }
            case "User": {
                return !database.getUserPassword(password).equals("No#Data");
            }
            default:
                return false;
        }
    }

}
