package com.depression.relief.depressionissues.specialist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.depression.relief.depressionissues.R;

public class DoctorAddProfileDataActivity extends AppCompatActivity {
    private EditText edtHospitalName, edtPincode, edtAddress, edtshopename,edtDoctorName;
    private Button btnPickAddress, btnContinue;
    private String doctorId;
    private String doctorPassword;
    ImageView img_doctor;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add_profile_data);

        edtHospitalName = findViewById(R.id.edtHospitalName);
        edtPincode = findViewById(R.id.edtPincode);
        edtAddress = findViewById(R.id.edtAddress);
        img_doctor = findViewById(R.id.img_doctor);
        btnContinue = findViewById(R.id.btnContinue);
        edtshopename = findViewById(R.id.edtshopename);
        edtDoctorName = findViewById(R.id.edtDoctorName);

        Intent intent = getIntent();
        if (intent != null) {
            doctorId = intent.getStringExtra("doctorId");
            doctorPassword = intent.getStringExtra("doctorPassword");
        }

        img_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continueToProfileExtraInfo();
            }
        });
    }

    private void continueToProfileExtraInfo() {
        String hospitalName = edtHospitalName.getText().toString().trim();
        String doctorname = edtDoctorName.getText().toString().trim();
        String pincode = edtPincode.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String shopename = edtshopename.getText().toString().trim();
        String shopFullAddress = shopename + " " + address+ "-" + pincode;

        if (hospitalName.isEmpty() || pincode.isEmpty() || address.isEmpty() || shopFullAddress.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, shopFullAddress, Toast.LENGTH_SHORT).show();
            // Data is valid, proceed to DoctorAddProfileExtraInfoActivity
            Intent intent = new Intent(DoctorAddProfileDataActivity.this, DoctorAddProfileExtraInfoActivity.class);
            intent.putExtra("hospitalName", hospitalName);
            intent.putExtra("pincode", pincode);
            intent.putExtra("address", shopFullAddress);
            intent.putExtra("doctorId", doctorId);
            intent.putExtra("doctorPassword", doctorPassword);
            intent.putExtra("doctorImageUri", imageUri.toString());
            intent.putExtra("doctorName", doctorname);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();

            // Set the selected image in the ImageView
            img_doctor.setImageURI(selectedImage);

            // You can save the image URI to use it later, for example, when navigating to the next activity
            imageUri = selectedImage;
        }
    }
}