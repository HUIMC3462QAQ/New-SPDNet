package com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.events;

import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.Data;
import com.shatteredpixel.shatteredpixeldungeon.spdnet.web.structure.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SEnterDungeon extends Data {
	private String name;
	private Status status;
	// SPDNet: 前缀系统 - 玩家前缀
	private String prefix;
	// SPDNet: 房间ID，用于多人同怪
	private String roomId;

	public SEnterDungeon(String name, Status status, String prefix, String roomId) {
		this.name = name;
		this.status = status;
		this.prefix = prefix;
		this.roomId = roomId;
	}
}
