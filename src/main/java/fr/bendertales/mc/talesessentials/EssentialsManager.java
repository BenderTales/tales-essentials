package fr.bendertales.mc.talesessentials;

import fr.bendertales.mc.talesessentials.data.ModSettings;
import fr.bendertales.mc.talesessentials.data.ModSettingsRepository;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;


public enum EssentialsManager {
	INSTANCE;

	private final ModSettingsRepository settingsRepository = new ModSettingsRepository();
	private ModSettings modSettings;

	public void load() {
		reload();
	}

	public void reload() {
		modSettings = settingsRepository.load();
	}

	public String getBroadcastFormat() {
		return modSettings.getBroadcastFormat();
	}

	public void handlePlayerJoin(ServerPlayerEntity player) {
		var motd = modSettings.getMessageOfTheDay();
		if (StringUtils.isNotBlank(motd)) {
			player.sendMessage(Text.of(motd));
		}
	}
}
