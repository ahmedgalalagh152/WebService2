package com.example.webservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
     Button get,show;
     TextView textView;
     String link="https://jsonplaceholder.typicode.com/users";
     URL url;
     HttpURLConnection urlConnection;
     InputStream inputStream;
     StringBuffer stringBuffer2=new StringBuffer();
     String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        get=findViewById(R.id.btn);
        show=findViewById(R.id.btn2);
        textView=findViewById(R.id.txt);

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            url=new URL(link);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try {
                            urlConnection= (HttpURLConnection) url.openConnection();
                            urlConnection.setReadTimeout(15000);
                            urlConnection.setConnectTimeout(15000);
                            urlConnection.setRequestMethod("GET");
                            inputStream=urlConnection.getInputStream();
                            int c;
                            StringBuffer stringBuffer=new StringBuffer();
                            int responceCode=urlConnection.getResponseCode();
                            if(responceCode==HttpURLConnection.HTTP_OK){
                                while ((c=inputStream.read())!=-1){

                                   stringBuffer.append((char) c);

                                }
                            }
                            result=stringBuffer.toString();

                            JSONArray array=new JSONArray(result);

                            for(int i=0;i<array.length();i++){
                                JSONObject object=array.getJSONObject(i);
                                int id=object.getInt("id");
                                String name=object.getString("name");
                                String username=object.getString("username");
                                stringBuffer2.append(id+" "+name+" "+username+"\n");
                            }
                            inputStream.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            urlConnection.disconnect();
                        }

                    }
                }).start();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(stringBuffer2);
            }
        });
    }
}
