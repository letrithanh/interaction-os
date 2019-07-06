package me.letrithanh.interactionos.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandExecutor {
	
	/**
	 * Currently, I can't pass all unit tests for this class when using logger as final and just getLogger one time.<br/>
	 * The logger need to get again when run a unit test.
	 */
	private static Logger logger = LoggerFactory.getLogger(CommandExecutor.class);
	
	public static void execute(String command) {
		logger = LoggerFactory.getLogger(CommandExecutor.class);
		
		if(StringUtils.isBlank(command)) {
			logger.error("Can't execute a blank or null command");
			return;
		}
		
		Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			process = runtime.exec(command);
			process.waitFor();
			executedCommandResult(process, command);
		} catch (IOException e) {
			logger.error("Can't execute the command [{}]", command);
			logger.error("{}", e);
		} catch (InterruptedException e) {
			logger.error("Interrupted when execute the command [{}]", command);
			logger.error("{}", e);
		}
	}

	private static void executedCommandResult(Process process, String command) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder result = new StringBuilder();
		String line = "";
		while ((line = bufferedReader.readLine()) != null) {
			result.append(line);
		}
		logger.info("Executed command [{}]", command);
		logger.info("{}", result);
	}
}
