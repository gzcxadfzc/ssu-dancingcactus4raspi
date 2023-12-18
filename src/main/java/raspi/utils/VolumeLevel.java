package raspi.utils;

import java.util.Arrays;

enum VolumeLevel {

    LEVEL1(-80.0f, 1),
    LEVEL2(-25.0f, 2),
    LEVEL3( -10.0f, 3),
    LEVEL4(1.0f, 4),
    LEVEL5(4.0f, 5),
    LEVEL6(6.020f, 6);

    private final float dbValue;
    private final int level;

    VolumeLevel(float dbValue, int level) {
        this.dbValue = dbValue;
        this.level = level;
    }

    public static VolumeLevel getLevel(int targetLevel) {
        return Arrays.stream(VolumeLevel.values())
                .filter(volLevel -> volLevel.level == targetLevel)
                .findAny()
                .orElse(LEVEL4);
    }

    public float getDbValue() {
        return dbValue;
    }

    public VolumeLevel up() {
        int nextLevel = Math.min(LEVEL6.level, this.level + 1);
        return getLevel(nextLevel);
    }

    public VolumeLevel down() {
        int prevLevel = Math.max(LEVEL1.level, this.level - 1);
        return getLevel(prevLevel);
    }
}
