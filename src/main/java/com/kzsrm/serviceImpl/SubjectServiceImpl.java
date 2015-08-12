package com.kzsrm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.OptionDao;
import com.kzsrm.dao.SubjectDao;
import com.kzsrm.model.Option;
import com.kzsrm.model.Subject;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.SubjectService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional
public class SubjectServiceImpl extends BaseServiceMybatisImpl<Subject, Integer> implements SubjectService {
	@Resource
	private SubjectDao<?> subjectDao;
	@Resource
	private OptionDao<?> optionDao;

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
	@Override
	public List<Map<String, Object>> checkAnswer(String answer) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		JSONArray _jAnswerList = new JSONArray().fromObject(answer);
		Iterator iter = _jAnswerList.iterator();
		while (iter.hasNext()) {
			Map<String, Object> ele = new HashMap<String, Object>();
			JSONObject _jAnswer = new JSONObject().fromObject(iter.next());
			String no = _jAnswer.get("no") + "";
			String optId = _jAnswer.get("optId") + "";
			String timeSpan = _jAnswer.get("timeSpan") + "";
			int _timeSpan = 0;
			try {
				_timeSpan = Integer.parseInt(timeSpan);
			}catch (Exception e){}
			
			Option opt = optionDao.getById(optId);
			Subject sub = subjectDao.getById(opt.getSid() + "");
			
			int ac = sub.getAllcount();
			sub.setAllcount(ac + 1);
			if ("1".equals(opt.getIsanswer()))
				sub.setRightcount(sub.getRightcount() + 1);
			sub.setAvgtime((sub.getAvgtime() * ac + _timeSpan) / (ac + 1));
			subjectDao.update(sub);
			
			ele.put("no", no);
			ele.put("isRight", opt.getIsanswer());
			ele.put("degree", sub.getDegree());
			ele.put("avgAcc", sub.getRightcount() / sub.getAllcount());
			ele.put("degree", sub.getAvgtime());
			ele.put("points", subjectDao.getPoisBySub(sub.getId() + ""));
			
			ret.add(ele);
		}
		return null;
	}

}
