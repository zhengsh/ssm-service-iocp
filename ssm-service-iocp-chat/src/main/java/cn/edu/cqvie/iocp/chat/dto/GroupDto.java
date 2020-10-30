package cn.edu.cqvie.iocp.chat.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ZHENG SHAOHONG
 */
@Data
public class GroupDto {

    private String id;
    private String name;

    private List<FriendDto> friendList;
}
