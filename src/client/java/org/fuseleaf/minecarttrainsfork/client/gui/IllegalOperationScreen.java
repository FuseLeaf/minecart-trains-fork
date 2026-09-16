package org.fuseleaf.minecarttrainsfork.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class IllegalOperationScreen {

    public static ConfirmScreen get(Screen parent) {
        return new ConfirmScreen(
            (result) -> Minecraft.getInstance().gui.setScreen(parent), // Return to the previous menu
            Component.translatable("screen.minecart-trains-fork.IllegalOperationScreen.title"),
            Component.translatable("screen.minecart-trains-fork.IllegalOperationScreen.desc"),
            Component.translatable("screen.minecart-trains-fork.IllegalOperationScreen.yes"),
            Component.translatable("screen.minecart-trains-fork.IllegalOperationScreen.no")
        );
    }
}
