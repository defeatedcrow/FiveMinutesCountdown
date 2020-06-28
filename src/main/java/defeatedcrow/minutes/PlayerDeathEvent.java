package defeatedcrow.minutes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerDeathEvent {

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event) {
		EntityLivingBase living = event.getEntityLiving();
		DamageSource source = event.getSource();
		if (living == null)
			return;
		if (event.isCanceled())
			return;

		if (living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			if (!living.world.isRemote && living.getPosition().getY() > 0) {
				EntityCounter entity = new EntityCounter(player.world, player.posX, player.posY + 0.5D, player.posZ,
						player);
				entity.setNoGravity(true);
				player.world.spawnEntity(entity);
			}
		}
	}

}
