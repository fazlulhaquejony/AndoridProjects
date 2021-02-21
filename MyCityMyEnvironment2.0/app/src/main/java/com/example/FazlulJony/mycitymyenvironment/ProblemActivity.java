package com.example.fuhad.mycitymyenvironment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProblemActivity extends AppCompatActivity {

    private static int probNum = 8;
    ListView listView ;
    String[] values;
    String problems="";
    String numprob="";
    Button button;
    SearchView searchView;
    String image,video,fileName;
    String Longi;
    String Latit;
    boolean isImage =false;
    boolean isVideo =false;
    boolean gallery =false;
    boolean nothing =false;

    List<String> li;
    /*LoggedUser lu;
    Bundle extras;*/

    ArrayList<String> list = new ArrayList<String>();
    ArrayList<Integer> num = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
//Toast.makeText(ProblemActivity.this,"new activity",Toast.LENGTH_SHORT).show();
        listView = (ListView) findViewById(R.id.list);
        values = new String[] { "Garbage","Water Pollution","Air Pollution","Soil Pollution",
                "Noise Pollution",
                "Thermal Pollution",
                "Light Pollution",
                "Visual Pollution" };
        li = Arrays.asList(values);
        button=(Button)findViewById(R.id.problemButton);
        searchView=(SearchView)findViewById(R.id.searchBar);

        /*extras = getIntent().getExtras();
        if (extras != null) {
            lu = (LoggedUser)getIntent().getSerializableExtra("userData"); //Obtaining data
            Toast.makeText(this,lu.getUserName(),Toast.LENGTH_SHORT).show();
        }*/

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, R.id.checkedTextView, values);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // we want multiple clicks
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new CheckBoxClick());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<list.size();i++)
                {
                    if(!problems.isEmpty())
                    {
                        problems=problems+","+list.get(i);
                        numprob=numprob+","+num.get(i);
                    }
                    else
                    {
                        problems=list.get(i);
                        numprob=num.get(i)+"";
                    }

                }
                Toast.makeText(ProblemActivity.this,problems,Toast.LENGTH_LONG).show();
                if(!problems.isEmpty())
                {
                    image=getIntent().getStringExtra("image");
                    Longi=getIntent().getStringExtra("longitude");
                    Latit=getIntent().getStringExtra("latitude");
                    gallery = getIntent().getBooleanExtra("gallery", false);
                    nothing = getIntent().getBooleanExtra("nothing", false);
                    isImage = getIntent().getBooleanExtra("isImage", false);
                    isVideo = getIntent().getBooleanExtra("isVideo", false);
                    video = getIntent().getStringExtra("video");
                    fileName = getIntent().getStringExtra("fileName");
                    Toast.makeText(ProblemActivity.this,Longi+"\n"+Latit+"\n"+"gallery="+gallery+"\nnothing="+nothing+"\nImage="+isImage+"\nVideo="+isVideo+"\n"+video+"\n"+fileName,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ProblemActivity.this,SubmissionActivity.class);
                    intent.putExtra("problems",problems);
                    intent.putExtra("probnum",numprob);
                    intent.putExtra("image", image);
                    intent.putExtra("latitude",Latit);
                    intent.putExtra("longitude",Longi);
                    intent.putExtra("gallery",gallery);
                    intent.putExtra("nothing",nothing);
                    intent.putExtra("isImage",isImage);
                    intent.putExtra("isVideo",isVideo);
                    intent.putExtra("video",video);
                    intent.putExtra("fileName",fileName);

                    /*if (extras != null){
                        intent.putExtra("userData",lu);
                    }*/
                    //Toast.makeText(ProblemActivity.this,image + gloc +address,Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    //finish();
                }
                else
                {
                    Toast.makeText(ProblemActivity.this,"Please select a Problem",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return false;
    }

    public class CheckBoxClick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub

            CheckedTextView ctv = (CheckedTextView)arg1.findViewById(R.id.checkedTextView);
            if(ctv.isChecked()){
                String check= (String) ctv.getText();
                //int x = (int) ctv.getId();

                if(list.contains(check))
                {
                    int i=list.indexOf(check);
                    list.remove(i);
                    /*int j = num.indexOf(x);
                    num.remove(j);*/
                    if(li.contains(check)){
                        num.remove(li.indexOf(check)+1);
                    }
                }
                ctv.setChecked(false);

                Toast.makeText(ProblemActivity.this, "now it is unchecked", Toast.LENGTH_SHORT).show();

            }else{
                ctv.setChecked(true);
                list.add((String) ctv.getText());

                if(li.contains(ctv.getText())){
                    num.add(li.indexOf(ctv.getText())+1);
                }
                //num.add(ctv.getId());
                Toast.makeText(ProblemActivity.this, "now it is checked", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
