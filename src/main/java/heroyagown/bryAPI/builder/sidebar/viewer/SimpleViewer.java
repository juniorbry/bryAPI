package heroyagown.bryAPI.builder.sidebar.viewer;

import heroyagown.bryAPI.builder.sidebar.Sidebar;
import heroyagown.bryAPI.builder.sidebar.SidebarLine;
import heroyagown.bryAPI.builder.sidebar.SidebarPage;
import heroyagown.bryAPI.builder.sidebar.manager.SidebarManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.*;

public class SimpleViewer extends ViewerImpl {

    private final UUID uuid;
    private Sidebar sidebar;

    public SimpleViewer(UUID uuid, Sidebar sidebar) {
        super(uuid, sidebar);
        this.uuid = uuid;
        this.sidebar = sidebar;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public <T extends Sidebar> T getSidebar() {
        return (T) sidebar;
    }

    @Override
    public ViewerPropertyMap getPropertyMap() {
        return SidebarManager.getViewerController().getViewerMap().get(uuid).getPropertyMap();
    }

    @Override
    public Scoreboard createSidebar() {
        Player player = getPlayer();
        Scoreboard board = player.getScoreboard();
        Objective obj = board.getObjective("heroscore");

        // Remove objetivo antigo, se existir
        if (obj != null) {
            obj.unregister();
        }

        // Cria novo objetivo
        obj = board.registerNewObjective("heroscore", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', sidebar.getTitles().get(0)));

        SidebarPage page = sidebar.getPage();

        // Itera sobre as linhas e configura cada Team
        for (Map.Entry<Team, SidebarLine> entry : page.getLines().entrySet()) {
            Team team = entry.getKey();
            SidebarLine line = entry.getValue();

            // Adiciona uma entrada única à Team (se necessário)
            String uniqueEntry = ChatColor.values()[(int) (Math.random() * ChatColor.values().length)].toString();
            if (team.getEntries().isEmpty()) {
                team.addEntry(uniqueEntry);
            }

            // Configura prefixo e sufixo
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', line.getPrefix()));
            team.setSuffix(ChatColor.translateAlternateColorCodes('&', line.getSuffix()));

            // Define a pontuação
            int position = page.getPosition(line.getLineName());
            obj.getScore(team.getEntries().stream().findFirst().orElse(uniqueEntry)).setScore(position);
        }

        return board;
    }

    @Override
    public void resetConfiguration() {
        getPlayer().getScoreboard().getTeams().forEach(Team::unregister);
        sidebar.removeSidebar(getPlayer());
        if (getPlayer().getScoreboard().getObjective("heroscore") != null) {
            getPlayer().getScoreboard().getObjective("heroscore").unregister();
        }
    }

    @Override
    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }
}
