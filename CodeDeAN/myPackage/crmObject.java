package CodeDeAN.myPackage;

public abstract class crmObject {
    protected int id;
    public crmObject() {}
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    abstract boolean equal(Object x ,int choice);

}
