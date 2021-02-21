package com.example.fuhad.mycitymyenvironment;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class NewsFragment extends Fragment {

    String link =  "http://kipailam.byethost10.com/learne/";
    WebView webView =null;
    TextView errorText;
    TextView re ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_news_fragment, null);


        final AlertDialog.Builder choose = new AlertDialog.Builder(v.getContext());
        final AlertDialog.Builder build = new AlertDialog.Builder(v.getContext());
        build.setTitle("Choose the way to upload").setItems(R.array.choice, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    choose.setMessage("Please Choose").setPositiveButton("Take Picture", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(v.getContext(), Camera.class);
                            intent.putExtra("camera", true);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }).setNegativeButton("Shoot A Video", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(v.getContext(), Camera.class);
                            intent.putExtra("camera", false);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }).show();
                    //Toast.makeText(getApplicationContext(), "camera open", Toast.LENGTH_SHORT).show();
                }
                if (which == 1) {
                    choose.setMessage("Please Choose").setPositiveButton("Choose Picture", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(v.getContext(), gallery.class);
                            intent.putExtra("camera", false);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }).setNegativeButton("Choose A Video", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(v.getContext(), gallery.class);
                            intent.putExtra("camera", true);
                            /*if (extras != null){
                                intent.putExtra("userData",lu);
                            }*/
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }).show();
                    //Toast.makeText(getApplicationContext(), "gellary open", Toast.LENGTH_SHORT).show();
                }
                if (which == 2) {
                    Intent intent = new Intent(v.getContext(), ProblemActivity.class);
                    intent.putExtra("nothing", true);
                    intent.putExtra("gloc", "Not avaliable");
                    //intent.putExtra("address","NULL");
                    intent.putExtra("isImage", false);
                    intent.putExtra("isVideo", false);
                    intent.putExtra("image", "No Image");
                    intent.putExtra("video", "No Video");
                    intent.putExtra("fileName", "No file");
                    /*if (extras != null){
                        intent.putExtra("userData",lu);
                    }*/
                    startActivity(intent);
                    getActivity().finish();
                    //Toast.makeText(getApplicationContext(), "nothing open", Toast.LENGTH_SHORT).show();
                }
                // The 'which' argument contains the index position
                // of the selected item
            }
        });

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                AlertDialog dialog = build.create();
                dialog.show();
            }
        });


        webView = (WebView) v.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        //webView.setWebChromeClient(new WebChromeClient());
        errorText = (TextView) v.findViewById(R.id.errorText);
        re = (TextView) v.findViewById(R.id.retryText);


        if (isNetworkAvailable()) {
            webView.loadUrl(link);
            webView.setWebViewClient(new webChecker());
            ProgressD();
            webView.setVisibility(View.VISIBLE);

        } else {
            // display error
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Error");
            builder.setMessage("No Internet Connection");
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (isNetworkAvailable()) {
                        // fetch data
                        webView.loadUrl(link);
                        webView.setWebViewClient(new webChecker());
                        ProgressD();
                        webView.setVisibility(View.VISIBLE);
                    } else {
                        webView.setVisibility(View.INVISIBLE);
                        errorText.setVisibility(View.VISIBLE);
                        re.setVisibility(View.VISIBLE);
                        re.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isNetworkAvailable()) {
                                    // fetch data
                                    webView.loadUrl(link);
                                    webView.setWebViewClient(new webChecker());
                                    ProgressD();
                                    webView.setVisibility(View.VISIBLE);
                                    errorText.setVisibility(View.INVISIBLE);
                                    re.setVisibility(View.INVISIBLE);

                                } else {
                                    Toast.makeText(v.getContext(),"No Internet connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
            builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    webView.setVisibility(View.INVISIBLE);
                    errorText.setVisibility(View.VISIBLE);
                    re.setVisibility(View.VISIBLE);

                    //NetworkInfo networkInfo1 = connMgr.getActiveNetworkInfo();
                    re.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isNetworkAvailable()) {
                                // fetch data
                                webView.loadUrl(link);
                                webView.setWebViewClient(new webChecker());
                                ProgressD();
                                webView.setVisibility(View.VISIBLE);
                                errorText.setVisibility(View.INVISIBLE);
                                re.setVisibility(View.INVISIBLE);

                            } else {
                                Toast.makeText(v.getContext(),"No Internet connection",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

        return v;
    }
    public void  ProgressD (){
        final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Checking Network", "Please Wait...", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Here you should write your time consuming task...
                    // Let the progress ring for 10 seconds...

                    Thread.sleep(6000);



                } catch (Exception e) {

                }
                ringProgressDialog.dismiss();

            }
        }).start();
    }

    public boolean  isNetworkAvailable(){
        final ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return networkInfo != null;
    }



    private class webChecker extends WebViewClient {
        /*NewsFragment nf = new NewsFragment();
        nf.*/
        //webView.getSettings().setJavaScriptEnabled(true);
        @Override
        public void onReceivedError(final WebView view, WebResourceRequest request, WebResourceError error) {
            view.setVisibility(View.INVISIBLE);
            // webView.setVisibility(View.INVISIBLE);
            errorText.setVisibility(View.VISIBLE);
            re.setVisibility(View.VISIBLE);
            final boolean[] z = {false};
            //NetworkInfo networkInfo1 = connMgr.getActiveNetworkInfo();
            re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        // fetch data
                        z[0] = true;
                        /*webView.loadUrl(link);
                        webView.setWebViewClient(new webChecker());*/
                        //view.setVisibility(View.INVISIBLE);
                        final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Checking Network", "Please Wait...", true);
                        ringProgressDialog.setCancelable(true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // Here you should write your time consuming task...
                                    // Let the progress ring for 10 seconds...

                                    Thread.sleep(6000);



                                } catch (Exception e) {

                                }
                                ringProgressDialog.dismiss();

                            }
                        }).start();

                        errorText.setVisibility(View.INVISIBLE);
                        re.setVisibility(View.INVISIBLE);
                        view.loadUrl(link);
                        view.setWebViewClient(new webChecker());
                        if(z[0])
                        {
                            view.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(v.getContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedError (final WebView viewz, int errorCode, String description, String failingUrl){
            viewz.setVisibility(View.INVISIBLE);
            // webView.setVisibility(View.INVISIBLE);
            errorText.setVisibility(View.VISIBLE);
            re.setVisibility(View.VISIBLE);
            final boolean[] x = {false};

            //NetworkInfo networkInfo1 = connMgr.getActiveNetworkInfo();
            re.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        x[0] = true;
                        // fetch data
                        /*webView.loadUrl(link);
                        webView.setWebViewClient(new webChecker());*/
                        viewz.setVisibility(View.INVISIBLE);
                        final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Checking Network", "Please Wait...", true);
                        ringProgressDialog.setCancelable(true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // Here you should write your time consuming task...
                                    // Let the progress ring for 10 seconds...

                                    viewz.loadUrl(link);
                                    viewz.setWebViewClient(new webChecker());
                                    Thread.sleep(6000);

                                    viewz.setVisibility(View.VISIBLE);

                                } catch (Exception e) {

                                }
                                ringProgressDialog.dismiss();

                            }
                        }).start();

                        errorText.setVisibility(View.INVISIBLE);
                        re.setVisibility(View.INVISIBLE);

                        /*viewz.loadUrl(link);
                        viewz.setWebViewClient(new webChecker());
                        if(x[0]) {
                            viewz.setVisibility(View.VISIBLE);
                        }*/
                    } else {
                        Toast.makeText(v.getContext(), "No Internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            super.onReceivedError(viewz, errorCode, description, failingUrl);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(Uri.parse(url).getHost().equals("kipailam.byethost10.com")){
                return false;
            }
            Intent intent  = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
}
