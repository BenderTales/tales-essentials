package fr.bendertales.mc.talesessentials.commands.nodes;

import java.util.List;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommand;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class CmdFly implements TalesCommandNode, TalesCommand {

	private final List<String> permissions = List.of("essentials.commands.*", "essentials.commands.fly.self");
	private final List<String> permissionsOther = List.of("essentials.commands.*", "essentials.commands.fly.other");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_MEDIOR, permissions);
	private final CommandNodeRequirements requirementsOther = CommandNodeRequirements.of(OP_MEDIOR, permissionsOther);

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("fly")
                .requires(getRequirements().asPredicate())
				.executes(this::flySelf)
		        .then(
					argument("targets", EntityArgumentType.players())
	                    .requires(requirementsOther.asPredicate())
	                    .executes(this::flyOther)
		        );
	}

	private int flyOther(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var source = context.getSource();
		var targetsSelector = context.getArgument("targets", EntitySelector.class);
		var players = targetsSelector.getPlayers(source);
		for (ServerPlayerEntity player : players) {
			toggleFlight(player);
		}

		return 1;
	}

	private int flySelf(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var source = context.getSource();
		var player = source.getPlayerOrThrow();

		toggleFlight(player);
		return 0;
	}

	private static void toggleFlight(ServerPlayerEntity player) {
		// Does not work ? Will need to find why someday
		if (!player.getAbilities().allowFlying) {
			player.getAbilities().allowFlying = true;
		}
		else {
			player.getAbilities().allowFlying = false;
			player.getAbilities().flying = false;
		}
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
