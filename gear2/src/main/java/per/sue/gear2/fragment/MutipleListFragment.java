package per.sue.gear2.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import per.sue.gear2.R;
import per.sue.gear2.adapter.MutipleListAdapter;
import per.sue.gear2.utils.CollectionUtils;
import per.sue.gear2.utils.ToastUtils;

/**
 * Created by SUE on 2016/7/22 0022.
 */
public class MutipleListFragment extends Fragment {

    private String[] items;
    private ListView listView;
    private boolean isMutiple;
    private String selectItem;
    private MutipleListAdapter mutipleListAdapter;


    public static MutipleListFragment newInstance(String[] items, String selectItem, boolean isMutiple) {
        MutipleListFragment fragment = new MutipleListFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("items", items);
        bundle.putString("selectItem", selectItem);
        bundle.putBoolean("isMutiple", isMutiple);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        createListView();
        return listView;
    }


    private ListView createListView() {

        items = getArguments().getStringArray("items");
        isMutiple = getArguments().getBoolean("isMutiple");
        selectItem = getArguments().getString("selectItem");

        mutipleListAdapter = new MutipleListAdapter(getActivity(), items);
        listView = new ListView(getContext());
        mutipleListAdapter.setMutiple(isMutiple);
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        listView.setDivider(new ColorDrawable(getResources().getColor(R.color.app_divider)));
        listView.setDividerHeight(2);
        listView.setBackgroundColor(getResources().getColor(R.color.gear_white));
        listView.setAdapter(mutipleListAdapter);
        setSelectItem();
        return listView;
    }

    private void setSelectItem() {
        if (TextUtils.isEmpty(selectItem))
            return;

        if (isMutiple) {
            String[] selectArr = selectItem.split(",");
            for (String str : selectArr) {
                int position =  CollectionUtils.getPositionAtArray(str, items);
                if(position >= 0)
                    mutipleListAdapter.checkItem(position, true);
            }

        } else {
            int position = CollectionUtils.getPositionAtArray(selectItem, items);
            if(position >= 0)
                mutipleListAdapter.checkItem(position, true);
        }
    }

    public ArrayList<Integer> getSelectedPosition(){
        ArrayList<Integer> lis = new ArrayList<>();

        boolean[] sparseBooleanArray =  mutipleListAdapter.getCheckPositions();
        for(int i =0; i < sparseBooleanArray.length; i++){
            if(sparseBooleanArray[i]){
                lis.add(i);
            }
         }
        return lis;
    }

    public String  getSelectedValue(){
        StringBuilder valueBuilder = new StringBuilder();
         boolean[] sparseBooleanArray =  mutipleListAdapter.getCheckPositions();
        for(int i =0; i < sparseBooleanArray.length; i++){
              if(sparseBooleanArray[i]){
                  if(valueBuilder.length() > 0)
                  valueBuilder.append(",");
                  valueBuilder.append(items[i]);
                  }
        }
        return valueBuilder.toString();
    }

}
