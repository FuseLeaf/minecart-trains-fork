package org.fuseleaf.minecarttrainsfork.client.extension.config;

import org.fuseleaf.minecarttrainsfork.client.gui.ConfigEntryScreen;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ConfigEntry implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            return new ConfigEntryScreen(parent);
        };
    }
}
