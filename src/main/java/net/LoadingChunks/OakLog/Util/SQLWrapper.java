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
	
	static public void setPlugin(OakLog plugin) {
		SQLWrapper.plugin = plugin;
	}
	
	static public OakLog getPlugin() {
		return plugin;
	}
	
	static public void setConfig(String user, String password, String host, String db) {
		SQLWrapper.user = user;
		SQLWrapper.password = password;
		SQLWrapper.host = host;
		SQLWrapper.db = db;
	}
	
	static public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			SQLWrapper.con = DriverManager.getConnection(SQLWrapper.host, SQLWrapper.user, SQLWrapper.password);
		} catch(SQLException e) {
			e.printStackTrace();
			SQLWrapper.success = false;
		} catch (ClassNotFoundException e) { e.printStackTrace(); SQLWrapper.success = false; }
	}
	
	static public void commitSlot(Player p, Integer slot, ItemStack stack) {
		try {
			PreparedStatement stat = con.prepareStatement("REPLACE INTO `inv_slots` (`server`,`player`,`json`,`slot`) VALUES (?,?,?,?)");
			
			stat.setString(1, SQLWrapper.plugin.getConfig().getString("general.server.name"));
			stat.setString(2, p.getName());
			JSONObject obj = new JSONObject();
			obj.putAll(stack.serialize());
			stat.setString(3, obj.toJSONString());
			stat.setInt(4, slot);
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	static public void commitLog(LogEntry entry) {
		try {
			PreparedStatement stat = con.prepareStatement("INSERT INTO logs ");
		} catch(SQLException e) { e.printStackTrace(); }
	}
	
	
	

}
