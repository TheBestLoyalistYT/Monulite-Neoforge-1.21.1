package net.thebestloyalist.monulite_mod.entity.custom;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;

public class HiveEntity extends Zombie {

    public HiveEntity(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ZombieAttackGoal(this ,1.48D, true));

        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createHiveEntityAttributes() {
        return Zombie.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30d)
                .add(Attributes.MOVEMENT_SPEED, 1.25D)
                .add(Attributes.ATTACK_DAMAGE, 2.5D)
                .add(Attributes.FOLLOW_RANGE, 65D);
    }


    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide() && this.getTarget() != null) {
            var hiveTarget = this.getTarget();

            double hordeCallRange = 105;
            List<Zombie> nearbyZombies = this.level().getEntitiesOfClass(
                    Zombie.class, this.getBoundingBox().inflate(hordeCallRange)
            );

            for (Zombie zombie : nearbyZombies) {
                if (zombie != this && zombie.getTarget() == null) {
                    zombie.setTarget(hiveTarget);
                }
            }
        }
    }
}