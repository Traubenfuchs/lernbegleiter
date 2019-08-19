package at.technikumwien.lernbegleiter.data.dto.quiz;

import at.technikumwien.lernbegleiter.data.QuizType;
import at.technikumwien.lernbegleiter.data.dto.BaseDto;
import at.technikumwien.lernbegleiter.entities.auth.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto extends BaseDto<QuizDto> {
    private Integer maxRetries;
    private String name;
    private String description;
    private Set<QuizQuestionDto> questions = new HashSet<>();
    private UserEntity author;
    private QuizType quizType;
    private Instant expirationDate;
    private Set<QuizRunDto> quizRuns = new HashSet<>();
}
