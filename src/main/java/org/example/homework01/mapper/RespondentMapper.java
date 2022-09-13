package org.example.homework01.mapper;

import org.example.homework01.domain.Respondent;
import org.example.homework01.dto.RespondentDto;
import org.springframework.stereotype.Component;

@Component
public class RespondentMapper {

    public Respondent toRespondent(RespondentDto dto) {
        return new Respondent(dto.getFirstName(),
                dto.getLastName(),
                dto.getCorrectAnswersCount(),
                dto.getQuestionsTotal(),
                dto.getIsTestPassed());
    }
}
