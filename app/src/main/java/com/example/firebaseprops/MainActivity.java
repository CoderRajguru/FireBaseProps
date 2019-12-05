package com.example.firebaseprops;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private FirebaseRemoteConfig firebaseRemoteConfig;
    public Button button1,button2,button3, button4,button5,button6,button8;
    public TextView textView,ad_nw_1,ad_nw_2,ad_nw_3,ad_nw_4;

    public String Ad_network_Position_1,Ad_network_Position_2,Ad_network_Position_3,Ad_network_Position_4,ad_network_filled="";
    public ArrayList<String> adNetworks = new ArrayList<String>();
    public ArrayList<TextView> adnw = new ArrayList<TextView>();
    Handler handler = new Handler();
    int i;



    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FirebaseApp.initializeApp(this);


        textView= findViewById(R.id.text);
        ad_nw_1= findViewById(R.id.textView6);
        ad_nw_2= findViewById(R.id.textView7);
        ad_nw_3= findViewById(R.id.textView8);
        ad_nw_4= findViewById(R.id.textView9);

        adnw.add(ad_nw_1);
        adnw.add(ad_nw_2);
        adnw.add(ad_nw_3);
        adnw.add(ad_nw_4);



//        status=findViewById(R.id.textView3);
//
//        status.setText(" ");


        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        //button8 = findViewById(R.id.button8);




        // Fetch singleton FirebaseRemoteConfig object
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();


        firebaseRemoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build());


        Map<String,Object> map =new HashMap<>();
        map.put("Ad_network_Position_1","House Ads");
        map.put("Ad_network_Position_2","3rd party");
        map.put("Ad_network_Position_3","AN");
        map.put("Ad_network_Position_4","DFP");


        firebaseRemoteConfig.setDefaults(map);

        button1.setText(Ad_network_Position_1);
        button2.setText(Ad_network_Position_2);
        button3.setText(Ad_network_Position_3);
        button4.setText(Ad_network_Position_4);



        firebaseRemoteConfig.fetch(0).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Activated", Toast.LENGTH_SHORT).show();

                            /*
                            Activiting fetched parameters. The new parameters will now be available to your app
                             */
                    firebaseRemoteConfig.activateFetched();


                    Ad_network_Position_1= firebaseRemoteConfig.getString("Ad_network_Position_1");

                    adNetworks.add(Ad_network_Position_1);

                    Ad_network_Position_2= firebaseRemoteConfig.getString("Ad_network_Position_2");

                    adNetworks.add(Ad_network_Position_2);

                    Ad_network_Position_3= firebaseRemoteConfig.getString("Ad_network_Position_3");

                    adNetworks.add(Ad_network_Position_3);

                    Ad_network_Position_4= firebaseRemoteConfig.getString("Ad_network_Position_4");

                    adNetworks.add(Ad_network_Position_4);


                    Log.d("Ad_call_position", firebaseRemoteConfig.getString("Ad_network_Position_1"));
                    Log.d("Ad_call_position", firebaseRemoteConfig.getString("Ad_network_Position_2"));
                    Log.d("Ad_call_position", firebaseRemoteConfig.getString("Ad_network_Position_3"));
                    Log.d("Ad_call_position", firebaseRemoteConfig.getString("Ad_network_Position_4"));

                    button1.setText(Ad_network_Position_1);
                    button2.setText(Ad_network_Position_2);
                    button3.setText(Ad_network_Position_3);
                    button4.setText(Ad_network_Position_4);


                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("status","here");



                for(i =0; i<adNetworks.size()-1; i++){

                    //Request each network here to find out which network is getting the ad request filled : I am using random prototype here

                    int random = r.nextInt(10 - 2) + 1;
                    if (random % 2 == 0){
//                        Log.d("Ad_filled_network",""+ adNetworks.get(i));
                        ad_network_filled=adNetworks.get(i);

                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                adnw.get(i).setTextColor(Color.BLUE);
                                adnw.get(i).setText("Ad filled ");

                            }
                        }, 2000);


                        break;
                    }
                    else
                    {
                        adnw.get(i).setTextColor(Color.RED);
                        adnw.get(i).setText("not filled XXX");
                    }

                }

                if(ad_network_filled.equals("")){

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            adnw.get(3).setTextColor(Color.GREEN);
                            adnw.get(3).setText("House Ads shown ");

                        }
                    }, 2000);

                }

            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ad_nw_1.setText("");
                ad_nw_2.setText("");
                ad_nw_3.setText("");
                ad_nw_4.setText("");
                ad_network_filled="";
            }
        });


    }





}
