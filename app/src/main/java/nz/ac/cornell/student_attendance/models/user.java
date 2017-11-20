package nz.ac.cornell.student_attendance.models;

/**
 * Created by Temur Mirzosharipov on 30-Oct-17.
 */

public abstract class user {

    private String first_name;
    private String last_name;
    private String dob;
    private String phone;
    private String address;
    private String email;

    public user(String first_name, String last_name, String dob, String phone, String address, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
