package canh.tan.nguye.newsreader.common;

import canh.tan.nguye.newsreader.interfaces.IconBetterIdeaService;
import canh.tan.nguye.newsreader.interfaces.NewsService;
import canh.tan.nguye.newsreader.remote.IconBetterIdeaClient;
import canh.tan.nguye.newsreader.remote.RetrofitClient;

public class Common {
    public static final String API_KEY = "4570783b10fb4bfd981e32717cc321ef";

    private static final String BASE_URL = "https://newsapi.org/";

    public static NewsService getNewsSource(){
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIcon(){
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    public static String getUrl(String source, String apiKey){
        StringBuilder stringBuilder = new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");

        return stringBuilder
                .append(source)
                .append("&apiKey=")
                .append(apiKey).toString();
    }
}
