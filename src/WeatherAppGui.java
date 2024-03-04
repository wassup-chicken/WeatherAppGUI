import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class WeatherAppGui extends JFrame {
    private JSONObject weatherData;
    public WeatherAppGui() {
        super("Weather App");

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(450, 650);

        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents() {
        JTextField searchTextField = new JTextField();

        searchTextField.setBounds(15, 15, 351, 45);

        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));

        add(searchTextField);



        JLabel weatherConditionImage = new JLabel(loadImage("cloudy.png"));
        weatherConditionImage.setBounds(0, 125, 450, 217);
        add(weatherConditionImage);

        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment((SwingConstants.CENTER));
        add(temperatureText);

        JLabel weatherConditionDesc = new JLabel(("Cloudy"));
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        JLabel humidityImage = new JLabel(loadImage("humidity.png"));
        humidityImage.setBounds(15, 500, 74, 66 );
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(90, 500, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        JLabel windspeedImage = new JLabel(loadImage("windspeed.png"));
        windspeedImage.setBounds(220, 500, 74, 66);
        add(windspeedImage);

        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(310, 500, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);

        JButton searchButton = new JButton(loadImage("search.png"));

        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375, 13, 47, 45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = searchTextField.getText();

                if (userInput.isEmpty()) {
                    return;
                }
               weatherData = WeatherApp.getWeatherData(userInput);

               String weatherCondition = (String) weatherData.get("weather_condition");

               switch(weatherCondition) {
                   case "Clear":
                       weatherConditionImage.setIcon(loadImage("clear.png"));
                       break;
                   case "Cloudy":
                       weatherConditionImage.setIcon(loadImage("cloudy.png"));
                       break;
                   case "Rain":
                       weatherConditionImage.setIcon(loadImage("rain.png"));
                       break;
                   case "Snow":
                       weatherConditionImage.setIcon(loadImage("snow.png"));
                       break;
               }

               weatherConditionDesc.setText(weatherCondition);

                Double temperature = (Double) weatherData.get("temperature");

                temperatureText.setText(String.valueOf(temperature));

                Long humidity = (Long) weatherData.get("humidity");

                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                Double windspeed = (Double) weatherData.get("windspeed");

                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "mph</html>");


            }
        });
        add(searchButton);
    }

    private ImageIcon loadImage(String resourcePath) {
        try {
            return new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource(resourcePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }

}
