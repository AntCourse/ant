package com.kzsrm.serviceImpl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.OptionDao;
import com.kzsrm.dao.SubjectDao;
import com.kzsrm.dao.SubjectLogDao;
import com.kzsrm.model.Option;
import com.kzsrm.model.Subject;
import com.kzsrm.model.SubjectLog;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.SubjectService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class SubjectServiceImpl extends BaseServiceMybatisImpl<Subject, String> implements SubjectService {
	@Resource
	private SubjectDao<?> subjectDao;
	@Resource
	private OptionDao<?> optionDao;
	@Resource
	private SubjectLogDao<?> subjectLogDao;

	@Override
	protected EntityDao<Subject, String> getEntityDao() {
		return subjectDao;
	}
	/**
	 * 获取视频对应的测试题
	 * @param videoId
	 * @return
	 */
	@Override
	public List<Subject> getSubjectByVideo(String videoId) {
		List<Subject> subList = subjectDao.getSubjectByVideo(videoId);
		for (Subject sub : subList)
			sub.setOptionList(optionDao.getOptionBySubject(sub.getId()));
		return subList;
	}
	/**
	 * 校验测试题的答案
	 * @param userId
	 * @param answer
	 * @return
	 */
	@Override
	@SuppressWarnings({ "static-access", "rawtypes" })
	public JSONArray checkAnswer(String userId, String answer, String type) {
		JSONArray ret = new JSONArray();
		JSONArray _jAnswerList = new JSONArray().fromObject(answer);
		Iterator iter = _jAnswerList.iterator();
		while (iter.hasNext()) {
			JSONObject ele = new JSONObject();
			JSONObject _jAnswer = new JSONObject().fromObject(iter.next());
			String no = _jAnswer.get("no") + "";
			String optId = _jAnswer.get("optId") + "";
//			String timeSpan = _jAnswer.get("timeSpan") + "";
//			int _timeSpan = ComUtils.parseInt(timeSpan);
			
			Option opt = optionDao.getById(optId);
			Subject sub = subjectDao.getById(opt.getSid() + "");
			
			int ac = sub.getAllcount();
			sub.setAllcount(ac + 1);
			if ("1".equals(opt.getIsanswer()))
				sub.setRightcount(sub.getRightcount() + 1);
//			sub.setAvgtime((sub.getAvgtime() * ac + _timeSpan) / (ac + 1));
			subjectDao.update(sub);
			
			ele.put("no", no);
			ele.put("isRight", opt.getIsanswer());
			ele.put("degree", sub.getDegree());
			ele.put("avgAcc", (sub.getRightcount() * 100 / sub.getAllcount()) + "%");
//			ele.put("avgTime", sub.getAvgtime());
//			JSONArray pois = new JSONArray();
//			for (Point point : pointDao.getPoisBySub(sub.getId() + "")){
//				JSONObject poi = new JSONObject();
//				poi.put("content", point.getContent());
//				poi.put("id", point.getId());
//				pois.add(poi);
//			}
//			ele.put("points", pois);
			
			ret.add(ele);
			if (StringUtils.isNotBlank(userId)){
				// 记录做题日志
				SubjectLog slog = new SubjectLog();
				slog.setCreatetime(new Date());
				slog.setIsright(opt.getIsanswer());
				slog.setOid(opt.getId()+"");
				slog.setSid(sub.getId()+"");
				slog.setUserid(userId);
				subjectLogDao.save(slog);
			}
			
		}
		return ret;
	}
	/**
	 * 获取知识点对应的测试题
	 * @param videoId
	 * @return
	 */
	@Override
	public List<Subject> getSubjectByPoint(String pointId) {
		List<Subject> subList = subjectDao.getSubjectByPoint(pointId);
		for (Subject sub : subList)
			sub.setOptionList(optionDao.getOptionBySubject(sub.getId()));
		return subList;
	}
	/**
	 * 获取知识点对应的测试题的数量
	 * @param videoId
	 * @return
	 */
	@Override
	public Integer getSubNumByPoint(String pointId) {
		return subjectDao.getSubNumByPoint(pointId);
	}
	/**
	 * 获取模考对应的测试题
	 * @param examId
	 * @return
	 */
	@Override
	public List<Subject> getSubjectByExam(String examId) {
		List<Subject> subList = subjectDao.getSubjectByExam(examId);
		for (Subject sub : subList)
			sub.setOptionList(optionDao.getOptionBySubject(sub.getId()));
		return subList;
	}

}
