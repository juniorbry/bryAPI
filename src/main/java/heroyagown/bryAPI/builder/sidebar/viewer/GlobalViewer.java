package heroyagown.bryAPI.builder.sidebar.viewer;

import heroyagown.bryAPI.builder.sidebar.Sidebar;
import heroyagown.bryAPI.builder.sidebar.SidebarLine;
import heroyagown.bryAPI.builder.sidebar.SidebarPage;
import heroyagown.bryAPI.builder.sidebar.manager.SidebarManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Objects;

public class GlobalViewer extends ViewerImpl {

    private final UUID uuid;
    private Sidebar sidebar;

    public GlobalViewer(UUID uuid, Sidebar sidebar) {
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
        Scoreboard board = getPlayer().getScoreboard();
        Objective obj = board.getObjective("heroscore");

        if (obj != null) {
            obj.unregister(); // Apaga as antigas informações do objetivo
        }

        obj = board.registerNewObjective("heroscore", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.translateAlternateColorCodes('&', sidebar.getTitles().get(0)));

        SidebarPage page = sidebar.getPage();
        List<ChatColor> chatColors = Arrays.asList(ChatColor.values());
        int chatColorIndex = 0;

        for (SidebarLine lines : page.getLines().values()) {
            Team team = Objects.requireNonNull(obj.getScoreboard()).registerNewTeam(lines.getLineName());
            team.addEntry(chatColors.get(chatColorIndex).toString());
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', lines.getPrefix()));
            team.setSuffix(ChatColor.translateAlternateColorCodes('&', lines.getSuffix()));

            int position = sidebar.getPage().getPosition(lines.getLineName());
            obj.getScore(chatColors.get(chatColorIndex).toString()).setScore(position);

            chatColorIndex++;
            if (chatColorIndex >= chatColors.size()) {
                chatColorIndex = 0; // Reinicia o índice de cores se necessário
            }
        }

        return board;
    }

    @Override
    public void resetConfiguration() {
        List<Team> teamList = getPlayer().getScoreboard().getTeams().stream().collect(Collectors.toList());
        teamList.forEach(Team::unregister);
        if (getPlayer().getScoreboard().getObjective("heroscore") != null) {
            getPlayer().getScoreboard().getObjective("heroscore").unregister();
        }
    }

    @Override
    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }
}
