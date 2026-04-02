package me.catand.spdnetserver.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SPDNet: 地牢房间/实例
 * 同一房间的所有玩家共享相同的地牢seed，可以共同打怪
 */
@Getter
@Setter
public class DungeonRoom {
    private final String roomId;
    private final long seed;
    private final int depth;
    private final int challenges;
    private final long createdAt;
    private final Map<UUID, String> players; // sessionId -> playerName

    public DungeonRoom(String roomId, long seed, int depth, int challenges) {
        this.roomId = roomId;
        this.seed = seed;
        this.depth = depth;
        this.challenges = challenges;
        this.createdAt = System.currentTimeMillis();
        this.players = new ConcurrentHashMap<>();
    }

    public void addPlayer(UUID sessionId, String playerName) {
        players.put(sessionId, playerName);
    }

    public void removePlayer(UUID sessionId) {
        players.remove(sessionId);
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public int getPlayerCount() {
        return players.size();
    }
}
