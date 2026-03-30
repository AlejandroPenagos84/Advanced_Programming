package org.example.shared;

import java.io.Serializable;
import java.util.List;

public record Fighter(
        String name,
        String weight,
        List<Kimarite> techniques
) implements Serializable {}
