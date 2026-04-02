package com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.events;

import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SPDNet: 怪物受伤事件 - 从服务器接收
 */
@Getter
@Setter
@NoArgsConstructor
public class SMobDamage extends Data {
	private int pos;
	private int damage;
	private String attacker;
	
	public SMobDamage(int pos, int damage, String attacker) {
		this.pos = pos;
		this.damage = damage;
		this.attacker = attacker;
	}
}
