package rugby.positions;

public enum Position {
    /**
     *  PROP (8)        HOOKER (9)          PROP (10)
     *
     *      SECOND ROW (11)     SECOND ROW (12)
     *
     *                  LOCK (13)
     *
     *                  HALFBACK (7)
     *                          FIVE-EIGHTH (6)
     *
     *      CENTRE (4)                  CENTRE (3)
     *
     * WING (5)         FULLBACK (1)            WING (2)
     */

    PROP_8(8),
    PROP_10(10),
    HOOKER(9),
    SECOND_ROW_11(11),
    SECOND_ROW_12(12),
    LOCK(13),
    HALFBACK(7),
    FIVE_EIGHTH(6),
    CENTRE_4(4),
    CENTRE_3(3),
    WING_5(5),
    WING_2(2),
    FULLBACK(1);

    public final int number;

    Position(int number) {
        this.number = number;
    }
}
