package per.sue.gear2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by SUE on 2016/8/3 0003.
 */
public class SelectListAdapter<T> extends ArrayListAdapter<T> {

    SelectListAdapterBindView selectListAdapterBindView;
    public SelectListAdapter(Context context, SelectListAdapterBindView selectListAdapterBindView) {
        super(context);
        this.selectListAdapterBindView = selectListAdapterBindView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T t = getItem(position);
        if(null == convertView){
            convertView =  LayoutInflater.from(getContext()).inflate(selectListAdapterBindView.getConvertViewResId(), null);
        }
        selectListAdapterBindView.bindView(position, convertView, parent, t);
        return convertView;
    }

    public interface SelectListAdapterBindView<T>{
        void bindView(int position, View convertView, ViewGroup parent, T t);
        int getConvertViewResId();
    }
}
