package dev.yopa.playsu.entities;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;

public class ChampSU extends Slime {

    public ChampSU(EntityType<? extends Slime> type, Level level) {
        super(type, level);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
    }

    public static AttributeSupplier.Builder getChampSUAttributes() {
        return Slime.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected ParticleOptions getParticleType() {
        return ParticleTypes.MYCELIUM;
    }
}
