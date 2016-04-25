package com.kzsrm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.CourseDao;
import com.kzsrm.model.Course;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.CourseService;
import com.kzsrm.utils.ComUtils;
import com.kzsrm.utils.CustomException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Service
@Transactional
public class CourseServiceImpl extends BaseServiceMybatisImpl<Course, String> implements CourseService {
	@Resource
	private CourseDao<?> courseDao;

	@Override
	protected EntityDao<Course, String> getEntityDao() {
		return courseDao;
	}
	
	JsonConfig courCf = ComUtils.jsonConfig(new String[]{"playcount"});

	/**
	 * 查询pid下的所有子项
	 * @param pid
	 * @param type
	 * @return
	 */
	@Override
	public List<Course> getchildrenCour(String pid, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", pid);
		map.put("type", type);
		return courseDao.getchildrenCour(map);
	}
	
	/**
	 * 查询课程
	 * @param pid
	 * @return
	 */
	@Override
	public Course getCourseById(String id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return courseDao.getCourseById(map);
	}
	
	/**
	 * 更新课程信息
	 * @param videoId
	 * @throws CustomException 
	 */
	@Override
	public void updateCourseInfo(String courseId) throws CustomException {

		Course course = courseDao.getById(courseId);
		if (course == null)
			throw new CustomException("课程不存在");
		
		course.setPlaycount(course.getPlaycount() + 1);
		courseDao.update(course);
	}

	/**
     * 钻取多级课程
     * @param pid
     * @return
     */
	@Override
	public JSONObject getMultilevelCour(Course course, String userid, String type) {
		if (course != null){
			JSONObject _course = JSONObject.fromObject(course, courCf);
			Integer subNum = 0;// 已做题数
			List<Course> courseList = getchildrenCour(course.getId()+"", type);
			if (courseList != null && courseList.size() > 0) {
				JSONArray children = new JSONArray();
				for (Course child : courseList){
					JSONObject ch = getMultilevelCour(child, userid, type);
					subNum += ch.getInt("subNum");
					children.add(ch);
				}
				_course.put("child", children);
			} else if (StringUtils.isNotBlank(userid)){
				subNum = getHasDoneSubNum(course.getId(), userid);
			}
			_course.put("subNum", subNum);
			return _course;
		}
		return new JSONObject();
	}

	private Integer getHasDoneSubNum(Integer cid, String userid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cid", cid);
		map.put("userid", userid);
		return courseDao.getHasDoneSubNum(map);
	}

}
