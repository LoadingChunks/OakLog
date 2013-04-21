package net.LoadingChunks.OakLog.LogHandler;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.annotation.RegEx;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;

import net.LoadingChunks.OakLog.OakLog;
import net.LoadingChunks.OakLog.Util.SQLWrapper;

public class LogEntry {
	public String serverName;
	public String message;
	public Plugin plugin;
	public String type;
	public Level level;
	public long milliEpoch;
	public ArrayList<Player> associations = new ArrayList<Player>();
	public OakLog loggerPlugin;
	
	public static LogEntry fromRecord(OakLog plugin, LogRecord r) {
		LogEntry ret = new LogEntry();
		ret.loggerPlugin = plugin;
		Logger logger = Logger.getLogger(r.getLoggerName());
		Bukkit.getLogger().severe("Got Plugin Log: " + logger.getName());
		
		ret.level = r.getLevel();
		
		ret.milliEpoch = r.getMillis();
		ret.serverName = SQLWrapper.getPlugin().getServerConfig().getString("server.name");
		ret.message = r.getMessage();
		
		String clean = ChatColor.stripColor(ret.message).replaceAll("\u001B\\[[;\\d]*m", "");
		
		if(logger instanceof PluginLogger && plugin.pluginLoggers.containsKey((PluginLogger)logger)) {
			ret.plugin = SQLWrapper.getPlugin().pluginLoggers.get((PluginLogger)logger);
			ret.type = "Plugin";
		} else if(clean.matches("^\\[([a-zA-Z0-9\\-+_\t]+)\\] (.*)")) {
			ret.type = "Plugin";
			String pluginstr = clean.substring(clean.indexOf("[")+1, clean.indexOf("]"));
			ret.plugin = plugin.getServer().getPluginManager().getPlugin(pluginstr);
		} else {
			if(clean.matches("^<([~a-zA-Z0-9\\-]+)> (.*)")) {
				return null;
			}
		}
		
		if(ret.type == null)
			ret.type = "Core";

		Bukkit.getLogger().severe("OakLog (" + ret.serverName + "|" + ret.type + "|" + ret.milliEpoch + "): " + ret.message);
		
		return ret;
	}
	
	public void associate(Player p) {
		associations.add(p);
	}
	
	public void commit() {
		SQLWrapper.commitLog(this);
	}
}