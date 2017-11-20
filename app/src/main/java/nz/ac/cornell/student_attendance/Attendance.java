package nz.ac.cornell.student_attendance;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nz.ac.cornell.student_attendance.DAO.classes_DAO;
import nz.ac.cornell.student_attendance.models.classes;
import nz.ac.cornell.student_attendance.models.tutors;

public class Attendance extends AppCompatActivity {
    tutors user;
    Bundle extras;
    String[] tmp;
    TextView nameLabel;
    NewSQLDataProvider mydb = new NewSQLDataProvider(this);
    TableLayout ll;
    List<CheckBox> checkBoxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        extras = getIntent().getExtras();
        tmp = extras.getStringArray("user_f");
        user = new tutors(tmp[0],tmp[1], tmp[2], tmp[3], tmp[4], tmp[5], tmp[6]);

        // Set Labels for name and tutor_id
        nameLabel = (TextView) findViewById(R.id.textView6);
        nameLabel.setText(user.getFirst_name() + " " + user.getLast_name());

        // Get subjects list for tutor
        mydb.open();
        Cursor subjectsList = mydb.ListOfTutorsSubjects(user.getTutor_id());
        List<String> sList = new ArrayList<String>();
        while(subjectsList.moveToNext()) {
            sList.add(subjectsList.getString(0));
        }

        // Get students list
        Cursor studentsList = mydb.AllStudents();
        List<String> tList = new ArrayList<String>();
        while(studentsList.moveToNext()) {
            tList.add(studentsList.getString(1) + " " + studentsList.getString(2) + " - " + studentsList.getString(0));
        }

        mydb.close();

        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sList);
        s.setAdapter(adapter);

        // Student List
        ll = (TableLayout) findViewById(R.id.studentsList);
        checkBoxList = new ArrayList<CheckBox>();

        for (int i = 0; i < tList.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            CheckBox subject = new CheckBox(this);
            subject.setText(tList.get(i));
            subject.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            checkBoxList.add(subject);
            row.addView(subject);
            ll.addView(row,i);
        }
    }


    public void setAttendance(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setTitle("Are you sure?");
        dialog.setContentView(R.layout.confirm);
        dialog.show();
    }

    public void confirmAttendance(View view) {
        String studentsList = "";
        String[] student_id;

        // Get name of tutor
        nameLabel = (TextView) findViewById(R.id.textView6);
        String tutor_credentials = nameLabel.getText().toString();

        // Get selected subject
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        String subjectTitle = spinner.getSelectedItem().toString();

        for(CheckBox checkBox : checkBoxList){

            if (checkBox.isChecked() == true) {
                student_id = checkBox.getText().toString().split(" - ");
                studentsList += student_id[1] + ", ";
            }
        }
        mydb.open();

        // Get current time
        Date currentTime = Calendar.getInstance().getTime();

        String class_date =  currentTime.toString();
        String class_id = class_date + user.getTutor_id();
        classes cl = new classes(
                class_id, class_date, subjectTitle, user.getTutor_id(), studentsList
        );

        boolean insert_cl =  mydb.insertClass(cl);

        mydb.close();

        if (insert_cl == true) {
            Toast.makeText(this, "Data created", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }


}

