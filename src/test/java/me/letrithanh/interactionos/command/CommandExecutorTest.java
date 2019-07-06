package me.letrithanh.interactionos.command;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoggerFactory.class})
public class CommandExecutorTest {
	
	private @Mock Logger logger;
	
	@Before
	public void prepare() {
		PowerMockito.mockStatic(LoggerFactory.class);
		PowerMockito.when(LoggerFactory.getLogger(CommandExecutor.class)).thenReturn(logger);
	}

	@Test
	public void execute_validCommand_commandExecuted() {
		CommandExecutor.execute("mkdir -p executeMakeDirectory");
		
		File file = new File("./executeMakeDirectory");
		Assert.assertTrue(file.isDirectory());
		file.deleteOnExit();
	}
	
	@Test
	public void execute_invalidCommand_commandExecuteWithErrorMessage() {
		CommandExecutor.execute("mkdir1 -p executeMakeDirectory");
		Mockito.verify(logger, Mockito.times(1)).error("Can't execute the command [{}]", "mkdir1 -p executeMakeDirectory");
	}
	
	@Test
	public void execute_nullCommand_commandNotExecute() {
		CommandExecutor.execute(null);
		Mockito.verify(logger, Mockito.times(1)).error("Can't execute a blank or null command");
	}
	
	@Test
	public void execute_blankCommand_commandNotExecute() {
		CommandExecutor.execute("      ");
		Mockito.verify(logger, Mockito.times(1)).error("Can't execute a blank or null command");
	}
	
}
