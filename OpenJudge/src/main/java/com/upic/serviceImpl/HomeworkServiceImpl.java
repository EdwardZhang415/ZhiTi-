package com.upic.serviceImpl;

import com.upic.po.Homework;
import com.upic.repository.HomeworkRepository;
import com.upic.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("HomeworkService")
public class HomeworkServiceImpl implements HomeworkService {
    @Autowired
    private HomeworkRepository homeworkRepository;
    @Override
    public Homework saveHomework(Homework homework) {
        return homeworkRepository.save(homework);
    }

    @Override
    public String deleteHomework(long id) {
        homeworkRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<Homework> searchHomework(Pageable pageable) {
        return homeworkRepository.findAll(pageable);
    }

    @Override
    public List<Homework> searchHomework() {
        return homeworkRepository.findAll();
    }

    @Override
    public Homework findOne(long id) {
        return homeworkRepository.getOne(id);
    }

    @Override
    public List<Homework> findByTeamId(long teamId) {
        return homeworkRepository.findByTeamId(teamId);
    }

    @Override
    public Page<Homework> findByTeamId(long teamId, Pageable pageable) {
        return homeworkRepository.findByTeamId(teamId,pageable);
    }

    @Override
    public String updateHomework(Homework homework) {
        homeworkRepository.saveAndFlush(homework);
        return "SUCCESS";
    }

    @Override
    public Homework findByHomeworkName(String homeworkName) {
        return homeworkRepository.findByHomeworkName(homeworkName);
    }
}
