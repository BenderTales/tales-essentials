package fr.bendertales.mc.talesessentials;

import fr.bendertales.mc.talesessentials.data.ModSettings;
import fr.bendertales.mc.talesessentials.data.ModSettingsRepository;


public enum EssentialsManager {
	INSTANCE;

	private final ModSettingsRepository settingsRepository = new ModSettingsRepository();
	private ModSettings modSettings;

	public void load() {
		reload();
	}

	public void reload() {
		modSettings = settingsRepository.getConfig();
	}

	public String getBroadcastFormat() {
		return modSettings.getBroadcastFormat();
	}
}
