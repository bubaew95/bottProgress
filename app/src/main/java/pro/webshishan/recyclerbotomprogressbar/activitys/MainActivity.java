package pro.webshishan.recyclerbotomprogressbar.activitys;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pro.webshishan.recyclerbotomprogressbar.R;
import pro.webshishan.recyclerbotomprogressbar.adapters.Adapter;
import pro.webshishan.recyclerbotomprogressbar.models.Model;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<Model> mItems = new ArrayList<>();
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mItems = getItems();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(llm);

        mAdapter = new Adapter(this, mItems, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnLoadMore(new Adapter.OnLoadMore() {
            @Override
            public void onLoadMore() {

                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        mItems.add(null);
                        mAdapter.notifyItemInserted(mItems.size() - 1);
                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mItems.remove(mItems.size() - 1);
                        mAdapter.notifyItemRemoved(mItems.size());

                        int index = mItems.size();
                        for(int i = index; i < index + 10; i++)
                        {
                            Model mod = new Model();
                            mod.setTitle("Title #" + i);
                            mod.setText("Text #" + i);
                            mItems.add(mod);
                        }
                        mAdapter.setIsLoading(false);
                    }
                }, 1000);

            }
        });

    }

    private List<Model> getItems()
    {
        List<Model> mList = new ArrayList<>();

        for(int i= 0; i < 10; i++)
        {
            Model model = new Model();
            model.setTitle("Title #" + i);
            model.setText("TEXT #" + i);
            mList.add(model);
        }
        return mList;
    }

}
