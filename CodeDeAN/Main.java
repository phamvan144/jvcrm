package CodeDeAN;

import CodeDeAN.myPackage.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void leadManagement() throws IOException {
        boolean cont = true;
        String leadFile = "CodeDeAN/leads.csv";
        if (Manage.initFile()) {
            System.out.println("Khong tim thay file leads.csv, hay tao mot file moi");
        }

        while (cont) {


            FileProcessor fp = new FileProcessor();
            Manage.setFP(fp);
            Manage.setFilePath(leadFile);

            Manage.showLeadRecords();

            System.out.println("                 |--------------------------------------------|");
            System.out.println("                 |               Lead Main Menu               |");
            System.out.println("                 |--------------------------------------------|");
            System.out.println("                 | 1.Create new lead                          |");
            System.out.println("                 |--------------------------------------------|");
            System.out.println("                 | 2.Modify an existing lead                  |");
            System.out.println("                 |--------------------------------------------|");
            System.out.println("                 | 3.Show report based on age                 |");
            System.out.println("                 |--------------------------------------------|");
            System.out.println("                 | 4.Back to main menu                        |");
            System.out.println("                 |--------------------------------------------|");


            int choice2 = Console.validateInt("Selection (number): ", 1, 4);
            switch (choice2) {
                case 1 -> {
                    Manage.addNewLead();
                    System.out.println("Added new lead!");
                }
                case 2 -> {
                    Manage.readLeadFile();
                    int indexOfLead = Manage.chooseLeadByID();
                    int choice = Console.validateInt("1.UPDATE 2.DELETE 3.CANCEL: ", 1, 3);
                    if (choice == 1) {
                        boolean loop = true;
                        while (loop) {
                            loop = Manage.updateLead(indexOfLead);
                        }
                    } else if (choice == 2) {
                        InteractionManagement manageInters = new InteractionManagement();
                        InteractionManagement.initFile();

                        System.out.println("Interaction related to this lead:");
                        List<Integer> relateIntersID = Manage.showRelatedInteractionID(manageInters.getAll(), Manage.getLeads().get(indexOfLead).getId());
                        for (int id : relateIntersID) {
                            System.out.println("inter_" + String.format("%03d", id));
                        }
                        System.out.println("Process to delete chosen lead will also delete any related interactions, continue?");
                        System.out.println("------------------");
                        choice = Console.validateInt("1.Yes   2.Cancel: ", 1, 2);
                        if (choice == 1) {
                            Manage.removeLead(indexOfLead);
                            for (int interID : relateIntersID) {
                                System.out.println("Deleted inter_" + String.format("%03d", interID));
                                Interaction temp = (Interaction) manageInters.getObject(interID);
                                manageInters.deleteObject(temp);
                            }
                        } else if (choice == 2) {
                            System.out.println("-------------------");
                            System.out.println("Cancelled deletion");
                            System.out.println("--------------------");

                        }
                    }

                }

                case 3 -> {
                    System.out.println("|--------------------------------------------------------------------|");
                    System.out.println("|                        REPORT LEADS BY AGE                         |");
                    System.out.println("|--------------------------------------------------------------------|");
                    Manage.readLeadFile();
                    Report report = new Report(Manage.getLeads());
                    report.showRecordAge();
                    cont = false;
                }
                case 4 -> cont = false;
            }
        }

    }

    public static void interManagement() throws IOException {
        String leadFile = "CodeDeAN/leads.csv";
        boolean isRun = true;

        FileProcessor fp = new FileProcessor();
        Manage.initFile();
        Manage.setFP(fp);
        Manage.setFilePath(leadFile);

        InteractionManagement manage = new InteractionManagement();
        if(InteractionManagement.initFile()){
            System.out.println("New file created!");
        }
        while (isRun) {
            System.out.println("                 |--------------------------------------------|");
            System.out.println("                 |            Interaction Main Menu           |");
            System.out.println("                 |--------------------------------------------|");
            System.out.println("                 |1.Show all records                          |");
            System.out.println("                 |--------------------------------------------|");
            System.out.println("                 |2.Add new interaction                       |");
            System.out.println("                 |--------------------------------------------|");
            System.out.println("                 |3.Select interaction                        |");
            System.out.println("                 |--------------------------------------------|");
            System.out.println("                 |4.Report                                    |");
            System.out.println("                 |--------------------------------------------|");
            System.out.println("                 |5.Back to main menu                         |");
            System.out.println("                 |--------------------------------------------|");

            int interMenu = Console.validateInt("Type in number of your choice: ", 1, 5);
            switch (interMenu) {
                case 1 -> {
                    System.out.format("%10s%20s%10s%17s%15s\n", "Interaction ID", "Interaction Date","Lead ID" ,"By which mean","Appreciation");
                    manage.readAll();
                }
                case 2 -> {
                    Manage.readLeadFile();
                    Lead newLead = Manage.getLead(Manage.chooseLeadByID());
                    Interaction newInter = new Interaction();

                    newInter.setInterDate(LocalDate.parse(Console.validateDateWithLimit("Interaction date (YYYY-MM-DD): ")));
                    newInter.setLead(newLead);

                    System.out.println();
                    System.out.println("Please choose the interaction method: ");
                    System.out.println("|----------------------|");
                    System.out.println("|1.Email               |");
                    System.out.println("|----------------------|");
                    System.out.println("|2.Phone               |");
                    System.out.println("|----------------------|");
                    System.out.println("|3.Face-to-face        |");
                    System.out.println("|----------------------|");
                    System.out.println("|4.Social media        |");
                    System.out.println("|----------------------|");
                    System.out.println();

                    int choice = Console.validateInt("Choice: ", 1, 4);
                    switch (choice){
                        case 1 -> newInter.setInterMedium("email");
                        case 2 -> newInter.setInterMedium("phone");
                        case 3 -> newInter.setInterMedium("face-to-face");
                        case 4 -> newInter.setInterMedium("Social_media");
                    }

                    // Appreciation choice
                    System.out.println();
                    System.out.println("Please choose the appreciation of this interaction: ");
                    System.out.println("|----------------------|");
                    System.out.println("|1.Bad                 |");
                    System.out.println("|----------------------|");
                    System.out.println("|2.Normal              |");
                    System.out.println("|----------------------|");
                    System.out.println("|3.Good                |");
                    System.out.println("|----------------------|");
                    System.out.println();
                    choice = Console.validateInt("Choice: ", 1, 3);
                    newInter.setAppreciation(choice);

                    InteractionManagement.write(newInter, true, false);
                }

                case 3 -> {
                    int id = Console.validateInt("Type in the ID (number only): ");
                    int index, field;
                    do {
                        index = manage.binarySearchObject(id);
                        if (index == -1)
                            id = Console.validateInt("The id is not existed, type another: ");
                    } while (index == -1);

                    Interaction inter1 = (Interaction) manage.getObjectbyIndex(index);

                    int selectAction = Console.validateInt("1.UPDATE 2.DELETE 3.CANCEL: ", 1, 3);
                    switch (selectAction) {
                        case 1 -> {
                            do {
                                System.out.println();
                                System.out.println("|-----------------------------|");
                                System.out.println("|1.Date                       |");
                                System.out.println("|-----------------------------|");
                                System.out.println("|2.Lead                       |");
                                System.out.println("|-----------------------------|");
                                System.out.println("|3.Contact medium             |");
                                System.out.println("|-----------------------------|");
                                System.out.println("|4.Appreciation               |");
                                System.out.println("|-----------------------------|");
                                System.out.println("|5.Save & Exit                |");
                                System.out.println("|-----------------------------|");
                                System.out.println("NOTE: Remember to \"Save & Exit\" to update your file.");

                                field = Console.validateInt("Type in one of the number represent the field you want to modify: ", 1, 5);
                                String temp;
                                switch (field) {
                                    case 1 -> {
                                        temp = Console.validateDateWithLimit("Update interaction date to (YYYY-MM-DD): ");
                                        inter1.setInterDate(LocalDate.parse(temp));
                                    }
                                    case 2 -> {
                                        Manage.readLeadFile();
                                        Lead newLead = Manage.getLead(Manage.chooseLeadByID());
                                        inter1.getLead().setId(newLead.getId());
                                    }
                                    case 3 -> {
                                        // Interaction medium choice
                                        System.out.println("1.Email");
                                        System.out.println("2.Phone");
                                        System.out.println("3.Face-to-face");
                                        System.out.println("4.Social media");
                                        int choice = Console.validateInt("Update contact medium: ", 1, 4);
                                        switch (choice){
                                            case 1 -> inter1.setInterMedium("email");
                                            case 2 -> inter1.setInterMedium("phone");
                                            case 3 -> inter1.setInterMedium("face-to-face");
                                            case 4 -> inter1.setInterMedium("Social_media");
                                        }
                                    }
                                    case 4 -> {
                                        System.out.println("1.Bad");
                                        System.out.println("2.Normal");
                                        System.out.println("3.Good");
                                        int choice = Console.validateInt("Choice: ", 1, 3);
                                        inter1.setAppreciation(choice);
                                    }
                                    default -> {
                                    }
                                }
                            } while (field != 5);
                            manage.updateObject(inter1);
                        }
                        case 2 -> manage.deleteObject(inter1);
                        default -> {
                        }
                    }
                }
                case 4 -> {
                    Report report = new Report();
                    int reportMenu;

                    do {
                        System.out.println();
                        System.out.println("|---------------------------------------------------|");
                        System.out.println("|1.Show all number of interactions by potential     |");
                        System.out.println("|---------------------------------------------------|");
                        System.out.println("|2.Show all number of interactions by month         |");
                        System.out.println("|---------------------------------------------------|");
                        System.out.println("|3.Back to interactions menu                        |");
                        System.out.println("|---------------------------------------------------|");
                        System.out.println();

                        reportMenu = Console.validateInt("Type in number of your choice: ", 1, 3);
                        if (reportMenu ==3)break;
                        LocalDate startDate= LocalDate.parse(Console.validateDateWithLimit("From date (YYYY-MM-dd number only): "));
                        LocalDate endDate =LocalDate.parse(Console.validateDateWithLimit("To date (YYYY-MM-dd number only): "));
                        boolean isValid = false;
                        while (!isValid) {
                            if(startDate.compareTo(endDate)>0){
                                System.out.println("Start date cannot be after end date!");
                                startDate= LocalDate.parse(Console.validateDateWithLimit("From date (YYYY-MM-dd number only): "));
                            }
                            if(endDate.compareTo(startDate)<0){
                                System.out.println("End date cannot be before start date!");
                                endDate= LocalDate.parse(Console.validateDateWithLimit("To date (YYYY-MM-dd number only): "));
                                isValid =false;
                            }else{
                                isValid =true;
                            }

                        }
                        switch (reportMenu){
                            case 1 -> report.showAppreciationReport(manage.getAll(),startDate,endDate);
                            case 2 -> report.showInterMonth(manage.getAll(),startDate,endDate);
                        }
                    }while (true);
                }
                case 5 -> isRun = false;
            }

        }


    }

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println();
            System.out.println("                 |-----------------------------|");
            System.out.println("                 |          MAIN MENU          |");
            System.out.println("                 |-----------------------------|");
            System.out.println("                 | 1.Manage leads              |");
            System.out.println("                 |-----------------------------|");
            System.out.println("                 | 2.Manage interactions       |");
            System.out.println("                 |-----------------------------|");
            System.out.println("                 | 3.Exit                      |");
            System.out.println("                 |-----------------------------|");
            System.out.println();
            int choice1 = Console.validateInt("Selection (number): ", 1, 3);
            switch (choice1) {
                case 1 -> leadManagement();
                case 2 -> interManagement();
                case 3 -> System.exit(0);
            }
        }


    }
}
