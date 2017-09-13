package per.sue.gear2.controll;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.AbstractWheelView;
import antistatic.spinnerwheel.OnWheelChangedListener;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;
import per.sue.gear2.R;
import per.sue.gear2.utils.date.DateStyle;
import per.sue.gear2.utils.date.DateUtils;

/**
 * Created by SUE on 2016/7/22 0022.
 */
public class GearDateViewController {

    public enum DateStyle{
        YEAR, YEAR_MONTH, YEAR_MONTH_DAY, YEAR_MONTH_DAY_HOUR, YEAR_MONTH_DAY_HOUR_MIN, MONTH, DAY,
        HOUR_MIN
    }

    private AbstractWheelView startY, startMon, startD;
    private AbstractWheelView startH, startMin;
    private Context context;


    private onChangeDateListenter dateListenter;
    private String currentDate;
    private Date selectDate;

    private Calendar calendar = Calendar.getInstance();
    private static int START_YEAR = 1900, END_YEAR = 2100;
    private DateStyle dateStyle = DateStyle.YEAR_MONTH_DAY;
    private View contentView;



    public GearDateViewController(Context context ) {
        this.context = context;
        this.currentDate = DateUtils.getCurrentDateByPattern("yyyy-MM-dd HH:mm:ss");
        selectDate = DateUtils.StringToDate(currentDate);
        calendar.setTime(selectDate);
        initialize();
    }

    public GearDateViewController(Context context, String currentDate) {
        this.context = context;
        this.currentDate = currentDate;
        if(TextUtils.isEmpty(currentDate)) currentDate = DateUtils.getCurrentDate();
        selectDate = DateUtils.StringToDate(currentDate);
        calendar.setTime(selectDate);
        initialize();
    }

    public GearDateViewController(Context context, long currentDate) {
        this.context = context;
        this.currentDate = DateUtils.getDate(currentDate, per.sue.gear2.utils.date.DateStyle.YYYY_MM_DD_HH_MM_SS ) ;
        selectDate = new Date(currentDate);
        calendar.setTime(selectDate);
        initialize();
    }

    private void initialize() {

        LayoutInflater inflater = LayoutInflater.from( this.context );
        contentView = inflater.inflate(R.layout.view_selectdate_wheel, null);
        intView();
        bindView();
        changeViewByDateStyle();

    }

    private void intView() {
        startY = (AbstractWheelView) contentView.findViewById(R.id.wheel_startdate_year);

        startMon = (AbstractWheelView) contentView.findViewById(R.id.wheel_startdate_month);
        startD = (AbstractWheelView) contentView.findViewById(R.id.wheel_startdate_day);
        startH = (AbstractWheelView) contentView.findViewById(R.id.wheel_startdate_hour);
        startMin = (AbstractWheelView) contentView.findViewById(R.id.wheel_startdate_minute);

    }




    public void setDateStyle(DateStyle type) {
        dateStyle = type;
        changeViewByDateStyle();
    }

