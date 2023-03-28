package GUI.controller.calendarControllers;

import BE.Event;
import GUI.controller.BaseController;
import GUI.controller.MainViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController extends BaseController implements Initializable {

    ZonedDateTime dateFocus, today;

    @FXML
    private Text year, month;

    @FXML
    private FlowPane calendar;

    private MainViewController mainViewController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        try {
            drawCalendar();
        } catch (Exception e) {
            throw new RuntimeException(e);
            //todo error message
        }
    }

    @FXML
    void backOneMonth(ActionEvent event) throws Exception {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) throws Exception {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    //todo create better calendar drawing.. does not resize and are ugly
    private void drawCalendar() throws Exception {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        //List of activities for a given month
        Map<Integer, List<Event>> calendarActivityMap = getCalendarActivitiesMonth(dateFocus);
        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                createCalendarField(j, i, dateOffset, monthMaxDate, calendarActivityMap);
            }
        }
    }

    private void createCalendarField(int j, int i, int dateOffset, int monthMaxDate, Map<Integer, List<Event>> calendarActivityMap) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/view/calendarViews/CalendarField.fxml"));
        VBox calendarBox;
        try {
            calendarBox = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int calculatedDate = (j+1)+(7*i);

        CalendarField controller = loader.getController();

        controller.setMainController(mainViewController);

        if(calculatedDate > dateOffset){
            int currentDate = calculatedDate - dateOffset;
            if(currentDate <= monthMaxDate){
                controller.setDate(String.valueOf(currentDate));
                List<Event> calendarActivities = calendarActivityMap.get(currentDate);
                if(calendarActivities != null) controller.setEvent(calendarActivities);
            }
            //if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
            //    rectangle.setStroke(Color.BLUE);//todo should be controlled from css sheet
            //}
        }
        calendar.getChildren().add(calendarBox);
    }

    private Map<Integer, List<Event>> createCalendarMap(List<Event> calendarActivities) {
        Map<Integer, List<Event>> calendarActivityMap = new HashMap<>();

        for (Event activity: calendarActivities) {
            int activityDate = activity.getDate().toInstant().atZone(ZoneId.systemDefault()).getDayOfMonth();
            if(!calendarActivityMap.containsKey(activityDate)){
                calendarActivityMap.put(activityDate, List.of(activity));
            } else {
                List<Event> OldListByDate = calendarActivityMap.get(activityDate);

                List<Event> newList = new ArrayList<>(OldListByDate);
                newList.add(activity);
                calendarActivityMap.put(activityDate, newList);
            }
        }
        return  calendarActivityMap;
    }

    private Map<Integer, List<Event>> getCalendarActivitiesMonth(ZonedDateTime dateFocus) throws Exception {
        List<Event> calendarActivities = getModelsHandler().getEventModel().getAllEvents();

        List<Event> result = new ArrayList<>();

        for (Event event: calendarActivities) {
            if(event.getDate().toInstant().atZone(ZoneId.systemDefault()).getMonth() == dateFocus.getMonth()){
                result.add(event);
            }
        }
        return createCalendarMap(result);
    }
}