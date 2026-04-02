package me.catand.spdnetserver.data.actions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catand.spdnetserver.data.Data;

/**
 * SPDNet: 怪物死亡事件 - 客户端发送给服务器
 */
@Getter
@Setter
@NoArgsConstructor
public class CMobDie extends Data {
	private int pos;
	private String killer;
	
	public CMobDie(int pos, String killer) {
		this.pos = pos;
		this.killer = killer;
	}
}
