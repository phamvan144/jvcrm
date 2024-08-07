package CodeDeAN.myPackage;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;


    public class Lead extends crmObject {
        private String name;
        private String phoneNumber;
        private boolean gender;
        private LocalDate birthDate;
        private String email;
        private String address;


        private int age;
        private String stringBDate;

        public Lead() {
        }

        public Lead(int id, String name, String phoneNumber, boolean gender, LocalDate birthDate, String email, String address) {
            this.id = id;
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.gender = gender;
            this.birthDate = birthDate;
            this.email = email;
            this.address = address;

        }

        public String getName() {
            return name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public boolean isGender() {
            return gender;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
        }

        public int getAge() {
            return age;
        }

        public String getStringBDate() {
            return stringBDate;
        }


        public void setName(String name) {
            this.name = name;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setGender(boolean gender) {
            this.gender = gender;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setStringBDate(String stringBDate) {
            this.stringBDate = stringBDate;
        }

        @Override
        boolean equal(Object x ,int choice) {
            return false;
        }

        public LocalDate stringToDate(String dateString) {
            LocalDate date = LocalDate.parse(dateString);
            return date;
        }

        public void ageCalculator() {
            LocalDate currentDate = LocalDate.now();
            this.age = Period.between(this.birthDate, currentDate).getYears();
        }

        public List<String> leadToString() {
            List<String> data = new ArrayList<>();
            data.add(name);
            data.add(stringBDate);
            data.add(String.valueOf(gender));
            data.add(phoneNumber);
            data.add(email);
            data.add(address);
            return data;
        }

    }
