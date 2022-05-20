import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class WeatherGUI implements ActionListener, ItemListener {
    private JLabel weatherInfo;
    private JTextField zipEntryField;
    private WeatherNetworking client;
    private JLabel pictureLabel;
    private Weather weather;

    public WeatherGUI()
    {
        weatherInfo = new JLabel(" ");
        zipEntryField = new JTextField();
        client = new WeatherNetworking();
        ImageIcon image = new ImageIcon("src/placeholder.jpg");
        Image imageData = image.getImage();
        Image scaledImage = imageData.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);
        pictureLabel = new JLabel(image);
        weather = new Weather(0.0, 0.0, "", "");
        setupGui();
    }

    private void setupGui()
    {
        JFrame frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Current Weather");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.blue);

        JPanel logoWelcomePanel = new JPanel();
        logoWelcomePanel.add(welcomeLabel);

        JPanel entryPanel = new JPanel();
        JLabel zipLabel = new JLabel("Enter Zip Code: ");
        zipEntryField = new JTextField(10);
        JButton submitButton = new JButton("Submit");
        JButton clearButton = new JButton("Clear");
        JCheckBox celsius = new JCheckBox("Show Celsius");
        entryPanel.add(zipLabel);
        entryPanel.add(zipEntryField);
        entryPanel.add(submitButton);
        entryPanel.add(clearButton);
        entryPanel.add(celsius);

        JPanel infoPanel = new JPanel();
        infoPanel.add(weatherInfo);
        infoPanel.add(pictureLabel);

        frame.add(logoWelcomePanel, BorderLayout.NORTH);
        frame.add(entryPanel, BorderLayout.CENTER);
        frame.add(infoPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);
        celsius.addItemListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    private void loadWeatherInfo()
    {
        String zip = zipEntryField.getText();
        WeatherAPI api = new WeatherAPI();
        weather = api.getCurrentWeather(zip);

        String info = "Temperature: " + weather.getF() + "F" + "       Condition: " + weather.getCondition();
        weatherInfo.setText(info);

        try {
            URL imageURL = new URL(weather.getIcon());
            BufferedImage image = ImageIO.read(imageURL);
            ImageIcon img = new ImageIcon(image);
            pictureLabel.setIcon(img);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());
        String text = button.getText();

        if (text.equals("Submit"))
        {
            loadWeatherInfo();
        }
        else if (text.equals("Clear"))
        {
            zipEntryField.setText("");
        }
    }

    public void itemStateChanged(ItemEvent e) {
        JCheckBox checkBox = (JCheckBox) (e.getSource());

        if (checkBox.isSelected())
        {
            String info = "Temperature: " + weather.getC() + "C" + "       Condition: " + weather.getCondition();
            weatherInfo.setText(info);
        }
        else
        {
            String info = "Temperature: " + weather.getF() + "F" + "       Condition: " + weather.getCondition();
            weatherInfo.setText(info);
        }
    };
}
