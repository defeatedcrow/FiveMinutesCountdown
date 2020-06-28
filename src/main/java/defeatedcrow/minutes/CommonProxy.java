package defeatedcrow.minutes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy {

	public EntityPlayer getPlayer() {
		return null;
	}

	public World getClientWorld() {
		return null;
	}

	public void init() {
		EntityRegistry.registerModEntity(new ResourceLocation("dcs_minutes",
				"dcs.minutes_counter"), EntityCounter.class, "dcs.minutes_counter", 0, MinutesCore.instance, 128, 5, false);
	}

	public void loadEvent() {
		MinecraftForge.EVENT_BUS.register(new PlayerDeathEvent());
	}

}
