package fr.bendertales.mc.talesessentials.commands.nodes;

import java.util.List;

import com.mojang.brigadier.arguments.IntegerArgumentType;
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


public class CmdExp implements TalesCommandNode, TalesCommand {

	private final List<String> permissions = List.of("essentials.commands.*", "essentials.commands.exp");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_MEDIOR, permissions);

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("exp")
		       .requires(requirements.asPredicate())
		       .then(
				   argument("amount", IntegerArgumentType.integer(1))
				        .executes(this::expSelf)
					    .then(
							argument("players", EntityArgumentType.players())
								.executes(this::expOther)
					    )
		       );
	}

	private int expOther(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var amount = context.getArgument("amount", Integer.class);
		var source = context.getSource();
		var playersSelector = context.getArgument("players", EntitySelector.class);
		var players = playersSelector.getPlayers(source);

		for (ServerPlayerEntity player : players) {
			exp(player, amount);
		}

		return 1;
	}

	private int expSelf(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var amount = context.getArgument("amount", Integer.class);
		var source = context.getSource();
		var player = source.getPlayerOrThrow();

		exp(player, amount);

		return 1;
	}

	private void exp(ServerPlayerEntity player, Integer amount) {
		player.addExperience(amount);
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}

}
