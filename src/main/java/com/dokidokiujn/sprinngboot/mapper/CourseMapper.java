package com.dokidokiujn.sprinngboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dokidokiujn.sprinngboot.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    @Select("select c.*, sc.student_id is not null as isSelected " +
            "from course c " +
            "left join sc on c.id = sc.course_id " +
            "where sc.student_id = #{student_id}")
    List<Course> queryCourseListWithStudentSelection(@Param("student_id") int studentId);

    @Select("select c.*, sc.student_id is not null as isSelected " +
            "from course c " +
            "left join sc on c.id = sc.course_id and sc.student_id = #{student_id}")
    List<Course> queryCourseList(@Param("student_id") int studentId);

    List<Course> queryCourseWithCapacity(@Param("student_id") int studentId, @Param("code") String code, @Param("hasCapacity") boolean hasCapacity,@Param("hasCapacity1") boolean hasCapacity1);

    @Insert("insert into sc (student_id, course_id) values (#{studentId}, #{courseId})")
    void courseSelect(@Param("studentId") int studentId, @Param("courseId") int courseId);

    @Delete("delete from sc where student_id = #{studentId} and course_id = #{courseId}")
    void courseCancel(@Param("studentId") int studentId, @Param("courseId") int courseId);

    @Update("update course set selectedCount = selectedCount + #{number} where id = #{courseId}")
    void updateCount(@Param("courseId") int courseId, @Param("number") int number);

    @Select("select IF(course.totalCapacity-course.selectedCount>0, true, false) from course where id = #{courseId}")
    boolean checkCapacity(@Param("courseId") int courseId);

    @Select("select EXISTS(select 1 from sc where student_id = #{studentId} and course_id = #{courseId})")
    boolean checkSelected(@Param("studentId") int studentId, @Param("courseId") int courseId);
}
