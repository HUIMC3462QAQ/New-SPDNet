package com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.actions;

import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SPDNet: 怪物死亡事件 - 发送给服务器
 */
@Getter
@Setter
@NoArgsConstructor
public class CMobDie extends Data {
	private int pos;           // 怪物位置
	private String killer;    // 击杀者名称
	
	public CMobDie(int pos, String killer) {
		this.pos = pos;
		this.killer = killer;
	}
}
