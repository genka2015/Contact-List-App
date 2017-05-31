package com.example.android.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    TextView out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent nintent = getIntent();
        ArrayList<Contact> list = (ArrayList<Contact>) nintent.getSerializableExtra("data");

        out = (TextView)findViewById(R.id.output);
        setResult(list);
    }

    private void setResult(ArrayList<Contact> mylist){

        String s = "";
        for(int i=0; i<mylist.size();i++){
            s += mylist.get(i).getFirstName() + " " + mylist.get(i).getLastName() + "\n";
            s += mylist.get(i).getPath() + "\n";
        }
        out.setText(s);
    }
}
