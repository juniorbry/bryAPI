package heroyagown.bryAPI.nms.sign;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.block.Block;
import org.bukkit.block.sign.SignSide;
import org.bukkit.entity.Player;

public class SignPlayer {

    /**
     * Atualiza um painel para um jogador específico.
     *
     * @param location Localização do painel.
     * @param side     Lado do painel.
     * @param args     Linhas do painel.
     * @param player   Jogador que verá o painel atualizado.
     */
    public static void setSign(Location location, SignSide side, String[] args, Player player) {
        Block block = location.getBlock();

        if (block.getType().name().toLowerCase().contains("sign")) {
            Sign sign = (Sign) block.getState();
            setter(sign, side, args, player);
        } else {
            Bukkit.getLogger().warning("Nothing to change for " + player.getName());
        }
    }

    /**
     * Atualiza um painel existente para um jogador específico.
     *
     * @param sign   Painel a ser atualizado.
     * @param side   Lado do painel.
     * @param args   Linhas do painel.
     * @param player Jogador que verá o painel atualizado.
     */
    public static void setSign(Sign sign, SignSide side, String[] args, Player player) {
        setter(sign, side, args, player);
    }

    /**
     * Método privado para atualizar o painel.
     *
     * @param sign   Painel a ser atualizado.
     * @param side   Lado do painel.
     * @param args   Linhas do painel.
     * @param player Jogador que verá o painel atualizado.
     */
    private static void setter(Sign sign, SignSide side, String[] args, Player player) {
        player.sendSignChange(sign.getLocation(), args);
    }
}