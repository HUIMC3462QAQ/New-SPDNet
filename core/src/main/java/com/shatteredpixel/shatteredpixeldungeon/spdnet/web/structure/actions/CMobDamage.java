package com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.actions;

import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SPDNet: 怪物受伤同步事件
 */
@Getter
@Setter
@NoArgsConstructor
public class CMobDamage extends Data {
	private int mobPos;      // 怪物位置
	private int damage;      // 伤害值
	private String attacker; // 攻击者名称

	public CMobDamage(int mobPos, int damage, String attacker) {
		this.mobPos = mobPos;
		this.damage = damage;
		this.attacker = attacker;
	}
}
