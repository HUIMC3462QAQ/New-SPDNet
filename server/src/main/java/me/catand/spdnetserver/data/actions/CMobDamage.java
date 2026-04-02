package me.catand.spdnetserver.data.actions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catand.spdnetserver.data.Data;

/**
 * SPDNet: 怪物受伤事件 - 客户端发送给服务器
 */
@Getter
@Setter
@NoArgsConstructor
public class CMobDamage extends Data {
	private int pos;
	private int damage;
	private String attacker;
	
	public CMobDamage(int pos, int damage, String attacker) {
		this.pos = pos;
		this.damage = damage;
		this.attacker = attacker;
	}
}
