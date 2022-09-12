package fr.bendertales.mc.talesessentials.commands.nodes;

import java.util.List;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import fr.bendertales.mc.talesessentials.EssentialsManager;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommand;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class CmdBroadcast implements TalesCommandNode, TalesCommand {

	private final List<String> permissions = List.of("essentials.commands.*", "essentials.commands.broadcast");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_JUNIOR, permissions);

	private final EssentialsManager essentialsManager;

	public CmdBroadcast(EssentialsManager essentialsManager) {
		this.essentialsManager = essentialsManager;
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("broadcast")
		       .requires(requirements.asPredicate())
		       .then(
			        argument("message", StringArgumentType.greedyString())
			        .executes(this::broadcast)
		       );
	}

	private int broadcast(CommandContext<ServerCommandSource> context) {
		var message = context.getArgument("message", String.class);
		if (StringUtils.isNotBlank(message)) {
			var playerManager = context.getSource().getServer().getPlayerManager();
			var text = Text.literal(essentialsManager.getBroadcastFormat().replace("%MESSAGE%", message));
			playerManager.broadcast(text, false);
			return 1;
		}
		return 0;
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}
}
