package com.example.helloandroid;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ImageView imgView; // 이미지뷰
    String imageUrl = "http://kiokahn.synology.me:30000/uploads/-/system/appearance/logo/1/Gazzi_Labs_CI_type_B_-_big_logo.png"; // 접속할 URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = findViewById(R.id.imgView);
    }

    // 이미지를 불러오기 위한 함수
    public void onClickForLoad(View v) {
        // Picasso를 사용하여 이미지 로딩
        Picasso.get()
                .load(imageUrl)
                .into(imgView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        // 이미지 로드 성공 시 동작할 코드를 여기에 추가
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void onClickForSave(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10 (Q) or higher, use the new way of saving images
            saveImageToGalleryLegacy();
        } else {
            // Before Android 10, use the old way of saving images
            saveImageToGalleryLegacy();
        }
    }

    private void saveImageToGalleryLegacy() {
        imgView.setDrawingCacheEnabled(true);
        Bitmap bitmap = imgView.getDrawingCache();

        String savedImagePath = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "MyImage",
                "Image saved from HelloAndroid app"
        );

        imgView.setDrawingCacheEnabled(false); // 캐시 비활성화

        // 이미지 저장 결과에 따라 Toast 메시지를 표시
        if (savedImagePath != null) {
            Toast.makeText(this, "이미지가 앨범에 저장되었습니다.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "이미지를 저장하는 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }
    }
}
