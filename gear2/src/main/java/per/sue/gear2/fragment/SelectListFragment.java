package per.sue.gear2.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import per.sue.gear2.R;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.adapter.MutipleListAdapter;
import per.sue.gear2.adapter.SelectListAdapter;
import per.sue.gear2.config.GlobalConfig;
import per.sue.gear2.presenter.GearPresenter;
import per.sue.gear2.presenter.GearResultView;
import per.sue.gear2.presenter.ListPresenter;
import per.sue.gear2.presenter.SelectListPresenter;
import per.sue.gear2.ui.UIBaseFragment;
import per.sue.gear2.widget.PageStatusLayout;
import per.sue.gear2.widget.RefreshLayout;
import rx.Observable;

/**
 * Created by SUE on 2016/8/3 0003.
 */
public class SelectListFragment<T> extends UIBaseFragment implements ListPresenter.ListResultView<ArrayList<T>>, AdapterView.OnItemClickListener {

    private boolean isNotPage;

    private ListView listView;
    private MaterialRefreshLayout refreshLayout;
    private SelectListAdapter<T> selectListAdapter;
    private PageStatusLayout pageStatusLayout;
    private SelectListPresenter<ArrayList<T>> listListPresenter;
    private OnSelectFragmentListerner<T> onSelectFragmentListerner;
    private  SelectListAdapter.SelectListAdapterBindView<T> selectListAdapterBindView;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_select_list;
    }

    @Override
    public void onInitialize(@Nullable Bundle savedInstanceState) {
        pageStatusLayout = (PageStatusLayout)getView().findViewById(R.id.pager_status);
        listView = (ListView)getView().findViewById(R.id.gear_select_list_view);
        refreshLayout = (MaterialRefreshLayout)getView().findViewById(R.id.gear_refresh_layout);
        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                listListPresenter.setObservable(onSelectFragmentListerner.getObservable(1));
                listListPresenter.refresh();;
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                listListPresenter.setObservable(onSelectFragmentListerner.getObservable(listListPresenter.getPageNum() + 1));
                listListPresenter.loadMore();
            }
        });

        selectListAdapter = new SelectListAdapter<T>(getActivity(),selectListAdapterBindView);
        listView.setAdapter(selectListAdapter);
        listView.setOnItemClickListener(this);

        listListPresenter = new SelectListPresenter<ArrayList<T>>(getActivity(), this ) ;
        listListPresenter.setObservable(onSelectFragmentListerner.getObservable(listListPresenter.getPageNum()));
        listListPresenter.refreshWithLoading();

        if(isNotPage){
            refreshLayout.setLoadMore(false);
           // refreshLayout.setMaterialRefreshListener(null);
        }
    }



    public void init(OnSelectFragmentListerner<T> onSelectFragmentListerner, SelectListAdapter.SelectListAdapterBindView<T> selectListAdapterBindView) {
        this.onSelectFragmentListerner = onSelectFragmentListerner;
        this.selectListAdapterBindView = selectListAdapterBindView;
    }

    public void isNotPage(){
        isNotPage = true;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(null != onSelectFragmentListerner)
        onSelectFragmentListerner.onItemClick(parent, view, position, id);
    }

    @Override
    public void showLoading() {
        pageStatusLayout.showLoading();
    }

    @Override
    public void onError(int code, String message) {
        super.onError(code, message);
        refreshLayout.finishRefresh();
        refreshLayout.finishRefreshLoadMore();
        pageStatusLayout.showFailed("请重新尝试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listListPresenter.refreshWithLoading();
            }
        });
    }

    @Override
    public void onSuccessRefresh(ArrayList<T> msg) {
        selectListAdapter.setList(msg);
        refreshLayout.finishRefresh();
        pageStatusLayout.showContent();
    }

    @Override
    public void onSuccessLoadModre(ArrayList<T> msg) {
        selectListAdapter.addList(msg);
        refreshLayout.finishRefreshLoadMore();
    }



    public interface  OnSelectFragmentListerner<T>{
        Observable<ArrayList<T>> getObservable(int page);
        void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }
}
