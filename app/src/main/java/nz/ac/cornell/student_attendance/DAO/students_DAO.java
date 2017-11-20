package nz.ac.cornell.student_attendance.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import nz.ac.cornell.student_attendance.NewSQLDataProvider;

/**
 * Created by Temur Mirzosharipov on 02-Nov-17.
 */

public class students_DAO {

    private static String table = "tbStudents";

    public static Cursor SelectAllStudents(Context context) {
        Cursor cursor;
        SQLiteDatabase db= new NewSQLDataProvider(context).getWritableDatabase();
        db.beginTransaction();
        cursor = db.rawQuery("SELECT * FROM " + table, null);
        db.endTransaction();
        db.setTransactionSuccessful();
        return cursor;
    }
}
