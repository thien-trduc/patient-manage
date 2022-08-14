package thien.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BaseDto {
    protected Date createdAt;
    protected Date updatedAt;
    protected Date deletedAt;
}
