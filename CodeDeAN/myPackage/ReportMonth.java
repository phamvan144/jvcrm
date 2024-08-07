package CodeDeAN.myPackage;

import java.time.Month;

public class ReportMonth {
    private Month month;
    private int year;
    private int number;
    public ReportMonth() {}
    public ReportMonth(Month month, int year, int number) {
        this.month = month;
        this.year = year;
        this.number = number;
    }

    public Month getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getNumber() {
        return number;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
