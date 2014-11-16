// keep count of a word's spam/non-spam frequency
// this is used as the tokenFrequencie's value
public class Pair {
    private int freqSpam;
    private int freqNonSpam;

    public Pair() {
        freqSpam = 0;
        freqNonSpam = 0;
    }

    public Pair(int initSpam, int initNonSpam) {
        freqSpam = initSpam;
        freqNonSpam = initNonSpam;
    }

    public void setSpamCount(int newCount) {
        freqSpam = newCount;
    }

    public void setNonSpamCount(int newCount) {
        freqNonSpam = newCount;
    }

    public int getSpamCount() {
        return freqSpam;
    }

    public int getNonSpamCount() {
        return freqNonSpam;
    }
}