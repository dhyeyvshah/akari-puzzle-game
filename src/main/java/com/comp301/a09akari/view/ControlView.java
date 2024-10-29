package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ControlView implements FXComponent {
  private final AlternateMvcController controller;

  public ControlView(AlternateMvcController controller) {
    this.controller = controller;
  }

  public Parent render() {
    Button nextButton = new Button("Next Puzzle");
    nextButton.setOnMouseClicked(
        e -> {
          controller.clickNextPuzzle();
        });
    nextButton.setLayoutY(10);
    nextButton.setLayoutX(10);
    nextButton.setMinWidth(100);

    Button prevButton = new Button("Previous Puzzle");
    prevButton.setOnMouseClicked(
        e -> {
          controller.clickPrevPuzzle();
        });
    prevButton.setLayoutY(10);
    prevButton.setLayoutX(110);
    prevButton.setMinWidth(100);

    Button randButton = new Button("Random Puzzle");
    randButton.setOnMouseClicked(
        e -> {
          controller.clickRandPuzzle();
        });
    randButton.setLayoutY(10);
    randButton.setLayoutX(210);
    randButton.setMinWidth(100);

    Button resetButton = new Button("Reset Puzzle");
    resetButton.setOnMouseClicked(
        e -> {
          controller.clickResetPuzzle();
        });
    resetButton.setLayoutY(10);
    resetButton.setLayoutX(310);
    resetButton.setMinWidth(100);

    Text text =
        new Text(
            String.format(
                "Puzzle %d of %d", controller.getPuzzleIndex() + 1, controller.getLibrarySize()));
    text.setStyle("-fx-font: 30 arial;");
    text.setLayoutY(100);

    Text congratsText = new Text("Congratulations! You have solved this puzzle!");
    congratsText.setStyle("-fx-font: 30 arial;");
    congratsText.setFill(Color.GREEN);
    congratsText.setLayoutX(600);
    congratsText.setLayoutY(200);

    Group group = new Group();
    group.getChildren().addAll(nextButton, prevButton, randButton, resetButton, text);

    if (controller.isSolved()) {
      group.getChildren().add(congratsText);
    }
    return group;
  }
}
