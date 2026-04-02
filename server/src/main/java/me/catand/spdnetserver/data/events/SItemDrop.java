package me.catand.spdnetserver.data.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catand.spdnetserver.data.Data;

/**
 * SPDNet: 物品掉落事件 - 同步房间内所有玩家
 */
@Getter
@Setter
@NoArgsConstructor
public class SItemDrop extends Data {
	private String itemId;     // 物品唯一ID
	private String item;       // 物品序列化数据
	private int pos;           // 掉落位置
	private String dropper;    // 掉落者（怪物/玩家）
	
	public SItemDrop(String itemId, String item, int pos, String dropper) {
		this.itemId = itemId;
		this.item = item;
		this.pos = pos;
		this.dropper = dropper;
	}
}
