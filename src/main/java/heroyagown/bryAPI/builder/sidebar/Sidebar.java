package heroyagown.bryAPI.builder.sidebar;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface Sidebar {

    void update();
    void update(Player player);

    void nextPage();
    void nextPage(Player player);

    void backPage();
    void backPage(Player player);

    void setSidebar(Player player);
    void removeSidebar(Player player);

    String getName();

    SidebarPage getPage();

    List<String> getTitles();
}
