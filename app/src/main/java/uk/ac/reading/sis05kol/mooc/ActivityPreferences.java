package uk.ac.reading.sis05kol.mooc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivityPreferences extends Activity {
    int georgeNum=0;
    int harryNum=0;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_preferences);
        NumberPicker np1 = (NumberPicker) findViewById(R.id.numberPicker);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.numberPicker2);
        ImageView camera = (ImageView) findViewById(R.id.camera);
        Button done = (Button) findViewById(R.id.button);
        np1.setMinValue(0);

        //Specify the maximum value/number of NumberPicker
        np1.setMaxValue(100);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np1.setWrapSelectorWheel(true);
        np2.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np2.setMaxValue(100);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np2.setWrapSelectorWheel(true);
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayInfo();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {dispatchTakePictureIntent();

            }});

        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                harryNum=newVal;
            }
        });
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                 georgeNum=newVal;
            }
        });

    }

    public void displayInfo() {



        Intent i = new Intent(this, MainActivity.class);

        Bundle bundle = new Bundle();

//Add your data to bundle
        // bundle.putStringArray("Stuff", infoString);
        // bundle.putIntArray("pics",pics);
        bundle.putInt("harry", harryNum);
        bundle.putInt("george", georgeNum);


//Add the bundle to the intent
        i.putExtras(bundle);


        startActivity(i);

    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView camera = (ImageView) findViewById(R.id.camera);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            camera.setImageBitmap(imageBitmap);
        }
    }
    String mCurrentPhotoPath;

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
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    }




