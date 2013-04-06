package net.LoadingChunks.OakLog.LogHandler;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;

import net.LoadingChunks.OakLog.Util.SQLWrapper;

public class LogEntry {
	public String serverName;
	public String message;
	public Plugin plugin;
	public long milliEpoch;
	
	public void commit() {
		SQLWrapper.commitLog(this);
	}
	
	public static LogEntry fromRecord(LogRecord r) {
		LogEntry ret = new LogEntry();
		Logger logger = Logger.getLogger(r.getLoggerName());
		
		if(logger instanceof PluginLogger)
			ret.plugin = Bukkit.getPluginManager().getPlugin(((PluginLogger) logger).getName());
		
		ret.milliEpoch = r.getMillis();
		ret.serverName = SQLWrapper.getPlugin().getServerConfig().getString("server.name");
		ret.message = r.getMessage();
		
		return ret;
	}
}
