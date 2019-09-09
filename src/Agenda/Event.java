package Agenda;

import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.management.Query;
import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Event {
    protected String title, type, place;
    protected Date startDate, endDate;
    protected Pair startingHour,endingHour;
    Event(){
        title = null;
    }
    Event(String title){
        this.title = new String(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}

class Job extends Event{
    Job(){
        startingHour = new Pair(0,0);
        type = "job";
    }
    Job(int hour, int minute) {
        startingHour = new Pair(hour,minute);
        type = "job";
    }
    Job(int hour){
        startingHour = new Pair(hour,0);
        type = "job";
    }

    public Pair getStartingHour() {
        return startingHour;
    }

    public Pair getEndingHour() {
        return endingHour;
    }

    public void setStartingHour(Pair startingHour) {
        this.startingHour = startingHour;
    }

    public void setEndingHour(Pair endingHour) {
        this.endingHour = endingHour;
    }
}

class Task extends Event{
    Task(){
        startDate = new Date();
        endDate = new Date();
        type = "Task";
    }
}

class Holyday extends Event{
    Holyday(){
        startDate = new Date();
        endDate = new Date();
        type = "Holyday";
    }
}

class Meeting extends Event{
    Meeting(){
        startDate = new Date();
        endDate = new Date();
        type = "Meeting";
    }
}

class GraphicInterface extends JFrame{
    private Color backgroundColor; //MAYBE COLOR TYPE!!
    private Color textColor;
    public static void addDay(Date d){

        
        return;
    }
    public static void generateCalendarUI(Date d){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame littleFrame = new JFrame("Agenda");
        littleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        littleFrame.setBounds(1400,100,500,500);
        littleFrame.setVisible(true);
        String weekColumn[] = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        int monthDays[] = {31,28,31,30,31,30,31,31,30,31,30,31};
        if(d.getYear()%4 == 0 && d.getYear()%400!=0){
            monthDays[2] = 29;
        }
        Date firstDayOfMonth = new Date(d.getYear(),d.getMonth(),1);
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("E");
        String dayOfWeek = simpleDateformat.format(firstDayOfMonth);
        Integer monthData[][] = new Integer[6][8];
        int weekOfMonth = 0;
        int i;
        int precMonthDays = monthDays[firstDayOfMonth.getMonth()];
        int currentDay = 0;
        System.out.print(dayOfWeek + " ");
        for( i = 0; i < 7; i++) {

            if (weekColumn[i].equals(dayOfWeek))
                break;
        }
        System.out.println(i);
        for(int j = 0 ; j<i; j++){
            monthData[weekOfMonth][j] = precMonthDays - i + j;
            currentDay++;
        }
        int dayNo = 1;
        System.out.println(monthDays[d.getMonth()]);
        int newMonthDay = 1;
        for(;weekOfMonth<6;weekOfMonth++){
            for(;currentDay<7;currentDay++,dayNo++){
                if( dayNo<=monthDays[d.getMonth()]) {
                    monthData[weekOfMonth][currentDay] = dayNo;
                }
                else{
                    monthData[weekOfMonth][currentDay] = newMonthDay++;
                }
            }
            currentDay = 0;
        }
        for(int j = 0; j < 6; j++){
            for(int k = 0; k < 7; k++)
                System.out.print(monthData[j][k] + " " );
            System.out.println();
        }
        String[] Months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        JTextField Month = new JTextField(Months[d.getMonth()]);
        Month.setFont(new Font("TimesRoman",Font.PLAIN,20));
        Month.isEnabled();
        JTable Calendar =  new JTable(monthData,weekColumn);
        Calendar.setRowHeight(54);
        Calendar.setBounds(30,280,400,400);

        Calendar.setDefaultEditor(Object.class,null);

        for(int j = 0; j < 6; j++)
            for(int k = 0; k < 7; k++) {
                Calendar.getModel().setValueAt(monthData[j][k], j, k);
            }
        littleFrame.getContentPane().setLayout(new FlowLayout());
        JScrollPane label = new JScrollPane(Calendar);
        JButton nextMonthButton = new JButton();
        JButton prevMonthButton = new JButton();
        nextMonthButton.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {

                        littleFrame.setVisible(false);
                        d.setMonth(d.getMonth() + 1);

                        generateCalendarUI(d);

                    }

                });
            }
        });
        prevMonthButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable(){
                    public void run() {
                        littleFrame.setVisible(false);
                        d.setMonth(d.getMonth() - 1);
                        generateCalendarUI(d);

                    }
                });

            }
        });
        Calendar.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = Calendar.rowAtPoint(evt.getPoint());
                int col = Calendar.columnAtPoint(evt.getPoint());
                if(row > 0 && col > 0 && row < 6 && col < 7){
                    generateDayUI(monthData[row][col], d);
                    littleFrame.setVisible(false);
                }

            }
        });
        littleFrame.add(prevMonthButton);
        littleFrame.add(nextMonthButton);
        littleFrame.add(Month);
        Calendar.getTableHeader().setReorderingAllowed(false);
       try {
            Image img1 = ImageIO.read(new File("library/leftArrow.png"));
            prevMonthButton.setIcon(new ImageIcon(img1));
            prevMonthButton.setPreferredSize(new Dimension(150,50));
            prevMonthButton.setVisible(true);
            Image img = ImageIO.read(new File("library/rightArrow.png"));
            nextMonthButton.setIcon(new ImageIcon(img));
            nextMonthButton.setPreferredSize(new Dimension(150,50));
            nextMonthButton.setVisible(true);
            littleFrame.getContentPane().add(nextMonthButton);

        } catch (Exception ex) {
            System.out.println(ex);
        }
        littleFrame.getContentPane().add(label);
        littleFrame.setVisible(true);
    }

    private static void generateDayUI(Integer day, Date d) {
        Date currentDate = new Date(d.getYear(),d.getMonth(),day);
        String mysqlDate = new String((d.getYear()+1900) + "-" + String.valueOf(d.getMonth()) + "-" + day.toString());
        JFrame DayFrame = new JFrame(String.valueOf((d.getYear()+1900) + " " + String.valueOf(d.getMonth()) + " " + day.toString()));
        DayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DayFrame.setBounds(300,0,600,600);
        JTextField newEvent = new JTextField();
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agenda", "root", "Date3241");
            Statement myStatement = myConn.createStatement(1,7);
            //ResultSet myResult = myStatement.executeQuery("select * from agenda.agenda where startDate < " +mysqlDate + " and endDate > " + mysqlDate);
            ResultSet myResult = myStatement.executeQuery("SELECT * FROM agenda.agenda");
            JTextField oldEvents = new JTextField(myResult.toString());
            JLabel dayLabel = new JLabel();
            dayLabel.setText("<html>");
            JLabel placeLabel = new JLabel();
            JLabel commentaryLabel = new JLabel();
            JLabel activityTypeLabel = new JLabel();
            placeLabel.setText("<html>");
            commentaryLabel.setText("<html>");
            activityTypeLabel.setText("<html>");

            while(myResult.next()) {
                Date databaseDate = myResult.getDate(2);
                if(databaseDate.getMonth() == d.getMonth() && databaseDate.getDay() == d.getDay()) {
                    dayLabel.setText(dayLabel.getText() + "<br/" + myResult.getDate(2).toString());
                    placeLabel.setText(placeLabel.getText()+"<br/>" + myResult.getString(4));
                    commentaryLabel.setText(commentaryLabel.getText() + "<br/>"+myResult.getString(5));
                    activityTypeLabel.setText(activityTypeLabel.getText()+"<br/>"+myResult.getString(6));
                }
            }
            dayLabel.setText(dayLabel.getText() + "</html>");
            placeLabel.setText(placeLabel.getText()+"</html>");
            commentaryLabel.setText(commentaryLabel.getText()+"</html>");
            activityTypeLabel.setText(activityTypeLabel.getText()+"</html>");
            DayFrame.setLayout(null);
            DayFrame.add(dayLabel);
            DayFrame.add(placeLabel);
            DayFrame.add(commentaryLabel);
            DayFrame.add(activityTypeLabel);
            Label top1 = new Label(), top2 = new Label(), top3 = new Label(),top4 = new Label();
            DayFrame.add(top1);
            DayFrame.add(top2);
            DayFrame.add(top3);
            DayFrame.add(top4);
            top1.setText("Day");top2.setText("Place"); top3.setText("Comment");top4.setText("Activity Type");
            top1.setBounds(0,0,100,100);
            top2.setBounds(100,0,100,100);
            top3.setBounds(200,0,100,100);
            top4.setBounds(300,0,100,100);
            dayLabel.setBounds(0,100,100,100);
            placeLabel.setBounds(100,100,100,100);
            commentaryLabel.setBounds(200,100,100,100);
            activityTypeLabel.setBounds(300,100,100, 100);
            JButton addEventButton = new JButton();
            addEventButton.setText("Add event");
            DayFrame.add(addEventButton);
            addEventButton.setBounds(500,500,100,50);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DayFrame.setVisible(true);


    }
}

class Planner{
    protected LocalDateTime today;
    private static final Planner planner = new Planner();
    private Planner() {
    }
    public void setDate(){
        today = LocalDateTime.now();
    }

}