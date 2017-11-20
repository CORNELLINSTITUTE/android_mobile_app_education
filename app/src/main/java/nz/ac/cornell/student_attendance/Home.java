package nz.ac.cornell.student_attendance;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nz.ac.cornell.student_attendance.models.tutors;


public class Home extends AppCompatActivity {
    tutors user;
    Bundle extras;
    String[] tmp;
    TextView nameLabel, tutor_idLabel;

    NewSQLDataProvider mydb = new NewSQLDataProvider(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        extras = getIntent().getExtras();
        tmp = extras.getStringArray("user_f");
        user = new tutors(tmp[0],tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6]);
        Toast.makeText(this, "Welcome, " + user.getFirst_name(), Toast.LENGTH_LONG).show();

        // Set Labels for name and tutor_id
        nameLabel = (TextView) findViewById(R.id.textView3);
        nameLabel.setText(user.getFirst_name() + " " + user.getLast_name());
        tutor_idLabel = (TextView) findViewById(R.id.textView10);
        tutor_idLabel.setText(user.getTutor_id());

        // Get subjects list for tutor
        mydb.open();
        Cursor subjectsList = mydb.ListOfTutorsSubjects(user.getTutor_id());
        List<String> sList = new ArrayList<String>();
        while(subjectsList.moveToNext()) {
            sList.add(subjectsList.getString(0));
        }
        mydb.close();

        TableLayout ll = (TableLayout) findViewById(R.id.tableLayout);

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


    public void markAttendance(View view) {
        if (user instanceof tutors) {
            Intent intent = new Intent(this, Attendance.class);
            intent.putExtra("user_f", tmp);
            startActivity(intent);
        } else {
            Toast.makeText(this, "You are not allowed", Toast.LENGTH_SHORT).show();
        }
    }

    public void confirmAttendance(View view) {
        if (user instanceof tutors) {
            Intent intent =  new Intent(this, Attendance.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "You are not allowed", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToStudenstList(View view) {
        if (user instanceof tutors) {
            Intent intent =  new Intent(this, Students.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "You are not allowed", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToClassesList(View view) {
        if (user instanceof tutors) {
            Intent intent =  new Intent(this, Classes.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "You are not allowed", Toast.LENGTH_SHORT).show();
        }
    }



}
