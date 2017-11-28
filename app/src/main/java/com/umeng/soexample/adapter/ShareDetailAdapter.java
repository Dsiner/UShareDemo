package com.umeng.soexample.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.d.lib.xrv.adapter.CommonAdapter;
import com.d.lib.xrv.adapter.CommonHolder;
import com.umeng.social.tool.UMImageMark;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.soexample.R;
import com.umeng.soexample.model.Defaultcontent;
import com.umeng.soexample.model.StyleUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by D on 2017/11/28.
 */
public class ShareDetailAdapter extends CommonAdapter<String> {
    private SHARE_MEDIA share_media;
    private UMImage imagelocal, imageurl;
    private UMWeb web;
    private UMusic music;
    private UMVideo video;
    private UMEmoji emoji;
    private File file;

    private UMShareListener shareListener;
    private ProgressDialog dialog;

    public ShareDetailAdapter(Context context, List<String> datas, int layoutId, SHARE_MEDIA share_media) {
        super(context, datas, layoutId);
        this.share_media = share_media;
        initMedia();
        dialog = new ProgressDialog(context);
        shareListener = new UMShareListener() {
            /**
             * @descrption 分享开始的回调
             * @param platform 平台类型
             */
            @Override
            public void onStart(SHARE_MEDIA platform) {
                SocializeUtils.safeShowDialog(dialog);
            }

            /**
             * @descrption 分享成功的回调
             * @param platform 平台类型
             */
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();
                SocializeUtils.safeCloseDialog(dialog);
            }

            /**
             * @descrption 分享失败的回调
             * @param platform 平台类型
             * @param t 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                SocializeUtils.safeCloseDialog(dialog);
                Toast.makeText(mContext, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
            }

            /**
             * @descrption 分享取消的回调
             * @param platform 平台类型
             */
            @Override
            public void onCancel(SHARE_MEDIA platform) {
                SocializeUtils.safeCloseDialog(dialog);
                Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
            }
        };
    }

    private void initMedia() {
        UMImageMark umImageMark = new UMImageMark();
        umImageMark.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
        umImageMark.setMarkBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.umsocial_defaultwatermark));
        imagelocal = new UMImage(mContext, R.drawable.logo/*, umImageMark*/);
        imagelocal.setThumb(new UMImage(mContext, R.drawable.thumb));

        imageurl = new UMImage(mContext, Defaultcontent.imageurl);
        imageurl.setThumb(new UMImage(mContext, R.drawable.thumb));

        web = new UMWeb(Defaultcontent.url);
        web.setTitle("This is web title");
        web.setThumb(new UMImage(mContext, R.drawable.thumb));
        web.setDescription("my description");

        music = new UMusic(Defaultcontent.musicurl);
        music.setTitle("This is music title");
        music.setThumb(new UMImage(mContext, R.drawable.thumb));
        music.setDescription("my description");
        music.setmTargetUrl(Defaultcontent.url);

        video = new UMVideo(Defaultcontent.videourl);
        video.setThumb(new UMImage(mContext, R.drawable.thumb));
        video.setTitle("This is video title");
        video.setDescription("my description");

        emoji = new UMEmoji(mContext, "http://img5.imgtn.bdimg.com/it/u=2749190246,3857616763&fm=21&gp=0.jpg");
        emoji.setThumb(new UMImage(mContext, R.drawable.thumb));

        //file
        file = new File(mContext.getFilesDir() + "test.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (SocializeUtils.File2byte(file).length <= 0) {
            String content = "U-share分享";
            byte[] contentInBytes = content.getBytes();
            try {
                FileOutputStream fop = new FileOutputStream(file);
                fop.write(contentInBytes);
                fop.flush();
                fop.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void convert(final int position, CommonHolder holder, final String item) {
        holder.setText(R.id.tv_content, item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(item);
            }
        });
    }

    private void share(String item) {
        switch (item) {
            case StyleUtil.TEXT:
                new ShareAction((Activity) mContext)
                        .withText(Defaultcontent.text)
                        .setPlatform(share_media)
                        .setCallback(shareListener)
                        .share();
                break;
            case StyleUtil.IMAGELOCAL:
                new ShareAction((Activity) mContext)
                        .withMedia(imagelocal)
                        .setPlatform(share_media)
                        .setCallback(shareListener)
                        .share();
                break;
            case StyleUtil.IMAGEURL:
                new ShareAction((Activity) mContext)
                        .withMedia(imageurl)
                        .setPlatform(share_media)
                        .setCallback(shareListener)
                        .share();
                break;
            case StyleUtil.TEXTANDIMAGE:
                new ShareAction((Activity) mContext)
                        .withText(Defaultcontent.text)
                        .withMedia(imagelocal)
                        .setPlatform(share_media)
                        .setCallback(shareListener)
                        .share();
                break;
            case StyleUtil.WEB11:
            case StyleUtil.WEB00:
            case StyleUtil.WEB10:
            case StyleUtil.WEB01:
                new ShareAction((Activity) mContext)
                        .withText(Defaultcontent.text)
                        .withMedia(web)
                        .setPlatform(share_media)
                        .setCallback(shareListener)
                        .share();
                break;
            case StyleUtil.MUSIC11:
            case StyleUtil.MUSIC00:
            case StyleUtil.MUSIC10:
            case StyleUtil.MUSIC01:
                new ShareAction((Activity) mContext)
                        .withMedia(music)
                        .setPlatform(share_media)
                        .setCallback(shareListener)
                        .share();
                break;
            case StyleUtil.VIDEO11:
            case StyleUtil.VIDEO00:
            case StyleUtil.VIDEO01:
            case StyleUtil.VIDEO10:
                new ShareAction((Activity) mContext)
                        .withMedia(video)
                        .setPlatform(share_media)
                        .setCallback(shareListener)
                        .share();
                break;
            case StyleUtil.EMOJI:
                new ShareAction((Activity) mContext)
                        .withMedia(emoji)
                        .setPlatform(share_media)
                        .setCallback(shareListener)
                        .share();
                break;
            case StyleUtil.FILE:
                new ShareAction((Activity) mContext)
                        .withFile(file)
                        .withText(Defaultcontent.text)
                        .withSubject(Defaultcontent.title)
                        .setPlatform(share_media)
                        .setCallback(shareListener)
                        .share();
                break;
            case StyleUtil.MINAPP:
                UMMin umMin = new UMMin(Defaultcontent.url);
                umMin.setThumb(imagelocal);
                umMin.setTitle(Defaultcontent.title);
                umMin.setDescription(Defaultcontent.text);
                umMin.setPath("pages/page10007/page10007");
                umMin.setUserName("gh_3ac2059ac66f");
                new ShareAction((Activity) mContext)
                        .withMedia(umMin)
                        .setPlatform(share_media)
                        .setCallback(shareListener).share();
                break;
        }
    }
}
