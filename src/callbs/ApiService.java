package callbs;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;


public interface ApiService {
	@GET("/publicAPI/v2/timeseries/data/{series}")
	public void dataset(@Path("series") String code, Callback<Cluster> cb);
}
