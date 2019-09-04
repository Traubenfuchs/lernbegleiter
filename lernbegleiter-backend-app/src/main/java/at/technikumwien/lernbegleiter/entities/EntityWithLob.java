package at.technikumwien.lernbegleiter.entities;

/**
 * Allows easier lob setting via {@link at.technikumwien.lernbegleiter.services.LobService)}
 *
 * @param <T>
 */
public interface EntityWithLob<T extends EntityWithLob<T>> {
    T setLob(LobEntity lobEntity);
}
