package Xera.Tablist;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Utility;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class TabUtil {
    private static String format(double tps) {
        return (tps > 18.0D ? ChatColor.GREEN : (tps > 16.0D ? ChatColor.YELLOW : ChatColor.RED)) + String.format("%.2f", Math.min((double) Math.round(tps * 100.0D) / 100.0D, 20.0D));
    }

    public static String getTps() {
        return format(Bukkit.getServer().getTPS()[0]);
    }

    public static String getFormattedInterval(long ms) {
        long seconds = ms / 1000L % 60L;
        long minutes = ms / 60000L % 60L;
        long hours = ms / 3600000L % 24L;
        long days = ms / 86400000L;
        return String.format("%dd %02dh %02dm %02ds", days, hours, minutes, seconds);
    }

    @NotNull
    @Contract(pure = true)
    public Component chatColorLegacyToComponent(@NotNull String input) {
        return XeraTablist.getMiniMessage().deserialize(convertToMiniMessageFormat(input.replace('§', '&')));
    }

    @NotNull
    @Contract(pure = true)
    public String convertToMiniMessageFormat(@NotNull String input) {
        input = input.replace('§', '&');
        input = input.replaceAll("&#([A-Fa-f0-9]{6})", "<#$1>");

        input = input.replace("&l", "<bold>");
        input = input.replace("&o", "<italic>");
        input = input.replace("&n", "<underlined>");
        input = input.replace("&m", "<strikethrough>");
        input = input.replace("&k", "<obfuscated>");
        input = input.replace("&r", "<reset>");
        input = input.replace("&0", "<black>");
        input = input.replace("&1", "<dark_blue>");
        input = input.replace("&2", "<dark_green>");
        input = input.replace("&3", "<dark_aqua>");
        input = input.replace("&4", "<dark_red>");
        input = input.replace("&5", "<dark_purple>");
        input = input.replace("&6", "<gold>");
        input = input.replace("&7", "<gray>");
        input = input.replace("&8", "<dark_gray>");
        input = input.replace("&9", "<blue>");
        input = input.replace("&a", "<green>");
        input = input.replace("&b", "<aqua>");
        input = input.replace("&c", "<red>");
        input = input.replace("&d", "<light_purple>");
        input = input.replace("&e", "<yellow>");
        input = input.replace("&f", "<white>");

        return input;
    }
}