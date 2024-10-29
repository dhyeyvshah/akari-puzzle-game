package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.CellType;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PuzzleView implements FXComponent {
  private final AlternateMvcController controller;

  public PuzzleView(AlternateMvcController controller) {
    this.controller = controller;
  }

  private Color getCorridorColor(int r, int c) {
    if (controller.isLamp(r, c)) {
      if (controller.isLampIllegal(r, c)) {
        return Color.RED;
      } else {
        return Color.YELLOW;
      }
    } else if (controller.isLit(r, c)) {
      return Color.KHAKI;
    } else {
      return Color.WHITE;
    }
  }

  public Parent render() {
    int boxSize = 50;
    int height = controller.getActivePuzzle().getHeight();
    int width = controller.getActivePuzzle().getWidth();

    Group group = new Group();
    group.setLayoutY(150);

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        CellType cellType = controller.getActivePuzzle().getCellType(r, c);
        StackPane pane = new StackPane();
        Rectangle rectangle = new Rectangle();

        rectangle.setX(c * boxSize);
        rectangle.setY(r * boxSize);
        rectangle.setHeight(boxSize);
        rectangle.setWidth(boxSize);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);

        pane.setLayoutX(c * boxSize);
        pane.setLayoutY(r * boxSize);

        if (cellType.equals(CellType.CLUE)) {
          rectangle.setFill(Color.BLACK);
          Text text = new Text(String.format("%d", controller.getClue(r, c)));
          if (controller.isClueSatisfied(r, c)) {
            text.setFill(Color.GREEN);
          } else {
            text.setFill(Color.WHITE);
          }
          text.setStyle("-fx-font: 24 arial;");
          pane.getChildren().addAll(rectangle, text);
        } else if (cellType.equals(CellType.CORRIDOR)) {
          rectangle.setFill(getCorridorColor(r, c));
          pane.getChildren().add(rectangle);
          if (controller.isLamp(r, c)) {
            Image img = new Image("light-bulb.png");
            ImageView imgView = new ImageView();
            imgView.setImage(img);
            imgView.setX(c * boxSize);
            imgView.setY(r * boxSize);
            imgView.setFitWidth(boxSize);
            imgView.setFitHeight(boxSize);
            pane.getChildren().add(imgView);
          }
        } else {
          pane.getChildren().add(rectangle);
        }
        int finalR = r;
        int finalC = c;
        pane.setOnMouseClicked(
            e -> {
              controller.clickCell(finalR, finalC);
            });
        rectangle.setStyle("-fx-stroke: black; -fx-stroke-width: 5;");

        group.getChildren().add(pane);
      }
    }
    return group;
  }
}
