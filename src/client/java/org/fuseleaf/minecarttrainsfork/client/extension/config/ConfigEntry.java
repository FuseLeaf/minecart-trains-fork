package org.fuseleaf.minecarttrainsfork.client.extension.config;

import org.fuseleaf.minecarttrainsfork.client.config.ClientConfigManager;
import org.fuseleaf.minecarttrainsfork.client.gui.ConfigEntryScreen;
import org.fuseleaf.minecarttrainsfork.client.util.ToastUtil;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ConfigEntry implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            if (ClientConfigManager.isConfigAvailable()) {
                return new ConfigEntryScreen(parent);
            } else {
                ToastUtil.toast(
                    "toast.minecart-trains-fork.api_not_found.title",
                    "toast.minecart-trains-fork.api_not_found.desc"
                );

                return parent;
            }
        };
    }
}
