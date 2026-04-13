package ViewModel;

import Model.Floare;
import Model.Florarie;
import Model.Repository.FloareRepository;
import Model.Repository.FlorarieRepository;
import Model.Repository.StocRepository;
import Model.Stoc;
import ViewModel.Commands.RelayCommand;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;

import java.io.File;
import java.net.URL;

import java.util.List;

public class FlorarieVM {

    private final FlorarieRepository florarieRepository;
    private final StocRepository stocRepository;
    private final FloareRepository floareRepository;

    private Florarie selectedFlorarie;
    private Floare selectedFloare;
    private Stoc selectedStoc;

    private final StringProperty message = new SimpleStringProperty("Ready");

    private final StringProperty search = new SimpleStringProperty("");
    private final StringProperty filterColor = new SimpleStringProperty("");

    private final StringProperty florarieId = new SimpleStringProperty("");
    private final StringProperty florarieDenumire = new SimpleStringProperty("");
    private final StringProperty florarieAdresa = new SimpleStringProperty("");

    private final StringProperty floareId = new SimpleStringProperty("");
    private final StringProperty floareDenumire = new SimpleStringProperty("");
    private final StringProperty floareImagine = new SimpleStringProperty("");

    private final StringProperty stocId = new SimpleStringProperty("");
    private final StringProperty stocIdFlorarie = new SimpleStringProperty("");
    private final StringProperty stocIdFloare = new SimpleStringProperty("");
    private final StringProperty stocCuloare = new SimpleStringProperty("");
    private final StringProperty stocCantitate = new SimpleStringProperty("");

    private final StringProperty selectedFlorarieCombo = new SimpleStringProperty("");

    private final ObservableList<List<String>> florarii = FXCollections.observableArrayList();
    private final ObservableList<List<String>> flori = FXCollections.observableArrayList();
    private final ObservableList<List<String>> stocuri = FXCollections.observableArrayList();

    private final ObservableList<List<String>> floriPreview = FXCollections.observableArrayList();
    private final ObservableList<List<String>> stocuriPreview = FXCollections.observableArrayList();

    private final ObservableList<String> florariiCombo = FXCollections.observableArrayList();

    private final RelayCommand addFlorarieCommand;
    private final RelayCommand updateFlorarieCommand;
    private final RelayCommand deleteFlorarieCommand;

    private final RelayCommand addFloareCommand;
    private final RelayCommand updateFloareCommand;
    private final RelayCommand deleteFloareCommand;

    private final RelayCommand addStocCommand;
    private final RelayCommand updateStocCommand;
    private final RelayCommand deleteStocCommand;

    private final RelayCommand searchFloareCommand;
    private final RelayCommand filterColorCommand;
    private final RelayCommand filterDispCommand;
    private final RelayCommand refreshCommand;

    private final ObjectProperty<Image> floarePreviewImage = new SimpleObjectProperty<>(null);
    private final StringProperty floarePreviewText = new SimpleStringProperty("Preview imagine");

    public FlorarieVM() {
        florarieRepository = new FlorarieRepository();
        stocRepository = new StocRepository();
        floareRepository = new FloareRepository();

        addFlorarieCommand = new RelayCommand(this::addFlorarie);
        updateFlorarieCommand = new RelayCommand(this::updateFlorarie);
        deleteFlorarieCommand = new RelayCommand(this::deleteFlorarie);

        addFloareCommand = new RelayCommand(this::addFloare);
        updateFloareCommand = new RelayCommand(this::updateFloare);
        deleteFloareCommand = new RelayCommand(this::deleteFloare);

        addStocCommand = new RelayCommand(this::addStoc);
        updateStocCommand = new RelayCommand(this::updateStoc);
        deleteStocCommand = new RelayCommand(this::deleteStoc);

        searchFloareCommand = new RelayCommand(this::searchFloare);
        filterColorCommand = new RelayCommand(this::filterFloriCuloare);
        filterDispCommand = new RelayCommand(this::filterFloriDisp);
        refreshCommand = new RelayCommand(this::refreshAll);

        floareImagine.addListener((obs, oldVal, newVal) -> updatePreviewImage(newVal));

        refreshAll();
    }

