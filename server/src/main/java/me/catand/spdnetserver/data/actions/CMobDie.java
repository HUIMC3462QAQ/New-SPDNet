package me.catand.spdnetserver.data.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catand.spdnetserver.data.Data;

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
