package com.example.fuhad.mycitymyenvironment;

import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class CitiesFragment extends Fragment {

    private PieChart mChart;
    private float[] yDataDhaka = { (float)81.82, (float)64.29,(float) 78.57,(float) 78.57, (float)74.11,(float)82.41,(float)79.31,(float)82.41,(float)75.69, (float) 76.89};
    private float[] yDataChittagong ={(float) 79.17, (float) 46.88,75, (float) 68.75,50, (float) 78.12,70, (float) 43.75, (float) 75.69, (float) 86.89};
    private String[] xData = { "Air Pollution", "Drinking Water Pollution and Inaccessibility", "Dissatisfaction with Garbage Disposal", "Dirty and Untidy", "Noise and Light Pollution","Water Pollution","Dissatisfaction to Spend Time in the City","Dissatisfaction with Green and Parks in the City","Deforestation","Indestrial pollution" };
    Button button1;
    TextView text1;
    MenuItem camera;
    ImageView imageview;
    TextView title;
    TextView description;
    AutoCompleteTextView autoText;
    ImageButton imageButton;
    Button cause;
    Button counterAction;
    Button affectedAreas;
    int counter=0;
    String city="";
    String[] cities={"Dhaka","Faridpur","Chittagong","Comilla"};
    int b1=0;
    String text="";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_cities_fragment, container, false);

        mChart = (PieChart) v.findViewById(R.id.pieChart1);
        mChart.setDescription("");
        //imageview=(ImageView)v.findViewById(R.id.descriptionImage);
        title=(TextView)v.findViewById(R.id.descriptionTitle);
        //description=(TextView)v.findViewById(R.id.problemDescription);
        autoText=(AutoCompleteTextView)v.findViewById(R.id.autoCompleteTextView);
        imageButton=(ImageButton)v.findViewById(R.id.citySearch);
        cause =(Button)v.findViewById(R.id.Cause);
        counterAction =(Button)v.findViewById(R.id.CounterAction);
        affectedAreas=(Button)v.findViewById(R.id.affectedAreas);



        ArrayAdapter adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                cities);
        autoText.setAdapter(adapter);
        autoText.setThreshold(1);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cityName = String.valueOf(autoText.getText());
                if (cityName.contains("Dhaka")) {

                    city = "Dhaka";

                    mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

                        @Override
                        public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                            // display msg when value selected
                            if (e == null)
                                return;
                            Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
                            a.reset();

                            //Toast.makeText(getActivity(), xData[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
                            title.setText(xData[e.getXIndex()] + "=" + e.getVal() + "%");
                            title.clearAnimation();
                            title.setAnimation(a);

                            String string = (String) title.getText();
                            //description.setText("yo");
                            if (string.contains("Air Pollution")) {
                                //description.setText(R.string.AirPolution);
                                title.setBackgroundColor(Color.rgb(204, 255, 136));
                            } else if (string.contains("Drinking Water Pollution and Inaccessibility")) {
                                title.setBackgroundColor(Color.rgb(255, 254, 136));
                            } else if (string.contains("Dissatisfaction with Garbage Disposal")) {
                                title.setBackgroundColor(Color.rgb(255, 220, 138));
                            } else if (string.contains("Dirty and Untidy")) {
                                title.setBackgroundColor(Color.rgb(136, 237, 255));
                            } else if (string.contains("Noise and Light Pollution")) {

                                title.setBackgroundColor(Color.rgb(255, 137, 153));
                            } else if (string.contains("Water Pollution")) {
                                title.setBackgroundColor(Color.rgb(121, 84, 136));
                            } else if (string.contains("Dissatisfaction to Spend Time in the City")) {
                                title.setBackgroundColor(Color.rgb(254, 153, 0));
                            } else if (string.contains("Dissatisfaction with Green and Parks in the City")) {
                                title.setBackgroundColor(Color.rgb(254, 254, 120));
                            } else if (string.contains("Deforestation")) {
                                title.setBackgroundColor(Color.rgb(103, 170, 135));
                            } else if (string.contains("Indestrial pollution")) {
                                title.setBackgroundColor(Color.rgb(51, 204, 220));
                            }


                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });

                    // add data
                    addData(xData, yDataDhaka);


                } else if (cityName.contains("Chittagong")) {
                    city = "Chittagong";
                    mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

                        @Override
                        public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                            // display msg when value selected
                            if (e == null)
                                return;
                            Animation a = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
                            a.reset();

                            //Toast.makeText(getActivity(), xData[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
                            title.setText(xData[e.getXIndex()] + "=" + e.getVal() + "%");
                            title.clearAnimation();
                            title.setAnimation(a);

                            String string = (String) title.getText();
                            //description.setText("yo");
                            if (string.contains("Air Pollution")) {
                                description.setText(R.string.AirPolution);
                                title.setBackgroundColor(Color.rgb(204, 255, 136));
                            } else if (string.contains("Drinking Water Pollution and Inaccessibility")) {
                                title.setBackgroundColor(Color.rgb(255, 254, 136));
                            } else if (string.contains("Dissatisfaction with Garbage Disposal")) {
                                title.setBackgroundColor(Color.rgb(255, 220, 138));
                            } else if (string.contains("Dirty and Untidy")) {
                                title.setBackgroundColor(Color.rgb(136, 237, 255));
                            } else if (string.contains("Noise and Light Pollution")) {

                                title.setBackgroundColor(Color.rgb(255, 137, 153));
                            } else if (string.contains("Water Pollution")) {
                                title.setBackgroundColor(Color.rgb(121, 84, 136));
                            } else if (string.contains("Dissatisfaction to Spend Time in the City")) {
                                title.setBackgroundColor(Color.rgb(254, 153, 0));
                            } else if (string.contains("Dissatisfaction with Green and Parks in the City")) {
                                title.setBackgroundColor(Color.rgb(254, 254, 120));
                            } else if (string.contains("Deforestation")) {
                                title.setBackgroundColor(Color.rgb(103, 170, 135));
                            } else if (string.contains("Indestrial pollution")) {
                                title.setBackgroundColor(Color.rgb(51, 204, 220));
                            }


                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });

                    // add data
                    addData(xData, yDataChittagong);


                }

            }

        });



        cause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setInverseBackgroundForced(true);
                builder1.setCancelable(true);



                builder1.setPositiveButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });



                if(city=="Dhaka")
                {
                    String string = (String) title.getText();



                    if (string.contains("Air Pollution")) {
                        //description.setText(R.string.AirPolution);
                        //title.setBackgroundColor(Color.rgb(204, 255, 136));


                        //Toast.makeText(getActivity(),string,Toast.LENGTH_LONG).show();

                        builder1.setMessage(R.string.AirPollution_Cause_Dhaka);


                    } else if (string.contains("Drinking Water Pollution and Inaccessibility")) {
                        builder1.setMessage(R.string.DrinkingWaterPollutionandInaccessibility_Cause_Dhaka);
                    } else if (string.contains("Dissatisfaction with Garbage Disposal")) {
                        builder1.setMessage(R.string.DissatisfactionToSpendTimeInTheCity_Cause_Dhaka);
                    } else if (string.contains("Dirty and Untidy")) {
                        builder1.setMessage(R.string.DirtyandUntidy_Cause_Dhaka);}
                    else if (string.contains("Noise and Light Pollution")) {

                        builder1.setMessage(R.string.NoisePollution_Cause_Dhaka);
                    } else if (string.contains("Water Pollution")) {
                        builder1.setMessage(R.string.WaterPollution_Cause_Dhaka);
                    } else if (string.contains("Dissatisfaction to Spend Time in the City")) {
                        builder1.setMessage(R.string.DissatisfactionToSpendTimeInTheCity_Cause_Dhaka);
                    } else if (string.contains("Dissatisfaction with Green and Parks in the City")) {
                        builder1.setMessage(R.string.DissatisfactionWithGreenAndParksInTheCity_Cause_Dhaka);
                    } else if (string.contains("Deforestation")) {
                        builder1.setMessage(R.string.Deforestation_Cause_Dhaka);
                    } else if (string.contains("Indestrial pollution")) {
                        builder1.setMessage(R.string.IndustrialPollution_Cause_Dhaka);
                    }
                }

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });
        affectedAreas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                if(city=="Dhaka")
                {
                    String string = (String) title.getText();


                    if (string.contains("Air Pollution")) {
                        //description.setText(R.string.AirPolution);
                        //title.setBackgroundColor(Color.rgb(204, 255, 136));


                        //Toast.makeText(getActivity(),string,Toast.LENGTH_LONG).show();

                        builder1.setMessage(R.string.AirPollution_Area_Dhaka);



                    } else if (string.contains("Drinking Water Pollution and Inaccessibility")) {
                        builder1.setMessage(R.string.DrinkingWaterPollutionandInaccessibility_Area_Dhaka);
                    } else if (string.contains("Dissatisfaction with Garbage Disposal")) {
                        builder1.setMessage(R.string.DissatisfactionToSpendTimeInTheCity_Area_Dhaka);
                    } else if (string.contains("Dirty and Untidy")) {
                        builder1.setMessage(R.string.DirtyandUntidy_Area_Dhaka);}
                    else if (string.contains("Noise and Light Pollution")) {

                        builder1.setMessage(R.string.NoisePollution_Area_Dhaka);
                    } else if (string.contains("Water Pollution")) {
                        builder1.setMessage(R.string.WaterPollution_Area_Dhaka);
                    } else if (string.contains("Dissatisfaction to Spend Time in the City")) {
                        builder1.setMessage(R.string.DissatisfactionToSpendTimeInTheCity_Area_Dhaka);
                    } else if (string.contains("Dissatisfaction with Green and Parks in the City")) {
                        builder1.setMessage(R.string.DissatisfactionWithGreenAndParksInTheCity_Area_Dhaka);
                    } else if (string.contains("Deforestation")) {
                        builder1.setMessage(R.string.Deforestation_Area_Dhaka);
                    } else if (string.contains("Indestrial pollution")) {
                        builder1.setMessage(R.string.IndustrialPollution_Area_Dhaka);
                    }

                }
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        counterAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                if(city=="Dhaka")
                {
                    String string = (String) title.getText();




                    if (string.contains("Air Pollution")) {
                        //description.setText(R.string.AirPolution);
                        //title.setBackgroundColor(Color.rgb(204, 255, 136));


                        //Toast.makeText(getActivity(),string,Toast.LENGTH_LONG).show();

                        builder1.setMessage(R.string.AirPollution_Counter_Dhaka);



                    } else if (string.contains("Drinking Water Pollution and Inaccessibility")) {
                        builder1.setMessage(R.string.DrinkingWaterPollutionandInaccessibility_Counter_Dhaka);
                    } else if (string.contains("Dissatisfaction with Garbage Disposal")) {
                        builder1.setMessage(R.string.DissatisfactionToSpendTimeInTheCity_Counter_Dhaka);
                    } else if (string.contains("Dirty and Untidy")) {
                        builder1.setMessage(R.string.DirtyandUntidy_Counter_Dhaka);}
                    else if (string.contains("Noise and Light Pollution")) {

                        builder1.setMessage(R.string.NoisePollution_Counter_Dhaka);
                    } else if (string.contains("Water Pollution")) {
                        builder1.setMessage(R.string.WaterPollution_Counter_Dhaka);
                    } else if (string.contains("Dissatisfaction to Spend Time in the City")) {
                        builder1.setMessage(R.string.DissatisfactionToSpendTimeInTheCity_Counter_Dhaka);
                    } else if (string.contains("Dissatisfaction with Green and Parks in the City")) {
                        builder1.setMessage(R.string.DissatisfactionWithGreenAndParksInTheCity_Counter_Dhaka);
                    } else if (string.contains("Deforestation")) {
                        builder1.setMessage(R.string.Deforestation_Counter_Dhaka);
                    } else if (string.contains("Indestrial pollution")) {
                        builder1.setMessage(R.string.IndustrialPollution_Counter_Dhaka);
                    }

                }
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });



        //Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");

        //mChart.setCenterTextTypeface(tf);
        mChart.setCenterText("POLLUTION");
        //mChart.setCenterTextSize(10f);
        //mChart.setCenterTextTypeface(tf);

        // radius of the center hole in percent of maximum radius
        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(45f);
        mChart.setTransparentCircleRadius(50f);


        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setEnabled(false);


        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);


        // set a chart value selected listener
        // mChart.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));

        //Button
//        button1=(Button)v.findViewById(R.id.button1);
        //text
        //text1=(TextView)v.findViewById(R.id.textView1);
        //text1.setVisibility(View.GONE);







        return v;

    }

    private void addData(String[] xData,float[] yData) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<String> demo = new ArrayList<>();

        for (int i = 0; i < xData.length; i++)
        {
            xVals.add(xData[i]);
            demo.add("");
        }

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(5);
        dataSet.setSelectionShift(5);




        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(demo,dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }
}
