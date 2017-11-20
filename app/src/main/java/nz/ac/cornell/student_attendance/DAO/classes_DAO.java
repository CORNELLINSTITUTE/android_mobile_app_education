package nz.ac.cornell.student_attendance.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import nz.ac.cornell.student_attendance.NewSQLDataProvider;
import nz.ac.cornell.student_attendance.models.classes;

/**
 * Created by Temur Mirzosharipov on 02-Nov-17.
 */

public class classes_DAO  {

    private String table = "tbClasses";
    private String col_1 = "class_id";
    private String col_2 = "date";
    private String col_3 = "subject";
    private String col_4 = "tutor";
    private String col_5 = "present_students";

    private static classes_DAO cInstance = null;

    public static classes_DAO getcInstance() {
        if (cInstance == null) {
            cInstance = new classes_DAO();
        }
        return cInstance;
    }

    public Cursor SelectAllClasses(Context context) {
        Cursor cursor;
        SQLiteDatabase db= new NewSQLDataProvider(context).getWritableDatabase();
        db.beginTransaction();
        cursor = db.rawQuery("SELECT * FROM " + table, null);
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return cursor;
    }

    public boolean InsertClass(Context context, classes cl){
        try {
            SQLiteDatabase  db = new NewSQLDataProvider(context).getWritableDatabase();
            db.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(col_1, cl.getClass_id());
            cv.put(col_2, cl.getDate());
            cv.put(col_3, cl.getSubject());
            cv.put(col_4, cl.getTutor());
            cv.put(col_5, cl.getPresent_students());

            db.insert(table, null, cv);
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    public boolean UpdateClass(Context context, classes cl) {
        try {
            SQLiteDatabase db = new NewSQLDataProvider(context).getWritableDatabase();
            db.beginTransaction();
            ContentValues cv = new ContentValues();
            cv.put(col_2, cl.getDate());
            cv.put(col_3, cl.getSubject());
            cv.put(col_4, cl.getTutor());
            cv.put(col_5, cl.getPresent_students());

            db.update(table, cv, col_1 + "=?", new String[]{cl.getClass_id() + " "});
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        } catch (Exception e) {
            return false;
        }
        return  true;
    }

    public boolean DeleteClass(Context context, classes cl) {
        try {
            SQLiteDatabase db = new NewSQLDataProvider(context).getWritableDatabase();
            db.beginTransaction();
            db.delete(table, col_1 + "=?", new String[]{cl.getClass_id() + " "});
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        } catch (Exception e) {
            return false;
        }
        return  true;
    }

}
