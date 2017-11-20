package nz.ac.cornell.student_attendance.models;

/**
 * Created by Temur Mirzosharipov on 27-Oct-17.
 */

public class subjects {
    Integer id;
    String title;
    String description;
    String tutor;

    public subjects(Integer id, String title, String description, String tutor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tutor = tutor;
    }
}
