package davigamer161.simplex.comandos;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import davigamer161.simplex.SimpleX;
import net.milkbowl.vault.economy.Economy;

public class ComandoHeal implements CommandExecutor{

    private SimpleX plugin;

    public ComandoHeal(SimpleX plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command comando, String label, String[] args){
    if(sender instanceof Player){
	    FileConfiguration config = plugin.getConfig();
		FileConfiguration messages = plugin.getMessages();
	    Player jugador = (Player) sender;
		if(args.length == 0){
			String poth = "Config.heal.pay-to-heal";
		    if(jugador.hasPermission("simplex.heal")){
				if(config.getString(poth).equals("true")){
					Economy econ = plugin.getEconomy();
        			double dinero = econ.getBalance(jugador);
       				int precio = Integer.valueOf(config.getString("Config.heal.heal-price"));
					if(jugador.hasPermission("simplex.econ.exempt")){
						jugador.setHealth(20);
						jugador.setFoodLevel(20);
						healMethod(jugador);
					}else if(dinero >=precio){
						econ.withdrawPlayer(jugador, precio);
						jugador.setHealth(20);
						jugador.setFoodLevel(20);
						healMethod(jugador);
					}else{
						String path = "Config.heal.heal-message";
						if(config.getString(path).equals("true")){
							List<String> mensaje = messages.getStringList("Messages.heal.no-money-to-heal");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
							}
						}
					}
				}else{
					jugador.setHealth(20);
					jugador.setFoodLevel(20);
					healMethod(jugador);
				}
			}else{
				noPermMethod(jugador);
			}
        }else if(args.length == 1){
			String poth = "Config.heal.pay-to-heal";
			if(jugador.hasPermission("simplex.heal.others")){
				if(config.getString(poth).equals("true")){
					Economy econ = plugin.getEconomy();
        			double dinero = econ.getBalance(jugador);
       				int precio = Integer.valueOf(config.getString("Config.heal.heal-price"));
					if(jugador.hasPermission("simplex.econ.exempt.others")){
						Player target = Bukkit.getPlayer(args[0]);
                		target.setHealth(20);
               			target.setFoodLevel(20);
						healMethod(target);
						String path = "Config.heal.heal-message";
						if(config.getString(path).equals("true")){
							List<String> mensaje = messages.getStringList("Messages.heal.heal-others");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}
					}else if(dinero >=precio){
						Player target = Bukkit.getPlayer(args[0]);
						econ.withdrawPlayer(jugador, precio);
						jugador.setHealth(20);
						jugador.setFoodLevel(20);
						healMethod(target);
						String path = "Config.heal.heal-message";
						if(config.getString(path).equals("true")){
							List<String> mensaje = messages.getStringList("Messages.heal.heal-others");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}
					}else{
						String path = "Config.heal.heal-message";
						if(config.getString(path).equals("true")){
							Player target = Bukkit.getPlayer(args[0]);
							List<String> mensaje = messages.getStringList("Messages.heal.no-money-to-heal-others");
							for(int i=0;i<mensaje.size();i++){
								String texto = mensaje.get(i);
								jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
							}
						}
					}
				}else{
					Player target = Bukkit.getPlayer(args[0]);
					target.setHealth(20);
					target.setFoodLevel(20);
					healMethod(target);
					String path = "Config.heal.heal-message";
					if(config.getString(path).equals("true")){
						List<String> mensaje = messages.getStringList("Messages.heal.heal-others");
						for(int i=0;i<mensaje.size();i++){
							String texto = mensaje.get(i);
							jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version).replaceAll("%target%", target.getName())));
						}
					}
				}
			}else{
				noPermMethod(jugador);
			}
		}
	}
	return false;
    }
	private void healMethod(Player jugador){
		FileConfiguration config = plugin.getConfig();
		FileConfiguration messages = plugin.getMessages();
		String path = "Config.heal.heal-message";
    	if(config.getString(path).equals("true")){
            List<String> mensaje = messages.getStringList("Messages.heal.heal");
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
