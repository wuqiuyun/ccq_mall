package ccj.sz28yun.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import ccj.sz28yun.com.R;
import ccj.sz28yun.com.bean.CouponChainBean;
import per.sue.gear2.adapter.ArrayListAdapter;

/**
 * Created by sue on 2017/1/5.
 */
public class CouponChainAdapter extends ArrayListAdapter<CouponChainBean> {


    ArrayList<Boolean> booleanArrayList = new ArrayList<>();

    public CouponChainAdapter(Context context) {
        super(context);
    }

    @Override
    public void setList(ArrayList<CouponChainBean> list) {
        super.setList(list);
        booleanArrayList.clear();
        for(CouponChainBean bean : list){
            booleanArrayList.add(false);
        }
    }

    @Override
    public void addList(ArrayList<CouponChainBean> list) {
        super.addList(list);
        for(CouponChainBean bean : list){
            booleanArrayList.add(false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_coupon_chian, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CouponChainBean bean = getItem(position);
        viewHolder.nameCb.setText(bean.storeName);
        viewHolder.nameCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booleanArrayList.set(position, !booleanArrayList.get(position));
                notifyDataSetChanged();
            }
        });
        viewHolder.nameCb.setChecked(booleanArrayList.get(position));
        return convertView;
    }

    public boolean hasSelected(){
        boolean b = false;
        for(Boolean bool : booleanArrayList){
            if(bool){
                b = true;
            }
        }
        return b;
    }


    public String getSelectedItemsForParams(){
        String parasm = "[]";
        ArrayList<Integer> ids = new ArrayList<>();
        for(int i = 0 ; i < booleanArrayList.size() ; i++){
            if(booleanArrayList.get(i)){
                ids.add(getItem(i).id);
            }
        }
        parasm = new Gson().toJson(ids);
        return parasm;
    }

    public void setSelectItems(String ids){
        ids = ids.replace("[", "");
        ids = ids.replace("]", "");
        String[] idArr = ids.split(",");
        for(String id : idArr){
            for(int i = 0; i < list.size() ; i++){
                CouponChainBean couponChainBean = list.get(i);
                if(id.equals(couponChainBean.id + "")){
                    booleanArrayList.set(i, true);
                }
            }
        }

        notifyDataSetChanged();
    }

    static class ViewHolder {
        @Bind(R.id.name_cb)
        CheckBox nameCb;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
