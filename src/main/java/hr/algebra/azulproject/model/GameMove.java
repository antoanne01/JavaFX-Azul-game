package hr.algebra.azulproject.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class GameMove implements Serializable {

    @Serial
    private static final long serialVersionUID = -4846347298357583065L;
    private final String factoryTile;
    private String tile;

    public GameMove(String tile, String position, LocalDateTime localDateTime, String factoryTile) {
        this.tile = tile;
        this.position = position;
        this.localDateTime = localDateTime;
        this.factoryTile = factoryTile;
    }

    private String position;
    private LocalDateTime localDateTime;

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameMove gameMove = (GameMove) o;
        return Objects.equals(tile, gameMove.tile) && Objects.equals(position, gameMove.position)
                && localDateTime.isEqual(gameMove.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tile, position, localDateTime);
    }

    public String getFactoryTile() {
        return factoryTile;
    }
}
