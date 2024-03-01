package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.event.EventData;
import com.depression.relief.depressionissues.authentication.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Nullable;

public class EventsDetailsUserShowActivity extends AppCompatActivity implements PaymentResultListener {
    private ImageView imageView;
    private TextView textViewTitle, textViewDate, textViewTime, textViewLocation, textViewAttendeeLimit, textViewDescription, textViewPrice;
    private ImageView btn_DownloadBill;
    ImageView btn_BookEvent;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference eventsRef = db.collection("events");
    private static final String RAZORPAY_KEY_ID = "rzp_test_XRlrZ7yCa9RTyJ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_details_user_show);

        imageView = findViewById(R.id.imageView);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewAttendeeLimit = findViewById(R.id.textViewAttendeeLimit);
        textViewDescription = findViewById(R.id.textViewDescription);
        btn_BookEvent = findViewById(R.id.btn_BookEvent);
        btn_DownloadBill = findViewById(R.id.btn_DownloadBill);
        textViewPrice = findViewById(R.id.textViewPrice);

        // Assuming you have passed the EventData object through Intent
        EventData eventData = (EventData) getIntent().getSerializableExtra("eventData");

        btn_DownloadBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                redirectToBillGeneratedActivity();

            }
        });

        if (eventData != null) {
            // Load image using Glide or your preferred library
            Glide.with(this).load(eventData.getImageUrl()).into(imageView);

            textViewTitle.setText(eventData.getTitle());
            textViewDate.setText(eventData.getDate());
            textViewTime.setText(eventData.getTime());
            textViewLocation.setText(eventData.getLocation());
//            textViewAttendeeLimit.setText(eventData.getAttendeeLimit());
            textViewDescription.setText(eventData.getDescription());
            textViewPrice.setText(String.format(getString(R.string.price_prefix) + String.format(" %.2f", eventData.getEventPrice()) + " For One Ticket"));

            btn_BookEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startRazorpayPayment();

                }
            });
        }
    }

    private void redirectToBillGeneratedActivity() {
        EventData eventData = (EventData) getIntent().getSerializableExtra("eventData");

        Intent intent = new Intent(this, BillGeneratedActivity.class);
        intent.putExtra("eventData", eventData); // Pass the event data to BillGeneratedActivity
        startActivity(intent);
    }

    private void startRazorpayPayment() {
        // Assuming you have passed the EventData object through Intent
        EventData eventData = (EventData) getIntent().getSerializableExtra("eventData");

        if (eventData == null) {
            // Handle the case where eventData is null
            showSnackBar("Invalid event data. Please try again.");
            return;
        }

        Toast.makeText(this, "event price " + eventData.getEventPrice(), Toast.LENGTH_SHORT).show();

        double eventPrice = eventData.getEventPrice();
        long amountInPaisa = (long) (eventPrice * 100);

        System.out.println("amountpaisa is : " + amountInPaisa);

        Log.d("Razorpay", "Event Price: " + eventPrice);
        Log.d("Razorpay", "Amount in Paisa: " + amountInPaisa);

        if (amountInPaisa < 100) {
            // Handle error: Amount is less than the minimum required by Razorpay
            showSnackBar("Invalid event price. Please check and try again." + amountInPaisa);
            Log.e("Razorpay", "startRazorpayPayment: " + amountInPaisa);
            return;
        }

        // Initialize Razorpay Checkout
        Checkout checkout = new Checkout();
        checkout.setKeyID(RAZORPAY_KEY_ID);

        // Create an order request with necessary details
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Unwind");
            options.put("description", "Event Booking");
            options.put("currency", "INR");
            options.put("amount", amountInPaisa);
            options.put("prefill.email", "unwindtherelief@gmail.com");
            options.put("prefill.contact", "7048777558");
            checkout.open(this, options);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a string containing user details
        String userDetails = String.format(
                "EventName: %s\nUserID: %s\nEventLocation: %s\nEventPrice: %.2f",
                eventData.getTitle(),
                currentUserUid, // Assuming you have a method to get the userID
                eventData.getLocation(),
                eventData.getEventPrice()
        );

        // Generate a QR code with user details
        Bitmap qrCodeBitmap = generateQrCode(userDetails);

        // Save the bitmap as a JPEG file
        String fileName = "qr_code_" + System.currentTimeMillis() + ".jpeg";
        File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        try {
            FileOutputStream out = new FileOutputStream(filePath);
            qrCodeBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            // Display a message or perform any action on successful QR code generation
            showSnackBar("QR code generated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
            showSnackBar("Failed to generate QR code. Please try again.");
        }

    }

    private Bitmap generateQrCode(String data) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 300, 300);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void showSnackBar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentSuccess(String s) {
//        generateBill();
        btn_DownloadBill.setVisibility(View.VISIBLE);
        showSnackBar("Payment successful. Bill generated.");
    }

    private void generateBill() {

    }


    @Override
    public void onPaymentError(int errorCode, String errorMessage) {
        // This method is called when the payment encounters an error
        // You can handle the error logic here
        Log.e("PaymentError", "Error Code: " + errorCode + ", Message: " + errorMessage);

        showSnackBar("Payment failed. Please try again.");
    }
}