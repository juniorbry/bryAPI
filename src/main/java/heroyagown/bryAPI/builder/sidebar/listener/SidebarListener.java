package heroyagown.bryAPI.builder.sidebar.listener;

import heroyagown.bryAPI.builder.sidebar.manager.SidebarManager;
import heroyagown.bryAPI.builder.sidebar.viewer.Viewer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class SidebarListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Viewer viewer = SidebarManager.getViewerController().findViewer(event.getPlayer().getUniqueId()).get();
        if (viewer != null) {
            viewer.resetConfiguration();
            SidebarManager.getViewerController().unregisterViewer(viewer.getUUID());
        }
    }
}
