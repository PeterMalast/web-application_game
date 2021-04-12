package sk.tuke.gamestudio.game.core;


public class Field {
    private final int rowCount;

    private final int columnCount;

    private final Tile[][] tiles;

    private GameState state = GameState.PLAYING;

    private int numberOfOpenTiles = 0;

    private long startMillis;

    public Field(int rowCount, int columnCount){
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        tiles = new Tile[rowCount][columnCount];
        generate();
    }

    private void generate() {
        fillWithClues();
        fillWithLevel_1();
        startMillis = System.currentTimeMillis();
    }

    private void fillWithClues() {
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                final Tile tile = tiles[row][column];
                if (tile == null) tiles[row][column] = new Clue(0);
            }
        }
    }

    private void fillWithLevel_1(){
        for (int row = 0; row < rowCount; row++) {
            for (int column = 0; column < columnCount; column++) {
                final Tile tile = tiles[row][column];
                if ( (row == 4 && column == 4)  || (row == 5 && column == 4) || (row == 3 && column == 4)
                        || (row == 4 && column == 3)|| (row == 4 && column == 5)) {
                    tile.setState(TileState.OPENED);
                    numberOfOpenTiles++;
                }
            }
        }
    }

    public void openCloseTile(int row, int column) {
        final Tile tile = tiles[row][column];
        SwitchLightTitle(tile);

        if (row > 0 && row < 8 && column > 0 && column < 8 ) switchLight(row, column);
        else  switchCornerLight(row , column);

        if (isSolved()) state = GameState.SOLVED;
    }


    public void switchCornerLight(int row, int column) {
        final Tile tile = tiles[row][column];

        if (tile.getState() == TileState.OPENED) {
            if(row >0) {
                final Tile tileS = tiles[row - 1][column];
                TileOpened(tileS);

            }
            if (column > 0) {
                final Tile tileZ = tiles[row][column - 1];
                TileOpened(tileZ);


            }
            if(row < 8 ) {
                final Tile tileJ = tiles[row + 1][column];
                TileOpened(tileJ);

            }
            if (column < 8) {
                final Tile tileV = tiles[row][column + 1];
                TileOpened(tileV);
            }

        }


        if (tile.getState() == TileState.CLOSED) {
            if(row >0) {
                final Tile tileS = tiles[row - 1][column];
                closedCornerLight(tileS);
            }

            if(row < 8) {
                final Tile tileJ = tiles[row + 1][column];
                closedCornerLight(tileJ);
            }
            if (column < 8) {
                final Tile tileV = tiles[row][column + 1];

                closedCornerLight(tileV);
            }


            if (column > 0) {
                final Tile tileZ = tiles[row][column - 1];
                closedCornerLight(tileZ);
            }
        }

    }

    private void closedCornerLight(Tile tile) {
        if(tile.getState() == TileState.OPENED) {
            tile.setState(TileState.CLOSED);
            numberOfOpenTiles--;
        }else {
            tile.setState(TileState.OPENED);
            numberOfOpenTiles++;
        }
    }

    private void TileOpened(Tile tile) {
        if(tile.getState() == TileState.CLOSED) {
             tile.setState(TileState.OPENED);
             numberOfOpenTiles++;
         }                else{
            tile.setState(TileState.CLOSED);
            numberOfOpenTiles--;
        }
    }


    public void switchLight(int row, int column) {
            final Tile tileS = tiles[row - 1][column];
            final Tile tileJ = tiles[row + 1][column];
            final Tile tileV = tiles[row][column + 1];
            final Tile tileZ = tiles[row][column - 1];
                 SwitchLightTitle(tileS);
                 SwitchLightTitle(tileJ);
                 SwitchLightTitle(tileV);
                 SwitchLightTitle(tileZ);

    }

    private void SwitchLightTitle(Tile tile) {
        if (tile.getState() == TileState.CLOSED) {
            tile.setState(TileState.OPENED);
            numberOfOpenTiles++;
        }
        else if(tile.getState() == TileState.OPENED){
            tile.setState(TileState.CLOSED);
            numberOfOpenTiles--;
        }
    }


    public int getRowCount() { return rowCount; }

    public int getColumnCount() { return columnCount; }

    public GameState getState() { return state; }

    public Tile getTile(int row, int column) { return tiles[row][column]; }

    private boolean isSolved() { return numberOfOpenTiles == 0; }

    private int getPlayingTime() { return ((int) (System.currentTimeMillis() - startMillis)) / 1000; }

    public int getScore() { return getPlayingTime(); }
}
