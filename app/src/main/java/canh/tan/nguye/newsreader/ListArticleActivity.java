package canh.tan.nguye.newsreader;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import canh.tan.nguye.newsreader.adapter.ListArticleAdapter;
import canh.tan.nguye.newsreader.common.Common;
import canh.tan.nguye.newsreader.interfaces.NewsService;
import canh.tan.nguye.newsreader.model.Article;
import canh.tan.nguye.newsreader.model.News;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListArticleActivity extends AppCompatActivity {

    DiagonalLayout diagonalLayout;
    KenBurnsView img_top;
    TextView txt_top_name, txt_top_title;

    String source = "", webUrl = "";

    SwipeRefreshLayout swipeRefreshLayout;

    NewsService mService;

    AlertDialog dialog;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ListArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_article);
        mService = Common.getNewsSource();

        source = getIntent().getStringExtra("source");

        img_top = findViewById(R.id.img_top_list_article);
        txt_top_name = findViewById(R.id.txt_top_name_list_article);
        txt_top_title = findViewById(R.id.txt_top_title_list_article);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        diagonalLayout = findViewById(R.id.diagonal);

        recyclerView = findViewById(R.id.rv_bottom_list_article);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        dialog = new SpotsDialog.Builder().setContext(this).build();

        dialog.show();



        loadArticles(false);
    }

    private void loadArticles(boolean isRefresh) {
        if (!isRefresh){
           mService.getNewsArticle(Common.getUrl(source, Common.API_KEY)).enqueue(new Callback<News>() {
               @Override
               public void onResponse(Call<News> call, final Response<News> response) {
                   //Log.e("Request", call.request().toString());
                   Picasso.get().load(response.body().getArticles().get(0).getUrlToImage()).into(img_top);
                   txt_top_name.setText(response.body().getArticles().get(0).getAuthor());
                   txt_top_title.setText(response.body().getArticles().get(0).getTitle());

                   diagonalLayout.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           Intent intent = new Intent(ListArticleActivity.this, DetailArticleActivity.class);
                           intent.putExtra("webUrl", response.body().getArticles().get(0).getUrl());
                           startActivity(intent);
                       }
                   });

                   News news = response.body();
                   news.getArticles().remove(0);
                   adapter = new ListArticleAdapter(ListArticleActivity.this, news);
                   adapter.notifyDataSetChanged();
                   recyclerView.setAdapter(adapter);



                   dialog.dismiss();
               }

               @Override
               public void onFailure(Call<News> call, Throwable t) {

               }
           });
        }
    }
}
