package component;

import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.Validation;
import de.paul2708.commands.core.CommandRegistry;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

/**
 * This class tests all methods in the {@link de.paul2708.commands.core.CommandRegistry}.
 *
 * @author Paul2708
 */
public class CommandRegistryTest {

    private CommandRegistry registry;

    /**
     * Create a command registry with a mocked java plugin for each test.
     */
    @Before
    public void setUp() {
        this.registry = CommandRegistry.create(mock(JavaPlugin.class));
    }

    /**
     * Test illegal command registry creation by passing <code>null</code> value.
     */
    @Test(expected = IllegalArgumentException.class)
    public void createIllegalRegistry() {
        CommandRegistry.create(null);
    }

    /**
     * Test null arguments.
     */
    @Test(expected = IllegalArgumentException.class)
    public void addIllegalArguments() {
        registry.addArgument((CommandArgument<?>[]) null);
    }

    /**
     * Test multiple null arguments.
     */
    @Test(expected = IllegalArgumentException.class)
    public void addNullArguments() {
        registry.addArgument(null, null);
    }

    /**
     * Override added command arguments.
     */
    @Ignore
    @Test
    public void overrideArguments() {
        registry.addArgument(new CommandArgument<Object>() {

            @Override
            public Validation<Object> validate(String argument) {
                return Validation.valid(argument);
            }

            @Override
            public String usage() {
                return null;
            }

            @Override
            public List<String> autoComplete(String argument) {
                return null;
            }
        }, new CommandArgument<Object>() {

            @Override
            public Validation<Object> validate(String argument) {
                return Validation.invalid(argument);
            }

            @Override
            public String usage() {
                return null;
            }

            @Override
            public List<String> autoComplete(String argument) {
                return null;
            }
        });

        assertEquals(1, registry.getArguments().size());
        assertFalse(registry.getArguments().get(0).validate("argument").isValid());
    }

    /**
     * Test null commands.
     */
    @Test(expected = IllegalArgumentException.class)
    public void registerNullCommand() {
        registry.register((Object[]) null);
    }
}
