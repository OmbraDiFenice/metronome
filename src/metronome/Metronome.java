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

import java.util.Observable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.sound.midi.MidiUnavailableException;

/**
 *
 * @author stefano
 */
public class Metronome extends Observable {

    private long bpm;
    private PercussionSound currentSound;
    private final Player playBeat;
    private final ScheduledThreadPoolExecutor threadPool;
    private ScheduledFuture task;

    public static enum Event {
        START, STOP, HALT, SOUND_CHANGE, BPM_CHANGE
    }

    public Metronome() throws MidiUnavailableException {
        bpm = 100;
        threadPool = new ScheduledThreadPoolExecutor(1);
        playBeat = new Player();
    }

    public void start() {
        task = threadPool.scheduleAtFixedRate(playBeat, 0, 1000 * 60 / bpm, TimeUnit.MILLISECONDS);
        setChanged();
        notifyObservers(Event.START);
    }

    public void halt() {
        threadPool.shutdown();
        setChanged();
        notifyObservers(Event.HALT);
    }

    public void stop() {
        task.cancel(true);
        setChanged();
        notifyObservers(Event.STOP);
    }

    public PercussionSound[] getSoundList() {
        return new PercussionSound[]{
            new PercussionSound("Claves", 75),
            new PercussionSound("Cow Bell", 56),
            new PercussionSound("High Bongo", 60),
            new PercussionSound("Low Bongo", 61),
            new PercussionSound("High Wood Block", 76),
            new PercussionSound("Low Wood Block", 77)
        };
    }

    public void setSound(PercussionSound sound) {
        setChanged();
        notifyObservers(Event.SOUND_CHANGE);
        currentSound = sound;
        playBeat.setNote(sound.getNote());
    }

    public PercussionSound getSound() {
        return currentSound;
    }

    public void setBpm(long bpm) {
        setChanged();
        notifyObservers(Event.BPM_CHANGE);
        this.bpm = bpm;
        stop();
        start();
    }

    public long getBpm() {
        return bpm;
    }
}
