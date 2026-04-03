package me.catand.spdnetserver.data.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catand.spdnetserver.data.Data;

/**
 * SPDNet: 物品掉落同步事件
 */
@Getter
@Setter
@NoArgsConstructor
public class CItemDrop extends Data {
	private int itemPos;     // 物品位置
	private String item;     // 物品序列化数据

	public CItemDrop(int itemPos, String item) {
		this.itemPos = itemPos;
		this.item = item;
	}
}
