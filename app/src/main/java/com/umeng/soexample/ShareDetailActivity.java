package com.umeng.soexample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.d.lib.xrv.LRecyclerView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.soexample.adapter.ShareDetailAdapter;
import com.umeng.soexample.model.StyleUtil;

import java.util.ArrayList;
import java.util.List;

public class ShareDetailActivity extends AppCompatActivity {
    private ShareDetailAdapter adapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        SHARE_MEDIA share_media = (SHARE_MEDIA) getIntent().getSerializableExtra("platform");
        setTitle(share_media.toString() + "选择分享类型");
        LRecyclerView lrvList = (LRecyclerView) findViewById(R.id.lrv_list);

        adapter = new ShareDetailAdapter(this, getDatas(share_media), R.layout.adapter_share, share_media);
        lrvList.setAdapter(adapter);

        dialog = new ProgressDialog(this);
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

    private List<String> getDatas(SHARE_MEDIA share_media) {
        List<String> styles = new ArrayList<>();
        styles.clear();
        if (share_media == SHARE_MEDIA.QQ) {
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        } else if (share_media == SHARE_MEDIA.QZONE) {
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        } else if (share_media == SHARE_MEDIA.WEIXIN) {
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
            styles.add(StyleUtil.EMOJI);
            styles.add(StyleUtil.MINAPP);
        } else if (share_media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            styles.add(StyleUtil.MUSIC11);
            styles.add(StyleUtil.VIDEO11);
        } else if (share_media == SHARE_MEDIA.SINA) {
            styles.add(StyleUtil.TEXT);
            styles.add(StyleUtil.TEXTANDIMAGE);
            styles.add(StyleUtil.IMAGELOCAL);
            styles.add(StyleUtil.IMAGEURL);
            styles.add(StyleUtil.WEB11);
            //styles.add(StyleUtil.MUSIC11);
            //styles.add(StyleUtil.VIDEO11);
        }
        return styles;
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
