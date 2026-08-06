package net.thebestloyalist.monulite_mod.event.item_event_logic;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ZWorldoLog {

    public static final Map<UUID, WorldoData> wldoData = new HashMap<>();

    public static class WorldoData {
        public Player pauser;
        public int tickTime;
        public int ACTIVE;

        public WorldoData(Player pauser, int tickTime, int ACTIVE) {
            this.pauser = pauser;
            this.tickTime = tickTime;
            this.ACTIVE = ACTIVE;
        }
    }

    public static void active(Player player) {
        UUID id = player.getUUID();

        if (!wldoData.containsKey(id)) {
            wldoData.put(id, new WorldoData(player, 0, 1));
        }
    }
}
