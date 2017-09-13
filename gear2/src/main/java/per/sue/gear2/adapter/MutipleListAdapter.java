package per.sue.gear2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;

import per.sue.gear2.R;

/**
 * Created by SUE on 2016/7/28 0028.
 */
public class MutipleListAdapter extends BaseAdapter {

    private boolean[] checkPositions;
    private Context context;
    private String[] items;
    private boolean isMutiple;


    public MutipleListAdapter(Context context, String[] items) {
        this.context = context;
        this.items =items;
        checkPositions = new boolean[items.length];
    }



    public void checkItem(int position, boolean b){
        checkPositions[position] = b;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public String getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CheckBox checkBox;
        if(convertView == null){
            checkBox  = (CheckBox)LayoutInflater.from(context).inflate(R.layout.gear_simple_list_item_checkable, null);
            convertView = checkBox;
        }else{
            checkBox = (CheckBox)convertView;
        }

        String item = getItem(position);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPositions[position] =  !checkPositions[position];
                if(!isMutiple &&  checkPositions[position]){
                    for(int i = 0 ; i< checkPositions.length; i++){
                        if(i != position){
                            checkPositions[i] = false;
                        }
                    }
                    }
                notifyDataSetChanged();
                }
        });

        checkBox.setChecked( checkPositions[position]);
        checkBox.setText(item);

        return checkBox;
    }

    public boolean[] getCheckPositions(){

    return checkPositions;
    }

    public void setMutiple(boolean mutiple) {
        isMutiple = mutiple;
    }
}
