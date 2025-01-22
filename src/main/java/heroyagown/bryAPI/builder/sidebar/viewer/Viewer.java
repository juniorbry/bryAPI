package heroyagown.bryAPI.builder.sidebar.viewer;

import heroyagown.bryAPI.builder.sidebar.Sidebar;
import heroyagown.bryAPI.builder.sidebar.SidebarPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

public interface Viewer {

    UUID getUUID();

    default Player getPlayer() {
        return Bukkit.getPlayer(getUUID());
    }

    <T extends Sidebar> T getSidebar();
    ViewerPropertyMap getPropertyMap();

    default Scoreboard getScoreboard() {
        return getPlayer().getScoreboard();
    }

    Scoreboard createSidebar();

    void resetConfiguration();

    void setSidebar(Sidebar sidebar);
}
