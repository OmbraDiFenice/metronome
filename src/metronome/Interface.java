/* 
 * Copyright (C) 2015 Stefano Stoduto
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package metronome;

/**
 *
 * @author stefano
 */
public class Interface extends javax.swing.JFrame {

    public Interface(Metronome metronome) {
        _metronome = metronome;
        _ui = new UI(_metronome);
        init();
    }

    private void init() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Metronome");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                _metronome.halt();
                dispose();
            }
        });

        getContentPane().add(_ui.getPanel(), java.awt.BorderLayout.CENTER);

        pack();
    }

    private final Metronome _metronome;
    private final UI _ui;
}
