//package com.haui.ScentLyt.response.promotion;
//
//import lombok.*;
//import org.springframework.beans.BeanUtils;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//public class PromotionResponse {
//    private Integer id;
//    private String name;
//    private BigDecimal discountPercentage;
//    private Date startDate;
//    private Date endDate;
//    private Boolean active;
//
//    public static PromotionResponse fromPromotion(Promotion promotion) {
//        PromotionResponse response = new PromotionResponse();
//        BeanUtils.copyProperties(promotion, response);
//        response.setId(promotion.getPromotionId());
//        return response;
//    }
//}
