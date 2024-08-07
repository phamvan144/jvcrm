package CodeDeAN.myPackage;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface DeAn {
    public crmObject getObject(int id) throws FileNotFoundException;
    public void readAll() throws FileNotFoundException;
    public void updateObject(crmObject customer)throws IOException;
    public void deleteObject(crmObject customer) throws IOException;
}
