package com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.actions;

import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SPDNet: 怪物受伤事件 - 发送给服务器
 */
@Getter
@Setter
@NoArgsConstructor
public class CMobDamage extends Data {
	private int pos;           // 怪物位置
	private int damage;        // 造成的伤害
	private String attacker;   // 攻击者名称
	
	public CMobDamage(int pos, int damage, String attacker) {
		this.pos = pos;
		this.damage = damage;
		this.attacker = attacker;
	}
}
