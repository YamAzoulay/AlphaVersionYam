package com.example.alphaversionyam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.alphaversionyam.FBref.refUsers;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ArrayList<String> UsersList = new ArrayList<>();
    ArrayList<String> refUsersList = new ArrayList<>();

    String Users;
    EditText usersET;
    Intent t=new Intent();
    ListView lv;
    String usrTMP;
    ValueEventListener stuListener;
    AlertDialog.Builder adb;
    ArrayAdapter adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main2);
        usersET= findViewById(R.id.editText);
        lv=(ListView) findViewById(R.id.lv);
        lv.setOnItemClickListener(this);

        stuListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                UsersList.clear();
                refUsersList.clear();
                for (DataSnapshot data : ds.getChildren()) {
                    usrTMP = (String) data.getValue();
                    UsersList.add(usrTMP);
                }
                adp = new ArrayAdapter<>(Main2Activity.this,R.layout.support_simple_spinner_dropdown_item, UsersList);
                lv.setAdapter(adp);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        refUsers.addValueEventListener(stuListener);
    }

    public void Write(View view) {
        Users=usersET.getText().toString();
        refUsers.child(Users).setValue(Users);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        adb=new AlertDialog.Builder(this);
        adb.setMessage("Do you want to delete this text?");
        adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tmp= UsersList.get(position);
                refUsers.child(tmp).removeValue();
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String s= item.getTitle().toString();
        t=new Intent(this, MainActivity.class);
        if (s.equals("Authentication")){
            t=new Intent(this, MainActivity.class);
            startActivity(t);}

        if (s.equals("Real Time Database")){
            t=new Intent(this, Main2Activity.class);
            startActivity(t);
        }
        if (s.equals("maps")){
            t=new Intent(this, Main3Activity.class);
            startActivity(t);
        }
        return super.onOptionsItemSelected(item);
    }
}
