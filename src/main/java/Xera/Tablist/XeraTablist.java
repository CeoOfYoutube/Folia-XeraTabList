package Xera.Tablist;

import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Logger;

public class XeraTablist extends JavaPlugin implements Listener {
    public static long startTime;
    public static boolean hasPapi = false;
    @Getter
    private static MiniMessage miniMessage = MiniMessage.miniMessage();
    @Getter
    public String header;
    @Getter
    public String footer;

    public void onEnable() {
        Logger log = getLogger();

        log.info("Loading config");
        saveDefaultConfig();
        loadConfig();

        startTime = System.currentTimeMillis();
        this.getCommand("tabrconfig").setExecutor(new ReloadCommand(this));
        getServer().getGlobalRegionScheduler().runAtFixedRate(
            this,
            task -> new TabList(this).run(),
            20,
            getConfig().getInt("delay")
        );


        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            hasPapi = true;
        }

        getServer().getGlobalRegionScheduler().runAtFixedRate(
            this,
            task -> new TabList(this).run(),
            20,
            getConfig().getInt("delay")
);

        log.info("XeraTablist enabled");
        log.info("Fixed by CEOofYT");
        log.info("D2S9 On top");
    }

    public static String parseText(Player player, String text) {
        int ping = player.getPing();
        
        // PAPI
        if (hasPapi) {
            text = PlaceholderAPI.setPlaceholders(player, text);
        }
        
        // ChatColor
        text = ChatColor.translateAlternateColorCodes('&', text);
        
        // Custom Placeholders
        text = text
                .replaceAll("%tps%", TabUtil.getTps())
                .replaceAll("%ping%", String.valueOf(ping))
                .replaceAll("%uptime%", TabUtil.getFormattedInterval(System.currentTimeMillis() - XeraTablist.startTime))
                .replaceAll("%players%", Integer.toString(Bukkit.getServer().getOnlinePlayers().size()));
        
        return text;
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        loadConfig();
    }

    public void loadConfig() {
        StringBuilder header = new StringBuilder();
        StringBuilder footer = new StringBuilder();
        List<String> headerList = getConfig().getStringList("tablist.header");
        List<String> footerList = getConfig().getStringList("tablist.footer");

        for (int i = 0; i < headerList.size(); i++) {
            if (i == (headerList.size() - 1)) {
                header.append(headerList.get(i));
            } else {
                header.append(headerList.get(i)).append("\n");
            }
        }

        for (int i = 0; i < footerList.size(); i++) {
            if (i == (footerList.size() - 1)) {
                footer.append(footerList.get(i));
            } else {
                footer.append(footerList.get(i)).append("\n");
            }
        }

        this.header = header.toString();
        this.footer = footer.toString();
    }
}
