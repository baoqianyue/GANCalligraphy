package com.barackbao.aicalligraphy.mvp.presenter;

import com.barackbao.aicalligraphy.R;
import com.barackbao.aicalligraphy.model.CopyBook;
import com.barackbao.aicalligraphy.mvp.contract.CopyBookContract;
import com.barackbao.aicalligraphy.view.fragment.home.CopyBookFragment;

import java.util.ArrayList;

/**
 * <pre>
 *     author : baoqianyue
 *     time   : 2018/09/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class CopyBookPresenter implements CopyBookContract.IPresenter {

    private CopyBookContract.IView copyBookView;

    private ArrayList<CopyBook> dataList = new ArrayList<>();


    public CopyBookPresenter(CopyBookContract.IView view) {
        copyBookView = view;
        testData();
    }

    private void testData() {
        for (int i = 0; i < 8; i++) {
            dataList.add(new CopyBook("R.drawable.barack", "颜真卿", "多宝塔碑" + i));
        }
    }

    @Override
    public void requestData() {
        copyBookView.showCopyBookList(dataList);
    }

    @Override
    public void loadMoreData() {
        for (int i = 0; i < 8; i++) {
            dataList.add(new CopyBook("R.drawable.barack", "颜真卿", "多宝塔碑" + i));
        }
        copyBookView.showCopyBookList(dataList);
    }


}
