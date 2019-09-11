package at.technikumwien.lernbegleiter.configuration;

import at.technikumwien.lernbegleiter.data.QuizRunState;
import at.technikumwien.lernbegleiter.entities.quiz.QuizQuestionEntity;
import at.technikumwien.lernbegleiter.entities.quiz.attempts.QuizRunEntity;
import at.technikumwien.lernbegleiter.repositories.quiz.attempts.QuizRunRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class QuizRunStatePromoter {
    @Lazy
    @Autowired
    private QuizRunStatePromoter self;

    @Autowired
    private QuizRunRepository quizRunRepository;

    @Scheduled(fixedDelay = 1000, initialDelay = 10000)
    public void scheduledClean() {
        try {
            self.promote();
        } catch (Throwable throwable) {
            log.error("Promotion failed.", throwable);
        }
    }

    @Transactional
    public void promote() {
        Set<QuizRunEntity> quizRunEntities = quizRunRepository.findByState(QuizRunState.WAITING_FOR_ANSWERS);
        quizRunEntities.forEach(quizRunEntity -> {
            QuizQuestionEntity currentQuestion = quizRunEntity.getCurrentQuestion();
            long secondsSinceStart = Duration.between(quizRunEntity.getStartedAt(), Instant.now()).getSeconds();
            if (secondsSinceStart < currentQuestion.getTimeLimit()) {
                return;
            }
            int currentPosition = currentQuestion.getPosition();
            List<QuizQuestionEntity> quizQuestions = quizRunEntity.getQuiz().getQuestions();
            quizQuestions.stream().filter(qq -> qq.getPosition() == currentPosition + 1).findFirst()
                    .ifPresentOrElse(
                            qq -> {
                                quizRunEntity
                                        .setNextTimeLimit(null)
                                        .setState(QuizRunState.WAITING_FOR_NEXT_QUESTION);

                            },
                            () -> {
                                quizRunEntity
                                        .setCurrentQuestion(null)
                                        .setNextTimeLimit(null)
                                        .setState(QuizRunState.DONE)
                                ;
                            });

        });
    }
}
