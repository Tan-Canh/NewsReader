package canh.tan.nguye.newsreader.interfaces;

import java.util.List;

import canh.tan.nguye.newsreader.common.Common;
import canh.tan.nguye.newsreader.model.Article;
import canh.tan.nguye.newsreader.model.News;
import canh.tan.nguye.newsreader.model.Website;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface NewsService {
    @GET("v2/sources?language=en&apiKey=" + Common.API_KEY)
    Call<Website> getSource();

    @GET()
    Call<News> getNewsArticle(@Url String url);

}
