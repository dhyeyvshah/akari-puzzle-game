package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    this.board = board;
  }

  public int getWidth() {
    return board[0].length;
  }

  public int getHeight() {
    return board.length;
  }

  public CellType getCellType(int r, int c) {
    if (r < 0 || r >= this.getHeight() || c < 0 || c >= this.getWidth()) {
      throw new IndexOutOfBoundsException();
    }

    if (board[r][c] == 5) {
      return CellType.WALL;
    }

    if (board[r][c] == 6) {
      return CellType.CORRIDOR;
    }

    return CellType.CLUE;
  }

  public int getClue(int r, int c) {
    if (r < 0 || r >= this.getHeight() || c < 0 || c >= this.getWidth()) {
      throw new IndexOutOfBoundsException();
    }

    if (!this.getCellType(r, c).equals(CellType.CLUE)) {
      throw new IllegalArgumentException();
    } else {
      return board[r][c];
    }
  }
}
