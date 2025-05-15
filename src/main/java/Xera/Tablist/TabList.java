package Xera.Tablist;

import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Collection;

@RequiredArgsConstructor
public class TabList implements Runnable {
    private final XeraTablist plugin;

    public void run() {
        Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
        try {
            if (players.isEmpty()) return;

            for (Player player : players) {
                player.sendPlayerListHeaderAndFooter(
                        TabUtil.chatColorLegacyToComponent(XeraTablist.parseText(player, plugin.getHeader())),
                        TabUtil.chatColorLegacyToComponent(XeraTablist.parseText(player, plugin.getFooter()))
                );
                player.playerListName(player.displayName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
