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
public class WeeklyOverviewController {
    @Autowired
    private WeeklyOverviewService weeklyOverviewService;

    @GetMapping("student/{studentUuid}/weekly-overview/{calendarWeek}")
    public WeeklyOverviewDto get(@PathVariable String studentUuid, @PathVariable Integer calendarWeek) {
        return weeklyOverviewService.adaptStudentsWeeklyOverview(studentUuid, calendarWeek);
    }

    @PatchMapping("student/{studentUui}/weekly-overview/{calendarWeek}")
    public void patch(
            @PathVariable String studentUuid,
            @PathVariable Integer calendarWeek,
            @RequestBody WeeklyOverviewDto weeklyOverviewDto) {
        if(!Objects.equals(calendarWeek, weeklyOverviewDto.getCalendarWeek())) {
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "" +
                    "CalendarWeek in path (<" +
                    calendarWeek +
                    ">) does not match calendarWeek in requestBody (<" +
                    weeklyOverviewDto.getCalendarWeek() +
                    ">)");
        }

        weeklyOverviewService.patch(studentUuid, weeklyOverviewDto);
    }
}
