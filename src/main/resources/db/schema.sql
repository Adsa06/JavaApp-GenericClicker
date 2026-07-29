CREATE TABLE IF NOT EXISTS achievements (
    id TEXT PRIMARY KEY,
    creation_date TEXT DEFAULT (datetime('now', 'localtime'))
);

-- Acceder solo a la fila 1
CREATE TABLE IF NOT EXISTS stats (
    id INTEGER PRIMARY KEY CHECK (id = 1),
    actualCounter INTEGER NOT NULL DEFAULT 0,
    clicksPerSecond INTEGER NOT NULL DEFAULT 0
);

INSERT OR IGNORE INTO stats (id, actualCounter, clicksPerSecond)
VALUES (1, 0, 0);

CREATE TABLE IF NOT EXISTS upgrades (
    id TEXT PRIMARY KEY,
    level INTEGER NOT NULL DEFAULT 0
);