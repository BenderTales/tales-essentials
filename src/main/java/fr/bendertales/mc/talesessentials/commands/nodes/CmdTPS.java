package fr.bendertales.mc.talesessentials.commands.nodes;

import java.util.List;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommand;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;


public class CmdTPS implements TalesCommandNode, TalesCommand {

	private static final int MAX_TICK_PER_SECOND = 20;
	private static final int NANOS_FACTOR = 1_000_000;
	private static final int MIN_TICK_SIZE = 1000 / MAX_TICK_PER_SECOND;
	private static final int MIN_TICK_SIZE_NANOS = MIN_TICK_SIZE * NANOS_FACTOR;

	private final List<String>            permissions  = List.of("essentials.commands.*", "essentials.commands.tps");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_MEDIOR, permissions);

	public CmdTPS() {
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("tps")
		       .requires(getRequirements().asPredicate())
		       .executes(this::run);
	}

	private int run(CommandContext<ServerCommandSource> context) {
		var source = context.getSource();
		var server = source.getServer();

		long[] lastTickTimes = server.lastTickLengths;
		long tickCount = 0;
		long totalTime = 0;

		for (long tickTime : lastTickTimes) {
			if (tickTime > 0) {
				tickCount += 1;
				if (tickTime < MIN_TICK_SIZE_NANOS) {
					totalTime += MIN_TICK_SIZE; // if the tick is faster, it will probably wait ?
				}
				else {
					totalTime += tickTime / NANOS_FACTOR;
				}
			}
		}

		long tps = tickCount / (totalTime / 1000);
		var averageTimeSinceStart = server.getTickTime();

		var result = Text.literal("TPS: %d | Average time: %f".formatted(tps, averageTimeSinceStart));
		source.sendFeedback(result, false);

		return (int)tps;
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
