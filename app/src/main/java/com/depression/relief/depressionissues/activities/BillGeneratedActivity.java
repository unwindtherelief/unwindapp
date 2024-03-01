package com.depression.relief.depressionissues.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.depression.relief.depressionissues.R;
import com.depression.relief.depressionissues.admin.event.EventData;
import com.depression.relief.depressionissues.authentication.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BillGeneratedActivity extends AppCompatActivity {
    private TextView tvUserName, tvUserMobile, tvEventName, tvUserId, tvEventPrice, tvEventLocation, tvEventDate, tvEventTime;
    private ImageView btnDownloadBill;
    ImageView imageViewQrCode, img_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_generated);

        initializeViews();
        // Get data from Intent
        EventData eventData = (EventData) getIntent().getSerializableExtra("eventData");


        // Use the getUserModel method with a callback
        getUserModel(new UserFetchCallback() {
            @Override
            public void onUserFetch(UserModel userModel) {
                if (userModel != null) {
                    // Set data to TextViews
                    tvUserName.setText(userModel.getFirstname());
                    tvUserMobile.setText(userModel.getMobile());
                    tvEventName.setText(eventData.getTitle());
                    tvUserId.setText("Ticket ID :" + userModel.getUserId());
                    tvEventPrice.setText(String.valueOf(eventData.getEventPrice()));
                    tvEventLocation.setText(eventData.getLocation());
                    tvEventDate.setText(eventData.getDate());
                    tvEventTime.setText(eventData.getTime());

                    Glide.with(BillGeneratedActivity.this).load(eventData.getImageUrl()).into(img_event);
                    //Qr code code initialization

                    generateAndShowQrCode(tvUserId.getText().toString());
                } else {
                    // Handle the case where user data is not available
                    Toast.makeText(BillGeneratedActivity.this, "User data not available", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDownloadBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateAndDownloadBill();
            }
        });
    }

    private void generateAndShowQrCode(String content) {
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 400, 400);
            // Set the generated QR code to an ImageView or any other view
            // For example, if you have an ImageView with id 'imageViewQrCode' in your XML:
            imageViewQrCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show();
        }
    }

/*
    private void generateAndDownloadBill() {
        // Create a PDF document
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Create a Paint object for styling
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12);

        // Get custom layout view
        View customLayoutView = getCustomLayoutView();

        // Measure and layout the view
        int widthSpec = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        customLayoutView.measure(widthSpec, heightSpec);
        customLayoutView.layout(0, 0, customLayoutView.getMeasuredWidth(), customLayoutView.getMeasuredHeight());

        // Draw the custom layout onto the PDF canvas
        customLayoutView.draw(canvas);

        // Finish the page
        pdfDocument.finishPage(page);

        // Save the PDF to external storage
        String fileName = "bill_" + System.currentTimeMillis() + ".pdf";
        File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        try {
            pdfDocument.writeTo(new FileOutputStream(filePath));
            pdfDocument.close();
            displayMessage("Bill downloaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            displayMessage("Failed to download bill. Please try again.");
        }
    }
*/

