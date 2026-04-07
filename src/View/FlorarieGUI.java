package View;

import ViewModel.FlorarieVM;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.util.Optional;
import java.util.stream.Stream;

public class FlorarieGUI extends JFrame {

    private final FlorarieVM vm;

    private JTextField txtIdFlorarie;
    private JTextField txtDenumireFlorarie;
    private JTextField txtAdresaFlorarie;

    private JTextField txtIdFloare;
    private JTextField txtDenumireFloare;
    private JTextField txtImagineFloare;

    private JTextField txtIdStoc;
    private JTextField txtIdFlorarieStoc;
    private JTextField txtIdFloareStoc;
    private JTextField txtCuloareStoc;
    private JTextField txtCantitateStoc;

    private JTextField txtSearchFloare;
    private JTextField txtFilterColor;

    private JLabel lblMessage;
    private JLabel lblImagePreview;

    private JTable tableFlorarii;
    private JTable tableFlori;
    private JTable tableStocuri;
    private JTable tableFloriPreview;
    private JTable tableStocuriPreview;

    private JButton btnAddFlorarie;
    private JButton btnUpdateFlorarie;
    private JButton btnDeleteFlorarie;

    private JButton btnAddFloare;
    private JButton btnUpdateFloare;
    private JButton btnDeleteFloare;
    private JButton btnBrowseImage;

    private JButton btnAddStoc;
    private JButton btnUpdateStoc;
    private JButton btnDeleteStoc;

    private JButton btnSearchFloare;
    private JButton btnFilterColor;
    private JButton btnFilterDisponibile;
    private JButton btnRefresh;

    private JComboBox<String> comboFlorarii;

    public FlorarieGUI() {
        vm = new FlorarieVM();

        setTitle("Administrare lant de florarii");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1360, 900);
        setLocationRelativeTo(null);

        initLook();
        initComponents();
        initLayout();
        bindData();
        loadFlorariiCombo();
        initActions();
        syncFromViewModel();

