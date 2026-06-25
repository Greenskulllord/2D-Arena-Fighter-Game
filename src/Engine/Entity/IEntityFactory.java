package Engine.Entity;
import Engine.Core.Entity;
import Engine.Core.GameContext;

public interface IEntityFactory {
    Entity createEntity (int ID, int X, int Y, GameContext gameContext);

}
