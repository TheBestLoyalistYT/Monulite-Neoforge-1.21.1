package net.thebestloyalist.monulite_mod.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.thebestloyalist.monulite_mod.block.ModBlocks;
import net.thebestloyalist.monulite_mod.client.KeyBinds;
import net.thebestloyalist.monulite_mod.item.ModItems;
import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlingItem extends Item {
    private static final Logger log = LoggerFactory.getLogger(FlingItem.class);

    private static final double FLY_STRENGTH = 0.5;
    private double deY = 0.0;

    public FlingItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

        if (entity instanceof Player player && !level.isClientSide()) {
            fling(player, level);
        }
    }

    public void fling(Player player, Level level) {
        if (player.isHolding(ModItems.GRAPPLE.get())) {
            if (KeyBinds.MON_KEY_1.isDown()) {
                player.displayClientMessage(Component.literal(
                        ChatFormatting.RED + "KEY PRESSED"), false);
                Vec3 lookVec = player.getLookAngle();
                Vec3 startPos = player.getEyePosition();
                Vec3 endPos = startPos.add(lookVec.scale(50));

                HitResult hitResult = level.clip(new ClipContext(
                        startPos, endPos,
                        ClipContext.Block.COLLIDER,
                        ClipContext.Fluid.NONE,
                        player));

                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    player.displayClientMessage(Component.literal(
                            ChatFormatting.RED + "getting hit"), false);
                    BlockHitResult blockHit = (BlockHitResult) hitResult;
                    BlockPos hitPos = blockHit.getBlockPos();


                    if (level.getBlockState(hitPos).is(ModBlocks.MONULITE_BLOCK)) {
                        player.displayClientMessage(Component.literal(
                                ChatFormatting.RED + "monulite block checked yes!"), false);
                        Vec3 hitPosy = hitResult.getLocation();

                        Vec3 direction = hitResult.getLocation().subtract(player.position()).normalize();
                        double distance = player.position().distanceTo(hitPosy);
                        if (distance <= 6) {
                            Vec3 flingVec = new Vec3(
                                    direction.x,
                                    direction.y * 0.13 - 0.1,
                                    direction.z);
                            player.setDeltaMovement(flingVec);
                            player.hurtMarked = true;
                        }else if (distance <= 8) {
                            Vec3 flingVec = new Vec3(
                                    direction.x,
                                    direction.y * 0.15,
                                    direction.z);
                            player.setDeltaMovement(flingVec);
                            player.hurtMarked = true;
                        }else if (distance <= 10) {
                            Vec3 flingVec = new Vec3(
                                    direction.x,
                                    direction.y *0.20,
                                    direction.z);
                            player.setDeltaMovement(flingVec);
                            player.hurtMarked = true;
                        }else if (distance <= 14) {
                            Vec3 flingVec = new Vec3(
                                    direction.x,
                                    direction.y *0.35,
                                    direction.z);
                            player.setDeltaMovement(flingVec);
                            player.hurtMarked = true;
                        }else if (distance <= 18) {
                            Vec3 flingVec = new Vec3(
                                    direction.x,
                                    direction.y * 0.50,
                                    direction.z);
                            player.setDeltaMovement(flingVec);
                            player.hurtMarked = true;
                        }else if (distance <= 22) {
                            Vec3 flingVec = new Vec3(
                                    direction.x,
                                    direction.y *0.75,
                                    direction.z);
                            player.setDeltaMovement(flingVec);
                            player.hurtMarked = true;
                        }else {
                            Vec3 flingVec = new Vec3(
                                    direction.x,
                                    direction.y,
                                    direction.z);

                            player.setDeltaMovement(flingVec);
                            player.hurtMarked = true;
                        }
                    }
                }
            }
        }
    }
}
