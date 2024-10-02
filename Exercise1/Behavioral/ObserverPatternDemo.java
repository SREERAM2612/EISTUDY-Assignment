package Exercise1.Behavioral;

// Subject (WeatherStation)
import java.util.ArrayList;
import java.util.List;

interface WeatherObserver {
    void update(float temperature, float humidity);
}

class WeatherMonitor {
    private List<WeatherObserver> observers = new ArrayList<>();
    private float temperature;
    private float humidity;

    public void addObserver(WeatherObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(WeatherObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (WeatherObserver observer : observers) {
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
class MobileDisplay implements WeatherObserver {
    @Override
    public void update(float temperature, float humidity) {
        System.out.println("Mobile Display - Temperature: " + temperature + ", Humidity: " + humidity);
    }
}

class OnlineDashboard implements WeatherObserver {
    @Override
    public void update(float temperature, float humidity) {
        System.out.println("Online Dashboard - Temperature: " + temperature + ", Humidity: " + humidity);
    }
}

// Main Application
public class ObserverPatternDemo {
    public static void main(String[] args) {
        WeatherMonitor weatherMonitor = new WeatherMonitor();
        MobileDisplay mobileDisplay = new MobileDisplay();
        OnlineDashboard onlineDashboard = new OnlineDashboard();

        weatherMonitor.addObserver(mobileDisplay);
        weatherMonitor.addObserver(onlineDashboard);

        weatherMonitor.setWeatherData(30.0f, 70.0f);
        weatherMonitor.setWeatherData(28.0f, 75.0f);
    }
}
