package com.lelefans.mwy.service.game.kdou.impl;

import com.lelefans.mwy.dao.Entity.FeedbackEntity;
import com.lelefans.mwy.dao.FeedbackDao;
import com.lelefans.mwy.model.FeedBackModel;
import com.lelefans.mwy.service.game.kdou.FeedBackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedBackServiceImpl implements FeedBackService {
    @Resource
    private FeedbackDao feedbackDao;

    @Override
    public void addFeedback(FeedBackModel feedBackModel) {
        feedbackDao.add(toFeedbackEntity(feedBackModel));
    }

    private FeedbackEntity toFeedbackEntity(FeedBackModel feedBackModel) {
        FeedbackEntity entity = new FeedbackEntity();
        entity.setContent(feedBackModel.getContent());
        entity.setPhone(feedBackModel.getPhone());
        entity.setUserId(feedBackModel.getUserId());
        return entity;
    }

    @Override
    public List<FeedBackModel> getFeedbackList(int offset, int limit) {
        List<FeedbackEntity> list = feedbackDao.getList(offset, limit);
        return list.stream().map(e->toModel(e)).collect(Collectors.toList());
    }

    private FeedBackModel toModel(FeedbackEntity entity){
        FeedBackModel model = new FeedBackModel();
        model.setContent(entity.getContent());
        model.setPhone(entity.getPhone());
        model.setUserId(entity.getUserId());
        return model;
    }
}
