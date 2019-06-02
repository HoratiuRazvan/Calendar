package Agenda;

import java.util.Date;

public class main {
    public static void main(String[] args){
            ReadService readService=ReadService.getServiceInstance();
            //ReadService.readEvent("E:\\PAO Proiect\\src\\Agenda\\toread.csv");
            readService.readMonth(1,2019);
            GraphicInterface g = new GraphicInterface();
            g.generateCalendarUI(new Date());
        }

}