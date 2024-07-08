package ac.kr.dankook.ace.dom_t1.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class ProductPostForm {
    private String location;
    private String day;
    private String month;
    private String year;
    private String price;
    private String category;
    private String title;
    private String detail;
}
