import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class WeatherGUI implements ActionListener {
    private JLabel weatherInfo;
    private JTextField zipEntryField;
    private WeatherNetworking client;

    public WeatherGUI()
    {
        weatherInfo = new JLabel("waiting for zipcode");
        zipEntryField = new JTextField();
        client = new WeatherNetworking();
        setupGui();
    }

    private void setupGui()
    {
        JFrame frame = new JFrame("Weather App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcomeLabel = new JLabel("Current Weather");
        welcomeLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.green);

        JPanel logoWelcomePanel = new JPanel();
        logoWelcomePanel.add(welcomeLabel);

        JPanel entryPanel = new JPanel();
        JLabel zipLabel = new JLabel("Enter Zip Code: ");
        zipEntryField = new JTextField(10);
        JButton submitButton = new JButton("Submit");
        JButton clearButton = new JButton("Clear");
        entryPanel.add(zipLabel);
        entryPanel.add(zipEntryField);
        entryPanel.add(submitButton);
        entryPanel.add(clearButton);

        JPanel infoPanel = new JPanel();
        infoPanel.add(weatherInfo);

        frame.add(logoWelcomePanel, BorderLayout.NORTH);
        frame.add(entryPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(this);
        clearButton.addActionListener(this);

        frame.pack();
        frame.setVisible(true);
    }

    private void loadWeatherInfo()
    {
        String zip = zipEntryField.getText();
        WeatherAPI api = new WeatherAPI();
        Weather weather = api.getCurrentWeather(zip);

        String info = "Temperature: " + weather.getF() + "F"
                + "       Condition: " + weather.getCondition();


        /*
        try {
            URL imageURL = new URL(weather.getPosterPath());
            BufferedImage image = ImageIO.read(imageURL);

            ImageIcon image = new ImageIcon("src/placeholder.jpg");
        Image imageData = image.getImage();
        Image scaledImage = imageData.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(scaledImage);
        JLabel pictureLabel = new JLabel(image);
        }

         */
    }

    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) (e.getSource());  // cast source to JButton
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
}
