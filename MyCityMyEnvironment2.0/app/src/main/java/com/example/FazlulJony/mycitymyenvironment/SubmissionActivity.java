package com.example.fuhad.mycitymyenvironment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SubmissionActivity extends AppCompatActivity {

    EditText problemType;
    EditText location;
    EditText description;
    //Button anonymous;
    Button submit;
    String name = "NULL";
    String email,problem,image,video,fileName,discreption,numprob;
    Intent i;
    String Longi;
    String Latit;
    String test="";
    static Boolean loci= true;
    boolean gallery,nothing,isImage,isVideo;
    long totalSize =0;
    RequestQueue requestQueue;
    StringRequest request;

    /*LoggedUser lu;
    Bundle extras;*/

    SharedPreferences dataSave;
    //int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //extras = getIntent().getExtras();
        /*if (extras != null) {
            lu = (LoggedUser)getIntent().getSerializableExtra("userData"); //Obtaining data
            Toast.makeText(this,lu.getUserName(),Toast.LENGTH_SHORT).show();
            name = lu.getUserName();
            email = lu.getEmail();
        }*/

        requestQueue = Volley.newRequestQueue(this);


        dataSave = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        name = dataSave.getString("username", "no user");
        email = dataSave.getString("email","no email");
        //pas = dataSave.getString("password","no pass");

        problemType = (EditText) findViewById(R.id.problemType);
        description = (EditText) findViewById(R.id.descrption);
        problem = getIntent().getStringExtra("problems");
        Longi=getIntent().getStringExtra("longitude");
        Latit=getIntent().getStringExtra("latitude");
        image = getIntent().getStringExtra("image");
        numprob=getIntent().getStringExtra("probnum");
        gallery = getIntent().getBooleanExtra("gallery", false);
        nothing = getIntent().getBooleanExtra("nothing", false);
        isImage = getIntent().getBooleanExtra("isImage", false);
        isVideo = getIntent().getBooleanExtra("isVideo", false);
        video = getIntent().getStringExtra("video");
        fileName = getIntent().getStringExtra("fileName");


        problemType.setText(problem);
        location = (EditText) findViewById(R.id.location);
        if(Longi.equals("0") && Latit.equals("0")){
            location.setText("");
        }else{
            location.setText(Longi+","+Latit);
        }




        //anonymous=(Button)findViewById(R.id.anonymous);
        submit = (Button) findViewById(R.id.submit);

        final AlertDialog.Builder ad = new AlertDialog.Builder(this);
        if(!(Longi.equals("0") && Latit.equals("0"))){
            location.setEnabled(false);
        }
        //Toast.makeText(this,gallery+"",Toast.LENGTH_SHORT).show();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q = location.getText().toString();
                discreption = description.getText().toString();
                if(gallery && q.equals("")||(!gallery && q.equals(""))){
                    ad.setTitle("ERROR")
                            .setMessage("Please Enter the Location")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
                else if(nothing && discreption.equals("") && q.equals("")){
                    ad.setTitle("ERROR")
                            .setMessage("Please Enter the Location and Discriptions")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }else {
                    if(nothing || isVideo){
                        image = "NO Image";
                    }
                    if(Longi.equals("0") && Latit.equals("0")){
                        Longi = "null";
                        Latit =q;
                        loci = false;
                        Toast.makeText(getApplicationContext(),"LOCIIIII:"+loci,Toast.LENGTH_SHORT).show();
                    }
                    //address = q;

                    onCreateDialog();
                    //dialog.show();
                    //new Encode_image().execute();
                    i = new Intent(getApplicationContext(), MainActivity.class);
                }
            }
        });

        //Toast.makeText(SubmissionActivity.this,"name="+name+"\nemail="+email+"\nproblem="+problem+"\ndiscreption="+discreption+"\ngloc="+gloc+"\naddres="+address+"\nimage="+isImage+"\nvideo="+isVideo+"\nfilename="+fileName,Toast.LENGTH_LONG).show();

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


    private void onCreateDialog() {
        // Get the layout inflater
        LayoutInflater inflater = LayoutInflater.from(SubmissionActivity.this);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View subView = inflater.inflate(R.layout.dialog_submit, null);
        final EditText userName = (EditText) subView.findViewById(R.id.name);
        final EditText userEmail = (EditText) subView.findViewById(R.id.emailname);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        userName.setText(name);
        userEmail.setText(email);
        builder.setTitle("ENTER YOUR INFORMATION ");
        builder.setView(subView);
        // Add action buttons
        //AlertDialog dialog = builder.create();

        test="\n\nName :"+name+"|\n"+"Problems:"+problem+"|\n"+
                "Address:"+"https://www.google.com/maps/preview/@<"+Latit+">,<"+Longi+">"+"|\n"+"location:"+location.getText()+
                "|\n"+"Filename:"+fileName+
                "|\n"+"desricp:"+discreption+"**\n\n";

        Toast.makeText(getApplicationContext(),test,Toast.LENGTH_SHORT).show();
        Log.i("sub",test);

        builder.setPositiveButton("proceed", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // sign in the user ...
                if(!name.equals(null) && !email.equals(null)){
                    userName.setText(name);
                    userEmail.setText(email);
                }else {
                    name = userName.getText().toString();
                    email = userEmail.getText().toString();
                }

                if(name.equals("") && email.equals("")){

                    Toast.makeText(SubmissionActivity.this,"Please enter Name and Email",Toast.LENGTH_LONG).show();
                }else{
                    if (isVideo) {
                        WriteToFile(test);
                        new Encode_image().execute();
                        //submit();
                        upload(video);
                    }
                    if(isImage) {
                        WriteToFile(test);
                        new Encode_image().execute();
                        //submit();
                        upload(image);
                    }
                    if(nothing){
                        WriteToFile(test);
                        new Encode_image().execute();
                        //submit();
                        startActivity(i);
                        finish();
                    }
                }

            }
        });
        builder.setNegativeButton("Go Anonymous", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                name = "Anonymous";
                email = "Anonymous";
                if (isVideo) {
                    WriteToFile(test);
                    new Encode_image().execute();
                    //submit();
                    upload(video);
                }
                if(isImage) {
                    WriteToFile(test);
                    new Encode_image().execute();
                    //submit();
                    upload(image);
                }
                if(nothing){
                    WriteToFile(test);
                    new Encode_image().execute();
                    //submit();
                    startActivity(i);
                    finish();
                }
            }
        });
        builder.show();
    }

    public class Encode_image extends AsyncTask<Void ,Void ,Void> {

        ProgressDialog load;
        //String encoded_string;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            load = ProgressDialog.show(SubmissionActivity.this, "Submitting Problem", "Please wait...", false, false);
            //encoded_string = encodeString(bitmap);
            //image_name = "gallery_pics.jpg";
            Toast.makeText(SubmissionActivity.this,"name="+name+
                    "\nemail="+email+
                    "\nproblem="+problem+
                    "\ndiscreption="+discreption+
                    "\nlatitude="+Latit+
                    "\nlongitutde="+Longi+
                    "\nnum pROb="+numprob+
                    "\nimage="+isImage+
                    "\nvideo="+isVideo+
                    "\nfilename="+fileName,Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        }
                    },new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("volly",error+"");
                    Toast.makeText(getApplicationContext(),"volley error in submitting "+error,Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("name",name);
                    hashMap.put("email",email);
                    hashMap.put("pTitle",problem);
                    hashMap.put("description",discreption);
                    hashMap.put("latit", Latit);
                    hashMap.put("longi", Longi);
                    hashMap.put("numprob", numprob);
                    hashMap.put("image_name", fileName);

                    return hashMap;
                }
            };
            requestQueue.add(request);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            load.dismiss();
            Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_SHORT).show();

        }
    }


    public void upload(String x){

        final String infile = x;
        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;
            //String file = infile;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(SubmissionActivity.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
                if(s != null && s.equals("done")){
                    //new Encode_image().execute();
                    Toast.makeText(SubmissionActivity.this, Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SubmissionActivity.this,Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"),Toast.LENGTH_SHORT).show();
                    Toast.makeText(SubmissionActivity.this,"can't submitted problem",Toast.LENGTH_SHORT).show();
                }
                startActivity(i);
                finish();
            /*textViewResponse.setText());
            textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());*/
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String msg = u.upLoad2Server(infile);
                return msg;
            }
        }
        new UploadVideo().execute();
    }

    public void WriteToFile(String str) {
        Context context = this;

//        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
//            outputStreamWriter.write(str);
//            Toast.makeText(this,str,Toast.LENGTH_LONG).show();
//
//            outputStreamWriter.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
        try {
            File root = Environment.getExternalStorageDirectory();

            if (root.canWrite()){
                File gpxfile = new File(root, "MyCityMyEnvironmentData.txt");
                if(gpxfile.exists())
                {
                    String line="\n\n"+str;

//                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("MyCityMyEnvironmentData.txt", Context.MODE_APPEND));
//                    outputStreamWriter.write(str);
//                    outputStreamWriter.close();

                    FileWriter gpxwriter = new FileWriter(gpxfile,true);
                    BufferedWriter out = new BufferedWriter(gpxwriter);
                    out.write(str);
                    out.close();

                    Toast.makeText(this,"appeding data",Toast.LENGTH_LONG).show();
                }
                else
                {

                    gpxfile.createNewFile();
                    FileWriter gpxwriter = new FileWriter(gpxfile);
                    BufferedWriter out = new BufferedWriter(gpxwriter);
                    out.write(str);
                    out.close();
                    Toast.makeText(this,"writing data",Toast.LENGTH_LONG).show();

                }

            }
        } catch (IOException e) {
            Log.e("exception:", "Could not write file " + e.getMessage());
        }
    }
}
