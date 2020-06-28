package defeatedcrow.minutes;

import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Optional;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.world.World;

public class EntityCounter extends Entity {

	protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager
			.<Optional<UUID>>createKey(EntityCounter.class, DataSerializers.OPTIONAL_UNIQUE_ID);

	public int livingCount = 0;

	public EntityCounter(World worldIn) {
		super(worldIn);
		this.setSize(1.0F, 1.0F);
		this.noClip = true;
	}

	public EntityCounter(World worldIn, double posX, double posY, double posZ) {
		this(worldIn);
		this.setPosition(posX, posY, posZ);
	}

	public EntityCounter(World worldIn, double posX, double posY, double posZ, @Nullable EntityPlayer player) {
		this(worldIn, posX, posY, posZ);
		if (player != null) {
			setOwnerId(player.getUniqueID());
		}
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		String s;
		if (compound.hasKey("OwnerUUID", 8)) {
			s = compound.getString("OwnerUUID");
		} else {
			String s1 = compound.getString("Owner");
			s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
		}

		if (!s.isEmpty()) {
			this.setOwnerId(UUID.fromString(s));
		}

		if (compound.hasKey("Count")) {
			livingCount = compound.getInteger("Count");
		}

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {

		if (this.getOwnerId() == null) {
			compound.setString("OwnerUUID", "");
		} else {
			compound.setString("OwnerUUID", this.getOwnerId().toString());
		}

		compound.setInteger("Count", livingCount);
	}

	@Nullable
	public UUID getOwnerId() {
		return (UUID) ((Optional) this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
	}

	public void setOwnerId(@Nullable UUID id) {
		this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(id));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!world.isRemote) {
			EntityPlayer owner = world.getPlayerEntityByUUID(getOwnerId());
			if (livingCount % 20 == 0) {
				if (owner != null && owner instanceof EntityPlayerMP) {
					if (livingCount > MinutesCore.timer) {
						PacketHandler.INSTANCE.sendTo(new PacketMessage(0, false), (EntityPlayerMP) owner);
						this.setDead();
					} else if (livingCount > 200 && this.getPosition().distanceSq(owner.getPosition()) < 4.0D) {
						PacketHandler.INSTANCE.sendTo(new PacketMessage(0, false), (EntityPlayerMP) owner);
						this.setDead();
					} else {
						PacketHandler.INSTANCE.sendTo(new PacketMessage(livingCount, true), (EntityPlayerMP) owner);
					}
				}
			}

			if (livingCount > MinutesCore.timer + 1200) {
				this.setDead();
			}

			livingCount++;
		}
	}

	@Override
	public boolean isGlowing() {
		return MinutesCore.useGlow;
	}

}
