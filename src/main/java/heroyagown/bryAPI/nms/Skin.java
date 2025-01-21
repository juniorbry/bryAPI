package heroyagown.bryAPI.nms;

import org.bukkit.entity.Player;

public class Skin {

    private String skinName;
    private SkinBuilder skinBuilder;

    public Skin(String skinName, SkinBuilder skinBuilder) {
        this.skinBuilder = skinBuilder;
        this.skinName = skinName;
    }

}
