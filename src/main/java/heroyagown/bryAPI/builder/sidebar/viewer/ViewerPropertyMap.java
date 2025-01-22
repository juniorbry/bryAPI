package heroyagown.bryAPI.builder.sidebar.viewer;

import java.util.LinkedHashMap;
import java.util.Map;

public class ViewerPropertyMap {

    private final Map<String, Object> map = new LinkedHashMap<>();

    public <T> T get(String key) {
        return (T) this.map.get(key);
    }

    public ViewerPropertyMap set(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
