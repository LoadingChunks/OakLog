package net.LoadingChunks.OakLog.LogHandler;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import net.LoadingChunks.OakLog.OakLog;

import org.bukkit.Bukkit;

public class LogHandler extends Handler {
	
	private OakLog plugin;
	
	public LogHandler(OakLog plugin) {
		this.plugin = plugin;
	}

	@Override
	public void close() throws SecurityException {
		System.out.println("Logger Closed.");
	}

	@Override
	public void flush() {
		System.out.println("Logger Flushed.");
	}

	@Override
	public void publish(LogRecord log) {		
			LogEntry entry = LogEntry.fromRecord(plugin, log);
			if(entry != null)
				entry.commit();
	}

}
