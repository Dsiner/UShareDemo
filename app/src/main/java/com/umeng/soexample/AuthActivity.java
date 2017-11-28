package com.umeng.soexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.d.lib.xrv.LRecyclerView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.soexample.adapter.AuthAdapter;
import com.umeng.soexample.model.PlatformBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {
    private AuthAdapter adapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle("授权");
        LRecyclerView lrvList = (LRecyclerView) findViewById(R.id.lrv_list);

        adapter = new AuthAdapter(this, getDatas(), R.layout.adapter_item);
        lrvList.setAdapter(adapter);

        dialog = new ProgressDialog(this);
        UMShareAPI.get(this).fetchAuthResultWithBundle(this, savedInstanceState, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                SocializeUtils.safeShowDialog(dialog);
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                Toast.makeText(getApplicationContext(), "onRestoreInstanceState Authorize succeed", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                SocializeUtils.safeCloseDialog(dialog);
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                Toast.makeText(getApplicationContext(), "onRestoreInstanceState Authorize onError", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                SocializeUtils.safeCloseDialog(dialog);
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                Toast.makeText(getApplicationContext(), "onRestoreInstanceState Authorize onCancel", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                SocializeUtils.safeCloseDialog(dialog);
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

    public List<PlatformBean> getDatas() {
        List<PlatformBean> platforms = new ArrayList<>();
        platforms.add(new PlatformBean(R.drawable.umeng_socialize_qq, "QQ", SHARE_MEDIA.QQ));
        platforms.add(new PlatformBean(R.drawable.umeng_socialize_wechat, "微信", SHARE_MEDIA.WEIXIN));
        platforms.add(new PlatformBean(R.drawable.umeng_socialize_sina, "新浪微博", SHARE_MEDIA.SINA));
        return platforms;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
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
