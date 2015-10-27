package com.kzsrm.serviceImpl;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kzsrm.baseservice.BaseServiceMybatisImpl;
import com.kzsrm.dao.OptionDao;
import com.kzsrm.dao.PointDao;
import com.kzsrm.dao.SubjectDao;
import com.kzsrm.model.Option;
import com.kzsrm.model.Point;
import com.kzsrm.model.Subject;
import com.kzsrm.mybatis.EntityDao;
import com.kzsrm.service.SubjectService;
import com.kzsrm.utils.ComUtils;

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
	private PointDao<?> pointDao;

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
	@SuppressWarnings({ "static-access", "rawtypes" })
	public JSONArray checkAnswer(String answer) {
		JSONArray ret = new JSONArray();
		JSONArray _jAnswerList = new JSONArray().fromObject(answer);
		Iterator iter = _jAnswerList.iterator();
		while (iter.hasNext()) {
			JSONObject ele = new JSONObject();
			JSONObject _jAnswer = new JSONObject().fromObject(iter.next());
			String no = _jAnswer.get("no") + "";
			String optId = _jAnswer.get("optId") + "";
			String timeSpan = _jAnswer.get("timeSpan") + "";
			int _timeSpan = ComUtils.parseInt(timeSpan);
			
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
			ele.put("avgAcc", (sub.getRightcount() * 100 / sub.getAllcount()) + "%");
			ele.put("avgTime", sub.getAvgtime());
			JSONArray pois = new JSONArray();
			for (Point point : pointDao.getPoisBySub(sub.getId() + "")){
				JSONObject poi = new JSONObject();
				poi.put("content", point.getContent());
				poi.put("id", point.getId());
				pois.add(poi);
			}
			ele.put("points", pois);
			
			ret.add(ele);
		}
		return ret;
	}

}
