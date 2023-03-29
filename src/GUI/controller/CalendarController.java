package GUI.controller;

import BE.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class CalendarController extends BaseController implements Initializable {

    ZonedDateTime dateFocus;
    ZonedDateTime today;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    private MainViewController mainViewController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();

            drawCalendar();
    }

    public void setMainViewController(MainViewController main){
        this.mainViewController = main;
    }

    @FXML
    void backOneMonth(ActionEvent event)  {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
            drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event){
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    //todo create better calendar drawing.. does not resize and are ugly
    private void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        //List of activities for a given month
        Map<Integer, List<Event>> calendarActivityMap = null;
        try {
            calendarActivityMap = getCalendarActivitiesMonth(dateFocus);
        } catch (Exception e) {
            displayError(e);
        }

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.BLACK);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+1)+(7*i);
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        List<Event> calendarActivities = calendarActivityMap.get(currentDate);
                        if(calendarActivities != null){
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<Event> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            Event event = calendarActivities.get(k);
            if(k >= 2) {
                Text moreActivities = new Text("...");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date
                    System.out.println(calendarActivities);
                });
                break;
            }
            //todo find better date format
            Text text = null;
            if(calendarActivities.get(k).getEventName().length() < 8){
                text= new Text(calendarActivities.get(k).getEventName() + ", " + calendarActivities.get(k).getDate().getHours() + ":" + calendarActivities.get(k).getDate().getMinutes());

            }else {
                text = new Text(calendarActivities.get(k).getEventName().substring(0, 8) + ", " + calendarActivities.get(k).getDate().getHours() + ":" + calendarActivities.get(k).getDate().getMinutes());
            }
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                mainViewController.handleViewEventInMain(event);
                if(mouseEvent.getClickCount()==2) {
                    mainViewController.handleViewEvent(event);
                }
            });
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:GRAY");
        stackPane.getChildren().add(calendarActivityBox);
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