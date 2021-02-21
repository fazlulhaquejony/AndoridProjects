package com.example.fuhad.mycitymyenvironment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class gallery extends AppCompatActivity {

    private static final int NoOfPic = 1;
    private static final int video =2;
    Bitmap selectedImage;
    ImageView view;
    VideoView videoView;
    String imagePath;
    String videoPath;
    String googleAddress = "Not avaliable";
    String address = "NULL";
    Boolean isImage,isVideo;
    String fileName = null;
    ExifInterface exif = null;
    String Longi;
    String Latit;

    double Latitude=0.0;
    double Longitude=0.0;

    private static int i=0;
    boolean x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        x= getIntent().getBooleanExtra("camera",false);
        view = (ImageView) findViewById(R.id.imageViewGallary);
        videoView=(VideoView)findViewById(R.id.videoView);

        if(!x){
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i,NoOfPic);
        }else{
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a Video "), video);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case NoOfPic:
                if(resultCode == RESULT_OK){

                    Toast.makeText(this,"starting",Toast.LENGTH_SHORT).show();
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(uri,projection,null,null,null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();

                    Toast.makeText(this,"bitmap",Toast.LENGTH_SHORT).show();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    // down sizing image as it throws OutOfMemory Exception for larger
                    // images
                    options.inSampleSize = 4;
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    selectedImage = BitmapFactory.decodeFile(filePath,options);
                    Drawable d = new BitmapDrawable(selectedImage);
                    view.setBackground(d);
                    videoView.setVisibility(View.GONE);
                    //image = getPath1(uri);
                    imagePath = filePath;
                    fileName = filePath.substring(filePath.lastIndexOf("/")+1);
                    isImage = true;
                    isVideo = false;
                    Toast.makeText(this,"ending "+imagePath,Toast.LENGTH_SHORT).show();
                    //i++;
                    //image = encodeString(selectedImage);
                    //new sendImage().execute();
                }
                else{
                    Intent x = new Intent(this,MainActivity.class);
                    startActivity(x);
                    finish();
                }
                break;
            case video:
                if(resultCode == RESULT_OK){
                    Uri selectedImageUri = data.getData();
                    videoPath = getPath(selectedImageUri);
                    view.setVisibility(View.GONE);
                    videoView.setVisibility(View.VISIBLE);
                    videoView.setVideoPath(videoPath);
                    videoView.start();
                    fileName = videoPath.substring(videoPath.lastIndexOf("/")+1);
                    isImage = false;
                    isVideo = true;
                    Toast.makeText(this,"video "+videoPath,Toast.LENGTH_SHORT).show();
                    //i++;
                    //textView.setText(selectedPath);
                }
        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
        cursor.close();

        return path;
    }

    public String getPath1(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    public String encodeString (Bitmap bp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bp.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] array = stream.toByteArray();
        String encoded_string = Base64.encodeToString(array, 0);
        return encoded_string;
    }
    public void proceed(View view){
        Intent i = new Intent(gallery.this,ProblemActivity.class);
        /*if(x){
            //image = "NULL";
            i.putExtra("isImage",false);
            i.putExtra("isVideo",true);
            i.putExtra("image", "NULL");
            i.putExtra("video",selectedPath);
        }else{
            selectedPath = "NULL";
            i.putExtra("isImage",true);
            i.putExtra("isVideo",false);
            i.putExtra("image", "NULL");
            i.putExtra("video",selectedPath);
        }*/
        String pp = "null";
        if(isImage){
            pp= imagePath;
        }else {
            pp=videoPath;
        }

        try {
            exif = new ExifInterface(pp);
            String LATITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String LATITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String LONGITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String LONGITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

            // your Final lat Long Values


            if((LATITUDE !=null)
                    && (LATITUDE_REF !=null)
                    && (LONGITUDE != null)
                    && (LONGITUDE_REF !=null))
            {

                if(LATITUDE_REF.equals("N")){
                    Latitude = convertToDegree(LATITUDE);
                }
                else{
                    Latitude = 0 - convertToDegree(LATITUDE);
                }

                if(LONGITUDE_REF.equals("E")){
                    Longitude = convertToDegree(LONGITUDE);
                }
                else{
                    Longitude = 0 - convertToDegree(LONGITUDE);
                }

            }

            //googleAddress = "https://www.google.com/maps/preview/@<"+Latitude+">,<"+Longitude+">";
            googleAddress = Latitude+","+Longitude;
            if(Latitude!=0.0 && Longitude !=0.0){
                //googleAddress = "https://www.google.com/maps/preview/@<"+latitude+">,<"+longitude+">";
                //latitude = latitude+","+longitude;
                Longi=Longitude+"";
                Latit=Latitude+"";
            }
            else
            {
                //latitude = "Not available";
                Longi="0";
                Latit="0";
            }
            getMyLocationAddress();

        } catch (IOException e) {
            Toast.makeText(this,"error"+e,Toast.LENGTH_LONG).show();
        }





        i.putExtra("latitude",Latit);
        i.putExtra("longitude",Longi);
        i.putExtra("isImage",isImage);
        i.putExtra("isVideo",isVideo);
        i.putExtra("image",imagePath);
        i.putExtra("video",videoPath);
        i.putExtra("fileName",fileName);

        startActivity(i);
        finish();
    }

    public void retry (View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,NoOfPic);
    }

    private Float convertToDegree(String stringDMS){
        Float result = null;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0/D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0/M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0/S1;

        result = new Float(FloatD + (FloatM/60) + (FloatS/3600));

        return result;


    }

    public void getMyLocationAddress() {
        Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);
        try {
            //Place your latitude and longitude
            List<Address> addresses = geocoder.getFromLocation(Latitude,Longitude, 1);

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
                Toast.makeText(gallery.this, address+"\nhttps://www.google.com/maps/preview/@<"+Latitude+">,<"+Longitude+">", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(gallery.this,"No location found",Toast.LENGTH_LONG).show();
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
