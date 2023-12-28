package dev.yopa.playsu.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackLinkedSet;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ItemStackMap;

import java.util.Random;

import static dev.yopa.playsu.PlaySUMod.DICE_ENTITY;

public class DiceEntity extends LivingEntity {
    static final EntityDataAccessor<Integer> SIDE = SynchedEntityData.defineId(DiceEntity.class, EntityDataSerializers.INT);

    public DiceEntity(EntityType<DiceEntity> entityType, Level level) {
        super(entityType, level);
    }

    public DiceEntity(Level level, Player player) {
        this(DICE_ENTITY.get(), level);
        this.setPos(player.getEyePosition());
        this.addDeltaMovement(player.getLookAngle());
    }

    public EntityDataAccessor<Integer> getSideData() {
        return SIDE;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SIDE, (int)new Random().nextInt(6 + 1) + 1);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag ct) {
        super.addAdditionalSaveData(ct);
        ct.putInt("Side", this.entityData.get(SIDE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag ct) {
        super.readAdditionalSaveData(ct);
        this.entityData.set(SIDE, ct.getInt("Side"));
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return ItemStackLinkedSet.createTypeAndTagSet();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack itemStack) {}

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, DamageSource p_147189_) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float k) {
        if (!this.level().isClientSide && !this.isRemoved()) {
            this.kill();
            return true;
        }
        return false;
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }
}
