package canh.tan.nguye.newsreader.interfaces;

import canh.tan.nguye.newsreader.model.IconBetterIdea;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IconBetterIdeaService {
    @GET()
    Call<IconBetterIdea> getIcon(@Url String url);
}
