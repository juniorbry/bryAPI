package heroyagown.bryAPI.builder;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Command extends BukkitCommand {

    private String permission = "default";
    private int cooldown = 0;
    private String bypass = "*";
    private String noPermissionMessage = "You don't have permission for this command";
    private String errorMessage = "Something went wrong";
    private String cooldownMessage = "You have to wait  @time seconds to use this command again, wait please!";
    private Map<UUID, Long> cooldowns = new ConcurrentHashMap<>();

    protected Command(String command,
                      String[] aliases,
                      String description,
                      String permission,
                      String errorMessage,
                      String noPermissionMessage,
                      String bypassPermission,
                      int cooldown,
                      String cooldownMessage) {
        super(command);
        if (aliases != null) {
            this.setAliases(Arrays.asList(aliases));
        }
        if (description != null) {
            this.setDescription(description);
        }
        this.setPermission(permission != null ? permission : "default");
        this.permission = permission;
        this.noPermissionMessage = noPermissionMessage != null ? noPermissionMessage : "You don't have permission for this command";
        this.errorMessage = errorMessage != null ? errorMessage : "Something went wrong";
        this.bypass = bypassPermission;
        this.cooldown = cooldown;

        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap map = (CommandMap) field.get(Bukkit.getServer());
            map.register(command, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (cooldownMessage != null) {
            this.cooldownMessage = cooldownMessage;
        }
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "[!] Console does not have permission to use this type of command.");
            return true;
        }
        Player player = (Player) sender;
        if (this.permission != null && !player.hasPermission(this.permission)) {
            player.sendMessage(ChatColor.RED + this.noPermissionMessage);
            return false;
        }
        if (!player.hasPermission(this.bypass)) {
            UUID playerId = player.getUniqueId();
            long currentTime = System.currentTimeMillis();
            if (cooldowns.containsKey(playerId)) {
                long lastUsed = cooldowns.get(playerId);
                long timeLeft = ((lastUsed + (cooldown * 1000)) - currentTime) / 1000;
                if (timeLeft > 0) {
                    player.sendMessage(ChatColor.RED + cooldownMessage.replace("@time", String.valueOf(timeLeft)));
                    return false;
                }
            }
            cooldowns.put(playerId, currentTime);
        }
        executeCommand(sender, args);
        return true;
    }

    public abstract void executeCommand(CommandSender sender, String[] args);

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return onTabComplete(sender, args);
    }

    public abstract List<String> onTabComplete(CommandSender sender, String[] args);
}
