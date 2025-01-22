package heroyagown.bryAPI.builder.sidebar.viewer;

import heroyagown.bryAPI.builder.sidebar.Sidebar;

import java.util.UUID;

public abstract class ViewerImpl implements Viewer {

    private final UUID uuid;
    private final Sidebar sidebar;

    private final ViewerPropertyMap viewerPropertyMap = new ViewerPropertyMap();

    protected ViewerImpl(UUID uuid, Sidebar sidebar) {
        this.uuid = uuid;
        this.sidebar = sidebar;
    }
}