        setVisible(true);
    }

    private void initLook() {
        getContentPane().setBackground(new Color(244, 247, 251));
    }

    private void initComponents() {
        txtIdFlorarie = createReadOnlyField(18);
        txtDenumireFlorarie = createTextField(18);
        txtAdresaFlorarie = createTextField(18);

        txtIdFloare = createReadOnlyField(18);
        txtDenumireFloare = createTextField(18);
        txtImagineFloare = createTextField(18);

        txtIdStoc = createReadOnlyField(18);
        txtIdFlorarieStoc = createTextField(18);
        txtIdFloareStoc = createTextField(18);
        txtCuloareStoc = createTextField(18);
        txtCantitateStoc = createTextField(18);

        txtSearchFloare = createTextField(18);
        txtFilterColor = createTextField(18);

        lblMessage = new JLabel(" ");
        lblMessage.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMessage.setForeground(new Color(39, 78, 114));

        lblImagePreview = new JLabel("Preview imagine", SwingConstants.CENTER);
        lblImagePreview.setPreferredSize(new Dimension(240, 190));
        lblImagePreview.setOpaque(true);
        lblImagePreview.setBackground(new Color(250, 250, 250));
        lblImagePreview.setBorder(BorderFactory.createLineBorder(new Color(210, 218, 226)));
        lblImagePreview.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblImagePreview.setForeground(new Color(110, 120, 130));

        btnAddFlorarie = createPrimaryButton("Adauga");
        btnUpdateFlorarie = createPrimaryButton("Actualizeaza");
        btnDeleteFlorarie = createDangerButton("Sterge");

        btnAddFloare = createPrimaryButton("Adauga");
        btnUpdateFloare = createPrimaryButton("Actualizeaza");
        btnDeleteFloare = createDangerButton("Sterge");
        btnBrowseImage = createSecondaryButton("Alege imagine");

        btnAddStoc = createPrimaryButton("Adauga");
        btnUpdateStoc = createPrimaryButton("Actualizeaza");
        btnDeleteStoc = createDangerButton("Sterge");

        btnSearchFloare = createPrimaryButton("Cauta");
        btnFilterColor = createPrimaryButton("Filtreaza culoare");
        btnFilterDisponibile = createPrimaryButton("Doar disponibile");
        btnRefresh = createSecondaryButton("Refresh");

        tableFlorarii = createTable(vm.getFlorariiTableModel(), 30);
        tableFlori = createTable(vm.getFloriTableModel(), 90);
        tableStocuri = createTable(vm.getStocuriTableModel(), 30);

        tableFlori.getColumnModel().getColumn(2).setCellRenderer(new FlowerImageRenderer(78, 78));
        tableFlori.getColumnModel().getColumn(2).setPreferredWidth(130);

        tableFloriPreview = createTable(vm.getFloriTableModel(), 90);
        tableStocuriPreview = createTable(vm.getStocuriTableModel(), 30);

        tableFloriPreview.getColumnModel().getColumn(2).setCellRenderer(new FlowerImageRenderer(78, 78));
        tableFloriPreview.getColumnModel().getColumn(2).setPreferredWidth(130);

        comboFlorarii = new JComboBox<>();
        comboFlorarii.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboFlorarii.setPreferredSize(new Dimension(220, 34));
    }

    private void initLayout() {
        setLayout(new BorderLayout(12, 12));
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createMainTabs(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createHeaderPanel() {
        JLabel title = new JLabel("Flower Shop Management", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(36, 52, 71));

        JLabel subtitle = new JLabel("MVVM - Florarii, flori, stoc, cautare si filtrare", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(102, 112, 122));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(244, 247, 251));
        panel.setBorder(new EmptyBorder(14, 14, 0, 14));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(title);
        panel.add(Box.createVerticalStrut(4));
        panel.add(subtitle);
        return panel;
    }

    private JTabbedPane createMainTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBorder(new EmptyBorder(0, 12, 0, 12));

        tabs.addTab("Florarii", createFlorarieTab());
        tabs.addTab("Flori", createFloareTab());
        tabs.addTab("Stoc", createStocTab());
        tabs.addTab("Cautare / Filtrare", createSearchTab());

        return tabs;
    }

    private JPanel createFlorarieTab() {
        JPanel panel = createContentPanel();
        panel.add(createLeftColumn(createFlorarieFormCard(), createFlorarieButtonsCard()), BorderLayout.WEST);
        panel.add(createTableCard("Lista florarii", new JScrollPane(tableFlorarii)), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createFloareTab() {
        JPanel leftColumn = createLeftColumn(createFloareFormCard(), createFloareButtonsCard(), createImagePreviewCard());

        JPanel panel = createContentPanel();
        panel.add(leftColumn, BorderLayout.WEST);
        panel.add(createTableCard("Lista flori", new JScrollPane(tableFlori)), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createStocTab() {
        JPanel panel = createContentPanel();
        panel.add(createLeftColumn(createStocFormCard(), createStocButtonsCard()), BorderLayout.WEST);
        panel.add(createTableCard("Lista stocuri", new JScrollPane(tableStocuri)), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSearchTab() {
        JPanel panel = createContentPanel();
        panel.add(createLeftColumn(createSearchCard()), BorderLayout.WEST);
        panel.add(createSearchInfoCard(), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(new Color(244, 247, 251));
        return panel;
    }

    private JPanel createLeftColumn(JPanel... cards) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(390, 100));

        Stream.of(cards).forEach(card -> {
            panel.add(card);
            panel.add(Box.createVerticalStrut(12));
        });

        return panel;
    }

    private JPanel createFlorarieFormCard() {
        JPanel panel = createCard("Date florarie");
        panel.setLayout(new GridBagLayout());

        addRow(panel, 0, "ID Florarie", txtIdFlorarie);
        addRow(panel, 1, "Denumire", txtDenumireFlorarie);
        addRow(panel, 2, "Adresa", txtAdresaFlorarie);

        return panel;
    }

    private JPanel createFloareFormCard() {
        JPanel panel = createCard("Date floare");
        panel.setLayout(new GridBagLayout());

        addRow(panel, 0, "ID Floare", txtIdFloare);
        addRow(panel, 1, "Denumire", txtDenumireFloare);
        addRow(panel, 2, "Imagine", createBrowsePanel());

        return panel;
    }

    private JPanel createStocFormCard() {
        JPanel panel = createCard("Date stoc");
        panel.setLayout(new GridBagLayout());

        addRow(panel, 0, "ID Stoc", txtIdStoc);
        addRow(panel, 1, "ID Florarie", txtIdFlorarieStoc);
        addRow(panel, 2, "ID Floare", txtIdFloareStoc);
        addRow(panel, 3, "Culoare", txtCuloareStoc);
        addRow(panel, 4, "Cantitate", txtCantitateStoc);

        return panel;
    }

    private JPanel createSearchCard() {
        JPanel panel = createCard("Cautare si filtrare");
        panel.setLayout(new GridBagLayout());

        addRow(panel, 0, "Florarie", comboFlorarii);
        addRow(panel, 1, "Denumire floare", txtSearchFloare);
        addControl(panel, 2, btnSearchFloare);

        addRow(panel, 3, "Culoare", txtFilterColor);
        addControl(panel, 4, btnFilterColor);
        addControl(panel, 5, btnFilterDisponibile);
        addControl(panel, 6, btnRefresh);

        return panel;
    }

    private JPanel createMiniPreviewTable(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setBorder(new EmptyBorder(0, 2, 6, 0));
        JScrollPane scrollPane = new JScrollPane(table); scrollPane.setPreferredSize(new Dimension(100, 220));
        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSearchInfoCard() {
        JPanel panel = createCard("Rezultate");
        panel.setLayout(new BorderLayout(12, 12));

        JPanel center = new JPanel(new GridLayout(2, 1, 0, 12));
        center.setOpaque(false);
        center.add(createMiniPreviewTable("Tabel flori", tableFloriPreview));
        center.add(createMiniPreviewTable("Tabel stocuri", tableStocuriPreview));

        panel.add(center, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFlorarieButtonsCard() {
        return createButtonsCard("Actiuni florarie", btnAddFlorarie, btnUpdateFlorarie, btnDeleteFlorarie);
    }

    private JPanel createFloareButtonsCard() {
        return createButtonsCard("Actiuni floare", btnAddFloare, btnUpdateFloare, btnDeleteFloare);
    }

    private JPanel createStocButtonsCard() {
        return createButtonsCard("Actiuni stoc", btnAddStoc, btnUpdateStoc, btnDeleteStoc);
    }

    private JPanel createButtonsCard(String title, JButton b1, JButton b2, JButton b3) {
        JPanel panel = createCard(title);
        panel.setLayout(new GridLayout(1, 3, 10, 10));
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        return panel;
    }

    private JPanel createImagePreviewCard() {
        JPanel panel = createCard("Preview imagine");
        panel.setLayout(new BorderLayout());
        panel.add(lblImagePreview, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTableCard(String title, JComponent component) {
        JPanel panel = createCard(title);
        panel.setLayout(new BorderLayout());
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createBrowsePanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 0));
        panel.setOpaque(false);
        panel.add(txtImagineFloare, BorderLayout.CENTER);
        panel.add(btnBrowseImage, BorderLayout.EAST);
        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(0, 12, 12, 12),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(217, 223, 230)),
                        new EmptyBorder(10, 12, 10, 12)
                )
        ));
        panel.add(lblMessage, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCard(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(217, 223, 230)),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder(
                                BorderFactory.createEmptyBorder(),
                                title
                        ),
                        new EmptyBorder(12, 12, 12, 12)
                )
        ));
        return panel;
    }

    private void addRow(JPanel panel, int row, String labelText, Component component) {
        GridBagConstraints left = baseConstraints();
        left.gridx = 0;
        left.gridy = row;
        left.weightx = 0;
        left.fill = GridBagConstraints.NONE;
        panel.add(createLabel(labelText), left);

        GridBagConstraints right = baseConstraints();
        right.gridx = 1;
        right.gridy = row;
        right.weightx = 1;
        right.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, right);
    }

    private void addControl(JPanel panel, int row, JButton button) {
        GridBagConstraints c = baseConstraints();
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(button, c);
    }

    private void bindData() {
        tableFlorarii.setModel(vm.getFlorariiTableModel());
        tableFlori.setModel(vm.getFloriTableModel());
        tableStocuri.setModel(vm.getStocuriTableModel());

        tableFloriPreview.setModel(vm.getFloriTableModel());
        tableStocuriPreview.setModel(vm.getStocuriTableModel());
    }

    private void initActions() {
        btnAddFlorarie.addActionListener(e -> executeCommand(vm.getAddFlorarieCommand()));
        btnUpdateFlorarie.addActionListener(e -> executeCommand(vm.getUpdateFlorarieCommand()));
        btnDeleteFlorarie.addActionListener(e -> executeCommand(vm.getDeleteFlorarieCommand()));

        btnAddFloare.addActionListener(e -> executeCommand(vm.getAddFloareCommand()));
        btnUpdateFloare.addActionListener(e -> executeCommand(vm.getUpdateFloareCommand()));
        btnDeleteFloare.addActionListener(e -> executeCommand(vm.getDeleteFloareCommand()));
        btnBrowseImage.addActionListener(e -> chooseImagePath());

        btnAddStoc.addActionListener(e -> executeCommand(vm.getAddStocCommand()));
        btnUpdateStoc.addActionListener(e -> executeCommand(vm.getUpdateStocCommand()));
        btnDeleteStoc.addActionListener(e -> executeCommand(vm.getDeleteStocCommand()));

        btnSearchFloare.addActionListener(e -> executeCommand(vm.getSearchFloareCommand()));
        btnFilterColor.addActionListener(e -> executeCommand(vm.getFilterColorCommand()));
        btnFilterDisponibile.addActionListener(e -> executeCommand(vm.getFilterDispCommand()));
        btnRefresh.addActionListener(e -> refreshAll());

        tableFlorarii.getSelectionModel().addListSelectionListener(e -> selectFlorarie());
        tableFlori.getSelectionModel().addListSelectionListener(e -> selectFloare());
        tableStocuri.getSelectionModel().addListSelectionListener(e -> selectStoc());

        comboFlorarii.addActionListener(e -> {
            vm.selectFlorarieByIndex(comboFlorarii.getSelectedIndex());
            syncFromViewModel();
        });
    }

    private void executeCommand(ViewModel.Commands.FlorarieCommands command) {
        syncToViewModel();
        command.execute();
        loadFlorariiCombo();
        syncFromViewModel();
    }

    private void refreshAll() {
        syncToViewModel();
        vm.refreshAll();
        syncFromViewModel();
    }

    private void selectFlorarie() {
        vm.selectFlorarieAt(tableFlorarii.getSelectedRow());
        syncFromViewModel();
    }

    private void selectFloare() {
        vm.selectFloareAt(tableFlori.getSelectedRow());
        syncFromViewModel();
    }

    private void selectStoc() {
        vm.selectStocAt(tableStocuri.getSelectedRow());
        syncFromViewModel();
    }

    private void chooseImagePath() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Selecteaza imagine");
        chooser.setFileFilter(new FileNameExtensionFilter(
                "Imagini", "png", "jpg", "jpeg", "gif", "bmp", "webp"
        ));

        String selectedPath = Optional.of(chooser.showOpenDialog(this))
                .filter(result -> result == JFileChooser.APPROVE_OPTION)
                .map(result -> chooser.getSelectedFile())
                .map(File::getAbsolutePath)
                .orElse(txtImagineFloare.getText());

        txtImagineFloare.setText(selectedPath);
        vm.setFloareImagine(selectedPath);
        updateImagePreview(selectedPath);
    }

    private void syncToViewModel() {
        vm.setFlorarieDenumire(txtDenumireFlorarie.getText());
        vm.setFlorarieAdresa(txtAdresaFlorarie.getText());

        vm.setFloareDenumire(txtDenumireFloare.getText());
        vm.setFloareImagine(txtImagineFloare.getText());

        vm.setStocIdFlorarie(txtIdFlorarieStoc.getText());
        vm.setStocIdFloare(txtIdFloareStoc.getText());
        vm.setStocCuloare(txtCuloareStoc.getText());
        vm.setStocCantitate(txtCantitateStoc.getText());

        vm.setSearch(txtSearchFloare.getText());
        vm.setFilterColor(txtFilterColor.getText());
    }

    private void syncFromViewModel() {
        txtIdFlorarie.setText(readVmValue(vm.getFlorarieId(), readSelectedTableValue(tableFlorarii, 0)));
        txtDenumireFlorarie.setText(vm.getFlorarieDenumire());
        txtAdresaFlorarie.setText(vm.getFlorarieAdresa());

        txtIdFloare.setText(readVmValue(vm.getFloareId(), readSelectedTableValue(tableFlori, 0)));
        txtDenumireFloare.setText(vm.getFloareDenumire());
        txtImagineFloare.setText(vm.getFloareImagine());

        txtIdStoc.setText(readVmValue(vm.getStocId(), readSelectedTableValue(tableStocuri, 0)));
        txtIdFlorarieStoc.setText(vm.getStocIdFlorarie());
        txtIdFloareStoc.setText(vm.getStocIdFloare());
        txtCuloareStoc.setText(vm.getStocCuloare());
        txtCantitateStoc.setText(vm.getStocCantitate());

        txtSearchFloare.setText(vm.getSearch());
        txtFilterColor.setText(vm.getFilterColor());

        lblMessage.setText(vm.getMessage());
        updateImagePreview(vm.getFloareImagine());

        tableFlorarii.repaint();
        tableFlori.repaint();
        tableStocuri.repaint();
    }

    private void loadFlorariiCombo() {
        comboFlorarii.removeAllItems();
        vm.getFlorariiList().forEach(comboFlorarii::addItem);
    }

    private String readVmValue(String primary, String fallback) {
        return Optional.ofNullable(primary)
                .filter(value -> !value.isBlank())
                .orElse(Optional.ofNullable(fallback).orElse(""));
    }

    private String readSelectedTableValue(JTable table, int column) {
        return Optional.of(table.getSelectedRow())
                .filter(row -> row >= 0)
                .map(row -> table.getModel().getValueAt(row, column))
                .map(String::valueOf)
                .orElse("");
    }

    private void updateImagePreview(String path) {
        ImageIcon icon = loadImageIcon(path);

        lblImagePreview.setText(Optional.ofNullable(icon).map(x -> "").orElse("Preview imagine"));
        lblImagePreview.setIcon(Optional.ofNullable(icon)
                .map(ImageIcon::getImage)
                .map(image -> image.getScaledInstance(220, 170, Image.SCALE_SMOOTH))
                .map(ImageIcon::new)
                .orElse(null));
    }

    private ImageIcon loadImageIcon(String path) {
        return Optional.ofNullable(path)
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .flatMap(this::loadExistingImageIcon)
                .orElse(null);
    }

    private Optional<ImageIcon> loadExistingImageIcon(String path) {
        Optional<ImageIcon> fromFiles = Stream.of(
                        new File(path),
                        new File("images/" + path),
                        new File("src/" + path),
                        new File("src/images/" + path)
                )
                .filter(File::exists)
                .findFirst()
                .map(File::getAbsoluteFile)
                .map(File::toString)
                .map(ImageIcon::new);

        Optional<ImageIcon> fromResource = Optional.ofNullable(getClass().getClassLoader().getResource(path))
                .map(ImageIcon::new);

        return fromFiles.isPresent() ? fromFiles : fromResource;
    }

    private JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(220, 34));
        textField.setMinimumSize(new Dimension(220, 34));
        return textField;
    }

    private JTextField createReadOnlyField(int columns) {
        JTextField textField = createTextField(columns);
        textField.setEditable(false);
        textField.setBackground(new Color(242, 244, 247));
        return textField;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(36, 52, 71));
        return label;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBackground(new Color(224, 239, 248));
        button.setPreferredSize(new Dimension(120, 36));
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBackground(new Color(240, 242, 245));
        button.setPreferredSize(new Dimension(140, 36));
        return button;
    }

    private JButton createDangerButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBackground(new Color(248, 224, 224));
        button.setPreferredSize(new Dimension(120, 36));
        return button;
    }

    private JTable createTable(TableModel model, int rowHeight) {
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(rowHeight);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(224, 233, 242));
        table.setSelectionBackground(new Color(189, 214, 235));
        table.setGridColor(new Color(220, 220, 220));
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        return table;
    }

    private GridBagConstraints baseConstraints() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    private class FlowerImageRenderer extends DefaultTableCellRenderer {

        private final int width;
        private final int height;

        public FlowerImageRenderer(int width, int height) {
            this.width = width;
            this.height = height;
            setHorizontalAlignment(CENTER);
        }

        @Override
        protected void setValue(Object value) {
            ImageIcon original = loadImageIcon(Optional.ofNullable(value).map(String::valueOf).orElse(""));
            ImageIcon scaled = Optional.ofNullable(original)
                    .map(ImageIcon::getImage)
                    .map(image -> image.getScaledInstance(width, height, Image.SCALE_SMOOTH))
                    .map(ImageIcon::new)
                    .orElse(null);

            setText(Optional.ofNullable(scaled).map(icon -> "").orElse("No image"));
            setIcon(scaled);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new FlorarieGUI();
        });
    }
}