package UserManagement;

class Person {
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

    public void displayBasicInfo() {
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("DOB: " + dob);
        System.out.println("Gender: " + gender);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Email: " + email);
    }
}