package at.technikumwien.lernbegleiter.services.user;

import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.data.responses.*;
import lombok.*;
import org.springframework.stereotype.*;

import java.security.*;
import java.time.*;

@AllArgsConstructor
@Service
public class MassRegistrationService {
  private final SecureRandom random = new SecureRandom();
  private final RegistrationService registrationService;

  private final String[] colorNames = "Red,Orange,Yellow,Green,Blue,Purple,Brown,Magenta,Tan,Cyan,Olive,Maroon,Navy,Aquamarine,Turquoise,Silver,Lime,Teal,Indigo,Violet,Pink,Black,White,Gray,Grey".toLowerCase().split(",");
  private final String[] adjectives = "affable,affectionate,agreeable,ambitious,amiable,amicable,amusing,brave,bright,broad-minded,calm,careful,charming,communicative,compassionate,conscientious,considerate,convivial,courageous,courteous,creative,decisive,determined,diligent,diplomatic,discreet,dynamic,easygoing,emotional,energetic,enthusiastic,exuberant,fair-minded,faithful,fearless,forceful,frank,friendly,funny,generous,gentle,good,gregarious,hard-working,helpful,honest,humorous,imaginative,impartial,independent,intellectual,intelligent,intuitive,inventive,kind,loving,loyal,modest,neat,nice,optimistic,passionate,patient,persistent,pioneering,philosophical,placid,plucky,polite,powerful,practical,pro-active,quick-witted,quiet,rational,reliable,reserved,resourceful,romantic,self-confident,self-disciplined,sensible,sensitive,shy,sincere,sociable,straightforward,sympathetic,thoughtful,tidy,tough,unassuming,understanding,versatile,warmhearted,willing,witty".split(",");
  private final String[] animalNames = "alligator,ant,bear,bee,bird,camel,cat,cheetah,chicken,chimpanzee,cow,crocodile,deer,dog,dolphin,duck,eagle,elephant,fish,fly,fox,frog,giraffe,goat,goldfish,hamster,hippopotamus,horse,kangaroo,kitten,lion,lobster,monkey,octopus,owl,panda,pig,puppy,rabbit,rat,scorpion,seal,shark,sheep,snail,snake,spider,squirrel,tiger,turtle,wolf,zebra".split(",");

  public MassRegistrationResponse massRegister(MassRegistrationRequest massRegistrationRequest) {
    MassRegistrationResponse result = new MassRegistrationResponse();

    for (int i = 0; i < massRegistrationRequest.getAmount(); i++) {
      String generatedName = generateName();

      registrationService.registerStudent(new RegistrationRequest()
        .setFirstName(generatedName)
        .setEmail(generatedName)
        .setPassword(generatedName)
        .setBirthday(LocalDate.now()));
    }

    return result;
  }

  public String generateName() {
    return "" +
      colorNames[random.nextInt(colorNames.length)] + " " +
      adjectives[random.nextInt(adjectives.length)] + " " +
      animalNames[random.nextInt(animalNames.length)];
  }
}
