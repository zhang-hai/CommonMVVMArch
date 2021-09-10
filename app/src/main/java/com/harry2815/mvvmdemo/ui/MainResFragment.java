package com.harry2815.mvvmdemo.ui;

import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.harry2815.mvvm.arch.ui.BaseWithViewBindingFragment;
import com.harry2815.mvvmdemo.R;
import com.harry2815.mvvmdemo.bean.FaqBean;
import com.harry2815.mvvmdemo.databinding.FragmentMainResBinding;
import com.harry2815.mvvmdemo.model.response.FaqListResponse;
import com.harry2815.mvvmdemo.viewmodel.MainResViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghai on 2019/6/3.
 * function：
 */
public class MainResFragment extends BaseWithViewBindingFragment<MainResViewModel, FragmentMainResBinding> {
    private BaseQuickAdapter<FaqBean, BaseViewHolder> mAdapter;
    private List<FaqBean> mItems = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_res;
    }

    @Override
    protected void init(){
        registerObserve(mViewModel.key,new Observer<FaqListResponse>() {
            @Override
            public void onChanged(@Nullable FaqListResponse faqListResponse) {
                if(faqListResponse != null){
                    mItems.clear();
                    mItems.addAll(faqListResponse.getDataList());
                    mAdapter.setNewData(mItems);
                }else {
                    //异常

                }
            }
        });

        initRecyclerView();

        mViewModel.getFaqList();
    }

    private void initRecyclerView(){
        mAdapter = new BaseQuickAdapter<FaqBean, BaseViewHolder>(R.layout.rv_item_layout,mItems) {
            @Override
            protected void convert(BaseViewHolder helper, FaqBean item) {
                helper.setText(R.id.tv_item_name,item.getUserName());
                helper.setText(R.id.tv_item_content, TextUtils.isEmpty(item.getQuestion()) ? "" : item.getQuestion());
                helper.setText(R.id.tv_item_from,TextUtils.isEmpty(item.getSource()) ? "" : item.getSource());
            }
        };

        mViewBinding.recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        mViewBinding.recyclerview.setAdapter(mAdapter);
    }

}
