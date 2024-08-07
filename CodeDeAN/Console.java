package CodeDeAN;

import CodeDeAN.myPackage.Lead;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);
    private Console() {}


    public static String charIn(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

    public static int intIn(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    public static int validateInt(String prompt) {
        System.out.print(prompt);
        int input;
        while (!scanner.hasNextInt()) {
            System.out.print(prompt);
            scanner.next();
        }
        input = scanner.nextInt();
        return input;
    }
    public static int validateInt(String prompt, int min, int max) {
        int input;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextInt()) {
                System.out.print(prompt);
                scanner.next();
            }
            input = scanner.nextInt();
        } while (!((input >= min) && (input <= max)));
        return input;
    }
    public static int validateLeadID(List<Lead> leadList) {
        int id = validateInt("Type in the lead ID (number only): ");
        while (true) {
            for (Lead lead : leadList) {
                if (lead.getId() == id) {
                    return id;
                }
            }
            id = validateInt("The lead id is not existed, type another: ");
        }
    }

    public static String validateName(String prompt) {
        String result = scanner.nextLine();
        while (!result.matches("^[a-zA-Z\\s]+")) {
            System.out.print(prompt);
            result = scanner.nextLine();
        }
        return result;
    }


    public static String validateDateWithLimit(String prompt) {
        String result = null;

        LocalDate valDate;
        // Parses the second date
        LocalDate limitDate = LocalDate.parse("2000-01-01");
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.print(prompt);
                result = scanner.next();
                valDate = LocalDate.parse(result);
                if (valDate.compareTo(limitDate)>=0 && valDate.compareTo(LocalDate.now())<=0){
                    isValid = true;
                }else{
                    System.out.println("Please input a valid date! (After 2000-01-01)");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Wrong date format!(yyyy-mm-dd)");
                isValid = false;
            }
        }
        return result;

    }

    public static String validateDate(String prompt) {
        String result = null;
        LocalDate valDate;
        boolean isValid = false;
        while (!isValid) {
            try {
                System.out.print(prompt);
                result = scanner.next();
                valDate = LocalDate.parse(result);
                if (valDate.isBefore(LocalDate.now())){
                    isValid = true;
                }else{
                    System.out.println("Cannot enter date after present time! Please re-enter date: ");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Wrong date format!(yyyy-mm-dd)");
                isValid = false;
            }
        }
        return result;
    }

    public static String validateGender(String prompt) {
        System.out.print(prompt);
        String result = scanner.next();
        while (!(result.equals("true") || result.equals("false"))) {
            System.out.print(prompt);
            result = scanner.next();
        }
        return result;
    }

    public static String validateEmail(String prompt) {
        System.out.print(prompt);
        String result = scanner.next();
        while (!result.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            System.out.print("Invalid format, type again: ");
            result = scanner.next();
        }
        return result;
    }

    public static String validatePhone(String prompt) {
        System.out.print(prompt);
        String result = scanner.next();
        while (!result.matches("^\\d{10}$")) {
            System.out.print("Invalid format, try again: ");
            result = scanner.next();
        }
        return result;
    }

    public static String validateLead(String prompt) {
        System.out.print(prompt);
        String result = scanner.next();
        while (!result.matches("^\\d{10}$")) {
            System.out.print("Invalid format, try again: ");
            result = scanner.next();
        }
        return result;
    }



}
