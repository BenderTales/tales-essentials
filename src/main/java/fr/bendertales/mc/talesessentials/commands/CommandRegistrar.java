package fr.bendertales.mc.talesessentials.commands;

import java.util.stream.Stream;

import fr.bendertales.mc.talesessentials.EssentialsManager;
import fr.bendertales.mc.talesessentials.commands.nodes.*;
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
		var manager = EssentialsManager.INSTANCE;
		return Stream.of(
			new CmdReload(manager),
			new CmdPing(),
			new CmdHeal(),
			new CmdFeed(),
			new CmdExp(),
			new CmdBroadcast(manager),
			new CmdClearInventory(),
			new CmdTop(),
			new CmdFly()
		);
	}
}
