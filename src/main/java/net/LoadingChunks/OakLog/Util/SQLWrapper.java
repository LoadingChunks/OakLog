package net.LoadingChunks.OakLog.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;

import net.LoadingChunks.OakLog.OakLog;
import net.LoadingChunks.OakLog.LogHandler.LogEntry;

public class SQLWrapper {
	static private OakLog plugin;
	static private Connection con;
	static private Statement stmt;
	static private boolean success;
	
	static private String user;
	static private String password;
	static private String host;
	static private String db;
	
	static public OakLog getPlugin() {
		return plugin;
	}
	
	static public void setConfig(OakLog plugin, String user, String password, String host, String db) {
		SQLWrapper.plugin = plugin;
		SQLWrapper.user = user;
		SQLWrapper.password = password;
		SQLWrapper.host = host;
		SQLWrapper.db = db;
	}
	
	static public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String uri = "jdbc:mysql://" + SQLWrapper.host + ":3306/" + SQLWrapper.db;
			plugin.getLogger().severe("Connect to DB: " + uri);
			SQLWrapper.con = DriverManager.getConnection(uri, SQLWrapper.user, SQLWrapper.password);
		} catch(SQLException e) {
			e.printStackTrace();
			SQLWrapper.success = false;
		} catch (ClassNotFoundException e) { e.printStackTrace(); SQLWrapper.success = false; }
	}
	
	static public void commitLog(LogEntry entry) {
		if(con == null) {
			plugin.getLogger().severe("Could not connect to logger database!");
			return;
		}

		try {
			PreparedStatement stat = con.prepareStatement("INSERT INTO logs (`server`,`type`,`level`,`message`,`plugin`) VALUES (?,?,?,?,?)");
			stat.setString(1, plugin.getServerConfig().getString("server.name"));
			stat.setString(2, entry.type);
			
			stat.setString(3, entry.level.getName());

			stat.setString(4, entry.message);
			
			if(entry.plugin != null)
				stat.setString(5, entry.plugin.getName());
			else
				stat.setString(5, null);
			
			stat.execute();
		} catch(SQLException e) { e.printStackTrace(); }
	}
	
	
	

}
