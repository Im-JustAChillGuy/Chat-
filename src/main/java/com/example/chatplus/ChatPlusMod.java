package com.example.chatplus;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChatPlusMod implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("chatplus");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Chat+ loaded!");
    }
}
