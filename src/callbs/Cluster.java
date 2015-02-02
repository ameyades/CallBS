package callbs;

import java.io.Serializable;

import org.json.JSONObject;

public class Cluster implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4888298198949507900L;
	private JSONObject status;

	public Cluster(JSONObject o)
	{
		status = o;
	}
	
	public JSONObject getResults() {
		return status;
	}

	public void setResults(JSONObject results) {
		this.status = status;
	}
	
	
}
