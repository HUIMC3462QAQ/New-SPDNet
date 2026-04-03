package me.catand.spdnetserver.data.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catand.spdnetserver.data.Data;

/**
 * SPDNet: 怪物受伤广播事件
 */
@Getter
@Setter
@NoArgsConstructor
public class SMobDamage extends Data {
	private int mobPos;      // 怪物位置
	private int damage;      // 伤害值
	private String attacker; // 攻击者名称

	public SMobDamage(int mobPos, int damage, String attacker) {
		this.mobPos = mobPos;
		this.damage = damage;
		this.attacker = attacker;
	}
}
