package com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.events;

import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SPDNet: 怪物死亡广播事件
 */
@Getter
@Setter
@NoArgsConstructor
public class SMobDie extends Data {
	private int mobPos;      // 怪物位置

	public SMobDie(int mobPos) {
		this.mobPos = mobPos;
	}
}
