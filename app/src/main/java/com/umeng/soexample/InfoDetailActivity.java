package com.umeng.soexample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

public class InfoDetailActivity extends AppCompatActivity {
    private TextView result;
    private SHARE_MEDIA share_media;
    private UMAuthListener authListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infodetail);
        share_media = (SHARE_MEDIA) getIntent().getSerializableExtra("platform");
        setTitle("获取用户信息");
        result = (TextView) findViewById(R.id.result);
        result.setMovementMethod(new ScrollingMovementMethod()); //导致gc lazy

        authListener = new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {

            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                String temp = "";
                for (String key : data.keySet()) {
                    temp = temp + key + " : " + data.get(key) + "\n";
                }
                result.setText(temp);
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                result.setText("错误: " + t.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {

            }
        };
        findViewById(R.id.getbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UMShareAPI.get(InfoDetailActivity.this).getPlatformInfo(InfoDetailActivity.this, share_media, authListener);
            }
        });
        findViewById(R.id.umeng_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("结果：");
            }
        });
        findViewById(R.id.umeng_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(result.getText());
                Toast.makeText(InfoDetailActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTitle(String title) {
        ((TextView) findViewById(R.id.umeng_title)).setText(title);
        findViewById(R.id.umeng_back).setVisibility(View.VISIBLE);
        findViewById(R.id.umeng_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
