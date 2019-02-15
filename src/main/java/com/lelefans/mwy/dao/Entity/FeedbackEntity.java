package com.lelefans.mwy.dao.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackEntity {
    private int id;
    private String phone;
    private String content;
    private int userId;
}
