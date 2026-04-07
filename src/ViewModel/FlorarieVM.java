package ViewModel;

import Model.Floare;
import Model.Florarie;
import Model.Repository.FloareRepository;
import Model.Repository.FlorarieRepository;
import Model.Repository.StocRepository;
import Model.Stoc;
import ViewModel.Commands.FlorarieCommands;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class FlorarieVM {

    private final FlorarieRepository florarieRepository;
    private final StocRepository stocRepository;
    private final FloareRepository floareRepository;

    private Florarie currentFlorarie;
    private Floare currentFloare;
    private Stoc currentStoc;

    private Florarie selectedFlorarie;
    private Floare selectedFloare;
    private Stoc selectedStoc;

    private String message;
    private String search;
    private String filterColor;

    private String florarieId;
    private String florarieDenumire;
    private String florarieAdresa;

    private String floareId;
    private String floareDenumire;
    private String floareImagine;

    private String stocId;
    private String stocIdFlorarie;
    private String stocIdFloare;
    private String stocCuloare;
    private String stocCantitate;

    private final DefaultTableModel florariiTableModel;
    private final DefaultTableModel floriTableModel;
    private final DefaultTableModel stocuriTableModel;

    private final FlorarieCommands addFloareCommand;
    private final FlorarieCommands updateFloareCommand;
    private final FlorarieCommands deleteFloareCommand;

    private final FlorarieCommands addFlorarieCommand;
    private final FlorarieCommands updateFlorarieCommand;
    private final FlorarieCommands deleteFlorarieCommand;

    private final FlorarieCommands addStocCommand;
    private final FlorarieCommands updateStocCommand;
    private final FlorarieCommands deleteStocCommand;

    private final FlorarieCommands searchFloareCommand;
    private final FlorarieCommands filterColorCommand;
    private final FlorarieCommands filterDispCommand;

    public FlorarieVM() {
        florarieRepository = new FlorarieRepository();
        stocRepository = new StocRepository();
        floareRepository = new FloareRepository();

        currentFlorarie = new Florarie();
        currentFloare = new Floare();
        currentStoc = new Stoc();

        selectedFlorarie = null;
        selectedFloare = null;
        selectedStoc = null;

        message = "Ready";
        search = "";
        filterColor = "";

        florarieId = "";
        florarieDenumire = "";
        florarieAdresa = "";

        floareId = "";
        floareDenumire = "";
        floareImagine = "";

        stocId = "";
        stocIdFlorarie = "";
        stocIdFloare = "";
        stocCuloare = "";
        stocCantitate = "";

        florariiTableModel = new DefaultTableModel(new Object[]{"ID", "Denumire", "Adresa"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        floriTableModel = new DefaultTableModel(new Object[]{"ID", "Denumire", "Imagine"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        stocuriTableModel = new DefaultTableModel(new Object[]{"ID Stoc", "ID Florarie", "ID Floare", "Culoare", "Stoc"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        addFloareCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.ADD_FLOARE);
        updateFloareCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.UPDATE_FLOARE);
        deleteFloareCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.DELETE_FLOARE);

        addFlorarieCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.ADD_FLORARIE);
        updateFlorarieCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.UPDATE_FLORARIE);
        deleteFlorarieCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.DELETE_FLORARIE);

        addStocCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.ADD_STOC);
        updateStocCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.UPDATE_STOC);
        deleteStocCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.DELETE_STOC);

        searchFloareCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.SEARCH_DENUMIRE);
        filterColorCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.FILTER_COLOR);
        filterDispCommand = new FlorarieCommands(this, FlorarieCommands.CommandType.FILTER_DISP);

        refreshAll();
    }

    public FlorarieCommands getAddFloareCommand() {
        return addFloareCommand;
    }

    public FlorarieCommands getUpdateFloareCommand() {
        return updateFloareCommand;
    }

    public FlorarieCommands getDeleteFloareCommand() {
        return deleteFloareCommand;
    }

    public FlorarieCommands getAddFlorarieCommand() {
        return addFlorarieCommand;
    }

    public FlorarieCommands getUpdateFlorarieCommand() {
        return updateFlorarieCommand;
    }

    public FlorarieCommands getDeleteFlorarieCommand() {
        return deleteFlorarieCommand;
    }

    public FlorarieCommands getAddStocCommand() {
        return addStocCommand;
    }

    public FlorarieCommands getUpdateStocCommand() {
        return updateStocCommand;
    }

    public FlorarieCommands getDeleteStocCommand() {
        return deleteStocCommand;
    }

    public FlorarieCommands getSearchFloareCommand() {
        return searchFloareCommand;
    }

    public FlorarieCommands getFilterColorCommand() {
        return filterColorCommand;
    }

    public FlorarieCommands getFilterDispCommand() {
        return filterDispCommand;
    }

    public DefaultTableModel getFlorariiTableModel() {
        return florariiTableModel;
    }

    public DefaultTableModel getFloriTableModel() {
        return floriTableModel;
    }

    public DefaultTableModel getStocuriTableModel() {
        return stocuriTableModel;
    }

    public String getMessage() {
        return message;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = safe(search);
    }

    public String getFilterColor() {
        return filterColor;
    }

    public void setFilterColor(String filterColor) {
        this.filterColor = safe(filterColor);
    }

    public String getFlorarieId() {
        return florarieId;
    }

    public String getFlorarieDenumire() {
        return florarieDenumire;
    }
    public void setFlorarieDenumire(String florarieDenumire) {
        this.florarieDenumire = safe(florarieDenumire);
    }

    public String getFlorarieAdresa() {
        return florarieAdresa;
    }

    public void setFlorarieAdresa(String florarieAdresa) {
        this.florarieAdresa = safe(florarieAdresa);
    }

    public String getFloareId() {
        return floareId;
    }

    public String getFloareDenumire() {
        return floareDenumire;
    }
    public void setFloareDenumire(String floareDenumire) {
        this.floareDenumire = safe(floareDenumire);
    }

    public String getFloareImagine() {
        return floareImagine;
    }
    public void setFloareImagine(String floareImagine) {
        this.floareImagine = safe(floareImagine);
    }

    public String getStocId() {
        return stocId;
    }

    public String getStocIdFlorarie() {
        return stocIdFlorarie;
    }

    public void setStocIdFlorarie(String stocIdFlorarie) {
        this.stocIdFlorarie = safe(stocIdFlorarie);
    }

    public String getStocIdFloare() {
        return stocIdFloare;
    }

    public void setStocIdFloare(String stocIdFloare) {
        this.stocIdFloare = safe(stocIdFloare);
    }

    public String getStocCuloare() {
        return stocCuloare;
    }

    public void setStocCuloare(String stocCuloare) {
        this.stocCuloare = safe(stocCuloare);
    }

    public String getStocCantitate() {
        return stocCantitate;
    }

    public void setStocCantitate(String stocCantitate) {
        this.stocCantitate = safe(stocCantitate);
    }

    public void refreshAll() {
        loadFlorarii();
        loadFlori();
        loadStocuri();

        selectedFlorarie = null;
        selectedFloare = null;
        selectedStoc = null;

        clearFlorarieFields();
        clearFloareFields();
        clearStocFields();

        search = "";
        filterColor = "";
        message = "Datele au fost actualizate.";
    }

    public void selectFlorarieAt(int row) {
        if (row < 0 || row >= florariiTableModel.getRowCount()) {
            return;
        }

        selectedFlorarie = new Florarie();
        selectedFlorarie.setIdFlorarie((Integer) florariiTableModel.getValueAt(row, 0));
        selectedFlorarie.setDenumire(String.valueOf(florariiTableModel.getValueAt(row, 1)));
        selectedFlorarie.setAdresa(String.valueOf(florariiTableModel.getValueAt(row, 2)));

        florarieId = String.valueOf(selectedFlorarie.getIdFlorarie());
        florarieDenumire = selectedFlorarie.getDenumire();
        florarieAdresa = selectedFlorarie.getAdresa();
    }

    public void selectFloareAt(int row) {
        if (row < 0 || row >= floriTableModel.getRowCount()) {
            return;
        }

        selectedFloare = new Floare();
        selectedFloare.setIdFloare((Integer) floriTableModel.getValueAt(row, 0));
        selectedFloare.setDenumire(String.valueOf(floriTableModel.getValueAt(row, 1)));
        selectedFloare.setImagine(String.valueOf(floriTableModel.getValueAt(row, 2)));

        floareId = String.valueOf(selectedFloare.getIdFloare());
        floareDenumire = selectedFloare.getDenumire();
        floareImagine = selectedFloare.getImagine();
    }

    public void selectStocAt(int row) {
        if (row < 0 || row >= stocuriTableModel.getRowCount()) {
            return;
        }

        selectedStoc = new Stoc();
        selectedStoc.setIdStoc((Integer) stocuriTableModel.getValueAt(row, 0));
        selectedStoc.setIdFlorarie((Integer) stocuriTableModel.getValueAt(row, 1));
        selectedStoc.setIdFloare((Integer) stocuriTableModel.getValueAt(row, 2));
        selectedStoc.setCuloare(String.valueOf(stocuriTableModel.getValueAt(row, 3)));
        selectedStoc.setStoc((Integer) stocuriTableModel.getValueAt(row, 4));

        stocId = String.valueOf(selectedStoc.getIdStoc());
        stocIdFlorarie = String.valueOf(selectedStoc.getIdFlorarie());
        stocIdFloare = String.valueOf(selectedStoc.getIdFloare());
        stocCuloare = selectedStoc.getCuloare();
        stocCantitate = String.valueOf(selectedStoc.getStoc());
    }

    public void addFlorarie() {
        if (florarieDenumire.isBlank() || florarieAdresa.isBlank()) {
            message = "Completeaza denumirea si adresa florariei.";
            return;
        }

        currentFlorarie = new Florarie();
        currentFlorarie.setDenumire(florarieDenumire);
        currentFlorarie.setAdresa(florarieAdresa);

        florarieRepository.addFlorarie(currentFlorarie);
        loadFlorarii();
        clearFlorarieFields();
        selectedFlorarie = null;
        message = "Florarie adaugata cu succes.";
    }

    public void updateFlorarie() {
        if (selectedFlorarie == null) {
            message = "Selecteaza o florarie pentru actualizare.";
            return;
        }

        selectedFlorarie.setDenumire(florarieDenumire);
        selectedFlorarie.setAdresa(florarieAdresa);

        florarieRepository.updateFlorarie(selectedFlorarie);
        loadFlorarii();
        florarieId = String.valueOf(selectedFlorarie.getIdFlorarie());
        message = "Florarie actualizata cu succes.";
    }

    public void deleteFlorarie() {
        if (selectedFlorarie == null) {
            message = "Selecteaza o florarie pentru stergere.";
            return;
        }

        florarieRepository.deleteFlorarie(selectedFlorarie.getIdFlorarie());
        loadFlorarii();
        clearFlorarieFields();
        selectedFlorarie = null;
        message = "Florarie stearsa cu succes.";
    }

    public void addFloare() {
        if (floareDenumire.isBlank() || floareImagine.isBlank()) {
            message = "Completeaza denumirea si calea imaginii pentru floare.";
            return;
        }

        currentFloare = new Floare();
        currentFloare.setDenumire(floareDenumire);
        currentFloare.setImagine(floareImagine);

        floareRepository.addFloare(currentFloare);
        loadFlori();
        clearFloareFields();
        selectedFloare = null;
        message = "Floare adaugata cu succes.";
    }

    public void updateFloare() {
        if (selectedFloare == null) {
            message = "Selecteaza o floare pentru actualizare.";
            return;
        }

        selectedFloare.setDenumire(floareDenumire);
        selectedFloare.setImagine(floareImagine);

        floareRepository.updateFloare(selectedFloare);
        loadFlori();
        floareId = String.valueOf(selectedFloare.getIdFloare());
        message = "Floare actualizata cu succes.";
    }

    public void deleteFloare() {
        if (selectedFloare == null) {
            message = "Selecteaza o floare pentru stergere.";
            return;
        }

        floareRepository.deleteFloare(selectedFloare.getIdFloare());
        loadFlori();
        clearFloareFields();
        selectedFloare = null;
        message = "Floare stearsa cu succes.";
    }

    public void addStoc() {
        if (stocIdFlorarie.isBlank() || stocIdFloare.isBlank() || stocCuloare.isBlank() || stocCantitate.isBlank()) {
            message = "Completeaza toate campurile pentru stoc.";
            return;
        }

        currentStoc = new Stoc();
        currentStoc.setIdFlorarie(parseInt(stocIdFlorarie));
        currentStoc.setIdFloare(parseInt(stocIdFloare));
        currentStoc.setCuloare(stocCuloare);
        currentStoc.setStoc(parseInt(stocCantitate));

        stocRepository.addStoc(currentStoc);
        loadStocuri();
        clearStocFields();
        selectedStoc = null;
        message = "Stoc adaugat cu succes.";
    }

    public void updateStoc() {
        if (selectedStoc == null) {
            message = "Selecteaza un rand de stoc pentru actualizare.";
            return;
        }

        selectedStoc.setIdFlorarie(parseInt(stocIdFlorarie));
        selectedStoc.setIdFloare(parseInt(stocIdFloare));
        selectedStoc.setCuloare(stocCuloare);
        selectedStoc.setStoc(parseInt(stocCantitate));

        stocRepository.updateStoc(selectedStoc);
        loadStocuri();
        stocId = String.valueOf(selectedStoc.getIdStoc());
        message = "Stoc actualizat cu succes.";
    }

    public void deleteStoc() {
        if (selectedStoc == null) {
            message = "Selecteaza un rand de stoc pentru stergere.";
            return;
        }

        stocRepository.deleteStoc(selectedStoc.getIdStoc());
        loadStocuri();
        clearStocFields();
        selectedStoc = null;
        message = "Stoc sters cu succes.";
    }

    public void searchFloare() {
        if (search.isBlank()) {
            message = "Introdu denumirea florii cautate.";
            return;
        }

        Floare floareGasita = floareRepository.searchFloare(search);

        clearTable(floriTableModel);
        clearTable(stocuriTableModel);

        if (floareGasita == null) {
            clearFloareFields();
            clearStocFields();
            selectedFloare = null;
            selectedStoc = null;
            message = "Nu a fost gasita nicio floare.";
            return;
        }

        floriTableModel.addRow(new Object[]{
                floareGasita.getIdFloare(),
                floareGasita.getDenumire(),
                floareGasita.getImagine()
        });

        floareId = String.valueOf(floareGasita.getIdFloare());
        floareDenumire = floareGasita.getDenumire();
        floareImagine = floareGasita.getImagine();

        List<Stoc> variante = stocRepository.getStocFlorari(floareGasita.getIdFloare());
        fillStocuriTable(variante);

        message = "Cautare realizata cu succes.";
    }

    public void filterFloriCuloare() {
        if (selectedFlorarie == null) {
            message = "Selecteaza o florarie pentru filtrare.";
            return;
        }

        if (filterColor.isBlank()) {
            message = "Introdu culoarea pentru filtrare.";
            return;
        }

        List<Stoc> rezultat = stocRepository.getStocDupaFlorarieSiCuloare(selectedFlorarie.getIdFlorarie(), filterColor);
        fillStocuriTable(rezultat);
        message = "Filtrare dupa culoare realizata cu succes.";
    }

    public void filterFloriDisp() {
        if (selectedFlorarie == null) {
            message = "Selecteaza o florarie pentru filtrare.";
            return;
        }

        List<Stoc> rezultat = stocRepository.getStocDisponibilDinFlorarie(selectedFlorarie.getIdFlorarie());
        fillStocuriTable(rezultat);
        message = "Filtrare dupa disponibilitate realizata cu succes.";
    }

    public void loadFlorarii() {
        clearTable(florariiTableModel);

        List<Florarie> florarii = florarieRepository.getFlorarii();
        for (Florarie florarie : florarii) {
            florariiTableModel.addRow(new Object[]{
                    florarie.getIdFlorarie(),
                    florarie.getDenumire(),
                    florarie.getAdresa()
            });
        }
    }

    public void loadFlori() {
        clearTable(floriTableModel);

        List<Floare> flori = floareRepository.getFloriSortedByDenumire();
        for (Floare floare : flori) {
            floriTableModel.addRow(new Object[]{
                    floare.getIdFloare(),
                    floare.getDenumire(),
                    floare.getImagine()
            });
        }
    }

    public void loadStocuri() {
        fillStocuriTable(stocRepository.getStocuri());
    }

    public List<String> getFlorariiList() {
        return florarieRepository.getFlorarii()
                .stream()
                .map(f -> f.getIdFlorarie() + " - " + f.getDenumire())
                .toList();
    }

    public void selectFlorarieByIndex(int index) {
        List<Florarie> list = florarieRepository.getFlorarii();
        if (index >= 0 && index < list.size()) {
            selectedFlorarie = list.get(index);
        }
    }

    private void fillStocuriTable(List<Stoc> stocuri) {
        clearTable(stocuriTableModel);

        for (Stoc stoc : stocuri) {
            stocuriTableModel.addRow(new Object[]{
                    stoc.getIdStoc(),
                    stoc.getIdFlorarie(),
                    stoc.getIdFloare(),
                    stoc.getCuloare(),
                    stoc.getStoc()
            });
        }
    }

    private void clearTable(DefaultTableModel model) {
        model.setRowCount(0);
    }

    private int parseInt(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }

    private void clearFlorarieFields() {
        florarieId = "";
        florarieDenumire = "";
        florarieAdresa = "";
    }

    private void clearFloareFields() {
        floareId = "";
        floareDenumire = "";
        floareImagine = "";
    }

    private void clearStocFields() {
        stocId = "";
        stocIdFlorarie = "";
        stocIdFloare = "";
        stocCuloare = "";
        stocCantitate = "";
    }
}