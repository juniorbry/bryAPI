package heroyagown.bryAPI.nms;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class SkinManager {

    private static HashMap<String, Skin> skinsCache = new HashMap<>();
    private static HashMap<UUID, Skin> playersCache = new HashMap<>();

    public static void setSkin(Player player, String skinName) {

    }
    public static void setSkin(Player player, Skin skin) {

    }

    private static void setter(Player player, Skin skin) {

    }

    public static HashMap<String, Skin> getSkinsCache() {
        return skinsCache;
    }

    public static void setSkinsCache(HashMap<String, Skin> skinsCache) {
        SkinManager.skinsCache = skinsCache;
    }

    public static HashMap<UUID, Skin> getPlayersCache() {
        return playersCache;
    }

    public static void setPlayersCache(HashMap<UUID, Skin> playersCache) {
        SkinManager.playersCache = playersCache;
    }
}
