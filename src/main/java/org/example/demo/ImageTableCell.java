/*package org.example.demo;

import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageTableCell<T> extends TableCell<T, Image> {
    private final ImageView imageView;

    public ImageTableCell() {
        this.imageView = new ImageView();
        imageView.setFitWidth(100); // Adjust width as needed
        imageView.setFitHeight(100); // Adjust height as needed
        setGraphic(imageView);
    }

    @Override
    protected void updateItem(Image item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            imageView.setImage(null);
        } else {
            imageView.setImage(item);
        }
    }
}*/
