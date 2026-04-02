package me.catand.spdnetserver.data.actions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.catand.spdnetserver.data.Data;
import me.catand.spdnetserver.data.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CEnterDungeon extends Data {
	private Status status;
	private Integer dailyGroupIndex;
	private Long dailySeed;
	private String dailyRecordDate;
	// SPDNet: 房间ID，用于多人同怪
	private String roomId;

	public CEnterDungeon(Status status) {
		this.status = status;
		this.dailyGroupIndex = null;
		this.dailySeed = null;
		this.dailyRecordDate = null;
		this.roomId = null;
	}
}