    public void refreshAll() {
        loadFlorarii();
        loadFlori();
        loadStocuri();
        loadFlorariiCombo();

        selectedFlorarie = null;
        selectedFloare = null;
        selectedStoc = null;

        clearFlorarieFields();
        clearFloareFields();
        clearStocFields();

        search.set("");
        filterColor.set("");
        selectedFlorarieCombo.set("");
        message.set("Datele au fost actualizate.");
    }

    public void selectFlorarieAt(int row) {
        if (row < 0 || row >= florarii.size()) {
            return;
        }

        List<String> data = florarii.get(row);

        selectedFlorarie = new Florarie();
        selectedFlorarie.setIdFlorarie(parseInt(data.get(0)));
        selectedFlorarie.setDenumire(data.get(1));
        selectedFlorarie.setAdresa(data.get(2));

        florarieId.set(data.get(0));
        florarieDenumire.set(data.get(1));
        florarieAdresa.set(data.get(2));
    }

    public void selectFloareAt(int row) {
        if (row < 0 || row >= flori.size()) {
            return;
        }

        List<String> data = flori.get(row);

        selectedFloare = new Floare();
        selectedFloare.setIdFloare(parseInt(data.get(0)));
        selectedFloare.setDenumire(data.get(1));
        selectedFloare.setImagine(data.get(2));

        floareId.set(data.get(0));
        floareDenumire.set(data.get(1));
        floareImagine.set(data.get(2));
    }

    public void selectStocAt(int row) {
        if (row < 0 || row >= stocuri.size()) {
            return;
        }

        List<String> data = stocuri.get(row);

        selectedStoc = new Stoc();
        selectedStoc.setIdStoc(parseInt(data.get(0)));
        selectedStoc.setIdFlorarie(parseInt(data.get(1)));
        selectedStoc.setIdFloare(parseInt(data.get(2)));
        selectedStoc.setCuloare(data.get(3));
        selectedStoc.setStoc(parseInt(data.get(4)));

        stocId.set(data.get(0));
        stocIdFlorarie.set(data.get(1));
        stocIdFloare.set(data.get(2));
        stocCuloare.set(data.get(3));
        stocCantitate.set(data.get(4));
    }

    public void selectFlorarieByComboValue(String value) {
        if (value == null || value.isBlank()) {
            selectedFlorarie = null;
            return;
        }

        List<Florarie> list = florarieRepository.getFlorarii();
        for (Florarie f : list) {
            String comboValue = f.getIdFlorarie() + " - " + f.getDenumire();
            if (comboValue.equals(value)) {
                selectedFlorarie = f;
                florarieId.set(String.valueOf(f.getIdFlorarie()));
                florarieDenumire.set(f.getDenumire());
                florarieAdresa.set(f.getAdresa());
                break;
            }
        }
    }

    public void addFlorarie() {
        if (florarieDenumire.get().isBlank() || florarieAdresa.get().isBlank()) {
            message.set("Completeaza denumirea si adresa florariei.");
            return;
        }

        Florarie florarie = new Florarie();
        florarie.setDenumire(florarieDenumire.get());
        florarie.setAdresa(florarieAdresa.get());

        florarieRepository.addFlorarie(florarie);
        loadFlorarii();
        loadFlorariiCombo();
        clearFlorarieFields();
        selectedFlorarie = null;
        message.set("Florarie adaugata cu succes.");
    }

