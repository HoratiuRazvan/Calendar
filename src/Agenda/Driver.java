package Agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Driver {
    public void DataBase() {
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "Date3241");
            Statement myStatement = myConn.createStatement();
            ResultSet myResult = myStatement.executeQuery("select * from agenda.agenda");
            while(myResult.next()) {
                System.out.println(myResult.getString("startDate"));
            }
        }
        catch(Exception exc){
            System.out.println(exc);
        }
        return;
    }
}
