package net.thebestloyalist.monulite_mod.item.custom.itemtypes;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public abstract class CustWeaponItem extends SwordItem {
    private final String id;

    public CustWeaponItem(String id, Tier tier, Properties properties) {
        super(tier, properties);
        this.id = id;
    }
}
