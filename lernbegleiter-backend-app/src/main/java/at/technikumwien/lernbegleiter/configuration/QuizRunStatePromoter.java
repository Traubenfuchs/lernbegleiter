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

import java.time.Instant;
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
            if (!Instant.now().isAfter(quizRunEntity.getNextTimeLimit())) {
                return;
            }

            int currentPosition = currentQuestion.getPosition();
            quizRunEntity
                    .getQuiz()
                    .getQuestions()
                    .stream()
                    .filter(qq -> qq.getPosition() == currentPosition + 1)
                    .findFirst()
                    .ifPresentOrElse(
                            qq -> {
                                log.info("Promoted quiz-run with uuid<{}> to state<WAITING_FOR_NEXT_QUESTION>", quizRunEntity.getUuid());
                                quizRunEntity
                                        .setNextTimeLimit(null)
                                        .setState(QuizRunState.WAITING_FOR_NEXT_QUESTION);
                            },
                            () -> {
                                log.info("Promoted quiz-run with uuid<{}> to state<DONE>", quizRunEntity.getUuid());
                                quizRunEntity
                                        .setCurrentQuestion(null)
                                        .setNextTimeLimit(null)
                                        .setState(QuizRunState.DONE)
                                ;
                            }
                    );
        });
    }
}
