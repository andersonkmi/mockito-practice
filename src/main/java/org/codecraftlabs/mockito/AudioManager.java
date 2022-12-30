package org.codecraftlabs.mockito;

public class AudioManager {
    private int volume = 50;
    private RINGER_MODE mode = RINGER_MODE.RINGER_MODE_SILENT;

    public RINGER_MODE getRingerMode() {
        return mode;
    }

    public int getStreamMaxVolume() {
        return volume;
    }

    public void setStreamVolume(int max) {
        volume = max;
    }

    public void makeReallyLoud() {
        if(mode == RINGER_MODE.RINGER_MODE_NORMAL) {
            setStreamVolume(100);
        }
    }
}
