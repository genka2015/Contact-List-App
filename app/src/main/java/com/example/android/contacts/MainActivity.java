package com.example.android.contacts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "alert";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button pic;
    private Button save;
    private Button display;
    private EditText firstName;
    private EditText lastName;
    ImageView mImageView;
    String imagePath;
    private ContactsDataSource datasource;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start the database
        datasource = new ContactsDataSource(this);
        datasource.open();


        firstName = (EditText)findViewById(R.id.fname);
        lastName = (EditText)findViewById(R.id.lname);
        pic = (Button)findViewById(R.id.picBt);
        save = (Button)findViewById(R.id.saveBt);
        display = (Button)findViewById(R.id.listBt);
        mImageView = (ImageView) findViewById(R.id.my_img);

        pic.setOnClickListener(this);
        save.setOnClickListener(this);
        display.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.picBt:
                // TAKE PHOTO
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.d(TAG,"Error trying to make a file");
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider",photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        //Log.d(TAG,"File created path: " + imagePath);
                    }

                }

                break;
            case R.id.saveBt:
                // SAVE CONTACT
                //Log.d(TAG,"Saving Contact " + firstName.getText().toString() + " " + lastName.getText().toString());

                Contact genka = new Contact();
                genka.setFirstName(firstName.getText().toString());
                genka.setLastName(lastName.getText().toString());
                genka.setPath(imagePath);
                datasource.addContact(genka);

                break;

            case R.id.listBt:
                // DISPLAY LIST
                Intent myIntent = new Intent(MainActivity.this,Main2Activity.class);

                ArrayList<Contact> my_list = datasource.getAllContacts();

                myIntent.putExtra("data", my_list);

                // Starting activity
                startActivity(myIntent);

        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mImageView.setImageBitmap(imageBitmap);
//        }
//    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagePath = image.getAbsolutePath();
        return image;
    }

}
