package CodeDeAN;

import CodeDeAN.myPackage.I_FileProcessor;
import CodeDeAN.myPackage.Interaction;
import CodeDeAN.myPackage.Lead;
import CodeDeAN.myPackage.crmObject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Manage extends Interaction {
    private static I_FileProcessor fileProcessor;

    private static List<Lead> leads;


    public static I_FileProcessor file() {
        return fileProcessor;
    }
    public static boolean initFile() {
        try {
            String filePath = "CodeDeAN/leads.csv";
            File myObj = new File(filePath);
            return myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

    private Manage() {
    }

    public static void setFilePath(String path) {
        fileProcessor.setFilePath(path);
    }
    public static void setFP(FileProcessor fp) {
        fileProcessor = fp;
    }

    public static List<Lead> getLeads() {
        return leads;
    }

    public static void readLeadFile() throws IOException {
        List<List<String>> stringData = fileProcessor.readFile();
        leads = dataToLeads(stringData);
    }


    public static void addNewLead() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String stringBDate;
        Lead lead = new Lead();
        lead.setName(Console.validateName("Name (alphabetical characters only): "));
        stringBDate = Console.validateDate("Birthday (YYYY-MM-DD): ");
        lead.setStringBDate(stringBDate);
        lead.setBirthDate(lead.stringToDate(stringBDate));
        lead.setGender(Boolean.parseBoolean(Console.validateGender("Gender (true/false): ")));
        lead.setPhoneNumber(Console.validatePhone("Phone number (10 digits format): "));
        lead.setEmail(Console.validateEmail("Email (example@example.com): "));
        System.out.print("Address: ");
        lead.setAddress(scanner.nextLine());
        fileProcessor.writeNewLead(lead.leadToString());

    }

    public static int chooseLeadByID() {
        int index = Console.validateLeadID(leads);
        for (Lead single : leads) {
            if (single.getId() == index) {
                System.out.println("You have chosen: " + formatLeadID(single.getId()));
                index = leads.indexOf(single);
            }
        }
        return index;

    }
    public static Lead getLead(int index) {
        return leads.get(index);
    }

    public static boolean updateLead( int index) throws IOException {
        Boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("|------------------------------------------------|");
        System.out.println("|1.Name                                          |");
        System.out.println("|------------------------------------------------|");
        System.out.println("|2.Birthday                                      |");
        System.out.println("|------------------------------------------------|");
        System.out.println("|3.Gender                                        |");
        System.out.println("|------------------------------------------------|");
        System.out.println("|4.Phone                                         |");
        System.out.println("|------------------------------------------------|");
        System.out.println("|5.Email                                         |");
        System.out.println("|------------------------------------------------|");
        System.out.println("|6.Address                                       |");
        System.out.println("|------------------------------------------------|");
        System.out.println("|7.Back to leads management menu                 |");
        System.out.println("|------------------------------------------------|");
        int field = Console.validateInt("Type in number represent your selection: ",1,7);
        String temp;
        switch (field) {
            case 1 -> {
                String name = Console.validateName("Update name (alphabetical characters only): ");
                leads.get(index).setName(name);
            }
            case 2 -> {
                temp = Console.validateDate("Update birthday: (number format YYYY-MM-dd): ");
                leads.get(index).setStringBDate(temp);
                leads.get(index).setBirthDate(LocalDate.parse(temp));
            }
            case 3 -> {
                temp = Console.validateGender("Update gender (true/false): ");
                leads.get(index).setGender(Boolean.parseBoolean(temp));
            }
            case 4 -> {

                temp = Console.validatePhone("Update phone (10 digits): ");
                leads.get(index).setPhoneNumber(temp);
            }
            case 5 -> {
                temp = Console.validateEmail("Update email (example@example.com): ");
                leads.get(index).setEmail(temp);
            }
            case 6 -> {
                System.out.print("Update address: ");
                temp = scanner.nextLine();
                leads.get(index).setAddress(temp);
            }
            case 7 -> {
                loop = false;
            }

        }
        fileProcessor.updateFile(leadsListToString(leads));
        return loop;
    }

    public static void removeLead(int index) throws IOException {
        leads.remove(index);
        fileProcessor.updateFile(leadsListToString(leads));
    }


    public static void showLeadRecords() throws IOException {
        fileProcessor.showRecords();
    }

    public static List<Integer> showRelatedInteractionID(List<crmObject> crmObjectList, int leadID) {

        List<Integer> interID = new ArrayList<>();
        for (crmObject single : crmObjectList) {
            Interaction inter = (Interaction) single;
            if (inter.getLead().getId() == leadID) {
                interID.add(inter.getId());
            }
        }
        return interID;
    }


    public static List<Lead> dataToLeads(List<List<String>> data) {
        List<Lead> tempArr = new ArrayList<>();
        int counter = 0;
        for (List<String> row : data) {
            tempArr.add(new Lead());
            tempArr.get(counter).setId(extractID(row.get(0)));
            tempArr.get(counter).setName(row.get(1));
            tempArr.get(counter).setStringBDate(row.get(2));
            tempArr.get(counter).setBirthDate(tempArr.get(counter).stringToDate(row.get(2)));
            tempArr.get(counter).setGender(Boolean.parseBoolean(row.get(3)));
            tempArr.get(counter).setPhoneNumber(row.get(4));
            tempArr.get(counter).setEmail(row.get(5));
            tempArr.get(counter).setAddress(row.get(6));

            counter++;
        }
        return tempArr;
    }


    public static List<List<String>> leadsListToString(List<Lead> leads) {
        List<List<String>> tempArr = new ArrayList<>();
        for (Lead single : leads) {
            List<String> temp = new ArrayList<>();
            temp.add(formatLeadID(single.getId()));
            temp.add(single.getName());
            temp.add(single.getStringBDate());
            temp.add(String.valueOf(single.isGender()));
            temp.add(single.getPhoneNumber());
            temp.add(single.getEmail());
            temp.add(single.getAddress());
            tempArr.add(temp);
        }

        return tempArr;
    }



    public static int extractID(String id) {
        int tempID;
        id = id.substring(id.lastIndexOf("_") + 1);
        tempID = Integer.parseInt(id);
        return tempID;
    }

    public static String formatLeadID(int id) {
        return "lead_" + String.format("%03d", id);
    }

    public String potential(String appreciation, int diff)
    {
        String potential = new String();
        appreciation= String.valueOf(getAppreciation());
        if (appreciation.toLowerCase().equals("good")) {
            if (diff>30)
                potential="Positive";
            else if (diff>90)
                potential="Neutral";
            else
                potential="Negative";
        }
        else if (appreciation.toLowerCase().equals("normal"))
            potential="Neutral";
        else
            potential="Negative";
        return potential;
    }



}
