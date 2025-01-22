package heroyagown.bryAPI.builder.sidebar.viewer;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ViewerController {

    private final Map<UUID, Viewer> viewerMap = new LinkedHashMap<>();

    public <T extends Viewer> T registerViewer(T viewer) {
        this.viewerMap.put(viewer.getUUID(), viewer);
        return viewer;
    }

    public <T extends Viewer> T unregisterViewer(UUID uuid) {
        return (T) this.viewerMap.remove(uuid);
    }

    public <T extends Viewer> Optional<T> findViewer(UUID uuid) {
        return Optional.ofNullable((T)this.viewerMap.get(uuid));
    }

    public Map<UUID, Viewer> getViewerMap() {
        return viewerMap;
    }
}
