package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewDto;
import at.technikumwien.lernbegleiter.services.WeeklyOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class WeeklyOverviewController extends BaseController {
  @Autowired
  private WeeklyOverviewService weeklyOverviewService;

  @GetMapping("student/{studentUuid}/weekly-overview/{calendarWeek}/{year}")
  public WeeklyOverviewDto get(
      @PathVariable String studentUuid,
      @PathVariable Integer calendarWeek,
      @PathVariable Short year) {
    return weeklyOverviewService.adaptStudentsWeeklyOverview(studentUuid, calendarWeek, year);
  }

  @PatchMapping("student/{studentUuid}/weekly-overview")
  public void patch(
      @PathVariable String studentUuid,
      @RequestBody WeeklyOverviewDto weeklyOverviewDto) {

    weeklyOverviewService.patch(studentUuid, weeklyOverviewDto);
  }
}
