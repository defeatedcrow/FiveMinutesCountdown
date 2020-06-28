package defeatedcrow.minutes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().player;
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().world;
	}

	@Override
	public void init() {
		super.init();
		RenderingRegistry.registerEntityRenderingHandler(EntityCounter.class, new IRenderFactory() {
			@Override
			public Render createRenderFor(RenderManager manager) {
				try {
					return new RenderEntityCounter(manager);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	@Override
	public void loadEvent() {
		super.loadEvent();
		MinecraftForge.EVENT_BUS.register(DisplayCounterEvent.INSTANCE);
	}
}
