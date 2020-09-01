package de.paul2708.commands.core.delegator.impl;

import de.paul2708.commands.arguments.impl.StringArgument;
import de.paul2708.commands.arguments.impl.primitive.DoubleArgument;
import de.paul2708.commands.arguments.impl.primitive.IntegerArgument;
import de.paul2708.commands.core.annotation.Command;
import de.paul2708.commands.core.command.SimpleCommand;
import de.paul2708.commands.core.delegator.CommandDelegatorTest;
import org.bukkit.command.CommandSender;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static de.paul2708.commands.arguments.CommandArgument.condition;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * This class tests if the command invocation works as expected.
 *
 * @author Paul2708
 * @see de.paul2708.commands.core.command.CommandDelegator#execute(CommandSender, List)
 */
final class ExecutionTest extends CommandDelegatorTest {

    @Test
    void valid() {
        SimpleCommand command = resolve(TestCommand.class, "test", List.of(new StringArgument(), new IntegerArgument(),
                new DoubleArgument()));

        assertSuccess(mockSender(), command, "heyho", "42", "3.14");
    }

    @Test
    void errorOnExecution() {
        SimpleCommand command = resolve(TestCommand.class, "error", Collections.emptyList());
        assertMessage("command.error", mockSender(), command);
    }

    @Test
    void failedCondition() {
        SimpleCommand command = resolve(TestCommand.class, "condition", Collections.emptyList());
        assertMessage("command.failed_condition", mockSender(), command);
    }

    private CommandSender mockSender() {
        CommandSender sender = mock(CommandSender.class);
        doReturn(true).when(sender).isOp();

        return sender;
    }

    public static class TestCommand {

        @Command(name = "test")
        public void command(CommandSender console, String word, int a, double b) {

        }

        @Command(name = "error")
        public void error(CommandSender sender) {
            throw new NullPointerException("I occur while executing the command.");
        }

        @Command(name = "condition")
        public void conditionCommand(CommandSender sender) {
            condition(false, "This will fail every time");
        }
    }
}