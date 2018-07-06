package canh.tan.nguye.newsreader.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import canh.tan.nguye.newsreader.ListArticleActivity;
import canh.tan.nguye.newsreader.R;
import canh.tan.nguye.newsreader.common.Common;
import canh.tan.nguye.newsreader.interfaces.IconBetterIdeaService;
import canh.tan.nguye.newsreader.interfaces.ItemOnClickListener;
import canh.tan.nguye.newsreader.interfaces.NewsService;
import canh.tan.nguye.newsreader.model.IconBetterIdea;
import canh.tan.nguye.newsreader.model.Website;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ListNewsSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    CircleImageView circleView;
    TextView txt_title;

    ItemOnClickListener listener;

    public void setListener(ItemOnClickListener listener) {
        this.listener = listener;
    }

    public ListNewsSourceViewHolder(View itemView) {
        super(itemView);

        circleView = itemView.findViewById(R.id.circleView_item_news_source);
        txt_title = itemView.findViewById(R.id.txt_item_news_source);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        listener.onCLick(v, getAdapterPosition(), false);
    }
}

public class ListNewsSourceAdapter  extends RecyclerView.Adapter<ListNewsSourceViewHolder>{
    Context context;
    Website website;

    IconBetterIdeaService mService;

    public ListNewsSourceAdapter(Context context, Website website) {
        this.context = context;
        this.website = website;

        mService = Common.getIcon();
    }

    @NonNull
    @Override
    public ListNewsSourceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ListNewsSourceViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news_source, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ListNewsSourceViewHolder holder, int position) {

        final StringBuilder stringBuilder = new StringBuilder("https://i.olsh.me/allicons.json?url=");
        stringBuilder.append(website.getSources().get(position).getUrl());

        mService.getIcon(stringBuilder.toString()).enqueue(new Callback<IconBetterIdea>() {
            @Override
            public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
                if (response.body().getIcons().size() > 0){
                    Picasso.get().load(response.body().getIcons().get(0).getUrl()).into(holder.circleView);
                }
            }

            @Override
            public void onFailure(Call<IconBetterIdea> call, Throwable t) {
                Log.e("ERROR_TAKE_IMAGE", t.getMessage());
            }
        });


        holder.txt_title.setText(website.getSources().get(position).getName());

        holder.setListener(new ItemOnClickListener() {
            @Override
            public void onCLick(View v, int position, boolean isLongClick) {
                Intent intent = new Intent(context, ListArticleActivity.class);
                intent.putExtra("source", website.getSources().get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return website.getSources().size();
    }
}
