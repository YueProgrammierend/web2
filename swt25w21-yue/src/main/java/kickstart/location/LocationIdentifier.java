package kickstart.location;

import java.util.UUID;
import org.jmolecules.ddd.types.Identifier;

public record LocationIdentifier(UUID id) implements Identifier {}

