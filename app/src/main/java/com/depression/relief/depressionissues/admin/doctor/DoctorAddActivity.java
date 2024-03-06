package com.depression.relief.depressionissues.admin.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.depression.relief.depressionissues.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class DoctorAddActivity extends AppCompatActivity {
    private EditText edtDoctorEmail, edtDoctorId, edtDoctorPassword;
    private Button btnSaveDoctor;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add);

        edtDoctorEmail = findViewById(R.id.edtDoctorEmail);
        edtDoctorId = findViewById(R.id.edtDoctorId);
        edtDoctorPassword = findViewById(R.id.edtDoctorPassword);
        btnSaveDoctor = findViewById(R.id.btnSaveDoctor);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        btnSaveDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDoctorToFirestore();
            }
        });
    }

    private void saveDoctorToFirestore() {
        String doctorEmail = edtDoctorEmail.getText().toString().trim();
        String doctorId = edtDoctorId.getText().toString().trim();
        String doctorPassword = edtDoctorPassword.getText().toString().trim();

        if (doctorEmail.isEmpty() || doctorId.isEmpty() || doctorPassword.isEmpty()) {
            showSnackbar("Please fill in all fields");
            return;
        }

        // Check if the email already exists in Firestore
        checkIfEmailExists(doctorEmail, new EmailExistsCallback() {
            @Override
            public void onCallback(boolean emailExists) {
                if (emailExists) {
                    showSnackbar("Email is already in use");
                    Toast.makeText(DoctorAddActivity.this, "Email is already in use!", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed to save the doctor to Firestore
                    saveDoctor(doctorId, doctorEmail, doctorPassword);
                }
            }
        });
    }

    private void checkIfEmailExists(String email, EmailExistsCallback callback) {
        db.collection("doctors")
                .whereEqualTo("doctorEmail", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            callback.onCallback(!task.getResult().isEmpty());
                        } else {
                            callback.onCallback(false);
                            task.getException().printStackTrace();
                        }
                    }
                });
    }

    private void saveDoctor(String doctorId, String doctorEmail, String doctorPassword) {
        // Create a DoctorModel object with auto-generated document ID
        DoctorModel doctor = new DoctorModel(doctorId, doctorEmail, doctorPassword);

        // Save the doctor to Firestore
        db.collection("doctors")
                .add(doctor)  // Use add() to auto-generate a document ID
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            // Get the auto-generated document ID
                            String generatedId = task.getResult().getId();
                            doctor.setDocumentId(generatedId);  // Set the document ID in the DoctorModel

                            // Update the document with the document ID
                            db.collection("doctors")
                                    .document(generatedId)
                                    .set(doctor)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> innerTask) {
                                            if (innerTask.isSuccessful()) {
                                                Toast.makeText(DoctorAddActivity.this, "Doctor saved successfully", Toast.LENGTH_SHORT).show();
                                                sendDoctorDetailsEmail(doctorEmail, doctorId, doctorPassword);
                                            } else {
                                                showSnackbar("Failed to save doctor");
                                                Toast.makeText(DoctorAddActivity.this, "Failed to save doctor", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        } else {
                            showSnackbar("Failed to save doctor we are Busy!");
                        }
                    }
                });
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    private interface EmailExistsCallback {
        void onCallback(boolean emailExists);
    }

    private void sendDoctorDetailsEmail(String doctorEmail, String doctorId, String doctorPassword) {
        try {
            String senderEmail = "unwindtherelief@gmail.com";
            String senderPassword = "ndicjlvojkxcxmyz";

            String message = "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "<tr>\n" +
                    "    <td align=\"center\">\n" +
                    "        <table width=\"600\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;\">\n" +
                    "            <tr>\n" +
                    "                <td bgcolor=\"#3871c1\" style=\"padding: 5px; text-align: center;\">\n" +
                    "                    <h2 style=\"color: #ffffff;\">Welcome to Our Family</h2>\n" +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "                <td bgcolor=\"#ffffff\" style=\"padding: 10px; color: black;\">\n" +
                    "                    <p>Hello Doctor,</p>\n" +
                    "                    <p>Your account details are successfully created. Here are your login credentials:</p>\n" +
                    "                    <p>Doctor ID: " + doctorId + "</p>\n" +
                    "                    <p>Password: " + doctorPassword + "</p>\n" +
                    "                    <p>Keep these credentials secure and do not share them with anyone.</p>\n" +
                    "                    <p>If you didn't create this account or have any concerns, please contact support.</p>\n" +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "                <td bgcolor=\"#3871c1\" style=\"padding: 5px; text-align: center;\">\n" +
                    "                    <p style=\"color: #ffffff;\">Thank You For Joining Us!</p>\n" +
                    "                </td>\n" +
                    "            </tr>\n" +
                    "        </table>\n" +
                    "    </td>\n" +
                    "</tr>\n" +
                    "</table>";

            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
            });

            // Creating a MimeMessage
            MimeMessage mimeMessage = new MimeMessage(session);

            // Setting the sender's name and email address
            InternetAddress senderAddress = new InternetAddress(senderEmail, "Unwind");
            mimeMessage.setFrom(senderAddress);

            // Adding the recipient's email address
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(doctorEmail));

            // Setting the subject and message content as HTML
            mimeMessage.setSubject("Welcome to Our Family!");
            mimeMessage.setContent(message, "text/html");

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        // Handling messaging exception
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(DoctorAddActivity.this, "Error Occurred : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        e.printStackTrace();
                    }
                }
            });
            t.start();

            Toast.makeText(this, "Successfully send email check this!", Toast.LENGTH_SHORT).show();
            // Handle success or show a toast
            // You can add your logic here to handle the success scenario
            // For example, you can show a success message or navigate to another screen
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(DoctorAddActivity.this, "Error Occurred : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}