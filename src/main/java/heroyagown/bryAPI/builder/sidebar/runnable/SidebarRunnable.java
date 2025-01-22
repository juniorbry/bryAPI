package heroyagown.bryAPI.builder.sidebar.runnable;

import heroyagown.bryAPI.builder.sidebar.Sidebar;
import heroyagown.bryAPI.builder.sidebar.SidebarController;
import heroyagown.bryAPI.builder.sidebar.SidebarGlobal;
import heroyagown.bryAPI.builder.sidebar.SidebarPage;
import heroyagown.bryAPI.builder.sidebar.manager.SidebarManager;
import heroyagown.bryAPI.builder.sidebar.viewer.GlobalViewer;
import heroyagown.bryAPI.builder.sidebar.viewer.Viewer;
import heroyagown.bryAPI.builder.sidebar.viewer.ViewerController;

import java.util.Map;
import java.util.UUID;

public class SidebarRunnable implements Runnable {

    @Override
    public void run() {
        SidebarController controller = SidebarManager.getSidebarController();
        ViewerController viewerController = SidebarManager.getViewerController();

        Map<String, Sidebar> sidebarMap = controller.getSidebarMap();
        for (Sidebar sidebar : sidebarMap.values()) {
            if (!(sidebar instanceof SidebarGlobal)) continue;
            updateGlobalSidebar(sidebar);
        }

        Map<UUID, Viewer> viewerMap = viewerController.getViewerMap();
        for (Viewer viewer : viewerMap.values()) {
            if (viewer instanceof GlobalViewer) continue;
            updateViewerSidebar(viewer);
        }
    }

    private void updateViewerSidebar(Viewer viewer) {
        Sidebar sidebar = viewer.getSidebar();
        sidebar.update(viewer.getPlayer());
    }

    private void updateGlobalSidebar(Sidebar sidebar) {
        SidebarPage page = sidebar.getPage();
        page.updatePage();
    }
}
