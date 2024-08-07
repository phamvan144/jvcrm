package CodeDeAN;

import CodeDeAN.myPackage.I_FileProcessor;

import java.io.*;
import java.util.*;

public class FileProcessor implements I_FileProcessor {
    private String filePath;

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void writeNewLead(List<String> data) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(filePath, true));
        String id = generateLeadID();
        String delimiter = ",";
        pw.print(id);
        for (String record : data) {
            pw.print(delimiter + record);
        }
        pw.println();
        pw.flush();
        pw.close();
    }

    @Override
    public void updateFile(List<List<String>> listData) throws IOException {
        PrintWriter pw = new PrintWriter(new FileWriter(filePath, false));
        for (List<String> row : listData) {
            ListIterator<String> iterator = row.listIterator(1);
            pw.print(row.get(0));
            while (iterator.hasNext()) {
                pw.print("," + iterator.next());
            }
            pw.println();
        }
        pw.close();
    }


    @Override
    public void showRecords() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
        String line;
        System.out.printf("%-10s %-30s %-15s %-10s %-15s %-30s %-30s %n","ID","NAME","BIRTHDATE","GENDER","PHONE","EMAIL","ADDRESS");

        while ((line = csvReader.readLine()) != null) {
            String[] data = line.split(",");
            System.out.printf("%-10s %-30s %-15s %-10s %-15s %-30s %-30s %n", data[0],data[1],data[2],data[3],data[4],data[5],data[6]);

        }
        System.out.println();
        csvReader.close();

    }

    @Override
    public List<List<String>> readFile() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
        String row;
        List<List<String>> records = new ArrayList<>();
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            records.add(Arrays.asList(data));
        }
        csvReader.close();
        return records;
    }




    @Override
    public String generateLeadID() throws FileNotFoundException {
        File file = new File(filePath);
        Scanner input = new Scanner(new File(filePath));
        String id = "lead_001";
        String line;
        if (file.length() == 0) {
            id = "lead_001";
        } else while (input.hasNext()) {
            line = input.nextLine();
            StringTokenizer inReader = new StringTokenizer(line, ",");
            id = inReader.nextToken();
            int temp = extractID(id) + 1;
            id = formatLeadID(temp);

        }
        input.close();

        return id;

    }



    @Override
    public int extractID(String id) {
        int tempID;
        id = id.substring(id.lastIndexOf("_") + 1);
        tempID = Integer.parseInt(id);
        return tempID;
    }

    @Override
    public String formatLeadID(int id) {
        return "lead_" + String.format("%03d", id);
    }
}

