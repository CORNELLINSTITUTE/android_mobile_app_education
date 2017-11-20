package nz.ac.cornell.student_attendance;

import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nz.ac.cornell.student_attendance.DAO.students_DAO;
import nz.ac.cornell.student_attendance.DAO.tutors_DAO;
import nz.ac.cornell.student_attendance.models.students;
import nz.ac.cornell.student_attendance.models.tutors;


public class Login extends AppCompatActivity {

    String id = "";
    String first_name = "";
    String last_name = "";
    String dob = "";
    String phone = "";
    String email = "";
    String address = "";
    EditText tutor_id, passw;
    NewSQLDataProvider mydb = new NewSQLDataProvider(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void login(View view) {
        mydb.open();

        tutor_id = (EditText) findViewById(R.id.loginInput);
        passw = (EditText) findViewById(R.id.editText2) ;

        boolean valid_login = isValidLogin(tutor_id.getText().toString());
        if (valid_login == false) {
            Toast.makeText(this, "Invalid login. Please use only letters and digits", Toast.LENGTH_LONG).show();
            return;
        }
        // Generate Hash
        String hash =  tutors.md5(passw.getText().toString());

        Cursor tmp = mydb.LoginGetTutor(tutor_id.getText().toString(), hash);
        while(tmp.moveToNext()) {
            id = tmp.getString(0);
            first_name = tmp.getString(1);
            last_name = tmp.getString(2);
            dob = tmp.getString(3);
            phone = tmp.getString(4);
            address = tmp.getString(5);
            email = tmp.getString(6);
        }

        if (id != "") {
            String[] user_f = {first_name, last_name, dob, phone, address, email, tutor_id.getText().toString()};
            Intent intent =  new Intent(this, Home.class);
            intent.putExtra("user_f", user_f);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Bad login or password...", Toast.LENGTH_LONG).show();
        };
        mydb.close();
    }

    public boolean isValidLogin(String string){
        final String loginPattern = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(loginPattern);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

}
