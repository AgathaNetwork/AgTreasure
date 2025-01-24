package cn.org.agatha.ag2025;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.objectweb.asm.Handle;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public final class Ag2025 extends JavaPlugin implements Listener {

    List<Location> locations = new ArrayList<>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
        locations.add(new Location(Bukkit.getWorld("world"),1,1,1));
	//Record the position of the treasures
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("qi")) {
            Bukkit.getPlayer(sender.getName()).teleport(new Location(Bukkit.getWorld("world"),938,70,3428));
            return true;
        } 
        return false;
    }

    public static String sendGet(String url) {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String result = null;
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(event.getInteractionPoint()!= null){
            if(event.getInteractionPoint().getBlock()!=null){
                if(event.getInteractionPoint().getBlock().getType()== Material.PLAYER_HEAD && locations.contains(event.getClickedBlock().getLocation())) {//
                    event.setCancelled(true);
                    if(event.getPlayer()!=null){
                        Player p = event.getPlayer();
                        String name = p.getName();
                        Thread asyncProc = new Thread(() -> {
                            event.getPlayer().sendMessage("§a§l喵！这只笼启解救成功！");
                            int cnt = 0;
                            cnt = locations.indexOf(event.getClickedBlock().getLocation())+1;
                            getLogger().info("Processing #"+ cnt +" head for" + name);
                            sendGet("https://[fe00::]/cmida.php?&id="+event.getPlayer().getName()+"&num="+cnt);

                        });
                        asyncProc.start();
                    }
                }
            }
        }

    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(event.getBlock()!=null){
            if(event.getBlock().getType()== Material.PLAYER_HEAD && locations.contains(event.getBlock().getLocation())) {//
                event.setCancelled(true);
            }
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll();
    }
}
