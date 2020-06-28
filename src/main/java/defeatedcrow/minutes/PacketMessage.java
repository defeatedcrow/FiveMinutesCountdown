package defeatedcrow.minutes;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketMessage implements IMessage {

	public int count;
	public boolean active;

	public PacketMessage() {}

	public PacketMessage(int c, boolean b) {
		count = c;
		active = b;
	}

	// read
	@Override
	public void fromBytes(ByteBuf buf) {
		count = buf.readInt();
		active = buf.readBoolean();
	}

	// write
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(count);
		buf.writeBoolean(active);
	}

}
