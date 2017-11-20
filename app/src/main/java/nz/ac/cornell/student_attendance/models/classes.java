package nz.ac.cornell.student_attendance.models;

/**
 * Created by Temur Mirzosharipov on 27-Oct-17.
 */

public class classes {
        private String class_id;
        private String date;
        private String subject;
        private String tutor;
        private String present_students;

    public classes(String class_id, String date, String subject, String tutor, String present_students) {
        this.class_id = class_id;
        this.date = date;
        this.subject = subject;
        this.tutor = tutor;
        this.present_students = present_students;
    }

    public boolean createNewClass() {
        return true;
    }

    public String getClass_id() {
        return class_id;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getTutor() {
        return tutor;
    }

    public String getPresent_students() {
        return present_students;
    }
}
