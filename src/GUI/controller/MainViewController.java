package GUI.controller;

import BE.Event;
import BLL.IEventManager;
import GUI.models.EventModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    public HBox topBar, searchHBox;
    public TextField textField;
    public ObservableList<Object> allEvents;
    public Label createEvent;
    public Label yourEvents;
    public BorderPane background;
    public ImageView listViewImage, cardViewImage, calendarView,  searchButton;
    public VBox contentArea, myEventSideBar, upcomingEventSideBar, sidebar;


    private TableView tableView;

    private EventModel eventmodel;

    TableColumn<Event, String> column1, column2, column3, column4, column5;

    private IEventManager eventManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //sets all image symbols
        createSymbolsForBtns();
        createColumnBoard();

        try {
            eventmodel = new EventModel();
            loadAllEvents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //adds the tableView to scene
        contentArea.getChildren().add(tableView);

        tableViewEventHandlers();
    }

    private void tableViewEventHandlers() {
        //Opens event info on Enter or double click
        tableView.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ENTER) && tableView.getSelectionModel().getSelectedItem() != null) {
                handleViewEvent((Event) tableView.getSelectionModel().getSelectedItem());
            }
        });
        tableView.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && tableView.getSelectionModel().getSelectedItem() != null){
                if(mouseEvent.getClickCount()==2) {
                    handleViewEvent((Event) tableView.getSelectionModel().getSelectedItem());
                }
            }
        });
    }

    private void createSymbolsForBtns() {
        Image searchSymbol = new Image("searchSymbol.png");
        searchButton.setImage(searchSymbol);

        Image calendarImage = new Image("callender.png");
        calendarView.setImage(calendarImage);

        Image listViewLogo = new Image("listView.png");
        listViewImage.setImage(listViewLogo);

        Image cardLogo = new Image("CardList.png");
        cardViewImage.setImage(cardLogo);
    }

    private void loadAllEvents() throws Exception {
        createColumnBoard();

        tableView.setItems(eventmodel.getAllEvents());
    }

    private void createColumnBoard() {
        tableView = new TableView();
        tableView.setPrefHeight(800);

        //create columns
        column1 =
                new TableColumn<>("Title");
        column1.setCellValueFactory(
                new PropertyValueFactory<>("eventName"));

        column2 =
                new TableColumn<>("location");
        column2.setCellValueFactory(
                new PropertyValueFactory<>("location"));

        column3 =
                new TableColumn<>("Max participants");
        column3.setCellValueFactory(
                new PropertyValueFactory<>("maxParticipant"));

        column4 =
                new TableColumn<>("Price");
        column4.setCellValueFactory(
                new PropertyValueFactory<>("price"));

        column5 =
                new TableColumn<>("date");
        column5.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        //adds all columns to tableView
        tableView.getColumns().addAll(column1, column2, column3, column4, column5);
    }

    public void handleCreateEvent(MouseEvent mouseEvent) {
        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/CreateEvent.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("Add new event");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        CreateEventController controller = loader.getController();
        controller.setEventModel(eventmodel);
    }

    public void handleViewEvent(Event event) {

        //Load the new stage & view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/View/EventView.fxml"));
        Parent root = null;

        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setTitle("Event information");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        EventController controller = loader.getController();
        controller.setContent(event);
    }

    public void handleTicket(ActionEvent actionEvent) throws IOException, DocumentException {

        String src = "resourses/temp.pdf";

        PdfWriter writer = new PdfWriter(src);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph("Hello World!"));
        document.close();

    }
}
