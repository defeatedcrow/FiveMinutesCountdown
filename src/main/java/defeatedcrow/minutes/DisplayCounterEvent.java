package defeatedcrow.minutes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DisplayCounterEvent {

	public static final DisplayCounterEvent INSTANCE = new DisplayCounterEvent();

	public static int count = MinutesCore.timer;
	public static boolean active = false;

	public static int check = 0;

	@SubscribeEvent
	public void doRender(RenderGameOverlayEvent.Post event) {
		if (event.getType() != null && event.getType() == ElementType.ALL) {
			EntityPlayer player = MinutesCore.proxy.getPlayer();
			World world = MinutesCore.proxy.getClientWorld();
			GuiScreen gui = Minecraft.getMinecraft().currentScreen;
			if (player != null && world != null && gui == null) {
				if (active) {
					int time = count / 20;
					int min = time / 60;
					int sec = time % 60;
					String disp = MinutesCore.timerName + (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec :
							sec);

					FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

					int offsetX = MinutesCore.offsetX;
					int offsetY = MinutesCore.offsetY;
					int x = offsetX;
					int y = offsetY;

					if (time <= 10) {
						fr.drawString(disp, x, y, 0xFF00FF, true);
					} else if (time <= 30) {
						fr.drawString(disp, x, y, 0xFF80FF, true);
					} else if (min == 0) {
						fr.drawString(disp, x, y, 0xFFCCFF, true);
					} else {
						fr.drawString(disp, x, y, 0xFFFFFF, true);
					}

					if (count <= 0) {
						check++;
					}

					if (check > 5) {
						active = false;
					}

				}
			}
		}
	}

}
