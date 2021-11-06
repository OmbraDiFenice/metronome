package metronome;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

public class UI {

    public UI(Metronome metronome) {
        _metronome = metronome;

        startStopCheckBox.addActionListener((ActionEvent e) -> {
            if (startStopCheckBox.isSelected()) {
                _metronome.start();
                startStopCheckBox.setText("Stop");
            } else {
                _metronome.stop();
                startStopCheckBox.setText("Start");
            }
        });
        speedSlider.addChangeListener((ChangeEvent e) -> {
            int bpm = speedSlider.getValue();
            speedText.setText(Long.toString(bpm));

            if (!speedSlider.getValueIsAdjusting()) {
                _metronome.setBpm(bpm);
            }
        });
        speedText.addActionListener((ActionEvent e) -> {
            long bpm = Long.parseLong(speedText.getText());
            speedSlider.setValue((int) bpm);
            _metronome.setBpm(bpm);
        });
        soundComboBox.addItemListener((ItemEvent e) -> {
            PercussionSound sound = (PercussionSound) soundComboBox.getSelectedItem();
            if (sound != null) {
                _metronome.setSound(sound);
            }
        });

        initUiStatus();
    }

    private void initUiStatus() {
        soundComboBox.setModel(new DefaultComboBoxModel<>(_metronome.getSoundList()) {
        });
        soundComboBox.setSelectedItem(_metronome.getSound());
        speedSlider.setValue((int) _metronome.getBpm());
        speedText.setText(Long.toString(_metronome.getBpm()));
    }

    JPanel getPanel() {
        return panel;
    }

    private final Metronome _metronome;

    private JComboBox<PercussionSound> soundComboBox;
    private JPanel panel;
    private JTextField speedText;
    private JSlider speedSlider;
    private JCheckBox startStopCheckBox;
    private JLabel soundLabel;
    private JLabel speedLabel;
    private JLabel bpmLabel;

}
