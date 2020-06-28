package defeatedcrow.minutes;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntityCounter extends Render<EntityCounter> {

	private final ModelHumanoidHead humanoidHead = new ModelHumanoidHead();

	public RenderEntityCounter(RenderManager renderManager) {
		super(renderManager);
		this.shadowSize = 0.5F;
		this.shadowOpaque = 0.5F;
	}

	@Override
	public void doRender(EntityCounter entity, double x, double y, double z, float yaw, float partialTicks) {
		if (MinutesCore.useGlow) {
			ResourceLocation resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();
			ModelBase modelbase = this.humanoidHead;
			this.bindTexture(resourcelocation);
			GlStateManager.pushMatrix();
			GlStateManager.translate((float) x, (float) y, (float) z);
			GlStateManager.enableBlend();
			GlStateManager
					.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);

			GlStateManager.scale(-1.0F, -1.0F, 1.0F);

			GlStateManager.enableAlpha();
			GlStateManager.enableBlendProfile(GlStateManager.Profile.PLAYER_SKIN);

			modelbase.render(entity, 0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
		}

	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCounter entity) {
		return null;
	}

}
