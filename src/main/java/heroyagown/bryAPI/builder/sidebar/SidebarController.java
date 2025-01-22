package heroyagown.bryAPI.builder.sidebar;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class SidebarController {

    private final Map<String, Sidebar> sidebarMap = new LinkedHashMap<>();

    public <T extends Sidebar> T registerSidebar(T sidebar) {
        this.sidebarMap.put(sidebar.getName(), sidebar);
        return sidebar;
    }

    public <T extends Sidebar>Optional<T> findSidebar(String name) {
        return Optional.ofNullable((T) this.sidebarMap.get(name));
    }

    public Map<String, Sidebar> getSidebarMap() {
        return sidebarMap;
    }
}
