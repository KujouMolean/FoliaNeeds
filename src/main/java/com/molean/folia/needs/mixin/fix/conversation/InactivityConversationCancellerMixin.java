package com.molean.folia.needs.mixin.fix.conversation;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.conversations.InactivityConversationCanceller;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InactivityConversationCanceller.class)
public abstract class InactivityConversationCancellerMixin {
    private ScheduledTask scheduledTask;

    @Shadow protected Plugin plugin;

    @Shadow protected Conversation conversation;

    @Shadow protected abstract void startTimer();

    @Shadow protected abstract void cancelling(@NotNull Conversation conversation);

    @Shadow protected int timeoutSeconds;

    @Inject(method = "startTimer", at = @At("HEAD"), cancellable = true)
    public void onStartTimer(CallbackInfo ci) {
        InactivityConversationCanceller conversationCanceller =(InactivityConversationCanceller)(Object) this;
        scheduledTask = Bukkit.getGlobalRegionScheduler().runDelayed(plugin, (scheduledTask) -> {
            if (conversation.getState() == Conversation.ConversationState.UNSTARTED) {
                startTimer();
            } else if (conversation.getState() == Conversation.ConversationState.STARTED) {
                cancelling(conversation);
                conversation.abandon(new ConversationAbandonedEvent(conversation, conversationCanceller));
            }
        }, timeoutSeconds * 20L);
        ci.cancel();

    }


    @Inject(method = "stopTimer", at = @At("HEAD"), cancellable = true)
    public void onStopTimer(CallbackInfo ci) {
        if (scheduledTask != null) {
            scheduledTask.cancel();
            scheduledTask = null;
        }
    }
}
