package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {

  private final PuzzleLibrary library;
  List<ModelObserver> observers = new ArrayList<>();
  private int index;
  private boolean[][] lamps;
  private int[][] illuminated;

  public ModelImpl(PuzzleLibrary library) {
    this.library = library;
    this.index = 0;
    Puzzle puzzle = library.getPuzzle(0);
    illuminated = new int[puzzle.getHeight()][puzzle.getWidth()];
    lamps = new boolean[puzzle.getHeight()][puzzle.getWidth()];
  }

  @Override
  public void addLamp(int r, int c) {
    Puzzle puzzle = library.getPuzzle(index);
    if (!puzzle.getCellType(r, c).equals(CellType.CORRIDOR)) {
      throw new IllegalArgumentException();
    }
    lamps[r][c] = true;
    illuminated[r][c] += 1;
    for (int i = r - 1; i >= 0; i--) {
      if (!getActivePuzzle().getCellType(i, c).equals(CellType.CORRIDOR)) {
        break;
      }
      illuminated[i][c] += 1;
    }
    // Check down
    for (int i = r + 1; i < puzzle.getHeight(); i++) {
      if (!getActivePuzzle().getCellType(i, c).equals(CellType.CORRIDOR)) {
        break;
      }
      illuminated[i][c] += 1;
    }
    // Check left
    for (int i = c - 1; i >= 0; i--) {
      if (!getActivePuzzle().getCellType(r, i).equals(CellType.CORRIDOR)) {
        break;
      }
      illuminated[r][i] += 1;
    }
    // Check right
    for (int i = c + 1; i < puzzle.getWidth(); i++) {
      if (!getActivePuzzle().getCellType(r, i).equals(CellType.CORRIDOR)) {
        break;
      }
      illuminated[r][i] += 1;
    }
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    Puzzle puzzle = library.getPuzzle(index);
    if (!puzzle.getCellType(r, c).equals(CellType.CORRIDOR)) {
      throw new IllegalArgumentException();
    }
    lamps[r][c] = false;
    illuminated[r][c] -= 1;
    for (int i = r - 1; i >= 0; i--) {
      if (!getActivePuzzle().getCellType(i, c).equals(CellType.CORRIDOR)) {
        break;
      }
      if (illuminated[i][c] > 0) {
        illuminated[i][c] -= 1;
      }
    }
    // Check down
    for (int i = r + 1; i < puzzle.getHeight(); i++) {
      if (!getActivePuzzle().getCellType(i, c).equals(CellType.CORRIDOR)) {
        break;
      }
      if (illuminated[i][c] > 0) {
        illuminated[i][c] -= 1;
      }
    }
    // Check left
    for (int i = c - 1; i >= 0; i--) {
      if (!getActivePuzzle().getCellType(r, i).equals(CellType.CORRIDOR)) {
        break;
      }
      if (illuminated[r][i] > 0) {
        illuminated[r][i] -= 1;
      }
    }
    // Check right
    for (int i = c + 1; i < puzzle.getWidth(); i++) {
      if (!getActivePuzzle().getCellType(r, i).equals(CellType.CORRIDOR)) {
        break;
      }
      if (illuminated[r][i] > 0) {
        illuminated[r][i] -= 1;
      }
    }
    for (ModelObserver mo : observers) {
      mo.update(this);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    Puzzle puzzle = library.getPuzzle(index);
    if (!puzzle.getCellType(r, c).equals(CellType.CORRIDOR)) {
      throw new IllegalArgumentException();
    }
    return illuminated[r][c] > 0;
  }

  @Override
  public boolean isLamp(int r, int c) {
    Puzzle puzzle = library.getPuzzle(index);
    if (!puzzle.getCellType(r, c).equals(CellType.CORRIDOR)) {
      throw new IllegalArgumentException();
    }
    return lamps[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    Puzzle puzzle = library.getPuzzle(index);
    if (!puzzle.getCellType(r, c).equals(CellType.CORRIDOR)) {
      throw new IllegalArgumentException("Not a corridor!");
    }
    if (!lamps[r][c]) {
      throw new IllegalArgumentException("No lamp there!");
    }
    boolean illegal = false;
    // Check up
    for (int i = r - 1; i >= 0; i--) {
      if (!getActivePuzzle().getCellType(i, c).equals(CellType.CORRIDOR)) {
        break;
      }
      if (lamps[i][c]) {
        illegal = true;
        break;
      }
    }
    // Check down
    for (int i = r + 1; i < puzzle.getHeight(); i++) {
      if (!getActivePuzzle().getCellType(i, c).equals(CellType.CORRIDOR)) {
        break;
      }
      if (lamps[i][c]) {
        illegal = true;
        break;
      }
    }
    // Check left
    for (int i = c - 1; i >= 0; i--) {
      if (!getActivePuzzle().getCellType(r, i).equals(CellType.CORRIDOR)) {
        break;
      }
      if (lamps[r][i]) {
        illegal = true;
        break;
      }
    }
    // Check right
    for (int i = c + 1; i < puzzle.getWidth(); i++) {
      if (!getActivePuzzle().getCellType(r, i).equals(CellType.CORRIDOR)) {
        break;
      }
      if (lamps[r][i]) {
        illegal = true;
        break;
      }
    }
    return illegal;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(index);
  }

  @Override
  public int getActivePuzzleIndex() {
    return index;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= library.size()) {
      throw new IndexOutOfBoundsException();
    }
    this.index = index;
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    Puzzle puzzle = library.getPuzzle(index);
    illuminated = new int[puzzle.getHeight()][puzzle.getWidth()];
    lamps = new boolean[puzzle.getHeight()][puzzle.getWidth()];
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public boolean isSolved() {
    Puzzle puzzle = library.getPuzzle(index);
    for (int r = 0; r < puzzle.getHeight(); r++) {
      for (int c = 0; c < puzzle.getWidth(); c++) {
        CellType type = puzzle.getCellType(r, c);
        if (type.equals(CellType.CORRIDOR)) {
          if (illuminated[r][c] == 0) {
            return false;
          }
          if (isLamp(r, c) && isLampIllegal(r, c)) {
            return false;
          }
        } else if (type.equals(CellType.CLUE)) {
          if (!isClueSatisfied(r, c)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    Puzzle puzzle = library.getPuzzle(index);
    int clueNum = puzzle.getClue(r, c);
    List<List<Integer>> adjacentCorridorCells = getAdjacentCorridorCells(r, c);
    int count = 0;
    for (List<Integer> temp : adjacentCorridorCells) {
      int row = temp.get(0);
      int col = temp.get(1);
      if (lamps[row][col]) {
        count++;
      }
    }
    return count == clueNum;
  }

  private List<List<Integer>> getAdjacentCorridorCells(int r, int c) {
    Puzzle puzzle = library.getPuzzle(index);
    List<List<Integer>> sol = new ArrayList<>();
    int[] dir = {-1, 1};
    for (int d : dir) {
      try {
        CellType cellType = puzzle.getCellType(r + d, c);
        if (cellType.equals(CellType.CORRIDOR)) {
          List<Integer> temp = new ArrayList<>();
          temp.add(r + d);
          temp.add(c);
          sol.add(temp);
        }
      } catch (Exception e) {
        // Do nothing.
      }
    }
    for (int d : dir) {
      try {
        CellType cellType = puzzle.getCellType(r, c + d);
        if (cellType.equals(CellType.CORRIDOR)) {
          List<Integer> temp = new ArrayList<>();
          temp.add(r);
          temp.add(c + d);
          sol.add(temp);
        }
      } catch (Exception e) {
      }
    }
    return sol;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }
}
