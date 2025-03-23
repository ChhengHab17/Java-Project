package UserManagement;

public class Person {
    protected String firstName;
    protected String lastName;
    protected String dob;
    protected String gender;
    protected String phoneNumber;
    protected String email;

    public Person(String firstName, String lastName, String dob, String gender, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Methods that can be overridden
    public void displayInfo() {
        System.out.println("\n====== Personal Information ======");
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Gender: " + gender);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Email: " + email);
    }

    public boolean validateEmail() {
        return email != null && email.contains("@");
    }

    public boolean validatePhone() {
        return phoneNumber != null && phoneNumber.matches("^\\+?[0-9]{9,12}$");
    }

    public void updateProfile(String phoneNumber, String email) {
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}