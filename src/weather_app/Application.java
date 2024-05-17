package weather_app;

import javax.swing.SwingUtilities;

public class Application {
//weather app with api calls
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new WeatherAppGui().setVisible(true);
				
				//System.out.println(WeatherApp.getLocationData("Tokyo"));
				
				//System.out.println(WeatherApp.getCurrentTime());
			}
		});

	}

}
