package com.example.chatplus.mixin;

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.ChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Mixin(ChatComponent.class)
public class ChatHudMixin {

    private String chatplus_lastMessage = "";
    private int chatplus_repeatCount = 1;

    @ModifyVariable(
        method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V",
        at = @At("HEAD"),
        argsOnly = true,
        index = 1
    )
    private Component chatplus_modifyMessage(Component original) {
        String rawMessage = original.getString();

        // Build timestamp prefix
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        MutableComponent timestamp = Component.literal("[" + time + "] ")
            .withStyle(ChatFormatting.DARK_GRAY);

        if (rawMessage.equals(chatplus_lastMessage)) {
            chatplus_repeatCount++;
            MutableComponent counter = Component.literal(" x" + chatplus_repeatCount)
                .withStyle(style -> style.withColor(ChatFormatting.YELLOW).withBold(true));
            return timestamp.append(original.copy()).append(counter);
        } else {
            chatplus_lastMessage = rawMessage;
            chatplus_repeatCount = 1;
            return timestamp.append(original.copy());
        }
    }
}
