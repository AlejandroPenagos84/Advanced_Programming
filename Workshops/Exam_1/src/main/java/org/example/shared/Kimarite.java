package org.example.shared;

import java.io.Serializable;

public record Kimarite (String name, Integer probability) implements Serializable {
}
