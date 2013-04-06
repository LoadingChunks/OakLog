package net.LoadingChunks.OakLog;

/*
    This file is part of OakLog

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.io.File;

import net.LoadingChunks.OakLog.LogHandler.LogHandler;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class OakLog extends JavaPlugin {

	//ClassListeners
	private final OakLogCommandExecutor commandExecutor = new OakLogCommandExecutor(this);
	
	private FileConfiguration globalConfig;
	private FileConfiguration serverConfig;
	//private final OakLogEventListener eventListener = new OakLogEventListener(this);
	//ClassListeners

	public void onDisable() {
		// add any code you want to be executed when your plugin is disabled
	}

	public void onEnable() { 

		PluginManager pm = this.getServer().getPluginManager();

		getCommand("command").setExecutor(commandExecutor);

		// you can register multiple classes to handle events if you want
		// just call pm.registerEvents() on an instance of each class
		//pm.registerEvents(eventListener, this);
		
		getLogger().addHandler(new LogHandler());

		// do any other initialisation you need here...
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
