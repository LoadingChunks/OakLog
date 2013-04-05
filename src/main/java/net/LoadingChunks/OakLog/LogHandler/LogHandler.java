package net.LoadingChunks.OakLog.LogHandler;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LogHandler extends Handler {

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
		System.out.println("Logger Published.");
		
		/*Logger logger = Logger.getLogger(log.getLoggerName());
if (logger instanceof PluginLogger) {
Plugin plugin = ((PluginLogger) logger).getPlugin();
}*/
	}

}
