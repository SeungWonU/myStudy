package org.techtown.todolistfin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    private EditText et_Id2,et_Type2,et_Todo2;
    private Button update_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        et_Id2=(EditText)findViewById(R.id.et_id2);
        et_Type2=(EditText)findViewById(R.id.et_type2);
        et_Todo2=(EditText)findViewById(R.id.et_todo2);

        update_bt=(Button)findViewById(R.id.Update_bt);

        update_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id =Integer.parseInt(et_Id2.getText().toString());
                String type = et_Type2.getText().toString();
                String todo = et_Todo2.getText().toString();

                MyDataList myDataList = new MyDataList();
                myDataList.setId(id);
                myDataList.setType(type);
                myDataList.setTodo(todo);
                MainActivity.myDatabase.myDao().update(myDataList);
                Toast.makeText(getApplicationContext(),"Data Update !",Toast.LENGTH_LONG).show();

                et_Id2.setText("");
                et_Type2.setText("");
                et_Todo2.setText("");

                Intent intent = new Intent();
                setResult(3,intent);
                finish();
            }
        });
    }
}