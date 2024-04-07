package com.xiaohe.xhapibackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.xhapibackend.common.ErrorCode;
import com.xiaohe.xhapibackend.constant.CommonConstant;
import com.xiaohe.xhapibackend.exception.BusinessException;
import com.xiaohe.xhapibackend.exception.ThrowUtils;
import com.xiaohe.xhapibackend.mapper.InterfaceInfoMapper;
import com.xiaohe.xhapibackend.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.xiaohe.xhapibackend.service.InterfaceInfoService;
import com.xiaohe.xhapibackend.utils.SqlUtils;
import com.xiaohe.xhapicommon.model.entity.InterfaceInfo;
import com.xiaohe.xhapicommon.model.enums.InterfaceInfoStatusEnum;
import com.xiaohe.xhapicommon.model.vo.UserVO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.xiaohe.xhapibackend.constant.UserConstant.ADMIN_ROLE;

/**
* @author Lenovo
* @description 针对表【interface_info(接口信息)】的数据库操作Service实现
* @createDate 2023-12-11 12:12:55
*/
@Service
public class InterfaceInfoServiceImpl extends ServiceImpl<InterfaceInfoMapper, InterfaceInfo>
    implements InterfaceInfoService {

    @Override
    public void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add) {
        if (interfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = interfaceInfo.getName();
        String description = interfaceInfo.getDescription();
        String url = interfaceInfo.getUrl();
        String method = interfaceInfo.getMethod();
        Long consumePoints = interfaceInfo.getConsumePoints();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(name, url, method), ErrorCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(method)) {
            interfaceInfo.setMethod(method.trim().toUpperCase());
        }
        if (StringUtils.isNotBlank(url)) {
            interfaceInfo.setUrl(url.trim());
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
        if (ObjectUtils.isNotEmpty(consumePoints) && consumePoints < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "消耗积分个数不能为负数");
        }
        if (StringUtils.isNotBlank(description) && description.length() > 200) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "描述过长");
        }
    }

    @Override
    public QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest, UserVO user) {
        if (interfaceInfoQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = interfaceInfoQueryRequest.getId();
        String name = interfaceInfoQueryRequest.getName();
        String description = interfaceInfoQueryRequest.getDescription();
        String url = interfaceInfoQueryRequest.getUrl();
        String method = interfaceInfoQueryRequest.getMethod();
        Integer status = interfaceInfoQueryRequest.getStatus();
        // 不是管理员只能查看已经上线的
        if (user == null || !user.getUserRole().equals(ADMIN_ROLE)) {
            status = InterfaceInfoStatusEnum.ONLINE.getValue();
        }
        Long consumePoints = interfaceInfoQueryRequest.getConsumePoints();
        String resultFormat = interfaceInfoQueryRequest.getResultFormat();
        String sortField = interfaceInfoQueryRequest.getSortField();
        String sortOrder = interfaceInfoQueryRequest.getSortOrder();
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name);
        queryWrapper.like(StringUtils.isNotBlank(description), "description", description);
        queryWrapper.like(StringUtils.isNotBlank(url), "url", url);
        queryWrapper.eq(StringUtils.isNotBlank(method), "method", method);
        queryWrapper.eq(status != null, "status", status);
        queryWrapper.eq(consumePoints != null, "consume_points", consumePoints);
        queryWrapper.like(StringUtils.isNotBlank(resultFormat), "result_format", resultFormat);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public boolean updateTotalInvokes(long interfaceInfoId) {
        UpdateWrapper<InterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", interfaceInfoId);
        updateWrapper.setSql("total_invokes = total_invokes +1");
        return this.update(updateWrapper);
    }

}




