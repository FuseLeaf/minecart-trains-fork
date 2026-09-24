package org.fuseleaf.minecarttrainsfork.client.extension.config;

import org.fuseleaf.minecarttrainsfork.client.config.ClientConfigManager;
import org.fuseleaf.minecarttrainsfork.client.gui.ConfigEntryScreen;
import org.fuseleaf.minecarttrainsfork.client.util.ToastUtil;

import net.minecraft.client.gui.screens.Screen;

public class ConfigEntry {

    public static Screen get(Screen parent) {
        if (ClientConfigManager.isConfigAvailable()) {
            return new ConfigEntryScreen(parent);
        } else {
            ToastUtil.toast(
                "toast.minecart-trains-fork.api_not_found.title",
                "toast.minecart-trains-fork.api_not_found.desc"
            );

            return parent;
        }
    }
}
