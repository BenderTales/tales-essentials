package fr.bendertales.mc.talesessentials.commands;

import java.util.stream.Stream;

import fr.bendertales.mc.talesessentials.commands.nodes.CmdFeed;
import fr.bendertales.mc.talesessentials.commands.nodes.CmdHeal;
import fr.bendertales.mc.talesessentials.commands.nodes.CmdTop;
import fr.bendertales.mc.talesservercommon.commands.TalesCommandNode;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;


public class CommandRegistrar {

	public static void registerCommands() {
		var event = CommandRegistrationCallback.EVENT;

		event.register((dispatcher, ra, env) ->
            buildCommands().forEach(cmd -> dispatcher.register(cmd.asBrigadierNode()))
		);
	}

	private static Stream<TalesCommandNode> buildCommands() {
		return Stream.of(
			new CmdHeal(),
			new CmdFeed(),
			new CmdTop()
		);
	}
}
