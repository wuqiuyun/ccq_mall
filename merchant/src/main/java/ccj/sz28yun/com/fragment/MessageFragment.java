package ccj.sz28yun.com.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.activity.MessageDetailsActivity;
import ccj.sz28yun.com.adapter.MessageAdapter;
import ccj.sz28yun.com.base.CCJFragment;
import ccj.sz28yun.com.bean.MessageBean;
import ccj.sz28yun.com.presenter.MessagePresenter;

/**
 * Created by sue on 2017/1/3.
 */
public class MessageFragment extends CCJFragment implements MessagePresenter.MessageView {


    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.refresh_layout)
    MaterialRefreshLayout refreshLayout;

    private MessagePresenter messagePresenter;
    private MessageAdapter messageAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        messagePresenter = new MessagePresenter(getActivity(), this);
        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                messagePresenter.refresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                messagePresenter.loadMore();
            }
        });

        messageAdapter = new MessageAdapter(getActivity());
        listView.setAdapter(messageAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageBean bean = messageAdapter.getItem(position);
                //messagePresenter.setRead(bean.getSmId());
                startActivity(MessageDetailsActivity.startIntent(getActivity(), bean));
            }
        });

        messagePresenter.refresh();

    }


    @Override
    public void onSetReaded(String message) {

    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        refreshLayout.finishRefresh();
        refreshLayout.finishRefreshLoadMore();
    }

    @Override
    public void onSuccessRefresh(ArrayList<MessageBean> result) {
        refreshLayout.finishRefresh();
        messageAdapter.setList(result);

    }

    @Override
    public void onSuccessLoadModre(ArrayList<MessageBean> result) {
        refreshLayout.finishRefreshLoadMore();
        messageAdapter.addList(result);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        messagePresenter.destroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        messagePresenter.refresh();
    }
}
