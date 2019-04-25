package com.hdl.words.presenter.main.recite;

import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.VocabAddResultBean;
import com.hdl.words.Beans.VocabDeleteResultBean;
import com.hdl.words.base.BasePresenter;
import com.hdl.words.fragment.main.recite.WordListFragment;
import com.hdl.words.model.IGETAddVocabWordsResult;
import com.hdl.words.model.IGETDeleteVocabWordsResult;
import com.hdl.words.model.WordModelImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date 2019/4/23 22:33
 * author hdl
 * Description:
 */
public class WordListPresenterImpl extends BasePresenter<WordListFragment> implements IWordListPresenter {
    private WordModelImpl model;
    public WordListPresenterImpl(WordListFragment view) {
        super(view);
        model = WordModelImpl.getInstance();
    }

    @Override
    public void requestDisLike(String username, String word, final ImageView img, final int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBean.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IGETDeleteVocabWordsResult request = retrofit.create(IGETDeleteVocabWordsResult.class);
        Call<VocabDeleteResultBean> call = request.getCall(username, word);
        call.enqueue(new Callback<VocabDeleteResultBean>() {
            @Override
            public void onResponse(@NonNull Call<VocabDeleteResultBean> call, @NonNull Response<VocabDeleteResultBean> response) {
                VocabDeleteResultBean bean = response.body();
                try {
                    if (bean.isResult()) {
                        model.getDataList().get(pos).setState(0);
                        mView.setDislike(img,pos);
                    } else {
                        model.getDataList().get(pos).setState(1);
                        mView.setLike(img,pos);
                    }
                    mView.showToast(bean.getMessage());
                } catch (NullPointerException e){
                    Log.e(TAG,e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VocabDeleteResultBean> call, @NonNull Throwable t) {
                mView.showToast("网络错误");
            }
        });
    }

    @Override
    public void requestLike(String username, String word, final ImageView img, final int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBean.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IGETAddVocabWordsResult request = retrofit.create(IGETAddVocabWordsResult.class);
        Call<VocabAddResultBean> call = request.getCall(username, word);
        call.enqueue(new Callback<VocabAddResultBean>() {
            @Override
            public void onResponse(@NonNull Call<VocabAddResultBean> call, @NonNull Response<VocabAddResultBean> response) {
                VocabAddResultBean bean = response.body();
                try {
                    if (bean.isResult()) {
                        model.getDataList().get(pos).setState(1);
                        mView.setLike(img,pos);
                    } else {
                        model.getDataList().get(pos).setState(0);
                        mView.setDislike(img,pos);
                    }
                    mView.showToast(bean.getMessage());
                } catch (NullPointerException e){
                    Log.e(TAG,e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VocabAddResultBean> call, @NonNull Throwable t) {
                mView.showToast("网络错误");
            }
        });
    }

    @Override
    public void requestState(String username, ImageView img, int pos) {
        if (model.getDataList().get(pos).getState() == 0) {
            requestLike(username,model.getDataList().get(pos).getWord(),img,pos);
        } else {
            requestDisLike(username,model.getDataList().get(pos).getWord(),img,pos);
        }
    }
}
