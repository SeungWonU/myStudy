package kr.teamcadi.glideexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView ivGlide = (ImageView) findViewById(R.id.iv_glide);

        /*
        ** glide 는 3가지 메소드를 필수적 사용
        ** 1. with : Context -> this
        ** 2. load : 이미지를 받아올 경로  외부에서 호출해서 받아옴(인터넷 권한 활성화)
        ** 3. into : 받아와서 어디에 넣을 건지
         */

        //sns에 있는 사진을 가져오려면 load안에 이미지 주소 복사를 한 후 bit.ly에 들어와서 링크 길이를 줄인다!!! -> load메소드 안에 넣음
        Glide.with(this).load("http://goo.gl/gEgYUd").into(ivGlide);
    }
}