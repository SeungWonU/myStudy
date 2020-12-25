package org.techtown.todolistfin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static MyDatabase myDatabase;
    private Button read_bt,add_bt,update_bt,delete_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = Room.databaseBuilder(getApplicationContext(),MyDatabase.class,"info").allowMainThreadQueries().build();

        read_bt = (Button) findViewById(R.id.ReadButton);
        add_bt = (Button) findViewById(R.id.AddButton);
        update_bt = (Button)findViewById(R.id.UpdateButton);
        delete_bt = (Button)findViewById(R.id.DeleteButton);

        read_bt.setOnClickListener(this);
        add_bt.setOnClickListener(this);
        update_bt.setOnClickListener(this);
        delete_bt.setOnClickListener(this);
    }
    public void onClick(View v){
        int id = v.getId();

        switch (id){
            case R.id.ReadButton:{
                Intent intent1 = new Intent(MainActivity.this,ReadActivity.class);
                startActivityForResult(intent1,1);
                break;
            }
            case R.id.AddButton:{
                Intent intent2 = new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(intent2,2);
                break;

            }
            case R.id.UpdateButton:{
                Intent intent3 = new Intent(MainActivity.this,UpdateActivity.class);
                startActivityForResult(intent3,3);
                break;

            }
            case R.id.DeleteButton:{
                Intent intent4 = new Intent(MainActivity.this,DeleteActivity.class);
                startActivityForResult(intent4,4);
                break;
            }
        }
    }
}