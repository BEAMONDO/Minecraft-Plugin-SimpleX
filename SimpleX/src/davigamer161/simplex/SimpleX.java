package davigamer161.simplex;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import davigamer161.simplex.comandos.ComandoHeal;
import davigamer161.simplex.comandos.ComandoSetSpawn;
import davigamer161.simplex.comandos.ComandoSpawn;
import davigamer161.simplex.comandos.ComandoFly;
import davigamer161.simplex.comandos.ComandoPrincipal;
import net.milkbowl.vault.economy.Economy;

public class SimpleX extends JavaPlugin{
	private FileConfiguration messages = null;
  private File messagesFile = null;
  private static Economy econ = null;
  public String rutaConfig;
	PluginDescriptionFile pdffile = getDescription();
	public String version = pdffile.getVersion();
	public String latestversion;
	public String nombre = ChatColor.RED+"["+ChatColor.YELLOW+this.pdffile.getName()+ChatColor.RED+"] "+ChatColor.WHITE;
	
	
	//---------------------Para cuando se activa el plugin----------------------------------//
    //------------------------------Desde aqui-----------------------------//
	public void onEnable(){
		Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"<------------------------------------>");
	  Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"Enabled, ("+ChatColor.GREEN+"Version: "+ChatColor.AQUA+version+ChatColor.WHITE+")");
	  if(setupEconomy()){
      Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"Vault "+ChatColor.GREEN+"found");
	  }else{
	      Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"Vault "+ChatColor.RED+"not found");
    }
	  Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.GOLD+"Thanks for use my plugin :)");
    Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.YELLOW+"Made by "+ChatColor.LIGHT_PURPLE+"davigamer161");
    Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"<------------------------------------>");
    registrarComandos();
    registrarConfig();
    registrarMensajes();
    updateChecker();
	}
	//------------------------------Hasta aqui-----------------------------//



    //------------------Para cuando se desactiva el plugin----------------------------------//
    //------------------------------Desde aqui-----------------------------//
	public void onDisable(){
	  Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.WHITE+"Disabled, ("+ChatColor.GREEN+"Version: "+ChatColor.AQUA+version+ChatColor.WHITE+")");
  }
	//------------------------------Hasta aqui-----------------------------//

    

    //-----------------------Para registrar comandos----------------------------------------//
    //------------------------------Desde aqui-----------------------------//
	public void registrarComandos() {
		this.getCommand("simplex").setExecutor(new ComandoPrincipal(this));
		this.getCommand("spawn").setExecutor(new ComandoSpawn(this));
    this.getCommand("setspawn").setExecutor(new ComandoSetSpawn(this));
    this.getCommand("heal").setExecutor(new ComandoHeal(this));
    this.getCommand("fly").setExecutor(new ComandoFly(this));
	}
	//------------------------------Hasta aqui-----------------------------//


    
    //--------------------------Para crear config.yml--------------------------------------//
    //------------------------------Desde aqui-----------------------------//
	public void registrarConfig(){
        File config = new File(this.getDataFolder(),"config.yml");
        rutaConfig = config.getPath();
        if(!config.exists()){
          this.getConfig().options().copyDefaults(true);
          saveConfig();
        }
      }
	//------------------------------Hasta aqui-----------------------------//
	
	
	
	//-------------------------Para crear messages.yml---------------------------------------//
    //------------------------------Desde aqui-----------------------------//
    public FileConfiguration getMessages(){
      if(messages == null){
        reloadMessages();
      }
      return messages;
    }
    public void reloadMessages(){
      if(messages == null){
        messagesFile = new File(getDataFolder(),"messages.yml");
      }
      messages = YamlConfiguration.loadConfiguration(messagesFile);
      Reader defConfigStream;
      try{
        defConfigStream = new InputStreamReader(this.getResource("messages.yml"),"UTF8");
        if(defConfigStream != null){
          YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
          messages.setDefaults(defConfig);
        }
      }catch(UnsupportedEncodingException e){
        e.printStackTrace();
      }
    }
    public void saveMessages(){
      try{
        messages.save(messagesFile);
      }catch(IOException e){
          e.printStackTrace();
      }
    }
    public void registrarMensajes(){
      messagesFile = new File(this.getDataFolder(),"messages.yml");
      if(!messagesFile.exists()){
        this.getMessages().options().copyDefaults(true);
        saveMessages();
      }
    }
      //------------------------------Hasta aqui-----------------------------//
      
      
      
    //-------------------------------Para usar vault--------------------------------------//
      //------------------------------Desde aqui-----------------------------//
      private boolean setupEconomy(){
        if(getServer().getPluginManager().getPlugin("Vault") == null){
          return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
          return false;
        }
        econ = rsp.getProvider();
        return econ != null;
      }
      public Economy getEconomy(){
        return this.econ;
      }
      //------------------------------Hasta aqui-----------------------------//
      
      
      
    //-------------------------------Para auto actualizar--------------------------------------//
      //------------------------------Desde aqui-----------------------------//
      public void updateChecker(){		  
  		  try {
  			  HttpURLConnection con = (HttpURLConnection) new URL(
  	                  "https://api.spigotmc.org/legacy/update.php?resource=112389").openConnection();
  	          int timed_out = 1250;
  	          con.setConnectTimeout(timed_out);
  	          con.setReadTimeout(timed_out);
  	          latestversion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
  	          if (latestversion.length() <= 7) {
  	        	  if(!version.equals(latestversion)){
  	        		  Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.RED +"There is a new version available. "+ChatColor.YELLOW+
  	        				  "("+ChatColor.GRAY+latestversion+ChatColor.YELLOW+")");
  	        		  Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.RED+"You can download it at: "+ChatColor.WHITE+"https://www.spigotmc.org/resources/112389/");  
  	        	  }      	  
  	          }
  	      } catch (Exception ex) {
  	    	  Bukkit.getConsoleSender().sendMessage(nombre + ChatColor.RED +"Error while checking update.");
  	      }
  	  }
      //------------------------------Hasta aqui-----------------------------//
}
