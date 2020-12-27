package com.example.cameraexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//implements OnClick 만들어줌
//로딩의 문제 앱 갤러리 ->glide
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView imageView;
    Button btnCamera;
    final static int TAKE_PICTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.iv_takepicture);
        btnCamera = findViewById(R.id.btn_takepicture);

        btnCamera.setOnClickListener(this);


        //카메라 권한 요청(마시멜로 이후부터)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    ==checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            }
            else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1 );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED){

        }
    }





    @Override
    public void onClick(View v) {
        /*
        * 장점 : setOnClickListener만 쓰면 됨
        * 장점 :  new OnClickListner 안해줘도 됨
        */
        //코드가 간결해짐
        switch (v.getId()) {
            //xml id값
            case R.id.btn_takepicture:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,TAKE_PICTURE);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == RESULT_OK && data.hasExtra("data")) {
                    /*
                    * 데이터 불러오는 동작
                    * Bitmap 변환(용량측면 효율적)
                    */
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    if(bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
                break;
        }
    }
}