package per.sue.gear2.controll;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import antistatic.spinnerwheel.AbstractWheelView;
import antistatic.spinnerwheel.OnWheelChangedListener;
import antistatic.spinnerwheel.WheelVerticalView;
import antistatic.spinnerwheel.adapters.ArrayWheelAdapter;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;
import per.sue.gear2.R;

/**
 * Created by SUE on 2016/7/22 0022.
 */
public class GearArrayWheelController {

    private Context context;
    private ArrayList<AbstractWheelView> list = new ArrayList<>();

    public GearArrayWheelController(Context context) {
        this.context = context;
    }

    public GearArrayWheelController add(int selectItem,String[] items,OnWheelChangedListener l ){
        WheelVerticalView wheelVerticalView = (WheelVerticalView)LayoutInflater.from(context).inflate(R.layout.wheel_view_vertical, null);
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(context, items);
        wheelVerticalView.setViewAdapter(adapter);
        wheelVerticalView.addChangingListener(l);
        wheelVerticalView.setVisibleItems(5);
        if(-1 != selectItem)
            wheelVerticalView.setCurrentItem(selectItem);
        list.add(wheelVerticalView);
        return this;
    }

    public GearArrayWheelController add(int selectItemValue, int minNum, int maxNum,OnWheelChangedListener l ){
        WheelVerticalView wheelVerticalView =(WheelVerticalView)LayoutInflater.from(context).inflate(R.layout.wheel_view_vertical, null);
        NumericWheelAdapter adapter=new NumericWheelAdapter(context, minNum, maxNum);
        adapter.setItemResource(R.layout.wheel_text_centered_dark_back);
        adapter.setItemTextResource(R.id.text);
        wheelVerticalView.setViewAdapter(adapter);
        wheelVerticalView.addChangingListener(l);
        if(-1 != selectItemValue)
            wheelVerticalView.setCurrentItem(selectItemValue - minNum);
        wheelVerticalView.setVisibleItems(5);
        list.add(wheelVerticalView);
        return this;
    }

    public GearArrayWheelController add(AbstractWheelView view, int selectItemValue, int minNum, int maxNum,OnWheelChangedListener l ){
        NumericWheelAdapter adapter=new NumericWheelAdapter(context, minNum, maxNum);
        adapter.setItemResource(R.layout.wheel_text_centered_dark_back);
        adapter.setItemTextResource(R.id.text);
        view.setViewAdapter(adapter);
        view.addChangingListener(l);
        if(-1 != selectItemValue)
        view.setCurrentItem(selectItemValue - minNum);
        view.setVisibleItems(5);
        list.add(view);
        return this;
    }


   public View createView(){
       LinearLayout linearLayout = new LinearLayout(context);
       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
       for(AbstractWheelView view : list){
           linearLayout.addView(view, params);
       }
       return linearLayout;
   }

    public interface  OnSelectedItemListener{

        void onSelectedItem(String[] item , int position);
    }





}
