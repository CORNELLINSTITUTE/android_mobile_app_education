package nz.ac.cornell.student_attendance;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Classes extends AppCompatActivity {

    public ArrayList<String> classesList = new ArrayList<>();
    ArrayAdapter<String> stdArrayAdapter;

    GridView gridView;
    EditText class_date, editText3, tutor_id, presentStudents;

    NewSQLDataProvider db = new NewSQLDataProvider(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        stdArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, classesList);

        Button showAll = (Button) findViewById(R.id.button4);
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classesList.clear();
                db.open();
                gridView = (GridView) findViewById(R.id.GridViewShow);
                Cursor cursor = db.getAllClasses();

                while (cursor.moveToNext()) {
                    String date = cursor.getString(0);
                    String[] separated = date.split("GMT");

                    String subject = cursor.getString(1);
                    String tutor = cursor.getString(2);
                    String present_students = cursor.getString(3);

                    classesList.add(separated[0]);
                    classesList.add(subject);
                    classesList.add(tutor);
                    classesList.add(present_students);

                    classesList.add("");
                    classesList.add("");
                    classesList.add("");
                    classesList.add("");
                }

                db.close();
                gridView.setAdapter(stdArrayAdapter);
            }
        });

    }

    public void deleteClass(View view) {
        class_date = (EditText) findViewById(R.id.class_date);
        tutor_id = (EditText) findViewById(R.id.tutor_id);
        editText3 = (EditText) findViewById(R.id.editText3);

        String date = class_date.getText().toString();
        String tutor = tutor_id.getText().toString();
        String subject = editText3.getText().toString();

        db.open();
        boolean is_deleted = db.deleteClass(date, subject, tutor);
        db.close();

        if (is_deleted == true) {
            Toast.makeText(this, "Data deleted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateClass(View view) {
        class_date = (EditText) findViewById(R.id.class_date);
        tutor_id = (EditText) findViewById(R.id.tutor_id);
        editText3 = (EditText) findViewById(R.id.editText3);
        presentStudents = (EditText) findViewById(R.id.presentStudents);

        String date = class_date.getText().toString();
        String tutor = tutor_id.getText().toString();
        String subject = editText3.getText().toString();
        String presentStudents = editText3.getText().toString();

        db.open();
        boolean is_updated = db.updateClass(date, subject, tutor, presentStudents);
        db.close();

        if (is_updated == true) {
            Toast.makeText(this, "Data updated", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }
}
