package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {
	private Map<String, Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;
	public BuilderBasedFactory() {
	// Create a HashMap for _builders, a LinkedList _buildersInfo
	// ...
		_builders = new HashMap<>();
		_buildersInfo = new LinkedList<>();
	}
	public BuilderBasedFactory(List<Builder<T>> builders) {
		this();
			for(Builder<T> b : builders) {  		//se aniaden los builders a las factorias
				this.addBuilder(b);
			}
		}
		public void addBuilder(Builder<T> b) {
			_builders.put(b.getTypeTag(), b);  	//se aniade el builder a su factoria
			_buildersInfo.add(b.getInfo());
		}
		
		
		@Override
		public T createInstance(JSONObject info) {
			T bb = null;
			if (info == null) {
				throw new IllegalArgumentException("Invalid value for createInstance:null");
			}
			if(!_builders.containsKey(info.getString("type"))) { 			 //si no existe un builder con dicho tag, se lanza excepcion
				throw new IllegalArgumentException("[ERROR]: builder not found");
			}
			if(info.has("data")) { 											// se crea le pasa al builder el json para que cree la instancia
				bb  = _builders.get(info.getString("type")).createInstance(info.getJSONObject("data"));
			}
			else {
				bb  = _builders.get(info.getString("type")).createInstance(info);
			}			
			if (bb != null) return bb;
			else {
				throw new IllegalArgumentException("Invalid value for createInstance: " +
						info.toString());
			}
		}
		@Override
		public List<JSONObject> getInfo() {
			return Collections.unmodifiableList(_buildersInfo);
			}
		}


