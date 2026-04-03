package me.catand.spdnetserver.data.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catand.spdnetserver.data.Data;

/**
 * SPDNet: 物品拾取同步事件
 */
@Getter
@Setter
@NoArgsConstructor
public class CItemPickup extends Data {
	private int itemPos;     // 物品位置
	private String picker;   // 拾取者名称

	public CItemPickup(int itemPos, String picker) {
		this.itemPos = itemPos;
		this.picker = picker;
	}
}
