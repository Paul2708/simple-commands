package de.paul2708.commands.core.delegator;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.core.command.CommandDelegator;
import de.paul2708.commands.core.command.CommandType;
import de.paul2708.commands.core.command.SimpleCommand;
import de.paul2708.commands.core.language.LanguageSelector;
import de.paul2708.commands.language.MessageResource;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

/**
 * This abstract class holds utility methods to mock and assert the delegation process.
 *
 * @author Paul2708
 * @see CommandDelegator
 */
public abstract class CommandDelegatorTest {

    /**
     * Assert that the first message that got send to the sender is the key.
     * Usually this indicates that the command wasn't executed.
     *
     * @param key       first expected message resource key that got send to the sender
     * @param sender    command sender
     * @param command   executed command
     * @param arguments passed arguments
     */
    public void assertMessage(String key, CommandSender sender, SimpleCommand command, String... arguments) {
        LanguageSelector selector = mock(LanguageSelector.class);

        doAnswer(invocation -> {
            MessageResource resource = invocation.getArgument(1);
            assertEquals(key, resource.getKey());
            return null;
        }).when(selector).sendMessage(any(), any());

        CommandDelegator delegator = new CommandDelegator(selector, command);
        delegator.execute(sender, "ignored", arguments);
    }

    /**
     * Assert that the command was executed properly.
     * This means that no message will be sent to the sender.
     *
     * @param sender    command sender
     * @param command   executed command
     * @param arguments passed arguments
     */
    public void assertSuccess(CommandSender sender, SimpleCommand command, String... arguments) {
        LanguageSelector selector = mock(LanguageSelector.class);

        doAnswer(invocation -> {
            MessageResource resource = invocation.getArgument(1);
            fail(String.format("Expected successfully command execution but sender got the message %s",
                    resource.getKey()));
            return null;
        }).when(selector).sendMessage(any(), any());

        CommandDelegator delegator = new CommandDelegator(selector, command);
        delegator.execute(sender, "ignored", arguments);
    }

    /**
     * Resolve a simple command by its class and the command name.
     * To wrap the commands, you have to specify the list of command arguments.
     *
     * @param clazz     class that holds the command method
     * @param name      command name
     * @param arguments list of command arguments that match the command method parameters
     * @return resolved command or <code>null</code> if the command was not found (the test will fail)
     */
    public SimpleCommand resolve(Class<?> clazz, String name, List<CommandArgument<?>> arguments) {
        for (Method method : clazz.getMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command command = method.getAnnotation(Command.class);

                if (command.name().equals(name)) {
                    CommandType commandType = CommandType.resolveType(method.getParameterTypes()[0]);
                    try {
                        return new SimpleCommand(command, commandType, clazz.getConstructor().newInstance(), method,
                                arguments);
                    } catch (Exception e) {
                        fail("Couldn't create a new instance.", e);
                    }
                }
            }
        }

        fail("Couldn't resolve the command.");
        return null;
    }
}