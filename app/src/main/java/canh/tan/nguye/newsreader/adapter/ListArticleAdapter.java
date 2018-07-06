package canh.tan.nguye.newsreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;

import canh.tan.nguye.newsreader.DetailArticleActivity;
import canh.tan.nguye.newsreader.R;
import canh.tan.nguye.newsreader.common.Common;
import canh.tan.nguye.newsreader.common.ISO8601Parser;
import canh.tan.nguye.newsreader.interfaces.ItemOnClickListener;
import canh.tan.nguye.newsreader.interfaces.NewsService;
import canh.tan.nguye.newsreader.model.News;
import de.hdodenhof.circleimageview.CircleImageView;

class ListArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CircleImageView circleImageView;
    TextView txt_title;
    RelativeTimeTextView txt_time;

    ItemOnClickListener listener;

    public ListArticleViewHolder(View itemView) {
        super(itemView);

        circleImageView = itemView.findViewById(R.id.circleView_item_news_article);
        txt_title = itemView.findViewById(R.id.txt_title_item_news_article);
        txt_time = itemView.findViewById(R.id.txt_time_item_news_article);

        itemView.setOnClickListener(this);
    }

    public void setListener(ItemOnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onCLick(v, getAdapterPosition(), false);
    }
}

public class ListArticleAdapter extends RecyclerView.Adapter<ListArticleViewHolder>{
    Context context;
    News news;

    NewsService mService;

    public ListArticleAdapter(Context context, News news) {
        this.context = context;
        this.news = news;
        mService = Common.getNewsSource();
    }

    @NonNull
    @Override
    public ListArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListArticleViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListArticleViewHolder holder, int position) {
        Picasso.get().load(news.getArticles().get(position).getUrlToImage()).into(holder.circleImageView);

        if (news.getArticles().get(position).getTitle().length() > 65){
            holder.txt_title.setText(news.getArticles().get(position).getTitle().substring(0, 65) + "...");
        }else {
            holder.txt_title.setText(news.getArticles().get(position).getTitle());
        }

        try {
            Date date = null;
            date = ISO8601Parser.parse(news.getArticles().get(position).getPublishedAt());

            if (date != null){
                holder.txt_time.setReferenceTime(date.getTime());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.setListener(new ItemOnClickListener() {
            @Override
            public void onCLick(View v, int position, boolean isLongClick) {
                Intent intent = new Intent(context, DetailArticleActivity.class);
                intent.putExtra("webUrl", news.getArticles().get(position).getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return news.getArticles().size();
    }
}
