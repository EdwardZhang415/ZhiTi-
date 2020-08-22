package com.upic.service;

import com.upic.po.TestPointInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface TestPointInformationService {
    TestPointInformation saveTestPointInformation(TestPointInformation testPointInformation);

    String deleteTestPointInformation(long id);

    Page<TestPointInformation> searchTestPointInformation(Pageable pageable);

    List<TestPointInformation> searchTestPointInformation();

    TestPointInformation findOne(long id);

    String updateTestPointInformation(TestPointInformation testPointInformation);
}
