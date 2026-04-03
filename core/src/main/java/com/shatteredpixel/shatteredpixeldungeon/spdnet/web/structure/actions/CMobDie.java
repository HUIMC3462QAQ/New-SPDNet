package com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.actions;

import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * SPDNet: 怪物死亡同步事件
 */
@Getter
@Setter
@NoArgsConstructor
public class CMobDie extends Data {
	private int mobPos;      // 怪物位置

	public CMobDie(int mobPos) {
		this.mobPos = mobPos;
	}
}
