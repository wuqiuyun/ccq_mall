package ccj.sz28yun.com.presenter;

import android.content.Context;



import java.util.ArrayList;

import ccj.sz28yun.com.api.GoodsAPI;
import ccj.sz28yun.com.bean.GoodsCategoryBean;
import per.sue.gear2.presenter.AbsPresenter;
import per.sue.gear2.presenter.GearResultView;
import rx.Subscriber;

/**
 * Created by sue on 2016/11/16.
 */
public class GoodsCategoryPresenter extends AbsPresenter {

    private Context context;
    private GoodsCategoryView goodsCategoryView;
    private GoodsAPI goodsAPI;
    ArrayList<GoodsCategoryBean> list;

    public GoodsCategoryPresenter(Context context, GoodsCategoryView view) {
        this.context = context;
        this.goodsCategoryView = view;
        this.goodsAPI  =  new GoodsAPI();
    }

    public void syncData(){
        this.goodsAPI.getGoodsCategoryList().subscribe(new Subscriber<ArrayList<GoodsCategoryBean>>() {
            @Override
            public void onCompleted() {
                goodsCategoryView.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                goodsCategoryView.onError(-1, e.getMessage());
            }

            @Override
            public void onNext(ArrayList<GoodsCategoryBean> result) {
                goodsCategoryView.onSuccess(result);
                list = result;
            }
        });
    }

    public void loadSecondData(int index){
        if(null != list && list.size() > index){
            goodsCategoryView.loadSecondList(list.get(index).getChildList());
        }
    }

    public interface GoodsCategoryView extends GearResultView<ArrayList<GoodsCategoryBean>> {

        void loadSecondList(ArrayList<GoodsCategoryBean> list);


    }
}
