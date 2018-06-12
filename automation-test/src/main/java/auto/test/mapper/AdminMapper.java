package com.ericsson.ct.cloud.nfvi.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import auto.test.Admin;

@Mapper
@Component
public interface AdminMapper {

	@Update("create table Admin(procedures CLOB)")
	void create();

	@Select("select * from Admin")
	List<Admin> selectAll();
	
	@Insert("insert into Admin(procedures) values(#{o.procedures})")
	int insert(@Param("o") Admin obj);

	@Update("update Admin set procedures = #{o.procedures}")
	int update(@Param("o") Admin obj);

}
