package defeatedcrow.minutes;

import java.util.UUID;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public class EntityCounter extends Entity {

	private static final DataParameter<ItemStack> ITEM = EntityDataManager
			.<ItemStack>createKey(EntityCounter.class, DataSerializers.ITEM_STACK);

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
			this.setCustomNameTag(player.getName());
			this.rotationYaw = player.rotationYaw;
		}
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(ITEM, ItemStack.EMPTY);
	}

	public ItemStack getItem() {
		return this.getDataManager().get(ITEM);
	}

	public void setItem(ItemStack stack) {
		this.getDataManager().set(ITEM, stack);
		this.getDataManager().setDirty(ITEM);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {

		if (compound.hasKey("Count")) {
			livingCount = compound.getInteger("Count");
		}

		NBTTagCompound tag = compound.getCompoundTag("Item");
		if (tag != null && !tag.hasNoTags()) {
			this.setItem(new ItemStack(tag));
		}

	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("Count", livingCount);

		if (!this.getItem().isEmpty()) {
			compound.setTag("Item", this.getItem().writeToNBT(new NBTTagCompound()));
		}
	}

	boolean tryP = false;

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!hasCustomName()) {
			this.setDead();
		} else if (this.getItem().isEmpty() && !tryP) {
			String name = this.getCustomNameTag();
			ItemStack skull = new ItemStack(Items.SKULL, 1, 3);
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("SkullOwner", name);
			((ItemSkull) Items.SKULL).updateItemStackNBT(tag);
			skull.setTagCompound(tag);
			this.setItem(skull.copy());

			tryP = true;
		}

		if (!world.isRemote) {

			EntityPlayer owner = world.getPlayerEntityByName(getCustomNameTag());
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

			if (livingCount > MinutesCore.timer + 600) {
				this.setDead();
			}

			livingCount++;

		}

		if (livingCount == 200) {
			LogManager.getLogger().info("UUID: " + getOwnerId());
			LogManager.getLogger().info("name: " + getCustomNameTag());
			LogManager.getLogger().info("hasItem: " + !getItem().isEmpty());
		}

	}

	public UUID getOwnerId() {
		EntityPlayer owner = world.getPlayerEntityByName(getCustomNameTag());
		return owner == null ? null : owner.getUniqueID();
	}

	@Override
	public boolean isGlowing() {
		return MinutesCore.useGlow;
	}
}
