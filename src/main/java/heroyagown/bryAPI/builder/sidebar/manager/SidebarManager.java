package heroyagown.bryAPI.builder.sidebar.manager;

import heroyagown.bryAPI.builder.sidebar.SidebarController;
import heroyagown.bryAPI.builder.sidebar.listener.SidebarListener;
import heroyagown.bryAPI.builder.sidebar.runnable.SidebarRunnable;
import heroyagown.bryAPI.builder.sidebar.viewer.ViewerController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public final class SidebarManager {

    private static final SidebarManager instance = new SidebarManager();
    private static boolean enabled;

    private static final SidebarController sidebarController = new SidebarController();
    private static final ViewerController viewerController = new ViewerController();

    public static void enable(Plugin plugin) {
        if (SidebarManager.isEnabled()) return;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SidebarListener(), plugin);

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(plugin, new SidebarRunnable(), 0, 20);

        SidebarManager.setEnabled(true);
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        SidebarManager.enabled = enabled;
    }

    public static SidebarController getSidebarController() {
        return sidebarController;
    }

    public static ViewerController getViewerController() {
        return viewerController;
    }
}
