package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  public void start(Stage stage) {
    PuzzleLibrary library = new PuzzleLibraryImpl();
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));

    Model model = new ModelImpl(library);
    AlternateMvcController controller = new ControllerImpl(model);

    FXComponent puzzle = new PuzzleView(controller);
    FXComponent controlView = new ControlView(controller);

    Group group = new Group();
    group.getChildren().addAll(puzzle.render(), controlView.render());
    Scene scene = new Scene(group, 500, 500, Color.ANTIQUEWHITE);

    stage.setScene(scene);
    stage.setMaximized(true);
    stage.setTitle("Akari");

    ModelObserver observer =
        new ModelObserver() {
          public void update(Model model) {
            group.getChildren().clear();
            group.getChildren().addAll(puzzle.render(), controlView.render());
          }
        };

    model.addObserver(observer);

    stage.show();
  }
}
