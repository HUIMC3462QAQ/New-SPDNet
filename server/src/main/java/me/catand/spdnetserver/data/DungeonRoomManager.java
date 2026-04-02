package me.catand.spdnetserver.data;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SPDNet: 地牢房间管理器
 * 管理所有活跃的地牢房间实例
 */
@Component
@Getter
public class DungeonRoomManager {
    private final Map<String, DungeonRoom> rooms = new ConcurrentHashMap<>();
    private final Map<UUID, String> sessionToRoom = new ConcurrentHashMap<>(); // sessionId -> roomId

    /**
     * 创建新房间或加入已有房间
     * @param sessionId 玩家会话ID
     * @param playerName 玩家名称
     * @param seed 地牢seed
     * @param depth 楼层
     * @param challenges 挑战
     * @param roomId 房间ID（如果为null则创建新房间）
     * @return 房间ID
     */
    public String joinRoom(UUID sessionId, String playerName, long seed, int depth, int challenges, String roomId) {
        // 如果指定了房间ID，尝试加入
        if (roomId != null && !roomId.isEmpty()) {
            DungeonRoom existingRoom = rooms.get(roomId);
            if (existingRoom != null) {
                // 验证seed和depth是否匹配
                if (existingRoom.getSeed() == seed && existingRoom.getDepth() == depth) {
                    existingRoom.addPlayer(sessionId, playerName);
                    sessionToRoom.put(sessionId, roomId);
                    return roomId;
                }
                // seed不匹配，返回null让客户端重新同步
                return null;
            }
            // 房间不存在，返回null
            return null;
        }

        // 查找是否有 compatible 的现有房间（相同seed和depth）
        for (DungeonRoom room : rooms.values()) {
            if (room.getSeed() == seed && room.getDepth() == depth && room.getChallenges() == challenges) {
                room.addPlayer(sessionId, playerName);
                sessionToRoom.put(sessionId, room.getRoomId());
                return room.getRoomId();
            }
        }

        // 没有找到兼容房间，创建新房间
        String newRoomId = UUID.randomUUID().toString();
        DungeonRoom newRoom = new DungeonRoom(newRoomId, seed, depth, challenges);
        newRoom.addPlayer(sessionId, playerName);
        rooms.put(newRoomId, newRoom);
        sessionToRoom.put(sessionId, newRoomId);
        
        return newRoomId;
    }

    /**
     * 玩家离开房间
     */
    public void leaveRoom(UUID sessionId) {
        String roomId = sessionToRoom.remove(sessionId);
        if (roomId != null) {
            DungeonRoom room = rooms.get(roomId);
            if (room != null) {
                room.removePlayer(sessionId);
                // 如果房间空了，删除房间
                if (room.isEmpty()) {
                    rooms.remove(roomId);
                }
            }
        }
    }

    /**
     * 获取玩家所在的房间
     */
    public DungeonRoom getPlayerRoom(UUID sessionId) {
        String roomId = sessionToRoom.get(sessionId);
        if (roomId != null) {
            return rooms.get(roomId);
        }
        return null;
    }

    	/**
    	 * 获取房间内的所有玩家会话ID
    	 */
    	public java.util.List<UUID> getRoomPlayers(String roomId) {
    		DungeonRoom room = rooms.get(roomId);
    		if (room != null) {
    			return new java.util.ArrayList<>(room.getPlayers().keySet());
    		}
    		return new java.util.ArrayList<>();
    	}
    }
