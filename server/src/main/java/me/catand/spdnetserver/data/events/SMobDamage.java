package me.catand.spdnetserver.data.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catand.spdnetserver.data.Data;

/**
 * SPDNet: 怪物受伤事件 - 同步怪物血量变化
 */
@Getter
@Setter
@NoArgsConstructor
public class SMobDamage extends Data {
	private String mobId;      // 怪物唯一ID
	private String attacker;   // 攻击者名称
	private int damage;        // 造成的伤害
	private int currentHP;     // 当前血量
	private int pos;           // 怪物位置
	
	public SMobDamage(String mobId, String attacker, int damage, int currentHP, int pos) {
		this.mobId = mobId;
		this.attacker = attacker;
		this.damage = damage;
		this.currentHP = currentHP;
		this.pos = pos;
	}
}
