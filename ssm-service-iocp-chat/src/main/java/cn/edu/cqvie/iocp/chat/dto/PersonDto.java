package cn.edu.cqvie.iocp.chat.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author ZHENG SHAOHONG
 */
@Data
public class PersonDto {

    private String id;

    private String name;

    private String remark;

    private String serialCode;

    private String headImg;

    private String note;

    private LocalDate birthday;
}