/*
    private View getCustomLayoutView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customLayoutView = inflater.inflate(R.layout.ticket_custom_layout, null);

        TextView tvEventDate = customLayoutView.findViewById(R.id.tvEventDate);
        TextView tvEventName = customLayoutView.findViewById(R.id.tvEventName);
        TextView tvEventTime = customLayoutView.findViewById(R.id.tvEventTime);
        TextView tvEventLocation = customLayoutView.findViewById(R.id.tvEventLocation);

        // Get ImageView from custom layout
        ImageView qrImageView = customLayoutView.findViewById(R.id.imageViewQrCode);

        Bitmap bitmap = getBitmapFromImageView(qrImageView); // Replace with your method to get Bitmap from ImageView


        return customLayoutView;
    }
*/


    private void generateAndDownloadBill() {
        // Create a PDF document
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();

        // Create a Paint object for styling
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12);


        View rootView = findViewById(android.R.id.content);
        tvUserName = rootView.findViewById(R.id.tvUserName);
        tvUserMobile = rootView.findViewById(R.id.tvUserMobile);
        tvEventName = rootView.findViewById(R.id.tvEventName);
        tvUserId = rootView.findViewById(R.id.tvUserId);
        tvEventPrice = rootView.findViewById(R.id.tvEventPrice);
        tvEventLocation = rootView.findViewById(R.id.tvEventLocation);
        tvEventDate = rootView.findViewById(R.id.tvEventDate);
        tvEventTime = rootView.findViewById(R.id.tvEventTime);
        imageViewQrCode = rootView.findViewById(R.id.imageViewQrCode);
        img_event = rootView.findViewById(R.id.img_event);


        // Add content to the PDF
        int y = 50;
        canvas.drawText("User Name: " + tvUserName.getText(), 10, y, paint);
        y += 20;
        canvas.drawText("User Mobile: " + tvUserMobile.getText(), 10, y, paint);
        y += 20;
        canvas.drawText("Event Name: " + tvEventName.getText(), 10, y, paint);
        y += 20;
        canvas.drawText("User ID: " + tvUserId.getText(), 10, y, paint);
        y += 20;
        canvas.drawText("Event Price: " + tvEventPrice.getText(), 10, y, paint);
        y += 20;
        canvas.drawText("Event Location: " + tvEventLocation.getText(), 10, y, paint);
        y += 20;
        canvas.drawText("Event Date: " + tvEventDate.getText(), 10, y, paint);
        y += 20;
        canvas.drawText("Event Time: " + tvEventTime.getText(), 10, y, paint);

        Bitmap bitmap = getBitmapFromImageView(imageViewQrCode); // Replace with your method to get Bitmap from ImageView
        if (bitmap != null) {
            int desiredWidth = 100; // Replace with your desired width in pixels
            int desiredHeight = 100; // Replace with your desired height in pixels
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, true);

            canvas.drawBitmap(resizedBitmap, 10, y, paint);
        }

        // Finish the page
        pdfDocument.finishPage(page);

        // Save the PDF to external storage
        String fileName = "bill_" + System.currentTimeMillis() + ".pdf";
        File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

        try {
            pdfDocument.writeTo(new FileOutputStream(filePath));
            pdfDocument.close();
            displayMessage("Bill downloaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            displayMessage("Failed to download bill. Please try again.");
        }
    }

    private Bitmap getBitmapFromImageView(ImageView imageViewQrCode) {
        BitmapDrawable drawable = (BitmapDrawable) imageViewQrCode.getDrawable();
        if (drawable != null) {
            return drawable.getBitmap();
        } else {
            return null;
        }
    }

    private void getUserModel(UserFetchCallback callback) {
        String currentUserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users")
                .document(currentUserUid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot.exists()) {
                            String firstname = documentSnapshot.getString("firstname");
                            String mobile = documentSnapshot.getString("completeNumber");

                            UserModel userModel = new UserModel(firstname, "", mobile, "", "", "", currentUserUid);
                            callback.onUserFetch(userModel);
                        } else {
                            callback.onUserFetch(null);
                        }
                    } else {
                        callback.onUserFetch(null);
                    }
                });
    }


    private void initializeViews() {
        tvUserName = findViewById(R.id.tvUserName);
        tvUserMobile = findViewById(R.id.tvUserMobile);
        tvEventName = findViewById(R.id.tvEventName);
        tvUserId = findViewById(R.id.tvUserId);
        tvEventPrice = findViewById(R.id.tvEventPrice);
        tvEventLocation = findViewById(R.id.tvEventLocation);
        tvEventDate = findViewById(R.id.tvEventDate);
        tvEventTime = findViewById(R.id.tvEventTime);
        btnDownloadBill = findViewById(R.id.btnDownloadBill);
        imageViewQrCode = findViewById(R.id.imageViewQrCode);
        img_event = findViewById(R.id.img_event);
    }

    private void displayMessage(String message) {
        Toast.makeText(this, "Downloaded status: " + message, Toast.LENGTH_SHORT).show();
    }

    // Callback interface for user data fetching
    public interface UserFetchCallback {
        void onUserFetch(UserModel userModel);
    }
}