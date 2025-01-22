package heroyagown.bryAPI.builder.sidebar;

import heroyagown.bryAPI.builder.sidebar.manager.SidebarManager;
import heroyagown.bryAPI.builder.sidebar.viewer.SimpleViewer;
import heroyagown.bryAPI.builder.sidebar.viewer.Viewer;
import heroyagown.bryAPI.builder.sidebar.viewer.ViewerImpl;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SidebarPlayer implements Sidebar {

    private final String name;
    private final List<String> titles; // Lista de títulos para animação
    private final List<SidebarPage> pages; // Lista de páginas do jogador
    private SidebarPage currentPage; // Página atual
    private int currentTitleIndex = 0; // Índice do título atual

    public SidebarPlayer(String name, List<String> titles, List<SidebarPage> pages) {
        this.name = name;
        this.titles = titles != null ? titles : new ArrayList<>();
        this.pages = pages != null ? pages : new ArrayList<>();
        if (!this.pages.isEmpty()) {
            this.currentPage = this.pages.get(0); // Inicializa com a primeira página
        }
    }

    @Override
    public void update() {
        // Atualiza o título para o próximo na lista, se houver mais de um
        if (titles.size() > 1) {
            currentTitleIndex = (currentTitleIndex + 1) % titles.size();
        }
        // Atualiza a página atual, se existir
        if (currentPage != null) {
            currentPage.updatePage();
        }
    }

    @Override
    public void update(Player player) {
        if (player == null) return;

        // Atualiza o scoreboard do jogador com título e conteúdo atual
        Scoreboard scoreboard = player.getScoreboard();
        String title = titles.isEmpty() ? name : titles.get(currentTitleIndex);

        // Configura o título e os elementos da página no scoreboard
        if (currentPage != null) {
            currentPage.updatePage();
        }
    }

    @Override
    public void nextPage() {
        if (pages.size() > 1) {
            ListIterator<SidebarPage> iterator = pages.listIterator();
            while (iterator.hasNext()) {
                SidebarPage page = iterator.next();
                if (page.equals(currentPage) && iterator.hasNext()) {
                    currentPage = iterator.next();
                    return;
                }
            }
            currentPage = pages.get(0); // Volta à primeira página
        }
    }

    @Override
    public void nextPage(Player player) {
        nextPage();
        update(player);
    }

    @Override
    public void backPage() {
        if (pages.size() > 1) {
            ListIterator<SidebarPage> iterator = pages.listIterator(pages.size());
            while (iterator.hasPrevious()) {
                SidebarPage page = iterator.previous();
                if (page.equals(currentPage) && iterator.hasPrevious()) {
                    currentPage = iterator.previous();
                    return;
                }
            }
            currentPage = pages.get(pages.size() - 1); // Volta à última página
        }
    }

    @Override
    public void backPage(Player player) {
        backPage();
        update(player);
    }

    @Override
    public void setSidebar(Player player) {
        if (player != null) {
            Viewer viewer = SidebarManager.getViewerController().findViewer(player.getUniqueId()).get();
            if (viewer == null) {
                Viewer newViewer = new SimpleViewer(player.getUniqueId(), this);
                SidebarManager.getViewerController().registerViewer(newViewer);
                viewer = newViewer;
            }else {
                viewer.resetConfiguration();
                viewer.setSidebar(this);
            }
            player.setScoreboard(viewer.createSidebar());
        }
    }

    @Override
    public void removeSidebar(Player player) {
        if (player != null) {
            player.setScoreboard(player.getServer().getScoreboardManager().getNewScoreboard());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SidebarPage getPage() {
        return currentPage;
    }

    @Override
    public List<String> getTitles() {
        return titles;
    }
}
