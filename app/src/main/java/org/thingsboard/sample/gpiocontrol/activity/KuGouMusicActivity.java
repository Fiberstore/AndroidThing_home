package org.thingsboard.sample.gpiocontrol.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.thingsboard.sample.gpiocontrol.R;
import org.thingsboard.sample.gpiocontrol.adapter.KuGouSearchAdapter;
import org.thingsboard.sample.gpiocontrol.base.BaseActivity;
import org.thingsboard.sample.gpiocontrol.bean.KuGouSearchBean;
import org.thingsboard.sample.gpiocontrol.util.MusicPlay;
import org.thingsboard.sample.gpiocontrol.util.Utils;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import okhttp3.Call;

/**
 * Created by zhangxiang on 2018/4/26.
 */

public class KuGouMusicActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    @InjectView(R.id.lv_kugou)
    ListView lvKugou;
    private List<KuGouSearchBean.DataBean.ListsBean> listsBeans;
    private KuGouSearchAdapter kuGouSearchAdapter;
    private MusicPlay musicPlay;

    /**
     * http://songsearch.kugou.com/song_search_v2?callback=jQuery112405307429385176461_1524751445450&keyword=%E5%A5%BD%E5%85%88%E7%94%9F
     * &page=1&pagesize=30&userid=-1&clientver=&pl
     * atform=WebFilter&tag=em&filter=2&iscorrecti
     * on=1&privilege_filter=0&_=1524751445452
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kugou_list);
        ButterKnife.inject(this);
        initAdapter();
        getMusicData();
    }

    private void initAdapter() {
        listsBeans = new LinkedList<>();
        kuGouSearchAdapter = new KuGouSearchAdapter(this, listsBeans);
        lvKugou.setAdapter(kuGouSearchAdapter);
        lvKugou.setOnItemClickListener(this);
    }

    private void getMusicData() {
        OkHttpUtils
                .get()
                .url("http://songsearch.kugou.com/song_search_v2?")
                .addParams("keyword", "好先生")
                .addParams("page", "1")
                .addParams("pagesize", "30")
                .addParams("userid", "-1")
                .addParams("platform", "WebFilter")
                .addParams("tag", "em")
                .addParams("filter", "2")
                .addParams("iscorrection", "1")
                .addParams("privilege_filter", "0")
                .addParams("_", "1524751445452")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Utils.myLog(response);
                        Gson gson = new Gson();
                        KuGouSearchBean kuGouSearchBean = gson.fromJson(response, KuGouSearchBean.class);
                        if (kuGouSearchBean.getData().getLists() != null) {
                            listsBeans.addAll(kuGouSearchBean.getData().getLists());
                            kuGouSearchAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        KuGouSearchBean.DataBean.ListsBean listsBean = listsBeans.get(i);
        musicPlay = new MusicPlay(null);
        String URL = "http://fs.w.kugou.com/201804262305/fbf0b3d1eb2461c5dc9502bc6cd90b5f/G070/M04/14/00/";
        Log.e("hash", listsBean.getFileHash());
        Log.e("music", URL + listsBean.getFileHash() + ".mp3");
        musicPlay.playUrl("http://fs.w.kugou.com/201804262305/fbf0b3d1eb2461c5dc9502bc6cd90b5f/G070/M04/14/00/JpQEAFdMOriALgW2AE8aq6fgNnU826.mp3");
    }
}
