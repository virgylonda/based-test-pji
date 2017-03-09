package pji.cbt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pji.cbt.entities.Question;
import pji.cbt.iservices.QuestionService;
import pji.cbt.mapper.QuestionMapper;

@Service
public class QuestionServiceImp implements QuestionService {
	
	@Autowired
	private QuestionMapper qMapper;

	@Override
	public List<Question> findAllQuestion() {
		return qMapper.findAllQuestion();
	}

	@Override
	public Question findOneQuestion(int categoryId) {
		return qMapper.findOneQuestion(categoryId);
	}

	@Override
	public void deleteQuestion(int id) {
		qMapper.deleteQuestion(id);
		
	}

	@Override
	public void createQuestion(Question question) {
		qMapper.createQuestion(question);
	}

	
}