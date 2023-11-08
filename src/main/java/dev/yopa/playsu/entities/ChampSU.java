package dev.yopa.playsu.entities;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class ChampSU extends Mob {

    public ChampSU(EntityType<? extends Mob> type, Level level) {
        super(type, level);
        this.moveControl = new ChampSU.ChampSUMoveControl(this);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(1, new ChampSU.ChampSUFloatGoal(this));
        this.goalSelector.addGoal(3, new ChampSU.ChampSURandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new ChampSU.ChampSUKeepOnJumpingGoal(this));

    }

    public static AttributeSupplier.Builder getChampSUAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    protected int getJumpDelay() {
        return this.random.nextInt(20) + 10;
    }

    static class ChampSUFloatGoal extends Goal {
        private final ChampSU champsu;

        public ChampSUFloatGoal(ChampSU p_33655_) {
            this.champsu = p_33655_;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
            p_33655_.getNavigation().setCanFloat(true);
        }

        public boolean canUse() {
            return (this.champsu.isInWater() || this.champsu.isInLava()) && this.champsu.getMoveControl() instanceof ChampSU.ChampSUMoveControl;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            if (this.champsu.getRandom().nextFloat() < 0.8F) {
                this.champsu.getJumpControl().jump();
            }

            MoveControl movecontrol = this.champsu.getMoveControl();
            if (movecontrol instanceof ChampSU.ChampSUMoveControl champsu$champsumovecontrol) {
                champsu$champsumovecontrol.setWantedMovement(1.2D);
            }

        }
    }

    static class ChampSUKeepOnJumpingGoal extends Goal {
        private final ChampSU champsu;

        public ChampSUKeepOnJumpingGoal(ChampSU p_33660_) {
            this.champsu = p_33660_;
            this.setFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
        }

        public boolean canUse() {
            return !this.champsu.isPassenger();
        }

        public void tick() {
            MoveControl movecontrol = this.champsu.getMoveControl();
            if (movecontrol instanceof ChampSU.ChampSUMoveControl champsu$champsumovecontrol) {
                champsu$champsumovecontrol.setWantedMovement(1.0D);
            }

        }
    }

    static class ChampSUMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final ChampSU champsu;
        private boolean isAggressive;

        public ChampSUMoveControl(ChampSU p_33668_) {
            super(p_33668_);
            this.champsu = p_33668_;
            this.yRot = 180.0F * p_33668_.getYRot() / (float)Math.PI;
        }

        public void setDirection(float p_33673_, boolean p_33674_) {
            this.yRot = p_33673_;
            this.isAggressive = p_33674_;
        }

        public void setWantedMovement(double p_33671_) {
            this.speedModifier = p_33671_;
            this.operation = MoveControl.Operation.MOVE_TO;
        }

        public void tick() {
            this.mob.setYRot(this.rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();
            if (this.operation != MoveControl.Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
            } else {
                this.operation = MoveControl.Operation.WAIT;
                if (this.mob.onGround()) {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                    if (this.jumpDelay-- <= 0) {
                        this.jumpDelay = this.champsu.getJumpDelay();
                        if (this.isAggressive) {
                            this.jumpDelay /= 3;
                        }

                        this.champsu.getJumpControl().jump();
                        /*if (this.champsu.doPlayJumpSound()) {
                            this.champsu.playSound(this.champsu.getJumpSound(), this.champsu.getSoundVolume(), this.champsu.getSoundPitch());
                        }*/
                    } else {
                        this.champsu.xxa = 0.0F;
                        this.champsu.zza = 0.0F;
                        this.mob.setSpeed(0.0F);
                    }
                } else {
                    this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                }

            }
        }
    }

    static class ChampSURandomDirectionGoal extends Goal {
        private final ChampSU champsu;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public ChampSURandomDirectionGoal(ChampSU p_33679_) {
            this.champsu = p_33679_;
            this.setFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        public boolean canUse() {
            return this.champsu.getTarget() == null && (this.champsu.onGround() || this.champsu.isInWater() || this.champsu.isInLava() || this.champsu.hasEffect(MobEffects.LEVITATION)) && this.champsu.getMoveControl() instanceof ChampSU.ChampSUMoveControl;
        }

        public void tick() {
            if (--this.nextRandomizeTime <= 0) {
                this.nextRandomizeTime = this.adjustedTickDelay(40 + this.champsu.getRandom().nextInt(60));
                this.chosenDegrees = (float)this.champsu.getRandom().nextInt(360);
            }

            MoveControl movecontrol = this.champsu.getMoveControl();
            if (movecontrol instanceof ChampSU.ChampSUMoveControl champsu$champsumovecontrol) {
                champsu$champsumovecontrol.setDirection(this.chosenDegrees, false);
            }

        }
    }
}
