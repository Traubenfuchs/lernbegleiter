package at.technikumwien.lernbegleiter.services.massregistration;

import at.technikumwien.lernbegleiter.data.dto.*;
import at.technikumwien.lernbegleiter.data.dto.converter.massregistration.*;
import at.technikumwien.lernbegleiter.data.requests.*;
import at.technikumwien.lernbegleiter.entities.massregistration.*;
import at.technikumwien.lernbegleiter.repositories.auth.*;
import at.technikumwien.lernbegleiter.repositories.massregistration.*;
import at.technikumwien.lernbegleiter.services.user.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.security.*;
import java.time.*;
import java.util.*;

@Transactional
@AllArgsConstructor
@Service
public class MassRegistrationService {
  private final SecureRandom random = new SecureRandom();
  private final MassRegistrationRepository massRegistrationRepository;
  private final MassRegistrationConverter massRegistrationConverter;
  private final RegistrationService registrationService;
  private final UserRepository userRepository;

  private final String[] colorNames = "Red,Orange,Yellow,Green,Blue,Purple,Brown,Magenta,Tan,Cyan,Olive,Maroon,Navy,Aquamarine,Turquoise,Silver,Lime,Teal,Indigo,Violet,Pink,Black,White,Gray,Grey".toLowerCase().split(",");
  private final String[] adjectives = "affable,affectionate,agreeable,ambitious,amiable,amicable,amusing,brave,bright,broad-minded,calm,careful,charming,communicative,compassionate,conscientious,considerate,convivial,courageous,courteous,creative,decisive,determined,diligent,diplomatic,discreet,dynamic,easygoing,emotional,energetic,enthusiastic,exuberant,fair-minded,faithful,fearless,forceful,frank,friendly,funny,generous,gentle,good,gregarious,hard-working,helpful,honest,humorous,imaginative,impartial,independent,intellectual,intelligent,intuitive,inventive,kind,loving,loyal,modest,neat,nice,optimistic,passionate,patient,persistent,pioneering,philosophical,placid,plucky,polite,powerful,practical,pro-active,quick-witted,quiet,rational,reliable,reserved,resourceful,romantic,self-confident,self-disciplined,sensible,sensitive,shy,sincere,sociable,straightforward,sympathetic,thoughtful,tidy,tough,unassuming,understanding,versatile,warmhearted,willing,witty".split(",");
  private final String[] animalNames = "alligator,ant,bear,bee,bird,camel,cat,cheetah,chicken,chimpanzee,cow,crocodile,deer,dog,dolphin,duck,eagle,elephant,fish,fly,fox,frog,giraffe,goat,goldfish,hamster,hippopotamus,horse,kangaroo,kitten,lion,lobster,monkey,octopus,owl,panda,pig,puppy,rabbit,rat,scorpion,seal,shark,sheep,snail,snake,spider,squirrel,tiger,turtle,wolf,zebra".split(",");

  public MassRegistrationDto massRegister(MassRegistrationDto massRegistrationDto) {
    MassRegistrationEntity massRegistrationEntity = massRegistrationConverter.toEntity(massRegistrationDto);

    for (int i = 0; i < massRegistrationDto.getAmount(); i++) {
      String generatedName = generateName();

      String createdUserUuid = registrationService.registerStudent(new RegistrationRequest()
        .setFirstName(generatedName)
        .setEmail(generatedName)
        .setPassword(generatedName)
        .setFamilyName("g")
        .setBirthday(LocalDate.now())).getUuid();

      massRegistrationEntity.addChild(new MassRegistrationEntryEntity()
        .setPassword(generatedName)
        .setSecret(generatedName)
        .setUsername(generatedName)
        .setCreatedUser(userRepository.getOne(createdUserUuid))
      );
    }

    massRegistrationEntity = massRegistrationRepository.save(massRegistrationEntity);

    return massRegistrationConverter.toDTO(massRegistrationEntity);
  }

  public String generateName() {
    return "" +
      colorNames[random.nextInt(colorNames.length)] + " " +
      adjectives[random.nextInt(adjectives.length)] + " " +
      animalNames[random.nextInt(animalNames.length)];
  }

  public MassRegistrationDto get(String massRegistrationUUID) {
    return massRegistrationConverter.toDTO(massRegistrationRepository.getOne(massRegistrationUUID));
  }

  public Set<MassRegistrationDto> getAll() {
    return massRegistrationConverter.toDtoSet(massRegistrationRepository.findAll());
  }

  public void patch(String uuid, MassRegistrationDto massRegistrationDto) {
    massRegistrationConverter.applyToEntityCustom(massRegistrationDto, massRegistrationRepository.getOne(uuid));
  }

  public void delete(String massRegistrationUUID) {
    MassRegistrationEntity massRegistrationEntity = massRegistrationRepository.getOne(massRegistrationUUID);
    //massRegistrationEntity.getChildren().forEach();
    massRegistrationRepository.delete(massRegistrationEntity);
  }
}
