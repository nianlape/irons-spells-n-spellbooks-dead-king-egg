package com.example.testmod.entity.mobs.goals;

import com.example.testmod.TestMod;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class DebugTargetClosestEntityGoal extends TargetGoal {
    @Nullable
    protected LivingEntity target;

    public DebugTargetClosestEntityGoal(Mob pMob) {
        super(pMob, false, false);
        this.setFlags(EnumSet.of(Goal.Flag.TARGET));
    }

    public boolean canUse() {
        this.findTarget();
        return target != null;
    }

    protected void findTarget() {
        LivingEntity tmp = target;
        target = mob.level.getNearestPlayer(this.mob, 40);

        if (tmp != target) {
            TestMod.LOGGER.debug("DebugTargetClosestEntityGoal: Target Changed old:{} new:{}", tmp, target);
        }
    }

    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }

    public void setTarget(@Nullable LivingEntity pTarget) {
        this.target = pTarget;
    }
}