    public void updateFlorarie() {
        if (selectedFlorarie == null) {
            message.set("Selecteaza o florarie pentru actualizare.");
            return;
        }

        selectedFlorarie.setDenumire(florarieDenumire.get());
        selectedFlorarie.setAdresa(florarieAdresa.get());

        florarieRepository.updateFlorarie(selectedFlorarie);
        loadFlorarii();
        loadFlorariiCombo();
        florarieId.set(String.valueOf(selectedFlorarie.getIdFlorarie()));
        message.set("Florarie actualizata cu succes.");
    }

    public void deleteFlorarie() {
        if (selectedFlorarie == null) {
            message.set("Selecteaza o florarie pentru stergere.");
            return;
        }

        florarieRepository.deleteFlorarie(selectedFlorarie.getIdFlorarie());
        loadFlorarii();
        loadFlorariiCombo();
        clearFlorarieFields();
        selectedFlorarie = null;
        message.set("Florarie stearsa cu succes.");
    }

    public void addFloare() {
        if (floareDenumire.get().isBlank() || floareImagine.get().isBlank()) {
            message.set("Completeaza denumirea si calea imaginii pentru floare.");
            return;
        }

        Floare floare = new Floare();
        floare.setDenumire(floareDenumire.get());
        floare.setImagine(floareImagine.get());

        floareRepository.addFloare(floare);
        loadFlori();
        clearFloareFields();
        selectedFloare = null;
        message.set("Floare adaugata cu succes.");
    }

    public void updateFloare() {
        if (selectedFloare == null) {
            message.set("Selecteaza o floare pentru actualizare.");
            return;
        }

        selectedFloare.setDenumire(floareDenumire.get());
        selectedFloare.setImagine(floareImagine.get());

        floareRepository.updateFloare(selectedFloare);
        loadFlori();
        floareId.set(String.valueOf(selectedFloare.getIdFloare()));
        message.set("Floare actualizata cu succes.");
    }

    public void deleteFloare() {
        if (selectedFloare == null) {
            message.set("Selecteaza o floare pentru stergere.");
            return;
        }

        floareRepository.deleteFloare(selectedFloare.getIdFloare());
        loadFlori();
        clearFloareFields();
        selectedFloare = null;
        message.set("Floare stearsa cu succes.");
    }

    public void addStoc() {
        if (stocIdFlorarie.get().isBlank() || stocIdFloare.get().isBlank() ||
                stocCuloare.get().isBlank() || stocCantitate.get().isBlank()) {
            message.set("Completeaza toate campurile pentru stoc.");
            return;
        }

        Stoc stoc = new Stoc();
        stoc.setIdFlorarie(parseInt(stocIdFlorarie.get()));
        stoc.setIdFloare(parseInt(stocIdFloare.get()));
        stoc.setCuloare(stocCuloare.get());
        stoc.setStoc(parseInt(stocCantitate.get()));

        stocRepository.addStoc(stoc);
        loadStocuri();
        clearStocFields();
        selectedStoc = null;
        message.set("Stoc adaugat cu succes.");
    }

    public void updateStoc() {
        if (selectedStoc == null) {
            message.set("Selecteaza un rand de stoc pentru actualizare.");
            return;
        }

        selectedStoc.setIdFlorarie(parseInt(stocIdFlorarie.get()));
        selectedStoc.setIdFloare(parseInt(stocIdFloare.get()));
        selectedStoc.setCuloare(stocCuloare.get());
        selectedStoc.setStoc(parseInt(stocCantitate.get()));

        stocRepository.updateStoc(selectedStoc);
        loadStocuri();
        stocId.set(String.valueOf(selectedStoc.getIdStoc()));
        message.set("Stoc actualizat cu succes.");
    }

    public void deleteStoc() {
        if (selectedStoc == null) {
            message.set("Selecteaza un rand de stoc pentru stergere.");
            return;
        }

        stocRepository.deleteStoc(selectedStoc.getIdStoc());
        loadStocuri();
        clearStocFields();
        selectedStoc = null;
        message.set("Stoc sters cu succes.");
    }

