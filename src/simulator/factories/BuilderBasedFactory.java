package simulator.factories;

public class BuilderBaseFactory {

	package simulator.factories;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String,Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;
	public BuilderBasedFactory() {
	// Create a HashMap for _builders, a LinkedList _buildersInfo
	// ...
	}
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
		// call addBuilder(b) for each builder b in builder
		// ...
		}
		public void addBuilder(Builder<T> b) {
		// add and entry ‘‘ b.getTag() −> b’’ to _builders.
		// ...
		// add b.getInfo () to _buildersInfo
		// ...
		}
		
		
		@Override
		public T createInstance(JSONObject info) {
		if (info == null) {
		throw new IllegalArgumentException("Invalid value for createInstance:
		null");
		}
		// Search for a builder with a tag equals to info . getString ("type"), call its
		// createInstance method and return the result if it is not null . The value you
		// pass to createInstance is :
		//
		// info . has("data") ? info . getJSONObject("data") : new getJSONObject()
		// If no builder is found or thr result is null ...
		throw new IllegalArgumentException("Invalid value for createInstance: " +
		info.toString());
		}
		@Override
		public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
		}
		}

}
