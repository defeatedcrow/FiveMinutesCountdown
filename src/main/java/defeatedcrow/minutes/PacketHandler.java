package defeatedcrow.minutes;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("dcs_minutes");

	public static void init() {
		INSTANCE.registerMessage(PacketMessageHandler.class, PacketMessage.class, 0, Side.CLIENT);
	}

}
