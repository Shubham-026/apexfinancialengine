package models;

public class UserDetails {
    private String userName;
    private long phoneNumber;
    private char gender;
    private int dateOfBirth;
    private String accountNumber;

    public UserDetails(String accountNumber, String userName,int dateOfBirth, char gender, long phoneNumber){
        this.accountNumber = accountNumber;
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
