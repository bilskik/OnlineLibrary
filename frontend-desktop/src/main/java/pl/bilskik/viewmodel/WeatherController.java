package pl.bilskik.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pl.bilskik.DI.DIContainer;
import pl.bilskik.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherController {
//    private final WeatherService weatherService;
//    private final GeoPosition geoPosition;
//    private final Temperature temperature;
//    private final WeatherConditions weatherConditions;
//    private final WeatherConditionsDaily weatherConditionsDaily;
//    private final AlarmDaily alarmDaily;
//    private final AlarmFiveDays alarmFiveDays;
//    private final static String BASE_URL = "http://dataservice.accuweather.com/";
//    private String API_KEY = "";
//    private final static String LOCATION_URL = "locations/v1/cities/search";
//    private final static String FORECAST_ONE_DAY_URL = "forecasts/v1/daily/1day/";
//    private final static String FORECAST_ONE_HOUR_URL = "forecasts/v1/hourly/1hour/";
//    private final static String CURRENT_FORECAST_URL = "currentconditions/v1/";
//    private final static String  ONE_DAY_ALARMS_URL  = "alarms/v1/1day/";
//    private final static String FIVE_DAY_ALARMS_URL = "alarms/v1/5day/";

//    public String LOCATION_KEY = "";
//    @FXML
//    public TextField locationField;
//    @FXML
//    public Text cityId;
//    @FXML
//    public TextArea textArea;
//    @FXML
//    public TextField loginField;
//
//    public WeatherController() throws IOException {
//        DIContainer di = DIContainer.getInstance();
//        weatherService = di.resolve(WeatherService.class);
//        geoPosition = di.resolve(GeoPosition.class);
//        temperature = di.resolve(Temperature.class);
//        weatherConditions = di.resolve(WeatherConditions.class);
//        weatherConditionsDaily = di.resolve(WeatherConditionsDaily.class);
//        alarmDaily = di.resolve(AlarmDaily.class);
//        alarmFiveDays = di.resolve(AlarmFiveDays.class);
//        readApiKey();
//    }
//
//    private void readApiKey() throws IOException {
//        BufferedReader reader = new BufferedReader(new FileReader("config.txt"));
//        StringBuilder stringBuilder = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            stringBuilder.append(line);
//        }
//        reader.close();
//        API_KEY = stringBuilder.toString();
//    }
//
//    public void getLocations(ActionEvent e) throws URISyntaxException {
//        if(!locationField.getText().equals("") && locationField != null) {
//            URI uri = new URI(createLocationUrl(LOCATION_URL, locationField.getText()));
//            HttpResponse<String> res = createGetRequest(uri);
//            if(res.body() != null && !res.body().equals("") && !res.body().equals("[]") && !res.body().equals("{}")) {
//                LOCATION_KEY = weatherService.getLocationKey(res.body());
//                weatherService.getGeoPosition(res.body());
//                cityId.setText(locationField.getText());
//                cityId.setFont(new Font(20));
//                textArea.textProperty().bind(geoPosition.getLatitude());
//            }
//            else {
//                cityId.setText("Error -> cannot find given city in database!");
//            }
//        }
//    }
//    public void getCurrentWeatherConditions(ActionEvent e) throws URISyntaxException {
//        if(!LOCATION_KEY.equals("")) {
//            URI uri = new URI(createCurrentForecastUrl());
//            HttpResponse<String> res = createGetRequest(uri);
//            if(res.body() != null && !res.body().equals("") && !res.body().equals("[]") && !res.body().equals("{}")) {
//                weatherService.getCurrentWeatherConditions(res.body());
////                textArea.setText(weatherConditions);
//                textArea.textProperty().bind(weatherConditions.getCurrentWeatherConditions());
//            } else {
//                textArea.setText("Error -> cannot perform API call!");
//            }
//        }
//    }
//    public void getWeatherConditionsForOneHour(ActionEvent e) throws URISyntaxException {
//        if(!LOCATION_KEY.equals("")) {
//            URI uri = new URI(createOneHourForecastUrl());
//            HttpResponse<String> res = createGetRequest(uri);
//            if(res.body() != null && !res.body().equals("") && !res.body().equals("[]") && !res.body().equals("{}")) {
//                weatherService.getTemperature(res.body());
//                textArea.textProperty().bind(temperature.getTemperature());
//            } else {
//                textArea.setText("Error -> Cannot perform API call!");
//            }
//        }
//    }
//    public void getWeatherConditionsForOneDay(ActionEvent e) throws URISyntaxException {
//        if(!LOCATION_KEY.equals("")) {
//            URI uri = new URI(createOneDayForecastUrl());
//            HttpResponse<String> res = createGetRequest(uri);
//            if(res.body() != null && !res.body().equals("") && !res.body().equals("[]") && !res.body().equals("{}")) {
//                weatherService.getWeatherCategory(res.body());
//                textArea.textProperty().bind(weatherConditionsDaily.getCurrentWeatherConditions());
//            } else {
//                textArea.setText("Error -> cannot perform API call!");
//            }
//        }
//
//    }
//
//    public void getWeatherAlarmsForOneDay(ActionEvent e) throws URISyntaxException {
//        if(!LOCATION_KEY.equals("")) {
//            URI uri = new URI(createWeatherAlarmOneDay());
//            HttpResponse<String> res = createGetRequest(uri);
//            if(res.body().equals("[]")) {
//                weatherService.getWeatherAlarmForOneDay(res.body());
//                textArea.textProperty().bind(alarmDaily.getAlarm());
//            } else if(res.body() == null || res.body().equals("")  || res.body().equals("{}")) {
//                textArea.setText("Wooops.... Cannot perform API call");
//            } else {
//                weatherService.getWeatherAlarmForOneDay(res.body());
//                textArea.textProperty().bind(alarmDaily.getAlarm());
//            }
//        }
//    }
//    public void getWeatherAlarmsForFiveDays(ActionEvent e) throws URISyntaxException {
//        if(!LOCATION_KEY.equals("")) {
//            URI uri = new URI(createWeatherAlarmFiveDay());
//            HttpResponse<String> res = createGetRequest(uri);
//            if(res.body().equals("[]")) {
//                weatherService.getWeatherAlarmForFiveDays(res.body());
//                textArea.textProperty().bind(alarmFiveDays.getNoAlarmProperty());
//            }
//            else if(res.body() == null || res.body().equals("")  || res.body().equals("{}")) {
//                textArea.setText("Wooops.... Cannot perform API call");
//            } else {
//                weatherService.getWeatherAlarmForFiveDays(res.body());
//                StringProperty allAlarms = convertAlarmArray();
//                textArea.textProperty().bind(allAlarms);
//            }
//        }
//    }
//
//    private String createWeatherAlarmFiveDay() {
//        return BASE_URL + FIVE_DAY_ALARMS_URL + LOCATION_KEY + "?apikey=" + API_KEY;
//    }
//    private String createWeatherAlarmOneDay() {
//        return BASE_URL + ONE_DAY_ALARMS_URL +  LOCATION_KEY + "?apikey=" + API_KEY;
//    }
//
//    private String createCurrentForecastUrl() {
//        return BASE_URL + CURRENT_FORECAST_URL + LOCATION_KEY + "?apikey=" + API_KEY;
//    }
//    private String createLocationUrl(String url, String queryParameters) {
//        return BASE_URL + url + "?" + "apikey=" + API_KEY + "&q=" + queryParameters;
//    }
//    private String createOneDayForecastUrl() {
//        return BASE_URL + FORECAST_ONE_DAY_URL + LOCATION_KEY + "?apikey=" + API_KEY;
//    }
//    private String createOneHourForecastUrl() {
//        return BASE_URL + FORECAST_ONE_HOUR_URL + LOCATION_KEY +"?apikey=" + API_KEY;
//    }
//    private HttpResponse<String> createGetRequest(URI uri) {
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(uri)
//                .GET()
//                .build();
//        HttpResponse<String> res = null;
//        try {
//            res = client.send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        return res;
//    }
//    private StringProperty convertAlarmArray() {
//        StringProperty allAlarms = new SimpleStringProperty();
//        for (StringProperty alarmString : alarmFiveDays.getAlarms()) {
//            allAlarms.bind(allAlarms.concat("\n").concat(alarmString));
//        }
//        return allAlarms;
//    }
}
