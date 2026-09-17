package org.fuseleaf.minecarttrainsfork.extension.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "minecart-trains-fork-server")
public class ConfigData implements me.shedaniel.autoconfig.ConfigData {

    /* # General */

    // Brake After Separation
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.PrefixText
    public boolean brakingAfterSeparation = true;

    // Cart Spacing
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.BoundedDiscrete(min = 3, max = 10)
    public int cartSpacing = 5;
}
