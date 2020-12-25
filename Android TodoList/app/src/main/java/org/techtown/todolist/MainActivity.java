package org.techtown.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Todo> arrayList;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview_main_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        final AppDatabase db =Room.databaseBuilder(this,AppDatabase.class,"todo-db").allowMainThreadQueries().build();

        arrayList = new ArrayList<>();
        adapter = new MyAdapter(this,arrayList);
        recyclerView.setAdapter(adapter);

        db.todoDao().getAll().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                adapter.setmList(todos);
            }
        });
        //textViewTo.setText(db.todoDao().getAll().toString());

        Button buttonIn = (Button)findViewById(R.id.buttonIn);
        buttonIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_box,null,false);
                builder.setView(view1);

                final Button ButtonSubmit = (Button)view1.findViewById(R.id.button_dialog_submit);
                final EditText editStr = (EditText)view1.findViewById(R.id.editText_dialog);
                final EditText editNum = (EditText)view1.findViewById(R.id.editText_num);

                ButtonSubmit.setText("삽입");

                final AlertDialog dialog = builder.create();
                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String textStr = editStr.getText().toString();
                        String textNum = editNum.getText().toString();

                        Todo to = new Todo(textStr,textNum);

                        db.todoDao().insert(to);

                        arrayList.add(0,to);
                        adapter.notifyItemChanged(0);

                        dialog.dismiss();
                    }
                });
            dialog.show();
            }
        });

    }
}