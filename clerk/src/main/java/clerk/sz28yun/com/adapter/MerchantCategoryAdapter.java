package clerk.sz28yun.com.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import clerk.sz28yun.com.R;
import clerk.sz28yun.com.bean.MerchantCategoryBean;

import butterknife.Bind;
import butterknife.ButterKnife;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2016/11/25.
 */
public class MerchantCategoryAdapter extends ArrayListAdapter<MerchantCategoryBean> {


    private ColorStateList colorStateList ;
    private int backgroundResId;
    private int selectedIndex;

    public MerchantCategoryAdapter(Context context) {
        super(context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_merchant_category, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MerchantCategoryBean bean = getItem(position);
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


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
