package uk.ac.reading.sis05kol.mooc;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ActivityPreferences extends Activity {
    int georgeNum = 0;
    int harryNum = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_preferences);
        NumberPicker np1 = (NumberPicker) findViewById(R.id.numberPicker);
        NumberPicker np2 = (NumberPicker) findViewById(R.id.numberPicker2);
        ImageView camera = (ImageView) findViewById(R.id.camera);

        LinearLayout backy = (LinearLayout) findViewById(R.id.backy);
        final ImageView harry = (ImageView) findViewById(R.id.harrypic);
        final ImageView george = (ImageView) findViewById(R.id.georgepic);
        final Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.tweener);
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
            public void onClick(View view) {
                dispatchTakePictureIntent();

            }
        });

        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                harryNum = newVal;
                harry.startAnimation(hyperspaceJumpAnimation);
            }
        });
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                georgeNum = newVal;

                george.startAnimation(hyperspaceJumpAnimation);


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
        bundle.putString("pic", mCurrentPhotoPath);


//Add the bundle to the intent
        i.putExtras(bundle);


        startActivity(i);

    }

    private void dispotchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        "uk.ac.reading.sis05kol.mooc.fileprovider",
                        photoFile);


                List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(takePictureIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);
                for (ResolveInfo resolveInfo : resInfoList) {
                    String packageName = resolveInfo.activityInfo.packageName;
                    this.grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);


            }


        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
        galleryAddPic();

    }


    String mCurrentPhotoPath="blank";

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        storageDir.mkdir();

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


}




