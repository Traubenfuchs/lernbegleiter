package at.technikumwien.lernbegleiter.services;

import at.technikumwien.lernbegleiter.repositories.reflection.WeeklyOverviewClassDayRepository;
import at.technikumwien.lernbegleiter.repositories.reflection.WeeklyOverviewClassRepository;
import at.technikumwien.lernbegleiter.repositories.reflection.WeeklyOverviewReflectionClassRepository;
import at.technikumwien.lernbegleiter.repositories.reflection.WeeklyOverviewReflectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReflectionService {
    @Autowired
    private WeeklyOverviewClassDayRepository weeklyOverviewClassDayRepository;
    @Autowired
    private WeeklyOverviewClassRepository weeklyOverviewClassRepository;
    @Autowired
    private WeeklyOverviewReflectionClassRepository weeklyOverviewReflectionClassRepository;
    @Autowired
    private WeeklyOverviewReflectionRepository weeklyOverviewReflectionRepository;
}
