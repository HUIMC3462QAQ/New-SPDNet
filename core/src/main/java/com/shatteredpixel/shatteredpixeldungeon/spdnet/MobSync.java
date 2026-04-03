package com.shatteredpixel.shatteredpixeldungeon.spdnet;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.Net;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.Sender;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.actions.CMobDamage;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.actions.CMobDie;

/**
 * SPDNet: 怪物同步辅助类
 */
public class MobSync {
    
    // SPDNet: 怪物受伤时调用
    public static void onMobDamaged(Mob mob, int damage) {
        if (Net.isConnected()) {
            String attacker = "hero";
            if (Dungeon.hero != null) {
                attacker = Net.name;
            }
            Sender.sendMobDamage(new CMobDamage(mob.pos, damage, attacker));
        }
    }
    
    // SPDNet: 怪物死亡时调用
    public static void onMobDie(Mob mob) {
        if (Net.isConnected()) {
            Sender.sendMobDie(new CMobDie(mob.pos));
        }
    }
}
