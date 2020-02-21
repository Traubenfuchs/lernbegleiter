package at.technikumwien.lernbegleiter;

import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.attempt.*;
import at.technikumwien.lernbegleiter.services.quiz.*;
import at.technikumwien.lernbegleiter.test.helper.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.client.*;
import org.springframework.web.client.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class QuizPerfTest {
  @Autowired
  private QuizManagementService qms;
  @Autowired
  private QuizRunService quizRunService;
  @Autowired
  private TestAuthHelper testAuthHelper;

  @Test
  void x() throws ExecutionException, InterruptedException {
    testAuthHelper.authAsAdmin();

    String quizUuid = qms.post(new QuizDto()
      .setName("Quizname")
      .addQuestions(
        new QuizQuestionDto()
          .setContent("q1")
          .setPosition(0)
          .setQuizQuestionType(QuizQuestionType.FREE_TEXT)
          .setFreeText("xxx")
          .setTimeLimit(1000),
        new QuizQuestionDto()
          .setContent("q2")
          .setPosition(1)
          .setQuizQuestionType(QuizQuestionType.FREE_TEXT)
          .setFreeText("xxx2")
          .setTimeLimit(1000)
      )).getUuid();

    String quizRunUuid = quizRunService.post(quizUuid, new QuizRunDto()
      .setState(QuizRunState.CREATED)
      .setQuizRunType(QuizRunType.ONE_QUESTION_AT_A_TIME)
    ).getUuid();

    quizRunService.advance(quizRunUuid);

    //QuizRunDto qrd = quizRunService.getCachedForAdmin(quizRunUuid);

    AtomicInteger ai = new AtomicInteger(0);

    IntStream.range(0, 10)
      .forEach(i -> {
        new Thread(() -> {
          RestTemplate rt = new RestTemplate();
          testAuthHelper.authAsUser(UUID.randomUUID().toString() + "_" + i);

          rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", testAuthHelper.getCurrentUsersSecret());
            ClientHttpResponse response = execution.execute(request, body);
            return response;
          });

          QuizAttemptDto qad = rt.postForObject("http://localhost:8666/api/quiz-run/" + quizRunUuid + "/quiz-attempt:create-if-not-exists", new HashMap<>(), QuizAttemptDto.class);

          while (true) {
            QuizRunDto qrd = rt.getForObject("http://localhost:8666/api/quiz-run-student/" + quizRunUuid, QuizRunDto.class);
            ai.incrementAndGet();
          }
        }).start();
      });

    new Thread(() -> {
      while (true) {
        try {
          int counter = ai.getAndSet(0);
          System.out.println("### " + counter / 5);
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          return;
        }
      }
    }).start();

    Thread.sleep(1000 * 9999);

  }
}
