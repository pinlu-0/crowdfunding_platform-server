package com.atcpl.crowd.mapper;

import com.atcpl.crowd.entity.Admin;
import com.atcpl.crowd.entity.AdminExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 蜡笔小新
 */
public interface AdminMapper {

    int countByExample(AdminExample example);

    int deleteByExample(AdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    List<Admin> selectByExample(AdminExample example);

    Admin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByExample(@Param("record") Admin record, @Param("example") AdminExample example);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    List<Admin> selectAdminByKeyword(String keyword);

    void deleteOldRelationShip(Integer adminId);

    void saveNewRelationShip(@Param("adminId") Integer adminId, @Param("roleIdList") List<Integer> roleIdList);

    /**
     * 通过邮箱查询用户id
     * @param email
     * @return
     */
    Admin getAdminIdByEmail(String email);

    /**
     * 将生成的令牌存入数据库
     * @param id 用户id
     * @param token 令牌
     */
    void createToken(@Param("adminid") Integer id, @Param("password_token") String token);

    /**
     * 根据token获取admin对象
     * @param token
     * @return
     */
    Admin getAdminByToken(String token);

    /**
     * 删除token
     * @param token
     * @return
     */
    int deleteToken(String token);

}