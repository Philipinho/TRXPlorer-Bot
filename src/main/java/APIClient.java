import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class APIClient {
    private  final String API_URL = "https://api.trxplorer.io/v2/";
    private OkHttpClient httpClient = new OkHttpClient();

    public  String doGETRequest(String endPoint){
        final StringBuilder result = new StringBuilder();

        Request request = new Request.Builder()
                .url(API_URL + endPoint)
                .build();

        Response response = null;
        try {
            response = httpClient.newCall(request).execute();

            result.append(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
