package View;

import ViewModel.FlorarieVM;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class ImageTableCell extends TableCell<List<String>, String> {

    private final FlorarieVM vm;
    private final ImageView imageView;

    public ImageTableCell(FlorarieVM vm, double width, double height) {
        this.vm = vm;
        this.imageView = new ImageView();
        this.imageView.setFitWidth(width);
        this.imageView.setFitHeight(height);
        this.imageView.setPreserveRatio(true);
    }

    @Override
    protected void updateItem(String path, boolean empty) {
        super.updateItem(path, empty);

        Image image = vm.getImageForPath(path);

        if (empty || image == null) {
            setText("No image");
            setGraphic(null);
            return;
        }

        imageView.setImage(image);
        setText(null);
        setGraphic(imageView);
    }
}