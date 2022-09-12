package fr.bendertales.mc.talesessentials.data;

public class ModSettings {

	public static final String defaultBroadcastFormat = "§4[/!\\]§e%MESSAGE%";

	private String broadcastFormat;

	public String getBroadcastFormat() {
		return broadcastFormat;
	}

	public void setBroadcastFormat(String broadcastFormat) {
		this.broadcastFormat = broadcastFormat;
	}

	public static ModSettings defaultSettings() {
		var modSettings = new ModSettings();

		modSettings.broadcastFormat = defaultBroadcastFormat;

		return modSettings;
	}
}
