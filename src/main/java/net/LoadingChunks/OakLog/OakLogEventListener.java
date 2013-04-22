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

import java.util.logging.Level;

import net.LoadingChunks.OakLog.LogHandler.LogEntry;

import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class OakLogEventListener implements Listener {

	private OakLog plugin;

	public OakLogEventListener(OakLog plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void OakLogAsyncChatEvent(AsyncPlayerChatEvent event) {
		LogEntry entry = new LogEntry();
		entry.associations.add(event.getPlayer());
		
		entry.message = event.getFormat();
		entry.type = "Chat";
		entry.serverName = plugin.getServerConfig().getString("server.name");
		entry.milliEpoch = System.currentTimeMillis();
		entry.level = Level.INFO;
		
		entry.commit();
	}
}
