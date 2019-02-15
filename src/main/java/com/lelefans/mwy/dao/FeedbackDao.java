package com.lelefans.mwy.dao;

import com.lelefans.mwy.dao.Entity.FeedbackEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackDao {
    /**
     * 添加反馈
     * @param feedbackEntity
     */
    void add(FeedbackEntity feedbackEntity);

    /**
     * 反馈列表
     * @param offset
     * @param limit
     * @return
     */
    List<FeedbackEntity> getList(int offset,int limit);

}
