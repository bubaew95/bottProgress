package pro.webshishan.recyclerbotomprogressbar.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import pro.webshishan.recyclerbotomprogressbar.models.Model;
import pro.webshishan.recyclerbotomprogressbar.R;

/**
 * Created by shiShan.inc on 21.03.2017.
 */

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Model> mList;
    private LayoutInflater mLInflater;
    private RecyclerView mRecyclerView;
    private OnLoadMore mOnLoadMore;
    private boolean isLoading;

    private static final int ITEM_VIEW = 0;
    private static final int ITEM_LOADING = 1;

    private int totalItemCount;
    private int lastVisibleItem;
    private int visibleThreashold = 5;

    public Adapter(Context context, List<Model> list, RecyclerView recyclerView) {
        mLInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mContext = context;
        mList = list;
        mRecyclerView = recyclerView;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();

                lastVisibleItem = llm.findLastVisibleItemPosition();
                totalItemCount = llm.getItemCount();

                if(!isLoading && totalItemCount <= (lastVisibleItem + visibleThreashold))
                {
                    if(mOnLoadMore != null)
                    {
                        mOnLoadMore.onLoadMore();
                    }
                    isLoading =true;
                }

            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_VIEW) {
            View v = mLInflater.inflate(R.layout.layout_item_card, parent, false);
            return new RecyclerViewHolder(v);
        } else if(viewType == ITEM_LOADING){
            View v1 = mLInflater.inflate(R.layout.layout_progress_bar, parent, false);
            return new ProgressViewHolder(v1);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int getViewType = holder.getItemViewType();

        if(getViewType == ITEM_VIEW) {

            Model mod = mList.get(position);
            RecyclerViewHolder hold = (RecyclerViewHolder) holder;

            hold.mTitle.setText(mod.getTitle());
            hold.mText.setText(mod.getText());
        } else if(getViewType == ITEM_LOADING) {

            ProgressViewHolder progressHold = (ProgressViewHolder) holder;
            progressHold.mProgressBar.setIndeterminate(true);

        }

    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position) != null ? ITEM_VIEW : ITEM_LOADING;
    }

    @Override
    public int getItemCount() {
        return (mList != null) ? mList.size() : 0;
    }

    public void setOnLoadMore(OnLoadMore onLoadMore)
    {
        mOnLoadMore = onLoadMore;
    }


    public void setIsLoading(boolean param)
    {
        isLoading = param;
    }

    //Для наших ITEMS
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle, mText;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mText = (TextView) itemView.findViewById(R.id.tv_text);
        }
    }

    //Для Вывода Progress BAr
    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar mProgressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }
    }

    //Интерфейс
    public interface OnLoadMore
    {
        public void onLoadMore();
    }

}
