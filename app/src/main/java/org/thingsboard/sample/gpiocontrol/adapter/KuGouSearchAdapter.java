package org.thingsboard.sample.gpiocontrol.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.thingsboard.sample.gpiocontrol.R;
import org.thingsboard.sample.gpiocontrol.bean.KuGouSearchBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangxiang on 2018/4/26.
 */

public class KuGouSearchAdapter extends BaseAdapter {

    Activity mActivity;
    private List<KuGouSearchBean.DataBean.ListsBean> listsBeans;
    private final LayoutInflater layoutInflater;
    private ViewHolder viewHolder;

    public KuGouSearchAdapter(Activity activity, List<KuGouSearchBean.DataBean.ListsBean> listsBeans) {
        super();
        this.mActivity = activity;
        this.listsBeans = listsBeans;
        layoutInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return listsBeans == null ? 0 : listsBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_kugou_search, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
            KuGouSearchBean.DataBean.ListsBean listsBean = listsBeans.get(i);
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.musicName.setText(listsBean.getFileName());
        }
        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.iv_music_home)
        ImageView ivMusicHome;
        @InjectView(R.id.music_name)
        TextView musicName;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
