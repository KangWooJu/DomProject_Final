package ac.kr.dankook.ace.dom_t1.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuctionBidForm {


    private String id;
    
    @NotNull(message="금액은 필수적으로 들어가야 합니다.")
    @Min(value=1,message="금액은 0원 보다 높아야 합니다.")
    private Integer bidAmount;

}
