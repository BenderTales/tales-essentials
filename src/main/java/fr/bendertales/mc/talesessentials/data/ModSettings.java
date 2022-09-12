package fr.bendertales.mc.talesessentials.data;

public class ModSettings {

	public static final String defaultBroadcastFormat = "§4[/!\\]§e%MESSAGE%";

	private String broadcastFormat;
	private String messageOfTheDay;

	public String getBroadcastFormat() {
		return broadcastFormat;
	}

	public void setBroadcastFormat(String broadcastFormat) {
		this.broadcastFormat = broadcastFormat;
	}

	public String getMessageOfTheDay() {
		return messageOfTheDay;
	}

	public void setMessageOfTheDay(String messageOfTheDay) {
		this.messageOfTheDay = messageOfTheDay;
	}

	public static ModSettings defaultSettings() {
		var modSettings = new ModSettings();

		modSettings.broadcastFormat = defaultBroadcastFormat;
		modSettings.messageOfTheDay = "";

		return modSettings;
	}
}
