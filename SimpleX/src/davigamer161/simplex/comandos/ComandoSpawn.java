package davigamer161.simplex.comandos;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import davigamer161.simplex.SimpleX;
import net.milkbowl.vault.economy.Economy;

public class ComandoSpawn implements CommandExecutor{

    private SimpleX plugin;

    public ComandoSpawn(SimpleX plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comando, String label, String[] args){
    if(sender instanceof Player){
	    FileConfiguration config = plugin.getConfig();
        FileConfiguration messages = plugin.getMessages();
	    Player jugador = (Player) sender;
		if(args.length == 0){
            String poth = "Config.spawn.pay-to-spawn";
            if(jugador.hasPermission("simplex.spawn")){
                if(config.contains("Config.spawn.spawn-location.x")){
                    if(config.getString(poth).equals("true")){
                        Economy econ = plugin.getEconomy();
                        double dinero = econ.getBalance(jugador);
                        int precio = Integer.valueOf(config.getString("Config.spawn.spawn-price"));
                            if(jugador.hasPermission("simplex.econ.exempt")){
                                teleportMethod(jugador);
                                spawnMethod(jugador);
                            }else if(dinero >=precio){
                                econ.withdrawPlayer(jugador, precio);
                                teleportMethod(jugador);
                                spawnMethod(jugador);
                            }else{
                                String path = "Config.spawn.spawn-message";
                                if(config.getString(path).equals("true")){
                                List<String> mensaje = messages.getStringList("Messages.spawn.no-money-to-teleport");
                                    for(int i=0;i<mensaje.size();i++){
                                        String texto = mensaje.get(i);
                                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
                                    }
                                }
                            }
                    }else{
                        teleportMethod(jugador);
                        spawnMethod(jugador);
                    }
                }else{
                    spawnNoExistMethod(jugador);
                }
            }else{
                noPermMethod(jugador);
            }
        }else if(args.length == 1){
            String poth = "Config.spawn.pay-to-spawn";
            if(jugador.hasPermission("simplex.spawn.others")){
                if(config.contains("Config.spawn.spawn-location.x")){
                    if(config.getString(poth).equals("true")){
                        Economy econ = plugin.getEconomy();
                        double dinero = econ.getBalance(jugador);
                        int precio = Integer.valueOf(config.getString("Config.spawn.spawn-price"));
                        Player target = Bukkit.getPlayer(args[0]);
                        if(jugador.hasPermission("simplex.econ.exempt.others")){
                            teleportMethod(target);
                            spawnMethod(target);
                            String path = "Config.spawn.spawn-message";
                            if(config.getString(path).equals("true")){
                                List<String> mensaje = messages.getStringList("Messages.spawn.teleport-others");
                                for(int i=0;i<mensaje.size();i++){
                                    String texto = mensaje.get(i);
                                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
                                }
                            }
                        }else if(dinero >=precio){
                        econ.withdrawPlayer(jugador, precio);
                        teleportMethod(target);
                        spawnMethod(target);
                        String path = "Config.spawn.spawn-message";
                            if(config.getString(path).equals("true")){
                                List<String> mensaje = messages.getStringList("Messages.spawn.teleport-others");
                                for(int i=0;i<mensaje.size();i++){
                                    String texto = mensaje.get(i);
                                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
                                }
                            }
                        }else{
                            String path = "Config.spawn.spawn-message";
                            if(config.getString(path).equals("true")){
                                List<String> mensaje = messages.getStringList("Messages.spawn.no-money-to-teleport-others");
                                for(int i=0;i<mensaje.size();i++){
                                    String texto = mensaje.get(i);
                                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
                                }
                            }
                        }
                    }else{
                        Player target = Bukkit.getPlayer(args[0]);
                        teleportMethod(target);
                        spawnMethod(target);
                        String path = "Config.spawn.spawn-message";
                        if(config.getString(path).equals("true")){
                        List<String> mensaje = messages.getStringList("Messages.spawn.teleport-others");
                            for(int i=0;i<mensaje.size();i++){
                                String texto = mensaje.get(i);
                                jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
                            }
                        }
                    }
                }else{
                    spawnNoExistMethod(jugador);
                }
            }else{
                noPermMethod(jugador);
            }
        }
    }
	return false;
	}
    private void spawnMethod(Player jugador){
		FileConfiguration config = plugin.getConfig();
        FileConfiguration messages = plugin.getMessages();
		String path = "Config.spawn.spawn-message";
    	if(config.getString(path).equals("true")){
            List<String> mensaje = messages.getStringList("Messages.spawn.teleport");
            for(int i=0;i<mensaje.size();i++){
                String texto = mensaje.get(i);
                jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
            }
        }
	}
    private void teleportMethod(Player jugador){
            FileConfiguration config = plugin.getConfig();
	        double x = Double.valueOf(config.getString("Config.spawn.spawn-location.x"));
            double y = Double.valueOf(config.getString("Config.spawn.spawn-location.y"));
            double z = Double.valueOf(config.getString("Config.spawn.spawn-location.z"));
            float yaw = Float.valueOf(config.getString("Config.spawn.spawn-location.yaw"));
            float pitch = Float.valueOf(config.getString("Config.spawn.spawn-location.pitch"));
            World world = plugin.getServer().getWorld(config.getString("Config.spawn.spawn-location.world"));
            Location l = new Location(world, x, y, z, yaw, pitch);
            jugador.teleport(l);
	}
    private void spawnNoExistMethod(Player jugador){
        FileConfiguration config = plugin.getConfig();
        FileConfiguration messages = plugin.getMessages();
        String path = "Config.spawn.spawn-message";
        if(config.getString(path).equals("true")){
            List<String> mensaje = messages.getStringList("Messages.spawn.no-exist");
            for(int i=0;i<mensaje.size();i++){
                String texto = mensaje.get(i);
                jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
            }
        }
	}
	private void noPermMethod(Player jugador){
		FileConfiguration config = plugin.getConfig();
        FileConfiguration messages = plugin.getMessages();
		String path = "Config.no-perm-message";
		if(config.getString(path).equals("true")){
			List<String> mensaje = messages.getStringList("Messages.no-perm");
			for(int i=0;i<mensaje.size();i++){
				String texto = mensaje.get(i);
				jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
			}
		}
	}
}





