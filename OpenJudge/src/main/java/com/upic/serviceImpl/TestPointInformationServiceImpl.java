package com.upic.serviceImpl;

import com.upic.po.TestPointInformation;
import com.upic.repository.TestPointInformationRepository;
import com.upic.service.TestPointInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("TestPointInformationService")
public class TestPointInformationServiceImpl implements TestPointInformationService {
    @Autowired
    private TestPointInformationRepository testPointInformationRepository;
    @Override
    public TestPointInformation saveTestPointInformation(TestPointInformation testPointInformation) {
        return testPointInformationRepository.save(testPointInformation);
    }

    @Override
    public String deleteTestPointInformation(long id) {
        testPointInformationRepository.delete(id);
        return "SUCCESS";
    }

    @Override
    public Page<TestPointInformation> searchTestPointInformation(Pageable pageable) {
        return testPointInformationRepository.findAll(pageable);
    }

    @Override
    public List<TestPointInformation> searchTestPointInformation() {
        return testPointInformationRepository.findAll();
    }

    @Override
    public TestPointInformation findOne(long id) {
        return testPointInformationRepository.getOne(id);
    }

    @Override
    public String updateTestPointInformation(TestPointInformation testPointInformation) {
        testPointInformationRepository.saveAndFlush(testPointInformation);
        return "SUCCESS";
    }
}
