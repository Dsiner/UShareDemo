package com.umeng.soexample.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.d.lib.xrv.adapter.CommonAdapter;
import com.d.lib.xrv.adapter.CommonHolder;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.soexample.R;
import com.umeng.soexample.model.PlatformBean;

import java.util.List;
import java.util.Map;

/**
 * Created by D on 2017/11/28.
 */
public class AuthAdapter extends CommonAdapter<PlatformBean> {
    private ProgressDialog dialog;
    private UMAuthListener authListener;

    public AuthAdapter(Context context, List<PlatformBean> datas, int layoutId) {
        super(context, datas, layoutId);
        dialog = new ProgressDialog(context);
        authListener = new UMAuthListener() {
            /**
             * @desc 授权开始的回调
             * @param platform 平台名称
             */
            @Override
            public void onStart(SHARE_MEDIA platform) {
                SocializeUtils.safeShowDialog(dialog);
            }

            /**
             * @desc 授权成功的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param data 用户资料返回
             */
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                SocializeUtils.safeCloseDialog(dialog);
                Toast.makeText(mContext, "成功了", Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
            }

            /**
             * @desc 授权失败的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param t 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                SocializeUtils.safeCloseDialog(dialog);
                Toast.makeText(mContext, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
            }

            /**
             * @desc 授权取消的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             */
            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                SocializeUtils.safeCloseDialog(dialog);
                Toast.makeText(mContext, "取消了", Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    public void convert(final int position, CommonHolder holder, final PlatformBean item) {
        final boolean isauth = UMShareAPI.get(mContext).isAuthorize((Activity) mContext, item.platform);
        holder.setImageResource(R.id.iv_icon, item.resId);
        holder.setText(R.id.tv_name, item.name);
        holder.setText(R.id.tv_auth, isauth ? "删除授权" : "授权");
        holder.setViewOnClickListener(R.id.tv_auth, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isauth) {
                    UMShareAPI.get(mContext).deleteOauth((Activity) mContext, item.platform, authListener);
                } else {
                    UMShareAPI.get(mContext).doOauthVerify((Activity) mContext, item.platform, authListener);
                }
            }
        });
    }
}
