package CodeDeAN.myPackage;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Interaction extends crmObject{
    public LocalDate interDate;
    private Lead lead;
    private String interMedium;
    private Appreciation appreciation;

    //Constructor
    public Interaction() {
    }

    public Interaction(int id, LocalDate interDate, Lead lead, String interMedium, String appreciation) {
        this.id = id;
        this.interDate = interDate;
        this.lead = lead;
        this.interMedium = interMedium;
        switch (appreciation.toLowerCase()){
            case ("good"):
                this.appreciation= Appreciation.good;
                break;
            case ("bad"):
                this.appreciation= Appreciation.bad;
                break;
            case ("normal"):
            default:
                this.appreciation= Appreciation.normal;
                break;
        }
    }

    public LocalDate getInterDate() {
        return interDate;
    }

    public Lead getLead() {
        return lead;
    }

    public String getInterMedium() {
        return interMedium;
    }

    public Appreciation getAppreciation() {
        return appreciation;
    }

    public void setInterDate(LocalDate interDate) {
        this.interDate = interDate;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public void setInterMedium(String interMedium) {
        this.interMedium = interMedium;
    }

    public void setAppreciation(int choice) {
        switch (choice) {
            case (3) -> this.appreciation = Appreciation.good;
            case (1) -> this.appreciation = Appreciation.bad;
            default -> this.appreciation= Appreciation.normal;
        }
    }

    @Override
    boolean equal(Object x ,int choice) {

        return false;
    }

    public LocalDate stringToDate(String dateString) {
        return LocalDate.parse(dateString);
    }

    public List<String> toArray() {
        String leadid = new String("lead_" + String.format("%03d",lead.getId()));
        List<String> data = new ArrayList<>();
        data.add(interDate.toString());
        data.add(leadid);
        data.add(interMedium);
        data.add(appreciation.toString());
        return data;
    }

    public int different() {
        LocalDate startDate = getInterDate();

        LocalDate endDate = LocalDate.now();

        Period different = Period.between(startDate, endDate);

        int diff=different.getYears()*365+different.getMonths()*30+different.getDays();
        return diff;
    }

}
