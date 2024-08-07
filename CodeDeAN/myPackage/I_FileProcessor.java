package CodeDeAN.myPackage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface I_FileProcessor {
    void setFilePath(String filePath);
    void writeNewLead(List<String> data) throws IOException;
    int extractID(String ID);
    String formatLeadID(int id);
    String generateLeadID() throws FileNotFoundException;
    List<List<String>> readFile() throws IOException;
    void showRecords() throws IOException;
    void updateFile(List<List<String>> listData) throws IOException;
}
