package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.HomeMenuBean;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2016/12/4.
 */
public class HomeMenuAdapter extends ArrayListAdapter<HomeMenuBean> {
    public HomeMenuAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeMenuBean bean = getItem(position);
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_home_menu, null);
            viewHolder= new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.homeTopMenuTv.setText(bean.name);
        viewHolder.homeTopMenuTv.setCompoundDrawablesWithIntrinsicBounds(0, bean.resImage ,0, 0);



        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.home_top_menu_tv)
        TextView homeTopMenuTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
