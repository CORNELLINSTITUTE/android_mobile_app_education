package nz.ac.cornell.student_attendance;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nz.ac.cornell.student_attendance.models.classes;

/**
 * Created by Temur Mirzosharipov on 06-Nov-17.
 */

public class NewSQLDataProvider extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public static final String db_n = "attendance_4.db";

    public NewSQLDataProvider(Context context) {
        super(context, db_n, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS tbUser (id INTEGER PRIMARY KEY AUTOINCREMENT,first_name TEXT,last_name TEXT,dob TEXT,phone TEXT,address TEXT, email TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbSubjects (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, description TEXT, tutor TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbStudents (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, student_id TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbTutors (id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,tutor_id TEXT, password TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tbClasses (id INTEGER PRIMARY KEY AUTOINCREMENT,class_id TEXT, date TEXT, subject text, tutor text, present_students TEXT)");

        db.execSQL("INSERT INTO tbUser (first_name, last_name, dob, phone, email) VALUES ('John', 'Doe', '07/02/1989', '012378332', 'user@user.com')");
        db.execSQL("INSERT INTO tbTutors (user_id, tutor_id, password) VALUES (1, '1007H23', '202cb962ac59075b964b07152d234b70')");

        db.execSQL("INSERT INTO tbUser (first_name, last_name, dob, phone, email) VALUES ('Harry', 'Owen', '01/05/1995', '012323212', 'harry@user.com')");
        db.execSQL("INSERT INTO tbStudents (user_id, student_id) VALUES (2, '17048292C')");

        db.execSQL("INSERT INTO tbUser (first_name, last_name, dob, phone, email) VALUES ('Jim', 'Sterling', '02/03/1996', '0123123122', 'jim@user.com')");
        db.execSQL("INSERT INTO tbStudents (user_id, student_id) VALUES (3, '1603923C')");

        db.execSQL("INSERT INTO tbUser (first_name, last_name, dob, phone, email) VALUES ('Wayne', 'Rooney', '01/05/1988', '031323212', 'wayne@user.com')");
        db.execSQL("INSERT INTO tbStudents (user_id, student_id) VALUES (3, '12318112A')");

        db.execSQL("INSERT INTO tbUser (first_name, last_name, dob, phone, email) VALUES ('David', 'Beckham', '01/05/1992', '032323212', 'david@user.com')");
        db.execSQL("INSERT INTO tbStudents (user_id, student_id) VALUES (4, '17048112A')");

        db.execSQL("INSERT INTO tbSubjects (title, description, tutor) VALUES ('Mobile Development', 'Android, iOS and Windows Phone', '1007H23')");
        db.execSQL("INSERT INTO tbSubjects (title, description, tutor) VALUES ('Project management', 'Class about project management', '1007H23')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void open() { db = this.getWritableDatabase(); }

    public void close() {
        db.close();
    }


    public Cursor SelectAllUsers() {
        Cursor cursor;
        cursor = db.rawQuery("SELECT * FROM tbUser", null);
        return cursor;
    }

    // Tutors DAO

    public Cursor LoginGetTutor(String tutor_id, String password) {
        Cursor cursor;
        cursor = db.rawQuery("SELECT " +
                "tbTutors.id, tbUser.first_name, tbUser.last_name, tbUser.dob, tbUser.phone, tbUser.address, tbUser.email " +
                "FROM tbTutors LEFT JOIN tbUser on tbTutors.user_id=tbUser.id WHERE tbTutors.tutor_id='" + tutor_id + "' AND tbTutors.password='" + password + "'", null);
        return cursor;
    }


    // Students DAO

    public Cursor AllStudents() {
        Cursor cursor;
        cursor = db.rawQuery("SELECT tbStudents.student_id, tbUser.first_name, tbUser.last_name FROM tbStudents LEFT JOIN tbUser on tbStudents.user_id=tbUser.id", null);
        return cursor;
    }


    // Subjects DAO

    public Cursor ListOfTutorsSubjects(String tutor_id) {
        Cursor cursor;
        cursor = db.rawQuery("SELECT title FROM tbSubjects WHERE tutor='" + tutor_id + "'", null);
        return cursor;
    }

    public Cursor FindSubjectIdbyName(String title) {
        Cursor cursor;
        cursor = db.rawQuery("SELECT id FROM tbSubjects WHERE title='" + title + "'", null);
        return cursor;
    }


    // Classes DAO

    public Cursor getAllClasses() {
        Cursor cursor;
        cursor = db.rawQuery("SELECT date, subject, tutor , present_students FROM tbClasses", null);
        return cursor;
    }


    public boolean insertClass(classes cl) {
        String query = "INSERT INTO tbClasses (class_id, date, subject, tutor, present_students)"
                + "VALUES( " +
                "'" + cl.getClass_id() + "' , " + // class_id
                "'" + cl.getDate() + "', " +  // date
                "'" + cl.getSubject() + "', " +  // subject
                "'" + cl.getTutor() + "', " +  // tutor
                "'" + cl.getPresent_students() + "'" + //present_students
                " );";

        try {
            db.execSQL(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteClass(String date, String subject, String tutor) {
        String query = "DELETE FROM tbClasses WHERE subject='" +  subject + "' AND tutor='" + tutor + "' AND date LIKE '%" + date + "%'";

        try {
            db.execSQL(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateClass(String date, String subject, String tutor, String present_students) {
        String query = "UPDATE tbClasses SET present_students='" + present_students + "' WHERE subject='" +  subject + "' AND tutor='" + tutor + "' AND date LIKE '%" + date + "%'";

        try {
            db.execSQL(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
