package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import per.sue.gear2.adapter.ArrayListAdapter;
import per.sue.gear2.widget.noscroll.NoScrollListView;

/**
 * Created by sue on 2016/11/25.
 */
public class GoodsCategoryAdapter extends ArrayListAdapter<GoodsCategoryBean> {


    private ColorStateList colorStateList ;
    private int backgroundResId;
    private int selectedIndex;

    public GoodsCategoryAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_goods_category, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GoodsCategoryBean bean = getItem(position);
        viewHolder.nameTv.setText(bean.getGcName());
        if(null != colorStateList){
            viewHolder.nameTv.setTextColor(colorStateList);
        }

        if(0 != backgroundResId){
            viewHolder.nameTv.setBackgroundResource(backgroundResId);
        }

        if(selectedIndex == position){
            viewHolder.nameTv.setSelected(true);
        }else{
            viewHolder.nameTv.setSelected(false);
        }
        return convertView;
    }


    public void setBackgroundResId(int backgroundResId) {
        this.backgroundResId = backgroundResId;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
        notifyDataSetChanged();
    }

    public void setColorStateList(ColorStateList colorStateList) {
        this.colorStateList = colorStateList;
    }

    static class ViewHolder {
        @Bind(R.id.name_tv)
        TextView nameTv;
        @Bind(R.id.child_list_view)
        NoScrollListView childListView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }



}
