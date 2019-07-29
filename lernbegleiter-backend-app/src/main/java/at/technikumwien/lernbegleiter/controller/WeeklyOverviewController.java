package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.data.dto.reflexion.WeeklyOverviewDto;
import at.technikumwien.lernbegleiter.services.WeeklyOverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Objects;

@RequestMapping("api")
@RestController
public class WeeklyOverviewController  extends BaseController{
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
