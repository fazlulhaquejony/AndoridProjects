package com.example.fuhad.mycitymyenvironment;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Camera extends AppCompatActivity {

    private Location location;
    private double latitude;
    private double longitude;
    private String image_name,video_name;
    private static int i=0;
    private static int CAMERA_REQUEST = 122;
    private static int VIDEO_REQUEST = 133;
    private String mCurrentPhotoPath;
    private String mCurrentVideoPath;
    String Longi;
    String Latit;
    boolean x;
    private Uri photoURI = null;
    private Uri videoURI = null;

    /*LoggedUser lu;
    Bundle extras;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        x = getIntent().getBooleanExtra("camera",true);
        GPSTracker gpsTracker = new GPSTracker(Camera.this);
        location=gpsTracker.getLocation();
        latitude = gpsTracker.getLatitude();
        longitude=gpsTracker.getLongitude();
        Toast.makeText(Camera.this, "" + latitude + "  " + longitude, Toast.LENGTH_SHORT).show();
        getMyLocationAddress();


        if(latitude!=0.0 && longitude !=0.0){
            //googleAddress = "https://www.google.com/maps/preview/@<"+latitude+">,<"+longitude+">";
            //latitude = latitude+","+longitude;
            Longi=longitude+"";
            Latit=latitude+"";
        }
        else
        {
            //latitude = "Not available";
            Longi="0";
            Latit="0";
        }
        if(x){

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Ensure that there's a camera activity to handle the intent

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                    Toast.makeText(this,photoFile+"",Toast.LENGTH_LONG).show();
                    Log.i("camera",""+photoFile);
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Toast.makeText(this,"Error occurs while creating file",Toast.LENGTH_SHORT).show();
                    Log.e("camera",ex+"");
                    finish();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    photoURI = Uri.fromFile(photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN) {
                        ClipData clip=
                                ClipData.newUri(getContentResolver(), "A photo", photoURI);

                        takePictureIntent.setClipData(clip);
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    else {
                        List<ResolveInfo> resInfoList=
                                getPackageManager()
                                        .queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);

                        for (ResolveInfo resolveInfo : resInfoList) {
                            String packageName = resolveInfo.activityInfo.packageName;
                            grantUriPermission(packageName, photoURI,
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        }
                    }
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
            }
        }
        else{
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            takeVideoIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT,10); //10 sec
            takeVideoIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 8 * 1024 * 1024L); //12*1024*1024=12MiB

            if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
                File videoFile = null;
                try {
                    videoFile = createVideoFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File
                    Toast.makeText(this,"Error occurs while creating file",Toast.LENGTH_SHORT).show();
                    Log.e("camera",ex+"");
                    finish();
                }
                // Continue only if the File was successfully created
                if (videoFile != null) {
                    videoURI = Uri.fromFile(videoFile);
                    takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                        takeVideoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    else if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN) {
                        ClipData clip=
                                ClipData.newUri(getContentResolver(), "A video", videoURI);

                        takeVideoIntent.setClipData(clip);
                        takeVideoIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    else {
                        List<ResolveInfo> resInfoList=
                                getPackageManager()
                                        .queryIntentActivities(takeVideoIntent, PackageManager.MATCH_DEFAULT_ONLY);

                        for (ResolveInfo resolveInfo : resInfoList) {
                            String packageName = resolveInfo.activityInfo.packageName;
                            grantUriPermission(packageName, videoURI,
                                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        }
                    }
                    startActivityForResult(takeVideoIntent, VIDEO_REQUEST);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MCMEP_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory(),"/MCME/Media/photos/");
        //getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
            storageDir.createNewFile();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        image_name = imageFileName+".jpg";
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private File createVideoFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String videoFileName = "MCMEV_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStorageDirectory(),"/MCME/Media/videos/");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
            storageDir.createNewFile();
        }
        File video = File.createTempFile(
                videoFileName,  /* prefix */
                ".mp4",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        video_name = videoFileName+".mp4";
        mCurrentVideoPath = video.getAbsolutePath();
        return video;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void galleryAddVideo() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentVideoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST)
        {
            if(resultCode == RESULT_OK)
            {
                galleryAddPic();
                Toast.makeText(this," \n"+Latit+" \n"+Longi + " \n"+mCurrentPhotoPath,Toast.LENGTH_LONG);

                Intent intent = new Intent(Camera.this,ProblemActivity.class);

                intent.putExtra("latitude",Latit);
                intent.putExtra("longitude",Longi);
                intent.putExtra("isImage",true);
                intent.putExtra("isVideo",false);
                intent.putExtra("image",mCurrentPhotoPath);
                intent.putExtra("video","NULL");
                intent.putExtra("fileName",image_name);
                /*if (extras != null){
                    intent.putExtra("userData",lu);
                }*/
                //Toast.makeText(this,encoded_string,Toast.LENGTH_SHORT).show();
                //Toast.makeText(this," \n"+latitude+" \n"+address + " \n"+image_name,Toast.LENGTH_LONG);
                startActivity(intent);
                finish();
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this,"User canceled image capture",Toast.LENGTH_SHORT).show();
                Intent x = new Intent(this,MainActivity.class);
                startActivity(x);
                finish();
            }
            else{
                Toast.makeText(this,"ERROR: CAN'T OPEN CAMERA",Toast.LENGTH_SHORT).show();
                Intent x = new Intent(this,MainActivity.class);
                startActivity(x);
                finish();
            }
        }
        if(requestCode == VIDEO_REQUEST){
            if(resultCode == RESULT_OK){

                galleryAddVideo();

                //String video_path = "/storage/sdcard/cameraAPP/"+video_name;
                Intent intent = new Intent(Camera.this,ProblemActivity.class);

                Toast.makeText(this," \n"+Latit+" \n"+Longi + " \n"+mCurrentVideoPath,Toast.LENGTH_LONG);

                intent.putExtra("latitude",Latit);
                intent.putExtra("longitude",Longi);
                intent.putExtra("isImage",false);
                intent.putExtra("isVideo",true);
                intent.putExtra("image","NULL");
                intent.putExtra("video",mCurrentVideoPath);
                intent.putExtra("fileName",video_name);
                /*if (extras != null){
                    intent.putExtra("userData",lu);
                }*/

                //Toast.makeText(this,video_name+" \n"+latitude+" \n"+address+" \n"+mCurrentVideoPath,Toast.LENGTH_SHORT).show();
                //Toast.makeText(this,encoded_string+" "+googleAddress+" "+address,Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(this,"User canceled video recording",Toast.LENGTH_SHORT).show();
                Intent x = new Intent(this,MainActivity.class);
                startActivity(x);
                finish();
            }
            else{
                Toast.makeText(this,"ERROR: CAN'T OPEN CAMERA",Toast.LENGTH_SHORT).show();
                Intent x = new Intent(this,MainActivity.class);
                startActivity(x);
                finish();
            }
        }

    }

    public void getMyLocationAddress() {
        String address;
        Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);
        try {
            //Place your latitude and longitude
            List<Address> addresses = geocoder.getFromLocation(latitude,longitude, 1);

            if(addresses != null && addresses.size() > 0) {

                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String zip = addresses.get(0).getPostalCode();
                String country = addresses.get(0).getCountryName();

                for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                }
                address = strAddress.toString()+","+city+","+state+","+zip+","+country;
                Toast.makeText(Camera.this, address+"\nhttps://www.google.com/maps/preview/@<"+latitude+">,<"+longitude+">", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(Camera.this,"No location found",Toast.LENGTH_LONG).show();
            //address = "No location found";
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
            //address = "Could not get address..!";
        }
    }
}
