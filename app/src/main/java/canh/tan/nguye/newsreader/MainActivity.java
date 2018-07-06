package canh.tan.nguye.newsreader;

import android.app.AlertDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import canh.tan.nguye.newsreader.adapter.ListNewsSourceAdapter;
import canh.tan.nguye.newsreader.common.Common;
import canh.tan.nguye.newsreader.interfaces.NewsService;
import canh.tan.nguye.newsreader.model.Website;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListNewsSourceAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    SwipeRefreshLayout swipeRefreshLayout;

    NewsService mService;

    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mService = Common.getNewsSource();

        recyclerView = findViewById(R.id.rv_news_source);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        dialog = new SpotsDialog.Builder().setContext(this).build();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNewsSources(true);
            }
        });

        loadNewsSources(false);
    }

    private void loadNewsSources(boolean isRefresh) {
        //
        dialog.show();

        mService.getSource().enqueue(new Callback<Website>() {
            @Override
            public void onResponse(Call<Website> call, Response<Website> response) {
                //Toast.makeText(MainActivity.this, response.body().getSources().get(0).getName(), Toast.LENGTH_SHORT).show();
                Website website = response.body();
                if (website != null){
                    adapter = new ListNewsSourceAdapter(MainActivity.this, website);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                }
                else {
                    Toast.makeText(MainActivity.this, "null", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<Website> call, Throwable t) {
                //Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error_Connect_server", t.getMessage());
            }
        });

    }
}
