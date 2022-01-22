package tul.swiercz.thesis.bookmind.dto.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import tul.swiercz.thesis.bookmind.domain.ShelfActionType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "shelfActionType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ShelfActionModifyDto.class, name = "UPDATE"),
        @JsonSubTypes.Type(value = ShelfActionBookDto.class, names = {"ADD_BOOK", "REMOVE_BOOK"}),
}
)
public class ShelfActionDto {

    @NotNull
    private ShelfActionType shelfActionType;

    @Past
    private LocalDateTime actionDate;

    public ShelfActionDto() {
    }

    public ShelfActionDto(ShelfActionType shelfActionType, LocalDateTime actionDate) {
        this.shelfActionType = shelfActionType;
        this.actionDate = actionDate;
    }

    public ShelfActionType getShelfActionType() {
        return shelfActionType;
    }

    public void setShelfActionType(ShelfActionType shelfActionType) {
        this.shelfActionType = shelfActionType;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }
}
