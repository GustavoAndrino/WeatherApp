package weather_app;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;

public class WeatherAppGui extends JFrame {
	private JSONObject weatherData;
	
	public WeatherAppGui() {
		//Name of the app 
		super("Weather App");
		
		//Stop program on close 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Set size
		setSize(450, 650);
		
		//starting location
		setLocationRelativeTo(null);
		
		//make layout manual
		setLayout(null);
		
		//No resizing for you
		setResizable(false);
		
		addGuiComponents();
	}
	
	private void addGuiComponents() {
		//weather image
		JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
		weatherConditionImage.setBounds(0, 125, 450, 217);
		add(weatherConditionImage);
		
		//temperature text
		JLabel temperatureText = new JLabel("10 C°");
		temperatureText.setBounds(0, 350, 450, 54);
		temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
		
		//center  the text
		temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
		add(temperatureText);
		
		//weather condition descritption
		JLabel weatherConditionDesc = new JLabel("Cloudy");
		weatherConditionDesc.setBounds(0, 405, 450, 36);
		weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
		weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
		add(weatherConditionDesc);
		
		//humidity image
		JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
		humidityImage.setBounds(15, 500, 74, 66);
		add(humidityImage);
		
		//Humidity text
		JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
		humidityText.setBounds(90, 500, 85, 55);
		humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
		add(humidityText);
		
		//Windspeed image
		JLabel windspeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
		windspeedImage.setBounds(220, 500, 74, 66);
		add(windspeedImage);
		
		//Windspeed text
		JLabel windspeedText = new JLabel("<html><b>WindSpeed</b> 15hm/h</html>");
		windspeedText.setBounds(310, 500, 90, 55);
		windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
		add(windspeedText);
		
		//Where the person is going to search sutff
		JTextField searchTextField = new JTextField();
				
		//size of text field
		searchTextField.setBounds(15, 15, 351, 45);
				
		//font
		searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
				
		searchTextField.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						searchWeather(searchTextField, weatherConditionImage, temperatureText, 
								weatherConditionDesc, windspeedText, humidityText);
					}
				});
				
				add(searchTextField);
		
		//search Button
		JButton searchButton = new JButton(loadImage("src/assets/search.png"));
				
		//Change to a hand cursor when hovering over button
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setBounds(375, 13, 47, 45);
		
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				searchWeather(searchTextField, weatherConditionImage, temperatureText,
						weatherConditionDesc, windspeedText, humidityText);
			}			
		});
		
		add(searchButton);

	}
	
	private ImageIcon loadImage(String resourcePath) {
		try {
			//get image from path
			BufferedImage image = ImageIO.read(new File(resourcePath));
			
			//return image
			return new ImageIcon(image);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Could not find resource");
		return null;
		
	}

	private void searchWeather(JTextField searchTextField, JLabel weatherConditionImage, 
			JLabel temperatureText, JLabel weatherConditionDesc, JLabel windspeedText, JLabel humidityText) {
		//get location from user
		String userInput = searchTextField.getText();
		
		//validate input - remove white space
		if(userInput.replaceAll("\\s", "").length() <= 0) {
			return;
		}
		
		//retrieve weather data
		weatherData = WeatherApp.getWeatherData(userInput);
		
		//update gui
		
		//update weather image
		String weatherCondition = (String) weatherData.get("weather_condition");
		
		//depending on the condition, we will update the weather image that corresponds with the condition
		switch(weatherCondition) {
			case "Clear":
				weatherConditionImage.setIcon(loadImage("src/assets/clear.png"));
				break;
			case "Cloudy":
				weatherConditionImage.setIcon(loadImage("src/assets/cloudy.png"));
				break;
			case "Rain":
				weatherConditionImage.setIcon(loadImage("src/assets/rain.png"));
				break;
			case "Snow":
				weatherConditionImage.setIcon(loadImage("src/assets/snow.png"));
				break;	
		}
		
		//update temperature text
		double temperature = (double) weatherData.get("temperature");
		temperatureText.setText(temperature + " C°");
		
		//update weather condition text
		weatherConditionDesc.setText(weatherCondition);
		
		//update humidity text
		long humidity = (long) weatherData.get("humidity");
		humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");
		
		// Later update of the windspeed text
		double windspeed = (double) weatherData.get("windspeed");
		windspeedText.setText("<html><b>WindSpeed</b> "  + windspeed + "&nbsp;Km/h</html>");
		
	}	
	
}
