package GUI.controller;

import BE.Event;
import BLL.IEventManager;
import GUI.models.EventModel;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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

import java.io.FileOutputStream;
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











    private static String FILE = "resourses/temp.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);

    public void handleTicket(ActionEvent actionEvent) throws IOException, DocumentException {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("Ticketttt", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        //preface.add(new Paragraph(
        //        "Report generated by: " + System.getProperty("JennyFtb") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        //        smallBold));
        addEmptyLine(preface, 3);
        preface.add(new Paragraph(
                "gæt lige hvem der kan lave pdf filer fra java Bitches  ",
                smallBold));

        addEmptyLine(preface, 8);

        preface.add(new Paragraph(
                "fuuucking Jenny From The Block ",
                redFont));

        document.add(preface);
        // Start a new page
        document.newPage();
    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("First Chapter", catFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("fkjepooooooooooo3ejfeeeeeeeeeeeeeeeeeeeeeeee"));

        // now add all this to the document
        document.add(catPart);

    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    private static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }
}
