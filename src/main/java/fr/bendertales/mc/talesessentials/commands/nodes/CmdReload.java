package fr.bendertales.mc.talesessentials.commands.nodes;

import java.util.List;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import fr.bendertales.mc.talesessentials.EssentialsManager;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommand;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import net.minecraft.server.command.ServerCommandSource;

import static net.minecraft.server.command.CommandManager.literal;


public class CmdReload implements TalesCommandNode, TalesCommand {

	private final List<String> permissions = List.of("essentials.commands.*", "essentials.commands.reload");
	private final CommandNodeRequirements requirements = CommandNodeRequirements.of(OP_FULL, permissions);
	private final EssentialsManager essentialsManager;

	public CmdReload(EssentialsManager essentialsManager) {
		this.essentialsManager = essentialsManager;
	}

	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return literal("essentials")
		       .requires(requirements.asPredicate())
		       .then(
				   literal("reload")
				   .executes(this::reload)
		       );
	}

	private int reload(CommandContext<ServerCommandSource> context) {
		essentialsManager.reload();
		return 1;
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return requirements;
	}


}
