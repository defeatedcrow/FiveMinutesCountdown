package defeatedcrow.minutes;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MinutesCore.MOD_ID, name = MinutesCore.MOD_NAME,
		version = MinutesCore.MOD_MEJOR + "." + MinutesCore.MOD_MINOR + "." + MinutesCore.MOD_BUILD, useMetadata = true)
public class MinutesCore {
	public static final String MOD_ID = "dcs_minutes";
	public static final String MOD_NAME = "5MinutesCountdown";
	public static final int MOD_MEJOR = 1;
	public static final int MOD_MINOR = 0;
	public static final int MOD_BUILD = 0;

	@SidedProxy(clientSide = "defeatedcrow.minutes.ClientProxy", serverSide = "defeatedcrow.minutes.CommonProxy")
	public static CommonProxy proxy;

	@Instance("dcs_minutes")
	public static MinutesCore instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		File cfgFile = new File(event.getModConfigurationDirectory(), "5minutes_countdown.cfg");
		loadConfig(new Configuration(cfgFile));

		proxy.init();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.loadEvent();
		PacketHandler.init();
	}

	public static boolean useGlow = true;
	public static int offsetX = 5;
	public static int offsetY = 2;
	public static double size = 2.0D;
	public static int timer = 6000;

	public void loadConfig(Configuration cfg) {

		try {
			cfg.load();

			Property t = cfg.get("setting", "TimerLength", timer);
			Property f = cfg.get("setting", "FontSize", size);
			Property x = cfg.get("setting", "DisplayOffsetX", offsetX);
			Property y = cfg.get("setting", "DisplayOffsetY", offsetY);
			Property g = cfg
					.get("setting", "EnableGlowing", useGlow, "Enable glowing effect on the player despawn point.");

			size = f.getDouble();
			timer = t.getInt();
			offsetX = x.getInt();
			offsetY = y.getInt();
			useGlow = g.getBoolean();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cfg.save();
		}
	}

}
