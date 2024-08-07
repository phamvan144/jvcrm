package CodeDeAN;

import CodeDeAN.myPackage.*;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;



public class InteractionManagement implements DeAn {
    private static InteractionManagement MANAGE = new InteractionManagement();
    private static final String filePath = "CodeDeAN/interaction.csv";

    public static boolean initFile() {
        try {
            File myObj = new File(filePath);
            return myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }

    public static void write(crmObject inter, boolean isappend, boolean isDel) {
        List<String> interaction = ((Interaction) inter).toArray();
        try {
            FileWriter myWriter = new FileWriter(filePath, isappend);
            if (isDel) {
                myWriter.append(formatID(inter.getId())).append(",").append(String.join(",", interaction)).append("\n");
            } else {
                myWriter.append(generateID()).append(",").append(String.join(",", interaction)).append("\n");
            }

            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @Override
    public void readAll() throws FileNotFoundException {
        String temp = "temp";
        String row;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                System.out.format("%10s%20s%15s%15s%15s\n", data[0], data[1],data[2] ,data[3],data[4]);
                temp = data[0];
            }
            csvReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public List<crmObject> getAll() throws FileNotFoundException {
        List<crmObject> interactions = new ArrayList<>();
        String temp = "temp";
        String row;
        try {
            BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");
                Lead lead = new Lead();
                lead.setId(extractID(data[2]));
                interactions.add(new Interaction(extractID(data[0]), LocalDate.parse(data[1]), lead, data[3], data[4]));
            }
            csvReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return insertionSort(interactions);
    }

    @Override
    public crmObject getObject(int id) throws FileNotFoundException {
        List<crmObject> interList = getAll();
        int index = binarySearch(interList, id);
        if (index != -1) {
            return (Interaction) interList.get(index);
        }

        return null;
    }

    public crmObject getObjectbyIndex(int index) throws FileNotFoundException {
        List<crmObject> interList = getAll();
        return (Interaction) interList.get(index);
    }



    private static int extractID(String id) {
        int tempID;
        id = id.substring(id.lastIndexOf("_") + 1);
        tempID = Integer.parseInt(id);
        return tempID;
    }

    private static String generateID() throws FileNotFoundException {
        File file = new File(filePath);
        String id = "inter_001", temp = "inter_001";
        String row;
        if (file.length() > 0) {
            try {
                BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
                while ((row = csvReader.readLine()) != null) {
                    String[] data = row.split(",");
                    if (!data[0].equals("")) {
                        temp = data[0];
                    }
                }
                csvReader.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            id = formatID(extractID(temp) + 1);
        }
        return id;
    }

    private static String formatID(int id) {
        return "inter_" + String.format("%03d", id);
    }

    @Override
    public void updateObject(crmObject newInter) throws FileNotFoundException {
        List<crmObject> interList = getAll();
        int index = binarySearch(interList, newInter.getId());
        if (index != -1) {
            interList.set(index, newInter);
        } else {
            System.out.println("Interaction does not exist.");
        }
        write(((Interaction) interList.get(0)), false, true);
        for (int i = 1; i < interList.size(); i++) {
            write(((Interaction) interList.get(i)), true, true);
        }
        System.out.println("Update successfully.");
    }


    @Override
    public void deleteObject(crmObject interaction) throws IOException {
        List<crmObject> interList = getAll();
        int x = 1;
        if (interList.get(0).getId() != interaction.getId()) {
            write(((Interaction) interList.get(0)), false, true);
        } else {
            write(((Interaction) interList.get(1)), false, true);
            x++;
        }
        for (int i = x; i < interList.size(); i++) {
            if (interList.get(i).getId() != interaction.getId()) {
                write(((Interaction) interList.get(i)), true, true);
            }
        }
    }

    public List<crmObject> insertionSort(List<crmObject> array) {
        for (int i = 1; i < array.size(); i++) {
            crmObject current = array.get(i);
            int j = i - 1;
            while (j >= 0 && current.getId() < array.get(j).getId()) {
                array.set(j + 1, array.get(j));
                j--;
            }
            array.set(j + 1, current);
        }
        return array;
    }

    public static int binarySearch(List<crmObject> arr, int elementToSearch) {
        int firstIndex = 0;
        int lastIndex = arr.size() - 1;

        while (firstIndex <= lastIndex) {
            int middleIndex = (firstIndex + lastIndex) / 2;
            if (arr.get(middleIndex).getId() == elementToSearch) {
                return middleIndex;
            }
            else if (arr.get(middleIndex).getId() < elementToSearch)
                firstIndex = middleIndex + 1;

            else if (arr.get(middleIndex).getId() > elementToSearch)
                lastIndex = middleIndex - 1;

        }
        return -1;
    }

    public  int binarySearchObject( int elementToSearch) throws FileNotFoundException {
        List<crmObject> arr = getAll();
        int firstIndex = 0;
        int lastIndex = arr.size() - 1;

        while (firstIndex <= lastIndex) {
            int middleIndex = (firstIndex + lastIndex) / 2;
            if (arr.get(middleIndex).getId() == elementToSearch) {
                return middleIndex;
            }

            else if (arr.get(middleIndex).getId() < elementToSearch)
                firstIndex = middleIndex + 1;

            else if (arr.get(middleIndex).getId() > elementToSearch)
                lastIndex = middleIndex - 1;

        }
        return -1;
    }
}
