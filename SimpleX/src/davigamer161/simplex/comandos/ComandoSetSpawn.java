package davigamer161.simplex.comandos;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import davigamer161.simplex.SimpleX;

public class ComandoSetSpawn implements CommandExecutor{

    private SimpleX plugin;
    public ComandoSetSpawn(SimpleX plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comando, String label, String[] args){
    if(sender instanceof Player){
	    FileConfiguration config = plugin.getConfig();
        FileConfiguration messages = plugin.getMessages();
	    Player jugador = (Player) sender;
		    if(jugador.hasPermission("simplex.setspawn")){
                Location l = jugador.getLocation();
                double x = l.getX();
                double y = l.getY();
                double z = l.getZ();
                String world = l.getWorld().getName();
                float yaw = l.getYaw();
                float pitch = l.getPitch();
                config.set("Config.spawn.spawn-location.x", x);
                config.set("Config.spawn.spawn-location.y", y);
                config.set("Config.spawn.spawn-location.z", z);
                config.set("Config.spawn.spawn-location.world", world);
                config.set("Config.spawn.spawn-location.yaw", yaw);
                config.set("Config.spawn.spawn-location.pitch", pitch);
                plugin.saveConfig();
                String path = "Config.spawn.setspawn-message";
                if(config.getString(path).equals("true")){
                    String mensaje = messages.getString("Messages.spawn.setspawn");
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
                }
            }else{
                String path = "Config.no-perm-message";
                if(config.getString(path).equals("true")){
                    String mensaje = messages.getString("Messages.no-perm");
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', mensaje.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
                }
            }
        
	}
	return false;
    }
}