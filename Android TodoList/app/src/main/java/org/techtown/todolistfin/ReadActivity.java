package org.techtown.todolistfin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class ReadActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Button back_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();
        back_bt = (Button)findViewById(R.id.backButton);
        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"GO MENU!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                setResult(1,intent);
                finish();
            }
        });
    }

    private void getData(){
        class GetData extends AsyncTask<Void,Void, List<MyDataList>>{

            @Override
            protected List<MyDataList> doInBackground(Void... voids) {
                List<MyDataList>myDataLists=MainActivity.myDatabase.myDao().getMyData();
                return myDataLists;
            }

            @Override
            protected void onPostExecute(List<MyDataList>myDataList){
                MyAdapter adapter = new MyAdapter(myDataList);
                recyclerView.setAdapter(adapter);
                super.onPostExecute(myDataList);
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }
}