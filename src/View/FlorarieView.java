package View;

import ViewModel.FlorarieVM;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FlorarieView implements Initializable {

    @FXML private TextField txtIdFlorarie;
    @FXML private TextField txtDenumireFlorarie;
    @FXML private TextField txtAdresaFlorarie;

    @FXML private TextField txtIdFloare;
    @FXML private TextField txtDenumireFloare;
    @FXML private TextField txtImagineFloare;

    @FXML private TextField txtIdStoc;
    @FXML private TextField txtIdFlorarieStoc;
    @FXML private TextField txtIdFloareStoc;
    @FXML private TextField txtCuloareStoc;
    @FXML private TextField txtCantitateStoc;

    @FXML private TextField txtSearchFloare;
    @FXML private TextField txtFilterColor;

    @FXML private Label lblMessage;

    @FXML private ImageView imgPreview;

    @FXML private ComboBox<String> comboFlorarii;

    @FXML private TableView<List<String>> tableFlorarii;
    @FXML private TableColumn<List<String>, String> colFlorarieId;
    @FXML private TableColumn<List<String>, String> colFlorarieDenumire;
    @FXML private TableColumn<List<String>, String> colFlorarieAdresa;

    @FXML private TableView<List<String>> tableFlori;
    @FXML private TableColumn<List<String>, String> colFloareId;
    @FXML private TableColumn<List<String>, String> colFloareDenumire;
    @FXML private TableColumn<List<String>, String> colFloareImagine;

    @FXML private TableView<List<String>> tableStocuri;
    @FXML private TableColumn<List<String>, String> colStocId;
    @FXML private TableColumn<List<String>, String> colStocIdFlorarie;
    @FXML private TableColumn<List<String>, String> colStocIdFloare;
    @FXML private TableColumn<List<String>, String> colStocCuloare;
    @FXML private TableColumn<List<String>, String> colStocCantitate;

    @FXML private TableView<List<String>> tableFloriPreview;
    @FXML private TableColumn<List<String>, String> colPrevFloareId;
    @FXML private TableColumn<List<String>, String> colPrevFloareDenumire;
    @FXML private TableColumn<List<String>, String> colPrevFloareImagine;

    @FXML private TableView<List<String>> tableStocuriPreview;
    @FXML private TableColumn<List<String>, String> colPrevStocId;
    @FXML private TableColumn<List<String>, String> colPrevStocIdFlorarie;
    @FXML private TableColumn<List<String>, String> colPrevStocIdFloare;
    @FXML private TableColumn<List<String>, String> colPrevStocCuloare;
    @FXML private TableColumn<List<String>, String> colPrevStocCantitate;

    private FlorarieVM vm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vm = new FlorarieVM();

        bindFields();
        bindTables();
        bindCombo();
        initSelections();
        initImagePreview();
    }

    private void bindFields() {
        txtIdFlorarie.textProperty().bind(vm.florarieIdProperty());
        txtDenumireFlorarie.textProperty().bindBidirectional(vm.florarieDenumireProperty());
        txtAdresaFlorarie.textProperty().bindBidirectional(vm.florarieAdresaProperty());

        txtIdFloare.textProperty().bind(vm.floareIdProperty());
        txtDenumireFloare.textProperty().bindBidirectional(vm.floareDenumireProperty());
        txtImagineFloare.textProperty().bindBidirectional(vm.floareImagineProperty());

        txtIdStoc.textProperty().bind(vm.stocIdProperty());
        txtIdFlorarieStoc.textProperty().bindBidirectional(vm.stocIdFlorarieProperty());
        txtIdFloareStoc.textProperty().bindBidirectional(vm.stocIdFloareProperty());
        txtCuloareStoc.textProperty().bindBidirectional(vm.stocCuloareProperty());
        txtCantitateStoc.textProperty().bindBidirectional(vm.stocCantitateProperty());

        txtSearchFloare.textProperty().bindBidirectional(vm.searchProperty());
        txtFilterColor.textProperty().bindBidirectional(vm.filterColorProperty());

        lblMessage.textProperty().bind(vm.messageProperty());
    }

    private void bindCombo() {
        comboFlorarii.setItems(vm.getFlorariiCombo());
        comboFlorarii.valueProperty().bindBidirectional(vm.selectedFlorarieComboProperty());

        comboFlorarii.valueProperty().addListener((obs, oldVal, newVal) -> {
            vm.selectFlorarieByComboValue(newVal);
        });
    }

    private void bindTables() {
        tableFlorarii.setItems(vm.getFlorarii());
        tableFlori.setItems(vm.getFlori());
        tableStocuri.setItems(vm.getStocuri());
        tableFloriPreview.setItems(vm.getFloriPreview());
        tableStocuriPreview.setItems(vm.getStocuriPreview());

        colFlorarieId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        colFlorarieDenumire.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        colFlorarieAdresa.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));

        colFloareId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        colFloareDenumire.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        colFloareImagine.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));

        colStocId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        colStocIdFlorarie.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        colStocIdFloare.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        colStocCuloare.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        colStocCantitate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));

        colPrevFloareId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        colPrevFloareDenumire.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        colPrevFloareImagine.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));

        colPrevStocId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        colPrevStocIdFlorarie.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        colPrevStocIdFloare.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        colPrevStocCuloare.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        colPrevStocCantitate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));

        configureImageColumn(colFloareImagine, 78, 78);
        configureImageColumn(colPrevFloareImagine, 78, 78);

        tableFlori.setFixedCellSize(90);
        tableFloriPreview.setFixedCellSize(90);
        tableFlorarii.setFixedCellSize(30);
        tableStocuri.setFixedCellSize(30);
        tableStocuriPreview.setFixedCellSize(30);
    }

    private void initSelections() {
        tableFlorarii.getSelectionModel().selectedIndexProperty().addListener(
                (obs, oldVal, newVal) -> vm.selectFlorarieAt(newVal.intValue())
        );

        tableFlori.getSelectionModel().selectedIndexProperty().addListener(
                (obs, oldVal, newVal) -> vm.selectFloareAt(newVal.intValue())
        );

        tableStocuri.getSelectionModel().selectedIndexProperty().addListener(
                (obs, oldVal, newVal) -> vm.selectStocAt(newVal.intValue())
        );
    }

    private void initImagePreview() {
        imgPreview.imageProperty().bind(vm.floarePreviewImageProperty());
    }

    @FXML
    private void onAddFlorarie() {
        vm.getAddFlorarieCommand().execute();
    }

    @FXML
    private void onUpdateFlorarie() {
        vm.getUpdateFlorarieCommand().execute();
    }

    @FXML
    private void onDeleteFlorarie() {
        vm.getDeleteFlorarieCommand().execute();
    }

    @FXML
    private void onAddFloare() {
        vm.getAddFloareCommand().execute();
    }

    @FXML
    private void onUpdateFloare() {
        vm.getUpdateFloareCommand().execute();
    }

    @FXML
    private void onDeleteFloare() {
        vm.getDeleteFloareCommand().execute();
    }

    @FXML
    private void onBrowseImage() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Selecteaza imagine");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imagini", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp", "*.webp")
        );

        File file = chooser.showOpenDialog(txtImagineFloare.getScene().getWindow());
        if (file != null) {
            vm.floareImagineProperty().set(file.getAbsolutePath());
        }
    }

    private void configureImageColumn(TableColumn<List<String>, String> column, double width, double height) {
        column.setCellFactory(col -> new ImageTableCell(vm, width, height));
    }

    @FXML
    private void onAddStoc() {
        vm.getAddStocCommand().execute();
    }

    @FXML
    private void onUpdateStoc() {
        vm.getUpdateStocCommand().execute();
    }

    @FXML
    private void onDeleteStoc() {
        vm.getDeleteStocCommand().execute();
    }

    @FXML
    private void onSearchFloare() {
        vm.getSearchFloareCommand().execute();
    }

    @FXML
    private void onFilterColor() {
        vm.getFilterColorCommand().execute();
    }

    @FXML
    private void onFilterDisponibile() {
        vm.getFilterDispCommand().execute();
    }

    @FXML
    private void onRefresh() {
        vm.getRefreshCommand().execute();
    }
}