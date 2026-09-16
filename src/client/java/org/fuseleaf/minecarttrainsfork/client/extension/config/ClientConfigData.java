package org.fuseleaf.minecarttrainsfork.client.extension.config;

import org.fuseleaf.minecarttrainsfork.client.extension.config.ConfigEnum.ParticleOption;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "minecart-trains-fork-client")
public class ClientConfigData implements ConfigData {

    /* # General */

    // Show Chain
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.PrefixText
    public boolean showChain = true;

    // Show Head Particle
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.PrefixText
    public boolean showHeadParticle = true;

    // Show Link Particle
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.PrefixText
    public boolean showLinkParticle = false;

    // Show Notice
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.PrefixText
    public boolean showNotice = true;

    /* # Advanced */

    // Chain Width
    @ConfigEntry.Category("advanced")
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    public int chainWidth = 5;

    // Always show head particle
    @ConfigEntry.Category("advanced")
    @ConfigEntry.Gui.PrefixText
    public boolean alwaysShowHeadParticle = false;

    // Head Particle Type
    @ConfigEntry.Category("advanced")
    @ConfigEntry.ColorPicker
    public ParticleOption headParticleType = ParticleOption.composter;

    // Head Particle Count
    @ConfigEntry.Category("advanced")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 32)
    public int headParticleCount = 6;

    // Head Particle Height
    @ConfigEntry.Category("advanced")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int headParticleHeight = 14;

    // Head Particle Interval
    @ConfigEntry.Category("advanced")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int headParticleInterval = 40;

    // Link Particle Type
    @ConfigEntry.Category("advanced")
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.EnumHandler
    public ParticleOption linkParticleType = ParticleOption.soul_fire_flame;

    // Link Particle Height
    @ConfigEntry.Category("advanced")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 20)
    public int linkParticleHeight = 6;

    // Link Particle Interval
    @ConfigEntry.Category("advanced")
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    public int linkParticleInterval = 40;
}
