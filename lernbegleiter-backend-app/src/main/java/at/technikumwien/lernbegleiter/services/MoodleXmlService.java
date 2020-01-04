package at.technikumwien.lernbegleiter.services;


import at.technikumwien.lernbegleiter.data.*;
import at.technikumwien.lernbegleiter.data.dto.quiz.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import at.technikumwien.lernbegleiter.services.quiz.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.w3c.dom.Node;
import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

@AllArgsConstructor
@Service
public class MoodleXmlService {
  private static final Base64.Decoder base64Decoder = Base64.getDecoder();
  private final QuizManagementService quizManagementService;

  public UuidResponse post(
    @NonNull String name,
    @NonNull String moodleXmlBase64) throws Exception {
    byte[] moodleXml = base64Decoder.decode(moodleXmlBase64);
    Document document = bytesToXmlDocument(moodleXml);

    Node node1 = document.getChildNodes().item(0);
    NodeList nodeList = node1.getChildNodes();

    Set<QuizQuestionDto> questions = new HashSet<>();

    AtomicInteger questionPosition = new AtomicInteger();
    for (int i = 0; i < nodeList.getLength(); i++) {
      if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
        Element question = (Element) nodeList.item(i);
        if (!question.getNodeName().equals("question")) {
          continue;
        }
        String type = question.getAttribute("type");

        switch (type) {
          case "multichoice" -> {
            Set<QuizAnswerDto> answers = new HashSet<>();

            int answerPosition = 0;
            NodeList questionChildren = question.getChildNodes();
            for (int x = 0; x < questionChildren.getLength(); x++) {

              if (!(questionChildren.item(x) instanceof Element)) {
                continue;
              }

              Element answer = (Element) questionChildren.item(x);
              if (!answer.getNodeName().equals("answer")) {
                continue;
              }

              String fraction = answer.getAttribute("fraction");
              boolean correct = !"0".equals(fraction);

              String content = answer.getChildNodes().item(1).getTextContent();

              answers.add(new QuizAnswerDto()
                .setCorrect(correct)
                .setPosition(answerPosition++)
                .setContent(content));
            }

            questions.add(new QuizQuestionDto()
              .setQuizQuestionType(QuizQuestionType.MULTIPLE_CHOICE)
              .setContent(questionText(question))
              .setAnswers(answers)
              .setPosition(questionPosition.getAndIncrement()));
          }

          case "truefalse" -> {
           /* String questionText = questionText(el);
            questions.add(new QuizQuestionDto()
              .setQuizQuestionType(QuizQuestionType.MULTIPLE_CHOICE)
              .setContent(questionText(el))
            .setAnswers(Set.of(
              new QuizAnswerDto().setPosition(0).setContent(),
              new QuizAnswerDto().setPosition(1)
            )));*/
            throw new RuntimeException("truefalse not implemented");
          }
          case "shortanswer" -> {
            questions.add(freeText(question).setPosition(questionPosition.getAndIncrement()));
          }
          case "matching" -> {
            throw new RuntimeException("matching not implemented");
          }
          case "cloze" -> {
            throw new RuntimeException("cloze not implemented");
          }
          case "essay" -> {
            // questions.add(freeText(question).setPosition(questionPosition.getAndIncrement()));
            throw new RuntimeException("essay not implemented");
          }
          case "numerical" -> {
            questions.add(freeText(question).setPosition(questionPosition.getAndIncrement()));
          }
          case "description" -> {
            throw new RuntimeException("description not implemented");
          }
        }
      }
    }

    int l = questions.size();
    int l2 = questions.stream().map(q -> q.getPosition()).distinct().collect(Collectors.toSet()).size();

    QuizDto quizDto = new QuizDto()
      .setDescription("Dieses Quiz wurde von Moodle importiert. Der original Filename war <" + name + ">.")
      .setName(name.substring(0, name.length() - 3))
      .setQuestions(questions);

    return quizManagementService.post(quizDto);
  }

  String questionText(Node question) {
    NodeList questionNodeList = question.getChildNodes();
    for (int i2 = 0; i2 < questionNodeList.getLength(); i2++) {
      if (!(questionNodeList.item(i2) instanceof Element)) {
        continue;
      }
      Element el2 = (Element) questionNodeList.item(i2);
      if (el2.getNodeName().equals("questiontext")) {
        return el2.getChildNodes().item(1).getTextContent();
      }
    }
    throw new RuntimeException("Should not happen");
  }

  private QuizQuestionDto freeText(Node question) {
    Node answer = findFirstNodeOf(question, "answer");
    String answerText = findFirstNodeOf(answer, "text").getTextContent();

    return new QuizQuestionDto()
      .setQuizQuestionType(QuizQuestionType.FREE_TEXT)
      .setFreeText(answerText)
      .setContent(questionText(question));
  }

  private Node findFirstNodeOf(Node n, String tagName) {
    NodeList nodeList = n.getChildNodes();
    for (int i = 0; i < nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if (Objects.equals(tagName, node.getNodeName())) {
        return node;
      }
    }
    return null;
  }

  private static Document bytesToXmlDocument(byte[] documentoXml) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    DocumentBuilder builder = factory.newDocumentBuilder();

    try (ByteArrayInputStream bais = new ByteArrayInputStream(documentoXml)) {
      return builder.parse(bais);
    }
  }
}
