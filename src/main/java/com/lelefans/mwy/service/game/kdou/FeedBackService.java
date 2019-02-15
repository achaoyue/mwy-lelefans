package com.lelefans.mwy.service.game.kdou;

import com.lelefans.mwy.model.FeedBackModel;

import java.util.List;

public interface FeedBackService {
    /**
     * 添加反馈
     * @param feedBackModel
     */
    void addFeedback(FeedBackModel feedBackModel);

    /**
     * 获取反馈列表
     * @return
     */
    List<FeedBackModel> getFeedbackList(int offset,int limit);
}
