package com.example.fuhad.mycitymyenvironment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    boolean islogin;
    SharedPreferences dataSave;
    /*LoggedUser lu;
    Bundle extras;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* extras = getIntent().getExtras();
        if (extras != null) {
            lu = (LoggedUser)getIntent().getSerializableExtra("userData"); //Obtaining data
            Toast.makeText(this,lu.getUserName(),Toast.LENGTH_SHORT).show();
        }*/
        dataSave = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        Toast.makeText(MainActivity.this,dataSave.getBoolean("LoggedIn",false)+" "+
                dataSave.getString("username","aa")+" "+
                dataSave.getString("email","bb"),Toast.LENGTH_LONG).show();
        /*islogin = dataSave.getBoolean("LoggedIn",false);*/


        final AlertDialog.Builder exitbuilder = new AlertDialog.Builder(this);

        final AlertDialog.Builder choose = new AlertDialog.Builder(this);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose the way to upload").setItems(R.array.choice, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    choose.setMessage("Please Choose").setPositiveButton("Take Picture", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), Camera.class);
                            intent.putExtra("camera",true);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            //finish();
                        }
                    }).setNegativeButton("Shoot A Video", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), Camera.class);
                            intent.putExtra("camera",false);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            //finish();
                        }
                    }).show();
                    //Toast.makeText(getApplicationContext(), "camera open", Toast.LENGTH_SHORT).show();
                }
                if (which == 1) {
                    choose.setMessage("Please Choose").setPositiveButton("Choose Picture", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), gallery.class);
                            intent.putExtra("camera",false);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            //finish();
                        }
                    }).setNegativeButton("Choose A Video", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), gallery.class);
                            intent.putExtra("camera",true);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            //finish();
                        }
                    }).show();
                    //Toast.makeText(getApplicationContext(), "gellary open", Toast.LENGTH_SHORT).show();
                }
                if (which == 2) {
                    Intent intent = new Intent(getApplicationContext(),ProblemActivity.class);
                    intent.putExtra("nothing",true);
                    intent.putExtra("gloc", "Not avaliable");
                    //intent.putExtra("address","NULL");
                    intent.putExtra("isImage",false);
                    intent.putExtra("isVideo",false);
                    intent.putExtra("image","No Image");
                    intent.putExtra("video","No Video");
                    intent.putExtra("fileName","No file");
                    /*if (extras != null){
                        intent.putExtra("userData",lu);
                    }*/
                    startActivity(intent);
                    //finish();
                    //Toast.makeText(getApplicationContext(), "nothing open", Toast.LENGTH_SHORT).show();
                }
                // The 'which' argument contains the index position
                // of the selected item
            }
        });
        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.loginNav) {


                    SharedPreferences.Editor editor = dataSave.edit();
                    editor.putString("username","no user");
                    editor.putString("email","no email");
                    editor.putBoolean("LoggedIn",false);
                    editor.commit();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);

                    startActivity(i);
                    finish();
                }

                if (menuItem.getItemId() == R.id.nav_item_about) {
                    Intent i = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(i);
                    //finish();
                }

                if(menuItem.getItemId() == R.id.nav_item_exit){
                    exitbuilder.setMessage("Do you want to exit?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    finish();
                                    System.exit(0);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    // Create the AlertDialog object and return it
                    AlertDialog xd =  exitbuilder.create();
                    xd.show();
                }
                if (menuItem.getItemId() == R.id.report) {

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                /*if (menuItem.getItemId() == R.id.nav_item_inbox) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                }*/

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();


    }

    /*public AlertDialog.Builder dialogs (){

        return builder;
    }*/

}
