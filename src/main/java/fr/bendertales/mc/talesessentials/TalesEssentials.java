package fr.bendertales.mc.talesessentials;

import fr.bendertales.mc.talesessentials.commands.CommandRegistrar;
import net.fabricmc.api.ModInitializer;


public class TalesEssentials implements ModInitializer {

	@Override
	public void onInitialize() {

		EssentialsManager.INSTANCE.load();
		CommandRegistrar.registerCommands();

	}

}
