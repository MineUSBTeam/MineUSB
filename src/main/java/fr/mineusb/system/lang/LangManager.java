package fr.mineusb.system.lang;

import java.util.Collection;
import java.util.HashMap;

import fr.mineusb.MineUSB;
import fr.mineusb.exceptions.LangNotFoundException;
import fr.mineusb.system.lang.defaults.EN_Lang;
import fr.mineusb.system.lang.defaults.FR_Lang;

public class LangManager {

	private static HashMap<String, Lang> languages = new HashMap<String, Lang>();

	public LangManager() {
		// Register defaults launcher
		EN_Lang enLang = new EN_Lang();
		FR_Lang frLang = new FR_Lang();
		languages.put(enLang.getId(), enLang);
		languages.put(frLang.getId(), frLang);
	}

	public static void registerLang(Lang lang) {
		if (!languages.containsKey(lang.getId())) {
			languages.put(lang.getId(), lang);
			MineUSB.getConsole().info(
					"Lang: " + lang.getName() + " was registered!");
		}
	}

	public static void unregisterLang(Lang lang)
			throws LangNotFoundException {
		if (languages.containsKey(lang.getId())) {
			languages.remove(lang.getId());
		} else {
			throw new LangNotFoundException("Lang: " + lang.getName()
					+ " was not found!");
		}
	}

	public static Lang getLang(String id) throws LangNotFoundException {
		if (languages.containsKey(id)) {
			return languages.get(id);
		} else {
			throw new LangNotFoundException("Lang " + id
					+ " was not found!");
		}
	}
	
	public static Lang getLangByName(String name) throws LangNotFoundException {
		for (Lang lang : getLangList()) {
			if (lang.getName().equals(name))
				return lang;
		}
		throw new LangNotFoundException("Lang " + name
				+ " was not found!");
	}

	public static Collection<Lang> getLangList() {
		return languages.values();
	}

}
