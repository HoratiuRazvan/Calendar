package Agenda;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ReadService {
    private ReadService(){}
    public static ReadService readService  = new ReadService();
    public static void readEvent(String fileNameDefined) {
        File file = new File(fileNameDefined);

        try{
            Scanner inputStream = new Scanner(file);
            while(inputStream.hasNext()){
                String data = inputStream.next();
                System.out.println(data + "***");

            }
            inputStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public void readMonth(int requiredMonth, int requiredYear){
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "Date3241");
            Statement myStatement = myConn.createStatement();
            ResultSet myResult = myStatement.executeQuery("select * from agenda.agenda");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            while(myResult.next()) {
                Date d = new Date();
                d = dateFormat.parse((myResult.getString("startDate")));

                if(d.getMonth() == requiredMonth && d.getYear() == requiredYear){
                    Agenda.GraphicInterface.addDay(d);
                }
            }

        }
        catch(Exception exc){
            System.out.println(exc);
        }
        return;
    }
    public static ReadService getServiceInstance(){
        return readService;
    }
}
