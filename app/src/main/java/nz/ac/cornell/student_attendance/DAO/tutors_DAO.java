package nz.ac.cornell.student_attendance.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import nz.ac.cornell.student_attendance.NewSQLDataProvider;

/**
 * Created by Temur Mirzosharipov on 02-Nov-17.
 */

public class tutors_DAO {
    private static String table = "tbTutors";
    private static String col_1 = "tutor_id";
    private static String col_2 = "password";

    public static Cursor LoginGetTutor(Context context, String tutor_id, String password) {

        Cursor cursor;
        SQLiteDatabase db= new NewSQLDataProvider(context).getWritableDatabase();
        db.beginTransaction();
        cursor = db.rawQuery("SELECT * FROM tbTutors WHERE id=1", null);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
