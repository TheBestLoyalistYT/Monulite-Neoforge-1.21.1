package net.thebestloyalist.monulite_mod.entity.custom;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.thebestloyalist.monulite_mod.block.ModBlocks;

import java.util.List;

public class HiveEntity extends Zombie {

    public HiveEntity(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {

        Block MonuliteBlockInstance = ModBlocks.MONULITE_BLOCK.get();

        this.goalSelector.addGoal(0, new ZombieAttackGoal(this ,1.48D, true));

        this.goalSelector.addGoal(1, new RemoveBlockGoal(MonuliteBlockInstance, this, 1.5D, 20));

        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.50D));
    }

    public static AttributeSupplier.Builder createHiveEntityAttributes() {
        return HiveEntity.createLivingAttributes()
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

            double hordeCallRange = 95;
            List<Zombie> nearbyZombies = this.level().getEntitiesOfClass(
                    Zombie.class, this.getBoundingBox().inflate(hordeCallRange)
            );

            for (Zombie zombie : nearbyZombies) {

                if (zombie != this && zombie.getTarget() == null && hiveTarget.isAttackable()) {
                    zombie.setTarget(hiveTarget);
                }

            }
        }
    }
}