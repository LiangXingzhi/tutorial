package auto.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import auto.test.selenium.OperationalProcess;

@Mapper
@Component
public interface OperationalProcessMapper {

	@Select("select * from OperationalProcess")
	List<OperationalProcess> selectAll();

	@Insert("insert into OperationalProcess(processId, processName) values(#{o.processId}, #{o.processName})")
	int insert(@Param("o") OperationalProcess obj);

	@Update("update Procedure set processName = #{o.processName} where processId = #{o.processId}")
	int update(@Param("o") OperationalProcess obj);

}
