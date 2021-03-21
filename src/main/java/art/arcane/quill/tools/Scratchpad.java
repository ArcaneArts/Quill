/*
 * This file is part of Quill by Arcane Arts.
 *
 * Quill by Arcane Arts is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Quill by Arcane Arts is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License in this package for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Quill.  If not, see <https://www.gnu.org/licenses/>.
 */

package art.arcane.quill.tools;

import javax.swing.*;
import java.awt.*;

public abstract class Scratchpad {
    private JFrame f;

    public Scratchpad() {
        f = new JFrame("Scratchpad");
        f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        f.add(new ScratchpadPanel() {
            private static final long serialVersionUID = 7932047221974269087L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Scratchpad.this.paint(g, Scratchpad.this);
            }
        });
        f.pack();
        f.setSize(1000, 1000);
        f.setVisible(true);
    }

    public JFrame getFrame() {
        return f;
    }

    public int getWidth() {
        return f.getWidth();
    }

    public int getHeight() {
        return f.getHeight();
    }

    public void redraw() {
        f.repaint();
    }

    public void hide() {
        f.setVisible(false);
    }

    public abstract void paint(Graphics g, Scratchpad pad);
}
