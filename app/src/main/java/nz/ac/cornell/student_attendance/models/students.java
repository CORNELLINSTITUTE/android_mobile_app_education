package nz.ac.cornell.student_attendance.models;

/**
 * Created by Temur Mirzosharipov on 27-Oct-17.
 */

public class students extends user {
    Integer user_id;
    String student_id;
    Integer classes;

    public students(String first_name, String last_name, String dob, String phone, String address, String email, Integer user_id, String student_id, Integer classes) {
        super(first_name, last_name, dob, phone, address, email);
        this.user_id = user_id;
        this.student_id = student_id;
        this.classes = classes;
    }

}
