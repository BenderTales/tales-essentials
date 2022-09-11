package fr.bendertales.mc.talesessentials.commands.nodes;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;


public class CmdPing implements TalesCommandNode {
	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("ping").executes(this::ping);
	}

	private int ping(CommandContext<ServerCommandSource> context) {
		context.getSource().sendMessage(Text.of("Pong"));
		return 1;
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return CommandNodeRequirements.noRequirements();
	}
}
