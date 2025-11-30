package kickstart.festival;

import java.io.Serializable;
import java.util.UUID;

import org.jmolecules.ddd.types.Identifier;

import jakarta.persistence.Embeddable;

@Embeddable
public class FestivalIdentifier implements Identifier, Serializable {

    private UUID id;

    protected FestivalIdentifier() { }

    public FestivalIdentifier(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FestivalIdentifier that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

