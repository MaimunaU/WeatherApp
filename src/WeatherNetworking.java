import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherNetworking {

    private String baseUrl;
    private String apiKey;

    public WeatherNetworking()
    {
        baseUrl = "http://api.weatherapi.com/v1";
        apiKey = "ff7b3e38bdb74382b1d165343221805";
    }

    public String makeAPICallForCurrentWeather(String zipCode)
    {
        String endPoint = "/current.json";
        String url = baseUrl + endPoint + "?q=" + zipCode;

        try {
            URI myUri = URI.create(url); // creates a URI object from the url string
            HttpRequest request = HttpRequest.newBuilder().uri(myUri).build();
            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Weather parseCurrentWeather(String json)
    {
        JSONObject jsonObj = new JSONObject(json);
        JSONObject currentObj = jsonObj.getJSONObject("current");
        double c = currentObj.getDouble("temp_c");
        double f = currentObj.getDouble("temp_f");
        JSONObject conditionObj = currentObj.getJSONObject("condition");
        String condition = conditionObj.getString("text");
        String icon = conditionObj.getString("icon");
        icon = "https" + icon;
        Weather weather = new Weather(f, c, condition, icon);
        return weather;

    }
}
