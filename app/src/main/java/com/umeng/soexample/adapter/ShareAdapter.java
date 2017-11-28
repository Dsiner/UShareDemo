package com.umeng.soexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.d.lib.xrv.adapter.CommonAdapter;
import com.d.lib.xrv.adapter.CommonHolder;
import com.umeng.soexample.R;
import com.umeng.soexample.ShareDetailActivity;
import com.umeng.soexample.model.PlatformBean;

import java.util.List;

/**
 * Created by D on 2017/11/28.
 */
public class ShareAdapter extends CommonAdapter<PlatformBean> {

    public ShareAdapter(Context context, List<PlatformBean> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(final int position, CommonHolder holder, final PlatformBean item) {
        holder.setImageResource(R.id.iv_icon, item.resId);
        holder.setText(R.id.tv_name, item.name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, ShareDetailActivity.class)
                        .putExtra("platform", item.platform));
            }
        });
    }
}
