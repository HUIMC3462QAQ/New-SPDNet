package com.shatteredpixel.shatteredpixeldungeon.spdnet;

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.Net;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.Sender;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.actions.CMobDamage;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.actions.CMobDie;

/**
 * SPDNet: 怪物同步辅助类
 */
public class MobSync {
	
	/**
	 * 当怪物受伤时调用此方法
	 * @param mob 受伤的怪物
	 * @param damage 伤害值
	 * @param attacker 攻击者名称
	 */
	public static void onMobDamaged(Mob mob, int damage, String attacker) {
		// 只在联机模式下同步
		if (!Net.isConnected() || NetInProgress.roomId == null) {
			return;
		}
		
		// 发送给服务器
		Sender.sendMobDamage(new CMobDamage(mob.pos, damage, attacker));
	}
	
	/**
	 * 当怪物死亡时调用此方法
	 * @param mob 死亡的怪物
	 * @param killer 击杀者名称
	 */
	public static void onMobDied(Mob mob, String killer) {
		// 只在联机模式下同步
		if (!Net.isConnected() || NetInProgress.roomId == null) {
			return;
		}
		
		// 发送给服务器
		Sender.sendMobDie(new CMobDie(mob.pos, killer));
	}
}
