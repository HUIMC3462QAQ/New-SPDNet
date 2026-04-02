package me.catand.spdnetserver.data.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catand.spdnetserver.data.Data;

/**
 * SPDNet: 怪物死亡事件 - 同步给房间内所有玩家
 */
@Getter
@Setter
@NoArgsConstructor
public class SMobDie extends Data {
	private String mobId;      // 怪物唯一ID
	private String killer;     // 击杀者名称
	private int pos;           // 怪物位置
	private int mobType;       // 怪物类型
	
	public SMobDie(String mobId, String killer, int pos, int mobType) {
		this.mobId = mobId;
		this.killer = killer;
		this.pos = pos;
		this.mobType = mobType;
	}
}
