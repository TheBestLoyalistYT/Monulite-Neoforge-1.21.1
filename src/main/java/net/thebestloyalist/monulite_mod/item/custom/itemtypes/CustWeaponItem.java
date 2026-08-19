package net.thebestloyalist.monulite_mod.item.custom.itemtypes;

import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public abstract class CustWeaponItem extends SwordItem {

    public CustWeaponItem(Tier tier, Properties properties) {
        super(tier, properties);
    }
}
