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
import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class CmdFeed implements TalesCommand, TalesCommandNode {

	private final List<String> permissions = List.of("essentials.commands.*", "essentials.commands.feed");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_JUNIOR, permissions);

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("feed")
		        .requires(getRequirements().asPredicate())
		        .executes(this::feedSelf)
                .then(
					argument("targets", EntityArgumentType.players())
                        .executes(this::feedOthers)
	            )
				;
	}

	private int feedOthers(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var source = context.getSource();
		var entitySelector = context.getArgument("targets", EntitySelector.class);
		var players = entitySelector.getPlayers(source);
		for (ServerPlayerEntity player : players) {
			feed(player);
		}
		source.sendMessage(Text.literal("Fed targets"));
		return 1;
	}

	private int feedSelf(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		var source = context.getSource();
		var player = source.getPlayerOrThrow();
		feed(player);
		player.sendMessage(Text.literal("Fed"), true);
		return 1;
	}

	private void feed(ServerPlayerEntity player) {
		var hungerManager = player.getHungerManager();
		hungerManager.setFoodLevel(20);
		hungerManager.setExhaustion(0.0f);
		hungerManager.setSaturationLevel(5f);
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
