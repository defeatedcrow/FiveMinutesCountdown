package defeatedcrow.minutes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityCounter extends Render<EntityCounter> {

	private final ModelHumanoidHead humanoidHead = new ModelHumanoidHead();
	private final RenderItem itemRenderer;

	public RenderEntityCounter(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.5F;
		this.shadowOpaque = 0.5F;
		itemRenderer = Minecraft.getMinecraft().getRenderItem();
	}

	@Override
	public void doRender(EntityCounter entity, double x, double y, double z, float yaw, float partialTicks) {
		if (MinutesCore.useGlow) {

			ItemStack item = entity.getItem();

			if (!item.isEmpty()) {

				GlStateManager.pushMatrix();
				GlStateManager.scale(0.5F, 0.5F, 0.5F);
				GlStateManager.translate((float) x, (float) y + 1.5F, (float) z);
				GlStateManager.pushAttrib();
				GlStateManager.disableLighting();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				this.itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED);
				GlStateManager.enableLighting();
				GlStateManager.popAttrib();
				GlStateManager.popMatrix();
			}
		}

	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCounter entity) {
		return null;
	}

}