    public void searchFloare() {
        if (search.get().isBlank()) {
            message.set("Introdu denumirea florii cautate.");
            return;
        }

        Floare floareGasita = floareRepository.searchFloare(search.get());

        floriPreview.clear();
        stocuriPreview.clear();

        if (floareGasita == null) {
            message.set("Nu a fost gasita nicio floare.");
            return;
        }

        floriPreview.add(List.of(
                String.valueOf(floareGasita.getIdFloare()),
                floareGasita.getDenumire(),
                safe(floareGasita.getImagine())
        ));

        floareId.set(String.valueOf(floareGasita.getIdFloare()));
        floareDenumire.set(floareGasita.getDenumire());
        floareImagine.set(safe(floareGasita.getImagine()));

        List<Stoc> variante = stocRepository.getStocFlorari(floareGasita.getIdFloare());
        fillPreviewStocuri(variante);

        message.set("Cautare realizata cu succes.");
    }

    public void filterFloriCuloare() {
        if (selectedFlorarie == null) {
            message.set("Selecteaza o florarie pentru filtrare.");
            return;
        }

        if (filterColor.get().isBlank()) {
            message.set("Introdu culoarea pentru filtrare.");
            return;
        }

        List<Stoc> rezultat = stocRepository.getStocDupaFlorarieSiCuloare(
                selectedFlorarie.getIdFlorarie(),
                filterColor.get()
        );

        fillPreviewStocuri(rezultat);
        message.set("Filtrare dupa culoare realizata cu succes.");
    }

    public void filterFloriDisp() {
        if (selectedFlorarie == null) {
            message.set("Selecteaza o florarie pentru filtrare.");
            return;
        }

        List<Stoc> rezultat = stocRepository.getStocDisponibilDinFlorarie(selectedFlorarie.getIdFlorarie());
        fillPreviewStocuri(rezultat);
        message.set("Filtrare dupa disponibilitate realizata cu succes.");
    }

    public void loadFlorarii() {
        florarii.clear();

        List<Florarie> list = florarieRepository.getFlorarii();
        for (Florarie florarie : list) {
            florarii.add(List.of(
                    String.valueOf(florarie.getIdFlorarie()),
                    safe(florarie.getDenumire()),
                    safe(florarie.getAdresa())
            ));
        }
    }

    public void loadFlori() {
        flori.clear();

        List<Floare> list = floareRepository.getFloriSortedByDenumire();
        for (Floare floare : list) {
            flori.add(List.of(
                    String.valueOf(floare.getIdFloare()),
                    safe(floare.getDenumire()),
                    safe(floare.getImagine())
            ));
        }
    }

    public void loadStocuri() {
        stocuri.clear();

        List<Stoc> list = stocRepository.getStocuri();
        for (Stoc stoc : list) {
            stocuri.add(List.of(
                    String.valueOf(stoc.getIdStoc()),
                    String.valueOf(stoc.getIdFlorarie()),
                    String.valueOf(stoc.getIdFloare()),
                    safe(stoc.getCuloare()),
                    String.valueOf(stoc.getStoc())
            ));
        }
    }

    private void fillPreviewStocuri(List<Stoc> list) {
        stocuriPreview.clear();

        for (Stoc stoc : list) {
            stocuriPreview.add(List.of(
                    String.valueOf(stoc.getIdStoc()),
                    String.valueOf(stoc.getIdFlorarie()),
                    String.valueOf(stoc.getIdFloare()),
                    safe(stoc.getCuloare()),
                    String.valueOf(stoc.getStoc())
            ));
        }
    }

