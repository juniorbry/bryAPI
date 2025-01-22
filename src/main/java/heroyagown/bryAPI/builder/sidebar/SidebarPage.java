package heroyagown.bryAPI.builder.sidebar;

import heroyagown.bryAPI.builder.sidebar.manager.SidebarManager;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.text.AttributedCharacterIterator;
import java.util.*;

public class SidebarPage {

    private String pageName;
    private Map<Team, SidebarLine> lines;
    private Map<String, Integer> positions;

    public SidebarPage(String name) {
        this.pageName = name;
        this.lines = new HashMap<>();
    }

    public String getPageName() {
        return pageName;
    }
    public void addLine(Team team, SidebarLine sidebarLine) {
        lines.put(team, sidebarLine);
        positions.put(team.getName(), lines.size());
    }
    public void updatePage() {
        SidebarManager.getViewerController().getViewerMap().values().forEach(v -> {
            List<String> titles = v.getSidebar().getTitles();
            if (titles.size() > 1) {
                Iterator<String> iterator = titles.iterator();
                String firstTitle = iterator.next();
                String currentTitle = Objects.requireNonNull(v.getPlayer().getScoreboard().getObjective("heroscore")).getDisplayName();

                // Find the current title in the iterator
                while (iterator.hasNext()) {
                    String title = iterator.next();
                    if (title.equals(currentTitle)) {
                        break;
                    }
                }

                // Set the next title, or loop back to the first title
                String nextTitle;
                if (iterator.hasNext()) {
                    nextTitle = iterator.next();
                } else {
                    nextTitle = firstTitle;
                }
                Objects.requireNonNull(v.getPlayer().getScoreboard().getObjective("heroscore")).setDisplayName(nextTitle);
            } else {
                Objects.requireNonNull(v.getPlayer().getScoreboard().getObjective("heroscore")).setDisplayName(titles.get(0));
            }
        });

        lines.values().stream()
                .filter(line -> !line.isStatic())
                .forEach(line -> lines.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(line))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .ifPresent(team -> {
                            team.setPrefix(line.getPrefix());
                            team.setSuffix(line.getSuffix());
                        })
                );
    }

    public Map<Team, SidebarLine> getLines() {
        return lines;
    }

    public int getPosition(String lineName) {
        return positions.get(lineName);
    }
}
