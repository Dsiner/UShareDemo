package com.umeng.soexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.d.lib.xrv.LRecyclerView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.soexample.adapter.InfoAdapter;
import com.umeng.soexample.model.PlatformBean;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle("用户信息");
        LRecyclerView lrvList = (LRecyclerView) findViewById(R.id.lrv_list);
        lrvList.setAdapter(new InfoAdapter(this, getDatas(), R.layout.adapter_item));
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
}
