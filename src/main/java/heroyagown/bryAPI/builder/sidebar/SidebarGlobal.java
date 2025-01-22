package heroyagown.bryAPI.builder.sidebar;

import heroyagown.bryAPI.builder.sidebar.manager.SidebarManager;
import heroyagown.bryAPI.builder.sidebar.viewer.GlobalViewer;
import heroyagown.bryAPI.builder.sidebar.viewer.SimpleViewer;
import heroyagown.bryAPI.builder.sidebar.viewer.Viewer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.ListIterator;

public class SidebarGlobal implements Sidebar {

    private final String name;
    private final List<String> title;
    private final List<SidebarPage> pages;
    private SidebarPage page;

    public SidebarGlobal(String name, List<String> title, List<SidebarPage> pages) {
        this.name = name;
        this.title = title;
        this.pages = pages;
        if (!pages.isEmpty()) {
            this.page = pages.get(0); // Inicializa com a primeira página
        }
    }

    @Override
    public void update() {
        if (page != null) {
            page.updatePage();
        }
    }

    @Override
    public void update(Player player) {
        // Nada aqui, pois é uma sidebar global
    }

    @Override
    public void nextPage() {
        if (pages.size() > 1) {
            ListIterator<SidebarPage> iterator = pages.listIterator();
            while (iterator.hasNext()) {
                SidebarPage currentPage = iterator.next();
                if (currentPage.equals(page) && iterator.hasNext()) {
                    this.page = iterator.next();
                    return;
                }
            }
            this.page = pages.get(0); // Retorna à primeira página
        }
    }

    @Override
    public void nextPage(Player player) {
        // Nada aqui, pois é uma sidebar global
    }

    @Override
    public void backPage() {
        if (pages.size() > 1) {
            ListIterator<SidebarPage> iterator = pages.listIterator(pages.size());
            while (iterator.hasPrevious()) {
                SidebarPage currentPage = iterator.previous();
                if (currentPage.equals(page) && iterator.hasPrevious()) {
                    this.page = iterator.previous();
                    return;
                }
            }
            this.page = pages.get(pages.size() - 1); // Retorna à última página
        }
    }

    @Override
    public void backPage(Player player) {
        // Nada aqui, pois é uma sidebar global
    }

    @Override
    public void setSidebar(Player player) {
        Viewer viewer = SidebarManager.getViewerController().findViewer(player.getUniqueId()).get();
        if (viewer == null) {
            Viewer newViewer = new GlobalViewer(player.getUniqueId(), this);
            SidebarManager.getViewerController().registerViewer(newViewer);
            viewer = newViewer;
        }else {
            viewer.resetConfiguration();
            viewer.setSidebar(this);
        }
        player.setScoreboard(viewer.createSidebar());
    }

    @Override
    public void removeSidebar(Player player) {
        player.setScoreboard(player.getServer().getScoreboardManager().getNewScoreboard());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SidebarPage getPage() {
        return page;
    }

    @Override
    public List<String> getTitles() {
        return title;
    }
}
