package me.catand.spdnetserver.data.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catand.spdnetserver.data.Data;

/**
 * SPDNet: 物品拾取事件 - 通知其他玩家物品已被拾取
 */
@Getter
@Setter
@NoArgsConstructor
public class SItemPickup extends Data {
	private String itemId;     // 物品唯一ID
	private String picker;      // 拾取者名称
	
	public SItemPickup(String itemId, String picker) {
		this.itemId = itemId;
		this.picker = picker;
	}
}
