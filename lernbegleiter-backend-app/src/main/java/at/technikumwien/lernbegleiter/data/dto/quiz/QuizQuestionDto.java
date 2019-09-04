package at.technikumwien.lernbegleiter.data.dto.quiz;

import at.technikumwien.lernbegleiter.data.dto.BaseDto;
import at.technikumwien.lernbegleiter.data.dto.LobDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizQuestionDto extends BaseDto<QuizQuestionDto> {
    private String content;
    private Set<QuizAnswerDto> answers = new HashSet<>();
    private Integer position;
    private LobDto lob;
}
