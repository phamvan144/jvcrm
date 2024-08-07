package CodeDeAN;

import CodeDeAN.myPackage.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Report {
    private List<Lead> data;
    public Report() {
    }
    public Report(List<Lead> data) {
        this.data = data;
    }

    public void showRecordAge() {
        List<Integer> ageList = new ArrayList<>();
        int counter1 = 0, counter2 = 0, counter3 = 0,counter4 = 0;
        for (Lead lead : data) {
            lead.ageCalculator();
            ageList.add(lead.getAge());
        }

        for (int age : ageList) {
            if (age <= 10) {
                counter1++;
            } else if (age <= 20) {
                counter2++;
            } else if (age <= 60) {
                counter3++;
            } else {
                counter4++;
            }
        }
        System.out.println();
        System.out.println("|-----------------------------------------------|");
        System.out.println("|     Number of customers classified by age     |");
        System.out.println("|-----------------------------------------------|");
        System.out.println("|<= 10 years old: " + counter1+"                             |");
        System.out.println("|-----------------------------------------------|");
        System.out.println("|11 - 20 years old: " + counter2+"                          |");
        System.out.println("|-----------------------------------------------|");
        System.out.println("|21 - 60 years old: " + counter3+"                         |");
        System.out.println("|-----------------------------------------------|");
        System.out.println("|>60 years old: " + counter4+"                              |");
        System.out.println("|-----------------------------------------------|");
        System.out.println();
    }

    public void showAppreciationReport(List<crmObject> list, LocalDate startdate, LocalDate enddate){
        int good =0 , bad =0 , normal =0;
        for (crmObject inter: list) {
            if (((Interaction) inter).getInterDate().compareTo(startdate) >=0 && ((Interaction) inter).getInterDate().compareTo(enddate) <=0){
                switch (((Interaction) inter).getAppreciation()) {
                    case good -> good++;
                    case bad -> bad++;
                    default -> normal++;
                }
            }
        }
        System.out.println();
        System.out.println("|---------------------------------------------|");
        System.out.println("|     Number of interactions by potential     |");
        System.out.println("|---------------------------------------------|");
        System.out.println("|Bad: " + bad+"                                      |");
        System.out.println("|---------------------------------------------|");
        System.out.println("|Normal: " + normal+"                                   |");
        System.out.println("|---------------------------------------------|");
        System.out.println("|Good: " + good+"                                     |");
        System.out.println("|---------------------------------------------|");
        System.out.println();
    }

    public void showInterMonth(List<crmObject> list,LocalDate startdate, LocalDate enddate){
        List<ReportMonth> monthlist = new ArrayList<>();
        startdate = startdate.withDayOfMonth(1);
        enddate = enddate.withDayOfMonth(enddate.lengthOfMonth());
        for (crmObject inter: list) {
            Interaction interaction = (Interaction) inter;
            if (interaction.getInterDate().compareTo(startdate) >=0 && interaction.getInterDate().compareTo(enddate) <=0){
                int index = indexMonth(monthlist,interaction.getInterDate().getMonth(),interaction.getInterDate().getYear());
                if(index!=-1){
                    monthlist.get(index).setNumber(monthlist.get(index).getNumber()+1);
                }else{
                    monthlist.add(new ReportMonth(interaction.getInterDate().getMonth(),interaction.getInterDate().getYear(),1));
                }
            }
        }
        for (ReportMonth data: monthlist) {
            System.out.println();
            System.out.println("------------------------------");
            System.out.print(data.getMonth() +" ");
            System.out.print(data.getYear()+": ");
            System.out.println(data.getNumber());
            System.out.println("-------------------------------");
            System.out.println();
        }

    }
    public int indexMonth(List<ReportMonth> list, Month month, int year){
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).getMonth()== month && list.get(i).getYear() == year){
                return i;
            }
        }
        return -1;
    }
}
