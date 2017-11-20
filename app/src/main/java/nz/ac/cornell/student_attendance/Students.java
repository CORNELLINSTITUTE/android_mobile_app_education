package nz.ac.cornell.student_attendance;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nz.ac.cornell.student_attendance.models.tutors;

public class Students extends AppCompatActivity {

    NewSQLDataProvider mydb = new NewSQLDataProvider(this);
    Bundle extras;
    String[] tmp;
    TextView nameLabel, tutor_idLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        // Get students list
        mydb.open();
        Cursor studentsList = mydb.AllStudents();
        List<String> sList = new ArrayList<String>();
        while(studentsList.moveToNext()) {
            sList.add(studentsList.getString(1) + " " + studentsList.getString(2) + " - " + studentsList.getString(0));
        }
        mydb.close();

        TableLayout ll = (TableLayout) findViewById(R.id.studentsLayout);

        for (int i = 0; i < sList.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView subject = new TextView(this);
            subject.setText(sList.get(i));
            subject.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            row.addView(subject);
            ll.addView(row,i);
        }
    }
}
