package com.FenrisFox86.fenris_workshop.client.screens;

import com.FenrisFox86.fenris_workshop.common.containers.AbstractConverterContainer;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Overwrite;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractConverterScreen<T extends AbstractConverterContainer> extends ContainerScreen<T> {
    private boolean widthTooNarrow;
    public final ResourceLocation texture;
    private final ITextComponent name;
    private final PlayerInventory playerInv;

    public AbstractConverterScreen(T p_i51104_1_, PlayerInventory playerInv, ITextComponent name, ResourceLocation texture) {
        super(p_i51104_1_, playerInv, name);
        this.texture = texture;
        this.name = name;
        this.playerInv = playerInv;
    }

    public void init() {
        super.init();
        this.widthTooNarrow = this.width < 379;
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    public void tick() {
        super.tick();
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Overwrite
    protected void renderLabels(MatrixStack matrixStack, int p_230451_2_, int p_230451_3_) {
        this.font.draw(matrixStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(matrixStack, this.inventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }

    protected void renderBg(MatrixStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(this.texture);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
        if (this.menu.isLit()) {
            int k = this.menu.getLitProgress();
            this.blit(matrixStack, i + 26, j + 38 + 12 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.menu.getBurnProgress();
        this.blit(matrixStack, i + 78, j + 36, 176, 14, l + 1, 16);
    }

    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        return this.widthTooNarrow || super.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_);
    }

    protected void slotClicked(Slot p_184098_1_, int p_184098_2_, int p_184098_3_, ClickType p_184098_4_) {
        super.slotClicked(p_184098_1_, p_184098_2_, p_184098_3_, p_184098_4_);
    }

    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        return super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_);
    }

    protected boolean hasClickedOutside(double p_195361_1_, double p_195361_3_, int p_195361_5_, int p_195361_6_, int p_195361_7_) {
        return p_195361_1_ < (double) p_195361_5_ || p_195361_3_ < (double) p_195361_6_ || p_195361_1_ >= (double) (p_195361_5_ + this.imageWidth) || p_195361_3_ >= (double) (p_195361_6_ + this.imageHeight);
    }

    public boolean charTyped(char p_231042_1_, int p_231042_2_) {
        return super.charTyped(p_231042_1_, p_231042_2_);
    }

    public void recipesUpdated() {
    }

    public void removed() {
        super.removed();
    }
}
