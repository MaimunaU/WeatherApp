public class WeatherAPI {

    private WeatherNetworking networker;

    public WeatherAPI() {
        this.networker = new WeatherNetworking();
    }

    public Weather getCurrentWeather(String zipCode)
    {
        String response = networker.makeAPICallForCurrentWeather(zipCode);
        Weather obj = networker.parseCurrentWeather(response);
        return obj;

    }
}