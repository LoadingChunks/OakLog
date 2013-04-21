package net.LoadingChunks.OakLog;

/*
    This file is part of OakLog

    OakLog is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    OakLog is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with OakLog.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.File;
import java.util.HashMap;

import net.LoadingChunks.OakLog.LogHandler.LogHandler;
import net.LoadingChunks.OakLog.Util.SQLWrapper;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class OakLog extends JavaPlugin {

	//ClassListeners
	private final OakLogCommandExecutor commandExecutor = new OakLogCommandExecutor(this);
	private final OakLogEventListener eventListener = new OakLogEventListener(this);
	private final LogHandler logHandler = new LogHandler(this);
	
	private FileConfiguration globalConfig;
	private FileConfiguration serverConfig;
	public HashMap<PluginLogger, Plugin> pluginLoggers = new HashMap<PluginLogger, Plugin>();
	//private final OakLogEventListener eventListener = new OakLogEventListener(this);
	//ClassListeners

	public void onDisable() {
		getServer().getLogger().removeHandler(logHandler);
	}

	public void onEnable() {
		getLogger().info("Loading OakLog Configs");
		reloadConfigs();
		
		getLogger().info("Connecting to OakLog Database");
		SQLWrapper.setConfig(this, getGlobalConfig().getString("db.user"), getGlobalConfig().getString("db.pass"), getGlobalConfig().getString("db.host"), getGlobalConfig().getString("db.name"));
		SQLWrapper.connect();

		getCommand("oak").setExecutor(commandExecutor);
		
		getServer().getPluginManager().registerEvents(eventListener, this);
		
		getLogger().info("Integrating OakLog Handler...");
		getServer().getLogger().addHandler(logHandler);
		
		getLogger().info("Logger Implemented!");
		
		getLogger().info("Collating list of plugins...");
		for(Plugin p : Bukkit.getServer().getPluginManager().getPlugins()) {
			pluginLoggers.put((PluginLogger)p.getLogger(), p);
		}
		
		getLogger().info("Found " + pluginLoggers.size() + " plugins.");
	}
	
	public void reloadConfigs() {
		globalConfig = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "global.yml"));
		serverConfig = getConfig();
	}
	
	public FileConfiguration getGlobalConfig() {
		if(globalConfig == null)
			globalConfig = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "global.yml"));
					
		return globalConfig;
	}
	
	public FileConfiguration getServerConfig() {
		if(serverConfig == null)
			serverConfig = getConfig();
		
		return serverConfig;
	}
}
