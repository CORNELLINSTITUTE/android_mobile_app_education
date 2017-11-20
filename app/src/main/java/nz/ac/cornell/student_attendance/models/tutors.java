package nz.ac.cornell.student_attendance.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Temur Mirzosharipov on 27-Oct-17.
 */

public class tutors extends user {
    String tutor_id;
    private static tutors tInstance = null;

    public tutors(String first_name, String last_name, String dob, String phone, String address, String email, String tutor_id) {
        super(first_name, last_name, dob, phone, address, email);
        this.tutor_id = tutor_id;
    }

    public String getTutor_id() {
        return tutor_id;
    }


    // Hash function for password
    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
