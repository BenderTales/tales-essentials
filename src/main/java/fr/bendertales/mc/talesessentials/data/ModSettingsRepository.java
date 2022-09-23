package fr.bendertales.mc.talesessentials.data;

import java.util.List;

import fr.bendertales.mc.talesessentials.EssentialsConstants;
import fr.bendertales.mc.talesservercommon.repository.ModPaths;
import fr.bendertales.mc.talesservercommon.repository.config.ConfigRepository;
import fr.bendertales.mc.talesservercommon.repository.serialization.JsonSerializerRegistration;


public class ModSettingsRepository extends ConfigRepository<ModSettings, ModSettings> {

	public ModSettingsRepository() {
		super(ModSettings.class, ModPaths.createConfigFile(EssentialsConstants.MODID));
	}

	@Override
	protected ModSettings getDefaultConfiguration() {
		return ModSettings.defaultSettings();
	}

	@Override
	protected boolean checkFileContent(ModSettings modSettings) {
		boolean changed = false;

		if (!modSettings.getBroadcastFormat().contains("%MESSAGE%")) {
			modSettings.setBroadcastFormat(ModSettings.defaultBroadcastFormat);
			changed = true;
		}

		return changed;
	}

	@Override
	protected List<JsonSerializerRegistration<?>> getSerializers() {
		return List.of();
	}

	@Override
	protected ModSettings convert(ModSettings modSettings) {
		return modSettings;
	}

	@Override
	protected ModSettings unconvert(ModSettings modSettings) {
		return modSettings;
	}
}