    public void loadFlorariiCombo() {
        florariiCombo.clear();

        florarieRepository.getFlorarii()
                .stream()
                .map(f -> f.getIdFlorarie() + " - " + f.getDenumire())
                .forEach(florariiCombo::add);
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private void updatePreviewImage(String path) {
        Image image = resolveImage(path);
        floarePreviewImage.set(image);
        floarePreviewText.set(image == null ? "Preview imagine" : "");
    }

    private Image resolveImage(String path) {
        if (path == null || path.trim().isEmpty()) {
            return null;
        }

        String cleanPath = path.trim();

        try {
            File[] options = new File[] {
                    new File(cleanPath),
                    new File("images/" + cleanPath),
                    new File("src/" + cleanPath),
                    new File("src/images/" + cleanPath)
            };

            for (File file : options) {
                if (file.exists()) {
                    return new Image(file.toURI().toString(), true);
                }
            }

            URL resource = getClass().getClassLoader().getResource(cleanPath);
            if (resource != null) {
                return new Image(resource.toExternalForm(), true);
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    public Image getImageForPath(String path) {
        return resolveImage(path);
    }

    private void clearFlorarieFields() {
        florarieId.set("");
        florarieDenumire.set("");
        florarieAdresa.set("");
    }

    private void clearFloareFields() {
        floareId.set("");
        floareDenumire.set("");
        floareImagine.set("");
    }

    private void clearStocFields() {
        stocId.set("");
        stocIdFlorarie.set("");
        stocIdFloare.set("");
        stocCuloare.set("");
        stocCantitate.set("");
    }

    public StringProperty messageProperty() { return message; }

    public StringProperty searchProperty() { return search; }
    public StringProperty filterColorProperty() { return filterColor; }

    public StringProperty florarieIdProperty() { return florarieId; }
    public StringProperty florarieDenumireProperty() { return florarieDenumire; }
    public StringProperty florarieAdresaProperty() { return florarieAdresa; }

    public StringProperty floareIdProperty() { return floareId; }
    public StringProperty floareDenumireProperty() { return floareDenumire; }
    public StringProperty floareImagineProperty() { return floareImagine; }

    public StringProperty stocIdProperty() { return stocId; }
    public StringProperty stocIdFlorarieProperty() { return stocIdFlorarie; }
    public StringProperty stocIdFloareProperty() { return stocIdFloare; }
    public StringProperty stocCuloareProperty() { return stocCuloare; }
    public StringProperty stocCantitateProperty() { return stocCantitate; }

    public StringProperty selectedFlorarieComboProperty() { return selectedFlorarieCombo; }

    public ObservableList<List<String>> getFlorarii() { return florarii; }
    public ObservableList<List<String>> getFlori() { return flori; }
    public ObservableList<List<String>> getStocuri() { return stocuri; }
    public ObservableList<List<String>> getFloriPreview() { return floriPreview; }
    public ObservableList<List<String>> getStocuriPreview() { return stocuriPreview; }
    public ObservableList<String> getFlorariiCombo() { return florariiCombo; }

    public RelayCommand getAddFlorarieCommand() { return addFlorarieCommand; }
    public RelayCommand getUpdateFlorarieCommand() { return updateFlorarieCommand; }
    public RelayCommand getDeleteFlorarieCommand() { return deleteFlorarieCommand; }

    public RelayCommand getAddFloareCommand() { return addFloareCommand; }
    public RelayCommand getUpdateFloareCommand() { return updateFloareCommand; }
    public RelayCommand getDeleteFloareCommand() { return deleteFloareCommand; }

    public RelayCommand getAddStocCommand() { return addStocCommand; }
    public RelayCommand getUpdateStocCommand() { return updateStocCommand; }
    public RelayCommand getDeleteStocCommand() { return deleteStocCommand; }

    public RelayCommand getSearchFloareCommand() { return searchFloareCommand; }
    public RelayCommand getFilterColorCommand() { return filterColorCommand; }
    public RelayCommand getFilterDispCommand() { return filterDispCommand; }
    public RelayCommand getRefreshCommand() { return refreshCommand; }
    public ObjectProperty<Image> floarePreviewImageProperty() {
        return floarePreviewImage;
    }

    public StringProperty floarePreviewTextProperty() {
        return floarePreviewText;
    }
}