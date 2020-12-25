package org.techtown.todolistfin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {
    private EditText et_Id,et_Type,et_Todo;
    private Button bt_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        et_Id = (EditText)findViewById(R.id.edit_id);
        et_Type=(EditText)findViewById(R.id.edit_type);
        et_Todo=(EditText)findViewById(R.id.edit_todo);

        bt_add=(Button)findViewById(R.id.Add_bt);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(et_Id.getText().toString());
                String type = et_Type.getText().toString();
                String todo = et_Todo.getText().toString();

                MyDataList myDataList = new MyDataList();
                //myDataList.getId(); auto할때 필요??
                myDataList.setId(id);
                myDataList.setType(type);
                myDataList.setTodo(todo);

                MainActivity.myDatabase.myDao().addData(myDataList);
                Toast.makeText(getApplicationContext(),"Data Saved!",Toast.LENGTH_LONG).show();
                et_Id.setText("");
                et_Type.setText("");
                et_Todo.setText("");
                Intent intent = new Intent();
                setResult(2,intent);
                finish();
            }
        });
    }
}