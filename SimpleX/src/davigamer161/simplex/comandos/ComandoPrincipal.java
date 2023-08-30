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

public class ComandoPrincipal implements CommandExecutor{
	
	private SimpleX plugin;

    public ComandoPrincipal(SimpleX plugin){
        this.plugin = plugin;
    }
	@Override
	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
        if(!(sender instanceof Player)){
            FileConfiguration messages = plugin.getMessages();
            List<String> mensaje = messages.getStringList("Messages.console-error");
                for(int i=0;i<mensaje.size();i++){
                    String texto = mensaje.get(i);
                    Bukkit.getConsoleSender().sendMessage(texto);
                }
        }
        else{
            Player jugador = (Player) sender;
            FileConfiguration config = plugin.getConfig();
            FileConfiguration messages = plugin.getMessages();
        if(args.length > 0){     
//-------------------------------------Comando version----------------------------------------------------------//
//----------------------------------------Desde aqui---------------------------------------//
    if(args[0].equalsIgnoreCase("version")){
        if(sender instanceof Player && (jugador.hasPermission("simplex.version"))){
            String path = "Config.version-message";
            if(config.getString(path).equals("true")){
                List<String> mensaje = messages.getStringList("Messages.version");
                for(int i=0;i<mensaje.size();i++){
                    String texto = mensaje.get(i);
                    jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
                }
            }
            return true;
        }if(sender instanceof Player && !(jugador.hasPermission("simplex.version"))){
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
//----------------------------------------Hasta aqui---------------------------------------//
    
                

//---------------------------------------Comando help--------------------------------------------------------//
//----------------------------------------Desde aqui---------------------------------------//
                else if(args[0].equalsIgnoreCase("help")){
                    if(sender instanceof Player && (jugador.hasPermission("simplex.help"))){
                        String path = "Config.help-message";
                        if(config.getString(path).equals("true")){
                            List<String> mensaje = messages.getStringList("Messages.help");
                            for(int i=0;i<mensaje.size();i++){
                                String texto = mensaje.get(i);
                                jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
                            }
                        }
                        return true;
                    }if(sender instanceof Player && !(jugador.hasPermission("simplex.help"))){
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
 //----------------------------------------Hasta aqui---------------------------------------//

                

 //--------------------------------------Comando reload---------------------------------------------------------//
 //----------------------------------------Desde aqui---------------------------------------//
                else if(args[0].equalsIgnoreCase("reload")){
                    if(sender instanceof Player && (jugador.hasPermission("simplex.reload"))){
                        String path = "Config.reload-message";
                        plugin.reloadConfig();
                        plugin.reloadMessages();
                        if(config.getString(path).equals("true")){
                            List<String> mensaje = messages.getStringList("Messages.reload");
                            for(int i=0;i<mensaje.size();i++){
                                String texto = mensaje.get(i);
                                jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
                            }
                        }
                        return true;
                    }if(sender instanceof Player && !(jugador.hasPermission("simplex.reload"))){
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
 //----------------------------------------Hasta aqui---------------------------------------//

            }else{
                if(sender instanceof Player && (jugador.hasPermission("simplex.help"))){
                String path = "Config.no-argument-message";
                if(config.getString(path).equals("true")){
                    List<String> mensaje = messages.getStringList("Messages.command-no-argument");
                    for(int i=0;i<mensaje.size();i++){
                        String texto = mensaje.get(i);
                        jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', texto.replaceAll("%player%", jugador.getName()).replaceAll("%plugin%", plugin.nombre).replaceAll("%version%", plugin.version)));
                    }
                }
                return true;
                }else if(sender instanceof Player && !(jugador.hasPermission("simplex.help"))){
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
        }
        return false;
    }
}
