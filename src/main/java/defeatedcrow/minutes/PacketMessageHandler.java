package defeatedcrow.minutes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketMessageHandler implements IMessageHandler<PacketMessage, IMessage> {

	@Override
	public IMessage onMessage(PacketMessage message, MessageContext ctx) {
		EntityPlayer player = MinutesCore.proxy.getPlayer();
		if (player != null) {
			int c = MinutesCore.timer - message.count;
			boolean b = message.active;
			if (DisplayCounterEvent.INSTANCE.count > c) {
				DisplayCounterEvent.INSTANCE.count = c;
			}
			if (!b) {
				DisplayCounterEvent.INSTANCE.count = MinutesCore.timer;
			}
			DisplayCounterEvent.INSTANCE.active = b;
		}
		return null;
	}

}
