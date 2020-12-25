package org.techtown.todolistfin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    private EditText et_Id3;
    private Button delete_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        et_Id3 = findViewById(R.id.et_id3);
        delete_bt = findViewById(R.id.Delete_bt);

        delete_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(et_Id3.getText().toString());
                MainActivity.myDatabase.myDao().deleteitem(id);
                Toast.makeText(getApplicationContext(), "Data Delete !", Toast.LENGTH_LONG).show();

                Intent intent = new Intent();
                setResult(4,intent);
                finish();
            }
        });
    }
}