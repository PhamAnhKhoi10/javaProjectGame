package utils;

public class GameConstant {
    public static class Directions {
        public static final int UP = 0;
        public static final int RIGHT = 1;
        public static final int DOWN = 2;
        public static final int LEFT = 3;
    }

    public static class mapConstants {
        public static final int TILES = 0;
        public static final int BACKGROUND_0 = 1;
        public static final int BACKGROUND_1 = 2;
        public static final int BRUSH = 3;
        public static final int GRASS_BACKGROUND_1 = 4;
        public static final int GRASS_BACKGROUND_2 = 5;
        public static final int SALT = 6;
        public static final int MY_LEVEL = 7;
    }


    public static class playerConstants {
        public static final int ATTACK = 0;
        public static final int ATTACK_2 = 1;
        public static final int ATTACK_2_NO_MOVEMENT = 2;
        public static final int ATTACK_COMBO = 3;
        public static final int ATTACK_COMBO_NO_MOVEMENT = 4;
        public static final int ATTACK_NO_MOVEMENT = 5;
        public static final int CROUCH = 6;
        public static final int CROUCH_ATTACK = 7;
        public static final int CROUCH_FULL = 8;
        public static final int CROUCH_TRANSITION = 9;
        public static final int CROUCH_WALK = 10;
        public static final int DASH = 11;
        public static final int DEATH = 12;
        public static final int DEATH_NO_MOVEMENT = 13;
        public static final int FALL = 14;
        public static final int HIT = 15;
        public static final int IDLE = 16;
        public static final int JUMP = 17;
        public static final int JUMP_FALL = 18;
        public static final int ROLL = 19;
        public static final int RUN = 20;
        public static final int SLIDE = 21;
        public static final int SLIDE_FULL = 22;
        public static final int SLIDE_TRANSITION_END = 23;
        public static final int SLIDE_TRANSITION_START = 24;
        public static final int TURN_AROUND = 25;
        public static final int WALL_CLIMB = 26;
        public static final int WALL_CLIMB_NO_MOVEMENT = 27;
        public static final int WALL_HANG = 28;
        public static final int WALL_SLIDE = 29;
    }

    public static int GetSprireAmount(int player_action) {
        switch (player_action) {
            case playerConstants.CROUCH:
            case playerConstants.CROUCH_TRANSITION:
            case playerConstants.HIT:
            case playerConstants.SLIDE_TRANSITION_END:
            case playerConstants.SLIDE_TRANSITION_START:
            case playerConstants.WALL_HANG:
                return 1;
            case playerConstants.DASH:
            case playerConstants.JUMP_FALL:
            case playerConstants.SLIDE:
                return 2;
            case playerConstants.CROUCH_FULL:
            case playerConstants.FALL:
            case playerConstants.JUMP:
            case playerConstants.TURN_AROUND:
            case playerConstants.WALL_SLIDE:
                return 3;
            case playerConstants.ATTACK:
            case playerConstants.ATTACK_NO_MOVEMENT:
            case playerConstants.CROUCH_ATTACK:
            case playerConstants.SLIDE_FULL:
                return 4;
            case playerConstants.ATTACK_2:
            case playerConstants.ATTACK_2_NO_MOVEMENT:
                return 6;
            case playerConstants.WALL_CLIMB:
            case playerConstants.WALL_CLIMB_NO_MOVEMENT:
                return 7;
            case playerConstants.CROUCH_WALK:
                return 8;
            case playerConstants.RUN:
            case playerConstants.ATTACK_COMBO:
            case playerConstants.ATTACK_COMBO_NO_MOVEMENT:
            case playerConstants.DEATH:
            case playerConstants.DEATH_NO_MOVEMENT:
            case playerConstants.IDLE:
                return 10;
            case playerConstants.ROLL:
                return 12;
        }
        return 1;
    }
}


