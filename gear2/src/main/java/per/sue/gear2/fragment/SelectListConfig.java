package per.sue.gear2.fragment;

import android.content.Context;
import android.widget.AdapterView;

import java.io.Serializable;
import java.util.ArrayList;

import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.adapter.SelectListAdapter;
import per.sue.gear2.presenter.ListPresenter;
import rx.Observable;

/**
 * Created by SUE on 2016/8/3 0003.
 */
public class SelectListConfig<T> implements Serializable{

    private SelectListAdapter.SelectListAdapterBindView<T> selectListAdapterBindView;
    private AdapterView.OnItemClickListener onItemClickListener;
   private  OnSelectConfigListener onSelectConfigListener;



    public SelectListAdapter.SelectListAdapterBindView<T> getSelectListAdapterBindView() {
        return selectListAdapterBindView;
    }

    public void setSelectListAdapterBindView(SelectListAdapter.SelectListAdapterBindView<T> selectListAdapterBindView) {
        this.selectListAdapterBindView = selectListAdapterBindView;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnSelectConfigListener getOnSelectConfigListener() {
        return onSelectConfigListener;
    }

    public void setOnSelectConfigListener(OnSelectConfigListener onSelectConfigListener) {
        this.onSelectConfigListener = onSelectConfigListener;
    }

    public interface  OnSelectConfigListener<T> {


    }

}
