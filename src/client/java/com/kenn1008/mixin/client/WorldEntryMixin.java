package com.kenn1008.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.nio.file.Path;

@Mixin(WorldSelectionList.WorldListEntry.class)
public abstract class WorldEntryMixin extends ObjectSelectionList.Entry {

    @Shadow @Final LevelSummary summary;
    @Shadow @Final Minecraft minecraft;
    @Shadow protected abstract int getTextX();

    @Unique
    private String realPlaytime = null;

    @Inject(method = "renderContent", at = @At("TAIL"))
    private void renderPlaytime(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean isHovered, float partialTick, CallbackInfo ci) {
        if (this.realPlaytime == null) {
            this.realPlaytime = fetchPlaytimeFromDat();
        }

        int nameWidth = this.minecraft.font.width(this.summary.getLevelName());
        int textX = this.getTextX() + nameWidth + 5;
        int textY = this.getContentY() + 2;

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(textX, textY);
        guiGraphics.pose().scale(0.8f, 0.8f);
        guiGraphics.drawString(this.minecraft.font, this.realPlaytime, 0, 0, 0xAAFFAA00, false);
        guiGraphics.pose().popMatrix();
    }

    @Unique
    private String fetchPlaytimeFromDat() {
        try {
            Path levelDatPath = this.minecraft.gameDirectory.toPath()
                    .resolve("saves").resolve(this.summary.getLevelId()).resolve("level.dat");
            CompoundTag nbt = NbtIo.readCompressed(levelDatPath, NbtAccounter.unlimitedHeap());
            long totalTicks = nbt.getCompound("Data").get().getLong("Time").orElse(0L);
            return String.format(" %dh %dm", totalTicks / 72000, (totalTicks % 72000) / 1200);
        } catch (Exception e) {
            return "";
        }
    }
}