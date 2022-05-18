public class Weather {
    private double c;
    private double f;
    private String condition;
    private String icon;

    public Weather(double c, double f, String condition, String icon)
    {
        this.c = c;
        this.f = f;
        this.condition = condition;
        this.icon = icon;
    }

    public String toString()
    {
        return "Farenheit: " + f + "\nCelcius: " + c + "\nCondition: " + condition + "\nIcon: " + icon;
    }
}