    private void changeViewByDateStyle(){
        switch(dateStyle){
        case YEAR:
            ((View)startY.getParent()).setVisibility(View.VISIBLE);
            ((View)startMon.getParent()).setVisibility(View.GONE);
            ((View)startD.getParent()).setVisibility(View.GONE);
            ((View)startH.getParent()).setVisibility(View.GONE);
            ((View)startMin.getParent()).setVisibility(View.GONE);
            break;
        case YEAR_MONTH:
            ((View)startY.getParent()).setVisibility(View.VISIBLE);
            ((View)startMon.getParent()).setVisibility(View.VISIBLE);
            ((View)startD.getParent()).setVisibility(View.GONE);
            ((View)startH.getParent()).setVisibility(View.GONE);
            ((View)startMin.getParent()).setVisibility(View.GONE);
            break;
        case YEAR_MONTH_DAY:
            ((View)startY.getParent()).setVisibility(View.VISIBLE);
            ((View)startMon.getParent()).setVisibility(View.VISIBLE);
            ((View)startD.getParent()).setVisibility(View.VISIBLE);
            ((View)startH.getParent()).setVisibility(View.GONE);
            ((View)startMin.getParent()).setVisibility(View.GONE);
            break;
        case YEAR_MONTH_DAY_HOUR:
            ((View)startY.getParent()).setVisibility(View.VISIBLE);
            ((View)startMon.getParent()).setVisibility(View.VISIBLE);
            ((View)startD.getParent()).setVisibility(View.VISIBLE);
            ((View)startH.getParent()).setVisibility(View.VISIBLE);
            ((View)startMin.getParent()).setVisibility(View.GONE);
            break;
        case YEAR_MONTH_DAY_HOUR_MIN:
            ((View)startY.getParent()).setVisibility(View.VISIBLE);
            ((View)startMon.getParent()).setVisibility(View.VISIBLE);
            ((View)startD.getParent()).setVisibility(View.VISIBLE);
            ((View)startH.getParent()).setVisibility(View.VISIBLE);
            ((View)startMin.getParent()).setVisibility(View.VISIBLE);
            break;
        case HOUR_MIN:
            ((View)startY.getParent()).setVisibility(View.GONE);
            ((View)startMon.getParent()).setVisibility(View.GONE);
            ((View)startD.getParent()).setVisibility(View.GONE);
            ((View)startH.getParent()).setVisibility(View.VISIBLE);
            ((View)startMin.getParent()).setVisibility(View.VISIBLE);
            break;
         case MONTH:
                ((View)startY.getParent()).setVisibility(View.GONE);
                ((View)startMon.getParent()).setVisibility(View.VISIBLE);
                ((View)startD.getParent()).setVisibility(View.GONE);
                ((View)startH.getParent()).setVisibility(View.GONE);
                ((View)startMin.getParent()).setVisibility(View.GONE);
                break;
         case DAY:
                ((View)startY.getParent()).setVisibility(View.GONE);
                ((View)startMon.getParent()).setVisibility(View.GONE);
                ((View)startD.getParent()).setVisibility(View.VISIBLE);
                ((View)startH.getParent()).setVisibility(View.GONE);
                ((View)startMin.getParent()).setVisibility(View.GONE);
                break;
        default:
            break;
    }
    }

    private void bindView()
    {
        bindWheelVerticalView(currentDate, startY, startMon, startD, startH, startMin);

    }

    private void bindWheelVerticalView(final String str,final AbstractWheelView wv_year,final AbstractWheelView  wv_month,final AbstractWheelView  wv_day,final AbstractWheelView wv_hours,final AbstractWheelView wv_mins) {

        //calendar.get(Calendar.HOUR_OF_DAY );
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        int hours=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
        String[] months_little = { "4", "6", "9", "11" };

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);


        NumericWheelAdapter adapter=new NumericWheelAdapter(context, START_YEAR, END_YEAR);
        adapter.setTextSize(20);
        wv_year.setViewAdapter(adapter);
        wv_year.setCyclic(true);

        wv_year.setCurrentItem(year - START_YEAR);


        adapter = new NumericWheelAdapter(context,1, 12);
        adapter.setTextSize(20);
        wv_month.setViewAdapter(adapter);
        wv_month.setCyclic(true);

        wv_month.setCurrentItem(month);


        wv_day.setCyclic(true);
        if (list_big.contains(String.valueOf(month + 1))) {
            adapter = new NumericWheelAdapter(context,1, 31);

        } else if (list_little.contains(String.valueOf(month + 1))) {
            adapter = new NumericWheelAdapter(context,1, 30);
        } else {
            // ����
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                adapter = new NumericWheelAdapter(context,1, 29);
            else
                adapter = new NumericWheelAdapter(context,1, 28);
        }
        adapter.setTextSize(20);
        wv_day.setViewAdapter(adapter);

        wv_day.setCurrentItem(day -1);



        // ʱ
        final  NumericWheelAdapter wheelAdapterHour=new NumericWheelAdapter(context,0, 23, "%02d");
        //wheelAdapterHour.setItemResource(R.layout.wheel_text_centered);
       // wheelAdapterHour.setItemTextResource(R.id.text);
        wheelAdapterHour.setTextSize(20);
        wv_hours.setViewAdapter(wheelAdapterHour);
        wv_hours.setCyclic(true);
        //wv_hours.setLabel("ʱ");
        wv_hours.setCurrentItem(hours);



        // ��
        final NumericWheelAdapter wheelAdapterMin=new NumericWheelAdapter(context,0, 59, "%02d");
       // wheelAdapterMin.setItemResource(R.layout.wheel_text_centered_dark_back);
       // wheelAdapterMin.setItemTextResource(R.id.text);
        wheelAdapterMin.setTextSize(20);
        wv_mins.setViewAdapter(wheelAdapterMin);
        wv_mins.setCyclic(true);
        wv_mins.setCurrentItem(minute);

        OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;

                if (list_big
                        .contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setViewAdapter(getDayAdapter( 1, 31));
                } else if (list_little.contains(String.valueOf(wv_month
                        .getCurrentItem() + 1))) {
                    wv_day.setViewAdapter(getDayAdapter(1, 30));
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0)
                            || year_num % 400 == 0)
                        wv_day.setViewAdapter(getDayAdapter(1, 29));
                    else
                        wv_day.setViewAdapter(getDayAdapter(1, 28));
                }
                setTextView(str, wv_year, wv_month, wv_day, wv_hours, wv_mins);
            }
        };

        OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                int month_num = newValue + 1;

                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setViewAdapter(getDayAdapter(1, 31));
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setViewAdapter(getDayAdapter(1, 30));
                } else {
                    if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year
                            .getCurrentItem() + START_YEAR) % 100 != 0)
                            || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
                        wv_day.setViewAdapter(getDayAdapter(1, 29));
                    else
                        wv_day.setViewAdapter(getDayAdapter(1, 28));
                }

                setTextView(str, wv_year, wv_month, wv_day, wv_hours, wv_mins);
            }
        };

        OnWheelChangedListener wheelListener_other=new OnWheelChangedListener() {

            @Override
            public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
                // TODO Auto-generated method stub
                setTextView(str, wv_year, wv_month, wv_day, wv_hours, wv_mins);
            }
        };

        wv_year.addChangingListener(wheelListener_year);
        wv_month.addChangingListener(wheelListener_month);
        wv_day.addChangingListener(wheelListener_other);
        wv_hours.addChangingListener(wheelListener_other);
        wv_mins.addChangingListener(wheelListener_other);

    }

    private NumericWheelAdapter getDayAdapter(int startDat, int endDay){
        NumericWheelAdapter adapter = new NumericWheelAdapter(context,startDat, endDay);
        adapter.setTextSize(20);
        return adapter;
    }



    private void setTextView(final String str,final AbstractWheelView wv_year,final AbstractWheelView  wv_month,final AbstractWheelView  wv_day,AbstractWheelView wv_hours,AbstractWheelView wv_mins) {


        int year = wv_year.getCurrentItem() + START_YEAR;
        int month= wv_month.getCurrentItem() + 1;
        int day= wv_day.getCurrentItem() + 1;
        int hour= wv_hours.getCurrentItem() ;
        int min= wv_mins.getCurrentItem();

        //currentDate = String.format("%d-%02d-%02d %02d:%02d",year,month,day,hour,min);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        currentDate = getTime(wv_year,wv_month, wv_day,  wv_hours,wv_mins );
        ParsePosition pos = new ParsePosition(0);
        selectDate   = formatter.parse(currentDate, pos);
        if(null != dateListenter){
            dateListenter.onChangeDate(selectDate,year,month, day, hour, min);
        }

    }

    public String getTime(final AbstractWheelView wv_year,final AbstractWheelView  wv_month,final AbstractWheelView  wv_day,AbstractWheelView wv_hours,AbstractWheelView wv_mins) {
        StringBuffer sb = new StringBuffer();

        String month= wv_month.getCurrentItem() + 1+"";
        String day= wv_day.getCurrentItem() + 1+"";
        String hour= wv_hours.getCurrentItem() +"";
        String min= wv_mins.getCurrentItem() +"";
        if(month.length()==1)
            month= "0"+ month;

        if(day.length()==1)
            day = "0"+ day;
        if(hour.length()==1)
            hour= "0" + hour;
        if(min.length()==1)
            min= "0" + min;


        sb.append((wv_year.getCurrentItem() + START_YEAR)).append("-")
                .append(month).append("-")
                .append(day).append(" ")
                .append(hour).append(":")
                .append(min);
        return sb.toString();
    }
    public interface onChangeDateListenter {
        void onChangeDate( Date selectDate ,int year, int month, int day, int house, int min);

    }

    public Date getSelectDate() {
        if(selectDate== null && currentDate.isEmpty()==false){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            ParsePosition pos = new ParsePosition(0);
            selectDate = formatter.parse(currentDate, pos);
        }
        return selectDate;
    }

    public View getContentView() {
        return contentView;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    /**
     * yyyy-MM-dd HH:mm
     * @param format
     * @return
     */
    public String getCurrentDateFormat(String format) {
        return  new SimpleDateFormat(format).format(selectDate);
    }
}

