package callbs;

import org.json.JSONObject;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.UrlConnectionClient;



public class RestClient {

	private static final String BASE_URL = "http://api.bls.gov";
	private static ApiService apiService; 
	
	static {
		setupRestClient();
	}

	private RestClient() {}
	
	
	public static ApiService get(){
			return apiService;
		}
		

	private static void setupRestClient(){
	
		
	}
	
	
}
