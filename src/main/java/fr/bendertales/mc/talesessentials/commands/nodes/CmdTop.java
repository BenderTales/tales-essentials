package fr.bendertales.mc.talesessentials.commands.nodes;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import fr.bendertales.mc.talesservercommon.commands.CommandNodeRequirements;
import fr.bendertales.mc.talesservercommon.commands.TalesCommand;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import net.minecraft.server.command.ServerCommandSource;


public class CmdTop implements TalesCommandNode, TalesCommand {
	@Override
	public LiteralArgumentBuilder<ServerCommandSource> asBrigadierNode() {
		return null;
	}

	@Override
	public CommandNodeRequirements getRequirements() {
		return null;
	}
}
