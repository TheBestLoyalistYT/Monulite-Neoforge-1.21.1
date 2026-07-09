package net.thebestloyalist.monulite_mod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = "monulite_mod", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class KeyBinds {

    public static final String MONULITE_CATEGORY = "key.categories.monulite_mod";

    public static final KeyMapping MON_KEY_1 = new KeyMapping(
            "key.monulite_mod.mon_key_1",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_U,
            MONULITE_CATEGORY);

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent e) {
        e.register(MON_KEY_1);
    }
}
