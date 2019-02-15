package com.lelefans.mwy.controller.kdou;

import com.lelefans.mwy.model.ApiResponseModel;
import com.lelefans.mwy.model.FeedBackModel;
import com.lelefans.mwy.service.game.kdou.FeedBackService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/game/feedback")
public class FeedbackController {
    @Resource
    private FeedBackService feedBackService;
    @RequestMapping("/add")
    public ApiResponseModel addFeedback(FeedBackModel feedBackModel){
        feedBackService.addFeedback(feedBackModel);
        return ApiResponseModel.success("");
    }
    @RequestMapping("/list")
    public ApiResponseModel list(int offset,int limit){
        List<FeedBackModel> feedbackList = feedBackService.getFeedbackList(offset, limit);
        return ApiResponseModel.success(feedbackList);
    }
}
