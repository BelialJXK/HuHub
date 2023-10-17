package hu.helper.bang.center.notify.service.impl;

import hu.helper.bang.center.common.exception.BangException;
import hu.helper.bang.center.common.result.BangPaging;
import hu.helper.bang.center.common.result.BangTableData;
import hu.helper.bang.center.common.util.BangPreconditions;
import hu.helper.bang.center.notify.controller.request.NotifyCreateRequest;
import hu.helper.bang.center.notify.controller.request.NotifyRequest;
import hu.helper.bang.center.notify.controller.request.NotifyUpdateRequest;
import hu.helper.bang.center.notify.dao.NotifyMapper;
import hu.helper.bang.center.notify.dao.model.NotifyDO;
import hu.helper.bang.center.notify.dao.model.NotifyLikesDO;
import hu.helper.bang.center.notify.enums.NotifyResultEnum;
import hu.helper.bang.center.notify.service.NotifyService;
import hu.helper.bang.center.notify.service.model.NotifyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author : Jiang XingKun
 * @Date : 2023/2/21
 * @Description : 通知服务实现
 */
@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {
    private final static Integer PAGEMAX = 8;
    @Resource
    NotifyMapper notifyMapper;

    int min = 0, max = 0;
    @Override
    public Integer getTotalUnread(Long receiverId) {
        BangPreconditions.checkNotNull(receiverId);
        if (isIdExist(receiverId)) {
            return notifyMapper.getTotalUnread(receiverId);
        }
        log.error("未读通知总数获取失败，用户id不存在:{}",receiverId);
        throw new BangException("未读通知总数获取失败，用户id不存在");
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createNotify(NotifyCreateRequest request) {
        NotifyDO notifyDO = new NotifyDO();
        BeanUtils.copyProperties(request, notifyDO);
        return notifyMapper.createNotify(notifyDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer batchCreateNotify(List<NotifyCreateRequest> request) {
        return notifyMapper.batchCreateNotify(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateNotifyState(NotifyUpdateRequest request) {
        NotifyDO notifyDO = new NotifyDO();
        BeanUtils.copyProperties(request, notifyDO);

        if (isIdExist(notifyDO.getId())) {
            return notifyMapper.updateNotify(request);
        }
        log.error("更新失败，帖子id不存在:{}",notifyDO.getId());
        throw new BangException("更新失败，帖子id不存在");

    }

    @Override
    public BangTableData getNotify(NotifyRequest notifyRequest) {
        Long receiverId = notifyRequest.getReceiverId();
        BangPaging bangPaging=new BangPaging();
        bangPaging.setCurrentPage(notifyRequest.getPageNum());
        bangPaging.setPageSize(PAGEMAX);
        //获取点赞通知
        List<NotifyDO> likes = Optional.of(notifyMapper.getNotifyLikes(receiverId)).get();
        BangTableData bangTableData=new BangTableData();
        if (likes.size() > 0) {
            List<NotifyDO> likesResult ;
            // 1、整合点赞的存到list
            likesResult = likesHandler(likes);
            // 2、获取其他类型通知存到list
            List<NotifyDO> resultList = Optional.of(notifyMapper.getNotifys(receiverId)).orElseThrow(() -> new BangException("该用户ID没有通知"));
            // 3、list排序，然后按8条反给前端
            resultList.addAll(likesResult);
            resultList.sort((a, b) -> b.getGmtModified().compareTo(a.getGmtModified()));

            bangPaging.setTotalCount(resultList.size());
            getBoundary(bangTableData,bangPaging, resultList);

            return bangTableData;
        } else {
            List<NotifyDO> result = Optional.of(notifyMapper.getNotifys(receiverId)).orElseThrow(() -> new BangException("该用户ID没有通知"));
            getBoundary(bangTableData,bangPaging, result);
            return bangTableData;
        }
    }

    private BangTableData getBangTableData(BangPaging bangPaging, BangTableData bangTableData, List<NotifyDO> resultList) {
        getBoundary(bangTableData,bangPaging, resultList);
        if (min == 0 && max == 0) {
            bangTableData.setTableData(new ArrayList<>());
        }
        bangTableData.setTableData(resultList.subList(min, max));
        bangTableData.setPaging(bangPaging);
        return bangTableData;
    }

    /**
     * 获取分页数据上下边界
     * @param bangTableData
     * @param bangPaging 分页实体
     * @param resultList 数据总数
     */
    private void getBoundary(BangTableData bangTableData, BangPaging bangPaging, List<NotifyDO> resultList) {
        int size=resultList.size();
        //页数边界在数据范围内
        if (bangPaging.getCurrentPage() * PAGEMAX < size && (bangPaging.getCurrentPage() - 1) * PAGEMAX < size) {
            this.min = (bangPaging.getCurrentPage() - 1) * PAGEMAX;
            this.max = bangPaging.getCurrentPage() * PAGEMAX;
        }
        //结束页数大于数据总数
        else if (bangPaging.getCurrentPage() * PAGEMAX > size && (bangPaging.getCurrentPage() - 1) * PAGEMAX < size) {
            this.min = (bangPaging.getCurrentPage() - 1) * PAGEMAX;
            this.max = size;
        }
        if (min == 0 && max == 0) {
            bangTableData.setTableData(new ArrayList<>());
        }
        bangTableData.setTableData(resultList.subList(min, max));
        bangTableData.setPaging(bangPaging);
    }


    /**
     * 点赞通知聚合处理
     *
     * @param likes 点赞列表
     * @return 聚合后的点赞列表
     */
    private List<NotifyDO> likesHandler(List<NotifyDO> likes) {
        //result列表接收聚合的点赞通知
        List<NotifyLikesDO> resultList = new ArrayList<>();
        for (NotifyDO like : likes) {
            if (resultList.size() == 0) {
                NotifyLikesDO notifyLikesDO = new NotifyLikesDO();
                BeanUtils.copyProperties(like,notifyLikesDO);
                notifyLikesDO.setNumber(1);
                notifyLikesDO.setNames(like.getSenderName());
                resultList.add(notifyLikesDO);
            } else {
                for (NotifyLikesDO result : resultList) {
                    //判断是否已经有同一条通知存入result列表,有就更新，没有就存入
                    if (like.getTitle().equals(result.getTitle())) {
                        //只取最新两条通知的发送人名字
                        if (1 == result.getNumber()) {
                            result.numberPlus();
                            result.namePlus(like.getSenderName());
                        } else {
                            result.numberPlus();
                        }
                    } else {
                        NotifyLikesDO notifyLikesDO = new NotifyLikesDO();
                        BeanUtils.copyProperties(like,notifyLikesDO);
                        notifyLikesDO.setNumber(1);
                        notifyLikesDO.setNames(like.getSenderName());
                        resultList.add(notifyLikesDO);
                    }
                }
            }
        }
        //拼接发送人名字和数量   xxx;xxx;3
        for (NotifyLikesDO result : resultList) {
            result.setSenderName(result.getNames() + ";" + result.getNumber());
        }
        return dos2dos(resultList);
    }

    private List<NotifyDO> dos2dos(List<NotifyLikesDO> list) {
        List<NotifyDO> result = new ArrayList<>();
        for (Object object : list) {
            NotifyDO notifyDTO = new NotifyDO();
            BeanUtils.copyProperties(object, notifyDTO);
            result.add(notifyDTO);
        }
        return result;
    }

    private List<NotifyDTO> dos2dtos(List<NotifyDO> list) {

        List<NotifyDTO> result = new ArrayList<>();
        for (Object object : list) {
            NotifyDTO notifyDTO = new NotifyDTO();
            BeanUtils.copyProperties(object, notifyDTO);
            result.add(notifyDTO);
        }
        return result;
    }


    private boolean isIdExist(Long receiverId) {
        return NotifyResultEnum.SUCCESS.getCode() <= notifyMapper.checkId(receiverId);
    }
}
