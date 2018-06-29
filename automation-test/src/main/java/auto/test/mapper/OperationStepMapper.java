package auto.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import auto.test.selenium.OperationStep;

@Mapper
@Component
public interface OperationStepMapper {

	@Select("select * from OperationStep where processId = #{o.processId} order by serial")
	List<OperationStep> selectActionStepByProcedure(OperationStep o);

	@Insert("insert into ActionStep(stepId, stepName, stepValue, processId, serial, operationType, byType, byCondition, failPolicy) "
			+ "values(#{o.stepId}, #{o.stepName}, #{o.stepValue}, #{o.processId}, #{o.serial}, #{o.operationType}, #{o.byType}, #{o.byCondition}, #{o.failPolicy})")
	int insert(@Param("o") OperationStep obj);

	@Update("update ActionStep set stepName = #{o.stepName}, stepValue = #{o.stepValue}, processId = #{o.processId}, serial = #{o.serial}, "
			+ "operationType = #{o.operationType}, byType = #{o.byType}, byCondition = #{o.byCondition}, failPolicy = #{o.failPolicy} where stepId = #{o.stepId}")
	int update(@Param("o") OperationStep obj);

	@Delete("delete from ActionStep where stepId = #{o.stepId}")
	int delete(@Param("o") OperationStep obj);

}
