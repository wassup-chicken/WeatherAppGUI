//Retrieve weather data from API
//data from the external API and return it

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {

    public static JSONObject getWeatherData(String locationName) {
        locationName = locationName.replaceAll(" ", "+");

        final String API_KEY = System.getenv("API_KEY");

        //build API URL with location param
        String urlString = "http://api.weatherapi.com/v1/current.json?key=" + API_KEY + "&q=" + locationName + "&aqi=no";

        try {
            HttpURLConnection connection = fetchApiResponse(urlString);

           if (connection.getResponseCode() != 200) {
               System.out.println("Error: Could not connect to API");
               return null;
           } else {
               StringBuilder resultJson = new StringBuilder();
               Scanner scanner = new Scanner(connection.getInputStream());

               while(scanner.hasNext()) {
                   resultJson.append(scanner.nextLine());
               }

               scanner.close();

               connection.disconnect();

               JSONParser parser = new JSONParser();

               JSONObject jsonResult = (JSONObject) parser.parse(String.valueOf(resultJson));

               JSONObject current = (JSONObject) jsonResult.get("current");

               JSONObject condition = (JSONObject) current.get("condition");

               Long weatherCode = (Long) condition.get("code");

               String weatherCondition = convertWeatherweatherCode(weatherCode);

               Double temperature = (Double) current.get("temp_f");

               Long humidity = (Long) current.get("humidity");

               Double windSpeed = (Double) current.get("wind_mph");

                JSONObject weatherData = new JSONObject();

                weatherData.put("temperature", temperature);
                weatherData.put("weather_condition", weatherCondition);
                weatherData.put("humidity", humidity);
                weatherData.put("windspeed", windSpeed);

                return weatherData;

           }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString) {
        try {

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.connect();
            return connection;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String convertWeatherweatherCode(long weatherCode) {
        String weatherCondition = "";
        if (weatherCode == 1000L) {
            weatherCondition = "Clear";
        } else if (weatherCode == 1063L || weatherCode == 1066L || weatherCode == 1069L || weatherCode == 1072L || weatherCode == 1180L || weatherCode == 1183L || weatherCode == 1186L || weatherCode == 1189L || weatherCode == 1192L || weatherCode == 1195L || weatherCode == 1198L || weatherCode == 1201L || weatherCode == 1240L || weatherCode == 1243L || weatherCode == 1246L || weatherCode == 1273L || weatherCode == 1276L) {
            weatherCondition = "Rain";
        } else if (weatherCode == 1114L || weatherCode == 1117L || weatherCode == 1210L || weatherCode == 1213L || weatherCode == 1216L || weatherCode == 1219L || weatherCode == 1222L || weatherCode == 1225L || weatherCode == 1204L || weatherCode == 1207L || weatherCode == 1252L || weatherCode == 1255L || weatherCode == 1258L || weatherCode == 1279L || weatherCode == 1282L) {
            weatherCondition = "Snow";
        } else if (weatherCode == 1003L || weatherCode == 1006L || weatherCode == 1009L || weatherCode == 1030L || weatherCode == 1069L || weatherCode == 1072L || weatherCode == 1087L || weatherCode == 1135L || weatherCode == 1147L || weatherCode == 1237L || weatherCode == 1240L || weatherCode == 1243L || weatherCode == 1246L || weatherCode == 1249L || weatherCode == 1252L || weatherCode == 1255L || weatherCode == 1261L || weatherCode == 1264L) {
            weatherCondition = "Cloudy";
        } else {
            weatherCondition = "Unknown";
        }

        return weatherCondition;
    }
}
