package Exercise1.Behavioral;

// Subject (WeatherStation)
import java.util.ArrayList;
import java.util.List;

interface Observer {
    void update(float temperature, float humidity);
}

class WeatherStation {
    private List<Observer> observers = new ArrayList<>();
    private float temperature;
    private float humidity;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(temperature, humidity);
        }
    }

    public void setWeatherData(float temperature, float humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
        notifyObservers();
    }
}

// Observers
class PhoneDisplay implements Observer {
    @Override
    public void update(float temperature, float humidity) {
        System.out.println("Phone Display - Temperature: " + temperature + ", Humidity: " + humidity);
    }
}

class WebDashboard implements Observer {
    @Override
    public void update(float temperature, float humidity) {
        System.out.println("Web Dashboard - Temperature: " + temperature + ", Humidity: " + humidity);
    }
}

// Main Application
public class CommandPatternDemo {
    public static void main(String[] args) {
        WeatherStation weatherStation = new WeatherStation();
        PhoneDisplay phoneDisplay = new PhoneDisplay();
        WebDashboard webDashboard = new WebDashboard();

        weatherStation.addObserver(phoneDisplay);
        weatherStation.addObserver(webDashboard);

        weatherStation.setWeatherData(30.0f, 70.0f);
        weatherStation.setWeatherData(28.0f, 75.0f);
    }
}

