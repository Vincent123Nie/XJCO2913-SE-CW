package com.cwk.gps.service.impl;

import com.cwk.context.BaseContext;
import com.cwk.gps.mapper.*;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.UserService;
import com.cwk.gps.utils.TimeUtils;
import com.cwk.pojo.dto.UserPageQueryDTO;
import com.cwk.pojo.entity.Activity;
import com.cwk.pojo.entity.Post;
import com.cwk.pojo.entity.Route;
import com.cwk.pojo.entity.User;
import com.cwk.pojo.vo.BloggerInfoVo;

import com.cwk.pojo.vo.IndividualCenterVo;
import com.cwk.pojo.vo.MotionVo;
import com.cwk.pojo.vo.TotalMotionVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private RouteMapper routeMapper;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private ActivityUserMapper activityUserMapper;
    @Autowired
    private ActivityMapper activityMapper;


    public User getOne(String email) {
        return userMapper.getOne(email);
    }


    public void save(User user) {
        userMapper.save(user);
    }


    public void update(User user) {
        if (user.getId() == null){
            user.setId(BaseContext.getCurrentId());
        }
        userMapper.update(user);
    }


    public PageResult pageQuery(UserPageQueryDTO userPageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(userPageQueryDTO.getPage(),userPageQueryDTO.getPageSize());

        Page<User> page = userMapper.pageQuery(userPageQueryDTO);
        long total = page.getTotal();
        List<User> records = page.getResult();
        return new PageResult(total, records);
    }


    public void delete(Long id) {
        userMapper.delete(id);
    }


    public BloggerInfoVo queryOther(Long id) {
        User user = userMapper.queryByUserId(id);
        BloggerInfoVo bloggerInfoVo = new BloggerInfoVo();
        bloggerInfoVo.setId(id);
        bloggerInfoVo.setUsername(user.getUsername());
        bloggerInfoVo.setAvatar(user.getAvatar());
        bloggerInfoVo.setFollowingcount(friendMapper.queryfollowingcount(id));
        bloggerInfoVo.setFunscount(friendMapper.queryfunscount(id));

        TotalMotionVo totalMotionVo = new TotalMotionVo();
        List<Route> list = routeMapper.queryByUserId(id);
        Double totalDistance = 0.0;
        Long t_Time = 0L;
        List<MotionVo> M_list = new ArrayList<>();
        for (Route route : list) {
            totalDistance += (route.getDistance()/1000);
            String[] parts = route.getTime().split(":");
            int minutes = Integer.parseInt(parts[0]);
            int seconds = Integer.parseInt(parts[1]);
            t_Time += minutes * 60 + seconds;
            MotionVo motionVo = new MotionVo();
            motionVo.setTime(route.getTime());
            motionVo.setCarlorie(route.getCalorie());
            Long initaldate = route.getEndTime();
            // 将时间戳转换为 Instant
            Instant instant = Instant.ofEpochMilli(initaldate);
            // 使用默认时区将 Instant 转换为 LocalDate
            LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            // 定义日期格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // 格式化日期
            String formattedDate = localDate.format(formatter);
            motionVo.setDate(formattedDate);
            M_list.add(motionVo);
        }
        String totalTime = TimeUtils.formatTime(t_Time);
        totalMotionVo.setTotalDistance(totalDistance);
        totalMotionVo.setTotalTime(totalTime);
        totalMotionVo.setList(M_list);
        bloggerInfoVo.setTotalTime(totalMotionVo.getTotalTime());
        List<Post> res = postMapper.queryByUserId(id);
        bloggerInfoVo.setList(res);
        bloggerInfoVo.setPostscount(postMapper.queryPostsCount(id));

        return bloggerInfoVo;
    }


    public IndividualCenterVo queryCenter() {
        Long id = BaseContext.getCurrentId();
        User user = userMapper.queryByUserId(id);
        IndividualCenterVo individualCenterVo = new IndividualCenterVo();
        individualCenterVo.setId(id);
        individualCenterVo.setUsername(user.getUsername());
        individualCenterVo.setAvatar(user.getAvatar());
        individualCenterVo.setFunscount(friendMapper.queryfunscount(id));
        individualCenterVo.setFollowingcount(friendMapper.queryfollowingcount(id));
        individualCenterVo.setPostscount(postMapper.queryPostsCount(id));
        return individualCenterVo;
    }


    public List<Post> queryMyPosts() {
        List<Post> list = postMapper.queryByUserId(BaseContext.getCurrentId());
        return list;
    }


    public List<Activity> queryMyActivity() {
        List<Long> list = activityUserMapper.queryByUserId(BaseContext.getCurrentId());
        List<Activity> res = new ArrayList<>();
        for (Long id : list) {
            Activity activity = activityMapper.getById(id);
            res.add(activity);
        }
        return res;
    }

}
