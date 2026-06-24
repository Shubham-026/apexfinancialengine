package models;

/**
 * Represents the personal and account details of an individual user.
 * This class acts as a data carrier (POJO/Model) to encapsulate user profile 
 * and banking information securely.
 */
public class UserDetails {
    // Encapsulated profile properties
    private String userName;
    private long phoneNumber;
    private char gender;
    private String dateOfBirth;
    private String accountNumber;

    /**
     * Initializes a new instance of UserDetails with the specified personal information.
     * * @param accountNumber The user's unique financial account number.
     * @param userName The full name of the user.
     * @param dateOfBirth The user's date of birth.
     * @param gender The user's gender (e.g., 'M', 'F').
     * @param phoneNumber The user's contact phone number.
     */
    public UserDetails(String accountNumber, String userName,String dateOfBirth, char gender, long phoneNumber){
        this.accountNumber = accountNumber;
        this.userName = userName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the full name of the user.
     * @return The user's name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the contact phone number of the user.
     * @return The phone number.
     */
    public long getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Gets the gender designation of the user.
     * @return The gender character.
     */
    public char getGender() {
        return gender;
    }

    /**
     * Gets the date of birth of the user.
     * @return The date of birth string.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets the unique account number associated with the user profile.
     * @return The account number.
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets or updates the user's full name.
     * @param userName The new name to assign.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Sets or updates the user's contact phone number.
     * @param phoneNumber The new phone number to assign.
     */
    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Sets or updates the user's gender designation.
     * @param gender The new gender character to assign.
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Sets or updates the user's date of birth.
     * @param dateOfBirth The new date of birth string to assign.
     */
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}