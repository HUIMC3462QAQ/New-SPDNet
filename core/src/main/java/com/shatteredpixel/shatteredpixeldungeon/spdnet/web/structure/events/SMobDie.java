package com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.events;

import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.Data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SPDNet: 怪物死亡事件 - 从服务器接收
 */
@Getter
@Setter
@NoArgsConstructor
public class SMobDie extends Data {
	private int pos;
	private String killer;
	
	public SMobDie(int pos, String killer) {
		this.pos = pos;
		this.killer = killer;
	}
}
