package arguments.other;

import arguments.AbstractArgumentTest;
import de.paul2708.commands.arguments.CommandArgument;
import de.paul2708.commands.arguments.impl.spigot.GameRuleArgument;
import de.paul2708.commands.arguments.util.Pair;
import org.bukkit.GameRule;

import java.util.function.Function;

/**
 * @author Stu (https://github.com/Stupremee)
 * @since 19.10.19
 */
public final class GameRuleArgumentTest extends AbstractArgumentTest {

    @Override
    public CommandArgument<?> create() {
        return new GameRuleArgument();
    }

    @Override
    public Pair[] validArguments() {
        Function<GameRule<?>, Pair<String, GameRule<?>>> makePair = rule -> Pair.of(rule.getName(), rule);

        GameRule<?> first = GameRule.ANNOUNCE_ADVANCEMENTS;
        GameRule<?> second = GameRule.DO_FIRE_TICK;
        GameRule<?> third = GameRule.SEND_COMMAND_FEEDBACK;
        return new Pair[]{
                makePair.apply(first),
                makePair.apply(second),
                makePair.apply(third),
        };
    }

    @Override
    public String[] invalidArguments() {
        return new String[]{"totally not a valid gamerule", "do fire tick"};
    }
}