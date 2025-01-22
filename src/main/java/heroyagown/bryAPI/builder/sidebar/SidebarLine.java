package heroyagown.bryAPI.builder.sidebar;

public class SidebarLine {

    private String lineName;
    private String prefix;
    private String suffix;
    private boolean isStatic;

    public SidebarLine(String lineName) {
        this.lineName = lineName;
        this.prefix = "";
        this.suffix = "";
        this.isStatic = false;
    }

    // Getters e Setters
    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }
}
