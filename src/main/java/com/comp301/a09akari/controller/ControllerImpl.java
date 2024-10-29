package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;
import java.util.Random;

public class ControllerImpl implements AlternateMvcController {
  private final Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  public void clickNextPuzzle() {
    int index = model.getActivePuzzleIndex();
    index++;
    if (index == model.getPuzzleLibrarySize()) {
      return;
    }
    model.setActivePuzzleIndex(index);
  }

  public void clickPrevPuzzle() {
    int index = model.getActivePuzzleIndex();
    index--;
    if (index < 0) {
      return;
    }
    model.setActivePuzzleIndex(index);
  }

  public void clickRandPuzzle() {
    int size = model.getPuzzleLibrarySize();
    int index = model.getActivePuzzleIndex();
    Random random = new Random();
    int randIndex = random.nextInt(size);
    while (randIndex == index) {
      randIndex = random.nextInt(size);
    }
    model.setActivePuzzleIndex(randIndex);
  }

  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  public void clickCell(int r, int c) {
    try {
      if (isLamp(r, c)) {
        model.removeLamp(r, c);
      } else {
        model.addLamp(r, c);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  public boolean isLampIllegal(int r, int c) {
    return model.isLampIllegal(r, c);
  }

  public int getPuzzleHeight() {
    return getActivePuzzle().getHeight();
  }

  public int getPuzzleWidth() {
    return getActivePuzzle().getWidth();
  }

  public int getPuzzleIndex() {
    return model.getActivePuzzleIndex();
  }

  public int getLibrarySize() {
    return model.getPuzzleLibrarySize();
  }

  public CellType getCellType(int r, int c) {
    return getActivePuzzle().getCellType(r, c);
  }

  public int getClue(int r, int c) {
    return getActivePuzzle().getClue(r, c);
  }

  public boolean isClueSatisfied(int r, int c) {
    return model.isClueSatisfied(r, c);
  }

  public boolean isSolved() {
    return model.isSolved();
  }

  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }
}
