package Agenda;

import java.awt.event.ActionEvent;
import java.util.Date;
public class AgendaService {
    private static final AgendaService agenda = new AgendaService();
    private AgendaService() {

    }
    public static void changeUI(Date d){

    }
    Event Adauga() {
        Event nou;
        System.out.println("Tipul de date de adaugat:");
        String s = System.console().readLine();
        switch (s) {
            case "Job":
                nou = new Job();
                break;
            case "Task":
                nou = new Task();
                break;
            case "Holyday":
                nou = new Holyday();
                break;
            case "Meeting":
                nou = new Meeting();
                break;
            default:
                nou = new Event();
        }
        System.out.println("Dati locatia de activitate:");
        nou.setPlace(System.console().readLine());
        System.out.println("Dati titlul activitatii:");
        nou.setTitle(System.console().readLine());
        return nou;
    }
}