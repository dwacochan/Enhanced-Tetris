package model;

import controller.Controls;

public enum ControlsSet {
    DEFAULT {
        @Override
        boolean isLeftPressed() { return Controls.left; }
        @Override
        boolean isRightPressed() { return Controls.right; }
        @Override
        boolean isDownPressed() { return Controls.down; }
        @Override
        boolean isUpPressed() { return Controls.up; }

        @Override
        void resetLeft() { Controls.left = false; }
        @Override
        void resetRight() { Controls.right = false; }
        @Override
        void resetDown() { Controls.down = false; }
        @Override
        void resetUp() { Controls.up = false; }
    },
    ALTERNATE {
        @Override
        boolean isLeftPressed() { return Controls.alt_left; }
        @Override
        boolean isRightPressed() { return Controls.alt_right; }
        @Override
        boolean isDownPressed() { return Controls.alt_down; }
        @Override
        boolean isUpPressed() { return Controls.alt_up; }

        @Override
        void resetLeft() { Controls.alt_left = false; }
        @Override
        void resetRight() { Controls.alt_right = false; }
        @Override
        void resetDown() { Controls.alt_down = false; }
        @Override
        void resetUp() { Controls.alt_up = false; }
    },
    EXTERNAL_1 {
        @Override
        boolean isLeftPressed() { return Controls.ext_1_left; }
        @Override
        boolean isRightPressed() { return Controls.ext_1_right; }
        @Override
        boolean isDownPressed() { return Controls.ext_1_down; }
        @Override
        boolean isUpPressed() { return Controls.ext_1_up; }

        @Override
        void resetLeft() { Controls.ext_1_left = false; }
        @Override
        void resetRight() { Controls.ext_1_right = false; }
        @Override
        void resetDown() { Controls.ext_1_down = false; }
        @Override
        void resetUp() { Controls.ext_1_up = false; }
    },
    EXTERNAL_2 {
        @Override
        boolean isLeftPressed() { return Controls.ext_2_left; }
        @Override
        boolean isRightPressed() { return Controls.ext_2_right; }
        @Override
        boolean isDownPressed() { return Controls.ext_2_down; }
        @Override
        boolean isUpPressed() { return Controls.ext_2_up; }

        @Override
        void resetLeft() { Controls.ext_2_left = false; }
        @Override
        void resetRight() { Controls.ext_2_right = false; }
        @Override
        void resetDown() { Controls.ext_2_down = false; }
        @Override
        void resetUp() { Controls.ext_2_up = false; }
    };


    ;

    abstract boolean isLeftPressed();
    abstract boolean isRightPressed();
    abstract boolean isDownPressed();
    abstract boolean isUpPressed();

    abstract void resetLeft();
    abstract void resetRight();
    abstract void resetDown();
    abstract void resetUp();
}

